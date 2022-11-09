package ru.jsft.voteforlunch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.jsft.voteforlunch.controller.dto.VoteDto;
import ru.jsft.voteforlunch.controller.mapper.impl.VoteMapper;
import ru.jsft.voteforlunch.model.Vote;
import ru.jsft.voteforlunch.service.VoteService;

import javax.validation.Valid;
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
    public List<Vote> getAll() {
        // TODO results not converted to DTO
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoteDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.get(id)));
    }

    @PostMapping
    public ResponseEntity<VoteDto> create(@Valid @RequestBody VoteDto voteDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(service.create(mapper.toEntity(voteDto))));
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<VoteDto> update(@PathVariable Long id, @Valid @RequestBody VoteDto voteDto) {
        return ResponseEntity.ok(mapper.toDto(service.update(id, mapper.toEntity(voteDto))));
    }
}
