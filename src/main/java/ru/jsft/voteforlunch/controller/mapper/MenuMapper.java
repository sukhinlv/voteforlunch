package ru.jsft.voteforlunch.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.controller.dto.MenuDto;
import ru.jsft.voteforlunch.model.Menu;

import java.util.stream.Collectors;

@Component
public class MenuMapper implements Mapper<Menu, MenuDto> {

    private final RestaurantMapper restaurantMapper;
    private final MealPriceMapper mealPriceMapper;

    public MenuMapper(RestaurantMapper restaurantMapper, MealPriceMapper mealPriceMapper) {
        this.restaurantMapper = restaurantMapper;
        this.mealPriceMapper = mealPriceMapper;
    }

    @Override
    public Menu toEntity(MenuDto dto) {
        Menu menu = new Menu();
        menu.setId(dto.getId());
        menu.setDateOfMenu(dto.getDateOfMenu());
        menu.setRestaurant(restaurantMapper.toEntity(dto.getRestaurant()));
        menu.setMealPrice(dto.getMealPrice().stream().map(mealPriceMapper::toEntity).collect(Collectors.toSet()));
        return menu;
    }

    @Override
    public MenuDto toDto(Menu entity) {
        MenuDto menuDto = new MenuDto();
        menuDto.setId(entity.getId());
        menuDto.setDateOfMenu(entity.getDateOfMenu());
        menuDto.setRestaurant(restaurantMapper.toDto(entity.getRestaurant()));
        menuDto.setMealPrice(entity.getMealPrice().stream().map(mealPriceMapper::toDto).collect(Collectors.toSet()));
        return menuDto;
    }
}
