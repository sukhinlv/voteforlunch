package ru.jsft.voteforlunch.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.jsft.voteforlunch.AbstractSpringBootTest;
import ru.jsft.voteforlunch.model.Menu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MenuServiceCacheTest extends AbstractSpringBootTest {

    @Autowired
    private MenuService underTest;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void shouldBeCached_whenFindByIdWithProps() {
        Menu menu = underTest.findByIdWithProps(1L);

        assertThat(menu).usingRecursiveComparison().isEqualTo(getCachedMenuById(1L).orElseThrow());
        assertThat(getCachedMenuById(2L)).isEqualTo(Optional.empty());
    }

    @Test
    void shouldBeCached_whenFindByDateWithProps() {
        List<Menu> menus = underTest.findByDateWithProps(LocalDate.now().minusDays(1));

        assertThat(menus).usingRecursiveComparison().isEqualTo(getCachedMenusByDate(LocalDate.now().minusDays(1)).orElseThrow());
        assertThat(getCachedMenusByDate(LocalDate.now().minusDays(2))).isEqualTo(Optional.empty());
    }

    private Optional<Menu> getCachedMenuById(long id) {
        return Optional.ofNullable(cacheManager.getCache("menu")).map(cache -> cache.get(id, Menu.class));
    }

    private Optional<List<Menu>> getCachedMenusByDate(LocalDate date) {
        return Optional.ofNullable(cacheManager.getCache("menu")).map(cache -> cache.get(date, ArrayList.class));
    }
}
