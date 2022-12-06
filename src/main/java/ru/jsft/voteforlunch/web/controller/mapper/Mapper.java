package ru.jsft.voteforlunch.web.controller.mapper;

public interface Mapper<E, D> {

    E toEntity(D dto);

    D toDto(E entity);
}
