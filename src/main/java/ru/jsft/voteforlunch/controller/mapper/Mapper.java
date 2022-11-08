package ru.jsft.voteforlunch.controller.mapper;

import ru.jsft.voteforlunch.controller.dto.AbstractDto;
import ru.jsft.voteforlunch.model.AbstractEntity;

public interface Mapper<E extends AbstractEntity, D extends AbstractDto> {

    E toEntity(D dto);

    D toDto(E entity);
}
