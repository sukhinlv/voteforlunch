package ru.jsft.voteforlunch.web.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.jsft.voteforlunch.model.Vote;
import ru.jsft.voteforlunch.model.VoteDistribution;
import ru.jsft.voteforlunch.service.VoteService;
import ru.jsft.voteforlunch.web.controller.dto.VoteDto;
import ru.jsft.voteforlunch.web.controller.mapper.VoteMapper;
import ru.jsft.voteforlunch.web.security.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    public static final String REST_URL = "/api/v1/votes";

    private final VoteService service;
    private final VoteMapper mapper;

    public VoteController(VoteService service, VoteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<VoteDto>> getAllForUser() {
        return ResponseEntity.ok(service.findAllForUser(SecurityUtil.authId()).stream()
                .map(mapper::toDto)
                .sorted(Comparator.comparing(VoteDto::getVoteDate)
                        .thenComparing(VoteDto::getVoteTime)
                        .reversed())
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteDto> getVote(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.find(id, SecurityUtil.authId())));
    }

    @GetMapping("/distribution")
    @ResponseStatus(HttpStatus.OK)
    public List<VoteDistribution> getVotesDistributionOnDate(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return service.getVotesDistributionOnDate(date);
    }

    @PostMapping(path = "/{restaurantId}")
    public ResponseEntity<VoteDto> voteForRestaurant(@PathVariable long restaurantId) {
        Vote savedEntity = service.saveAndReturnWithDetails(restaurantId, SecurityUtil.authId());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(savedEntity.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(mapper.toDto(savedEntity));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVote() {
        service.delete(SecurityUtil.authId());
    }
}
