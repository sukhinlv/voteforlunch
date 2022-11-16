package ru.jsft.voteforlunch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jsft.voteforlunch.controller.dto.VoteDto;
import ru.jsft.voteforlunch.controller.mapper.impl.VoteMapper;
import ru.jsft.voteforlunch.service.VoteService;

import javax.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<VoteDto>> getAll() {
        return ResponseEntity.ok(service.getAll().stream()
                .map(mapper::toDto)
                .sorted(Comparator.comparing(VoteDto::getVoteDate)
                        .thenComparing(VoteDto::getVoteTime)
                        .reversed())
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteDto> get(@PathVariable long id) {
        return ResponseEntity.ok(mapper.toDto(service.get(id)));
    }

    @PostMapping
    public ResponseEntity<VoteDto> create(@Valid @RequestBody VoteDto voteDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(voteDto))));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<VoteDto> update(@PathVariable long id, @Valid @RequestBody VoteDto voteDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, mapper.toEntity(voteDto))));
    }
}
