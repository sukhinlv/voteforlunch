package ru.jsft.voteforlunch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jsft.voteforlunch.controller.dto.VoteDto;
import ru.jsft.voteforlunch.controller.mapper.VoteMapper;
import ru.jsft.voteforlunch.model.Vote;
import ru.jsft.voteforlunch.service.VoteService;
import ru.jsft.voteforlunch.web.security.SecurityUtil;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("api/v1/votes")
public class VoteController {

    private final VoteService service;

    private final VoteMapper mapper;

    public VoteController(VoteService service, VoteMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<VoteDto>> getAll() {
        return ResponseEntity.ok(service.findAll().stream()
                .map(mapper::toDto)
                .sorted(Comparator.comparing(VoteDto::getVoteDate)
                        .thenComparing(VoteDto::getVoteTime)
                        .reversed())
                .toList()
        );
    }

    @GetMapping
    public ResponseEntity<List<VoteDto>> getAllForUser() {
        return ResponseEntity.ok(service.findAllForUser(SecurityUtil.authenticatedUser.getId()).stream()
                .map(mapper::toDto)
                .sorted(Comparator.comparing(VoteDto::getVoteDate)
                        .thenComparing(VoteDto::getVoteTime)
                        .reversed())
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.find(id, SecurityUtil.authenticatedUser.getId())));
    }

    @PostMapping(path = "/{restaurantId}")
    public ResponseEntity<VoteDto> save(@PathVariable long restaurantId) {
        Vote entity = service.save(restaurantId, SecurityUtil.authenticatedUser.getId());
        entity = service.find(entity.getId(), SecurityUtil.authenticatedUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(entity));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        service.delete(SecurityUtil.authenticatedUser.getId());
    }
}
