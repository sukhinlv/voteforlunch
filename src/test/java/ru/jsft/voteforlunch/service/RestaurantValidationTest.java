package ru.jsft.voteforlunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.jsft.voteforlunch.AbstractSpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

// TODO should be removed
public class RestaurantValidationTest extends AbstractSpringBootTest {

    @Autowired
    private RestaurantService serviceUnderTest;

    @Test
    public void testNotNull() {
        assertThatThrownBy(() -> serviceUnderTest.create(null))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
