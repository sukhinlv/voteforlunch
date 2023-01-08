package ru.jsft.voteforlunch.validation;

import lombok.experimental.UtilityClass;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.lang.NonNull;
import ru.jsft.voteforlunch.error.IllegalRequestDataException;
import ru.jsft.voteforlunch.model.AbstractEntity;

import java.util.Optional;

@UtilityClass
public class ValidationUtils {

    public static void checkNew(@NonNull AbstractEntity bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id = null)");
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> T checkEntityWasFound(@NonNull Optional<T> obj, long id, Class<T> clazz) {
        if (obj.isEmpty()) {
            throw new IllegalRequestDataException(String.format("%s with id = %d not found", clazz.getSimpleName(), id));
        }
        return obj.get();
    }

    public static <T> void checkEntityExist(boolean exist, long id, Class<T> clazz) {
        if (!exist) {
            throw new IllegalRequestDataException(String.format("%s with id = %d not found", clazz.getSimpleName(), id));
        }
    }

    @NonNull
    public static Throwable getRootCause(@NonNull Throwable t) {
        // https://stackoverflow.com/a/65442410/548473
        Throwable rootCause = NestedExceptionUtils.getRootCause(t);
        return rootCause != null ? rootCause : t;
    }
}
