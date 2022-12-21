package ru.jsft.voteforlunch.utils;

import org.assertj.core.matcher.AssertionMatcher;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StringToListMatcher extends AssertionMatcher<String> {

    private final String delimiter;
    private final List<String> expectedList;

    public StringToListMatcher(@NotNull String delimiter, @NotNull List<String> expectedList) {
        this.delimiter = delimiter;
        this.expectedList = expectedList;
    }

    @Override
    public void assertion(String string) throws AssertionError {
        List<String> actualList = Arrays.stream(string.split(delimiter)).toList();
        assertThat(actualList).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedList);
    }
}
