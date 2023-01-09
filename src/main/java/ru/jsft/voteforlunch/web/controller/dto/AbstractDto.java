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

    public boolean isNew() {
        return id == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

/*
* Mind that you have to add hand-made AllArgsConstructor to all inheritors that use @Value
* because otherwise you can get deserialization error "Cannot construct instance of..."
*/
}
