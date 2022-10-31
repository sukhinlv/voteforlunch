package ru.jsft.voteforlunch.basemodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.lang.Nullable;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Optional;

@MappedSuperclass
public abstract class BaseEntity<Id extends Serializable> extends AbstractPersistable<Id> {

    @Version
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long version;

    public @NotNull Optional<Long> getVersion() {
        return Optional.ofNullable(version);
    }

    protected void setVersion(@Nullable Long version) {
        this.version = version;
    }
}
