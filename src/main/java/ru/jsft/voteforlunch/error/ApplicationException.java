package ru.jsft.voteforlunch.error;

import lombok.Getter;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class ApplicationException extends ResponseStatusException {
    private final ErrorAttributeOptions options;

    public ApplicationException(HttpStatus status, String message, ErrorAttributeOptions options) {
        super(status, message);
        this.options = options;
    }

    @Override
    public String getMessage() {
        return getReason();
    }
}
