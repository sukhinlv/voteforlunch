package ru.jsft.voteforlunch.web.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.jsft.voteforlunch.model.VoteDistribution;
import ru.jsft.voteforlunch.service.VoteService;
import ru.jsft.voteforlunch.web.controller.dto.VoteDto;
import ru.jsft.voteforlunch.web.controller.mapper.VoteMapper;
import ru.jsft.voteforlunch.web.security.AuthorizedUser;

import java.time.LocalDate;
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
    public ResponseEntity<List<VoteDto>> getAllForUser(@AuthenticationPrincipal AuthorizedUser authUser) {
        return ResponseEntity.ok(service.findAllForUser(authUser.id()).stream()
                .map(mapper::toDto)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteDto> getVote(@PathVariable long id, @AuthenticationPrincipal AuthorizedUser authUser) {
        return ResponseEntity.ok(mapper.toDto(service.find(id, authUser.id())));
    }

    @GetMapping("/distribution")
    @ResponseStatus(HttpStatus.OK)
    public List<VoteDistribution> getVotesDistributionOnDate(
            @RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return service.getVotesDistributionOnDate(date);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<VoteDto> vote(@RequestParam("restaurantId") long restaurantId, @AuthenticationPrincipal AuthorizedUser authUser) {
        return ResponseEntity.ok(mapper.toDto(service.saveAndReturnWithDetails(restaurantId, authUser.id())));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<VoteDto> changeVote(@RequestParam("restaurantId") long restaurantId, @AuthenticationPrincipal AuthorizedUser authUser) {
        return ResponseEntity.ok(mapper.toDto(service.saveAndReturnWithDetails(restaurantId, authUser.id())));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVote(@AuthenticationPrincipal AuthorizedUser authUser) {
        service.delete(authUser.id());
    }
}
