package ru.jsft.voteforlunch.web.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class AbstractDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    protected Long id;

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}
