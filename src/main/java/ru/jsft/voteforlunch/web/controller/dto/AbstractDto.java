package ru.jsft.voteforlunch.web.controller.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class AbstractDto {
    protected Long id;

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}
