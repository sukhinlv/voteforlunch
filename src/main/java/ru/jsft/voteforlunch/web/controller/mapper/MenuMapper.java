package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.service.RestaurantService;
import ru.jsft.voteforlunch.web.controller.dto.MealPriceDto;
import ru.jsft.voteforlunch.web.controller.dto.MenuDto;

import java.util.stream.Collectors;

@Component
public class MenuMapper implements Mapper<Menu, MenuDto> {

    private final RestaurantMapper restaurantMapper;
    private final MealPriceMapper mealPriceMapper;
    private final RestaurantService restaurantService;

    public MenuMapper(RestaurantMapper restaurantMapper, MealPriceMapper mealPriceMapper, RestaurantService restaurantService) {
        this.restaurantMapper = restaurantMapper;
        this.mealPriceMapper = mealPriceMapper;
        this.restaurantService = restaurantService;
    }

    @Override
    public Menu toEntity(MenuDto dto) {
        Menu menu = new Menu();
        menu.setId(dto.getId());
        menu.setDateOfMenu(dto.getDateOfMenu());
        menu.setRestaurant(restaurantService.findById(dto.getRestaurant().getId()));
        for (MealPriceDto mealPriceDto : dto.getMealPrice()) {
            menu.addMealPrice(mealPriceMapper.toEntity(mealPriceDto));
        }
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
