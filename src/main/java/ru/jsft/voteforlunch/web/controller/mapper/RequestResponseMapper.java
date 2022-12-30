package ru.jsft.voteforlunch.web.controller.mapper;

public interface RequestResponseMapper<E, RequestType, ResponseType> {

    E toEntity(RequestType dto);

    ResponseType toDto(E entity);
}
