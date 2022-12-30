package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.service.RestaurantService;
import ru.jsft.voteforlunch.web.controller.dto.MealPriceRequestDto;
import ru.jsft.voteforlunch.web.controller.dto.MenuRequestDto;
import ru.jsft.voteforlunch.web.controller.dto.MenuResponseDto;

import java.util.stream.Collectors;

@Component
public class MenuMapper implements RequestResponseMapper<Menu, MenuRequestDto, MenuResponseDto> {

    private final RestaurantMapper restaurantMapper;
    private final MealPriceMapper mealPriceMapper;
    private final RestaurantService restaurantService;

    public MenuMapper(RestaurantMapper restaurantMapper, MealPriceMapper mealPriceMapper, RestaurantService restaurantService) {
        this.restaurantMapper = restaurantMapper;
        this.mealPriceMapper = mealPriceMapper;
        this.restaurantService = restaurantService;
    }

    @Override
    public Menu toEntity(MenuRequestDto dto) {
        Menu menu = new Menu();
        menu.setId(dto.getId());
        menu.setDateOfMenu(dto.getDateOfMenu());
        menu.setRestaurant(restaurantService.findById(dto.getRestaurantId()));
        for (MealPriceRequestDto mealPriceDto : dto.getMealPrices()) {
            menu.addMealPrice(mealPriceMapper.toEntity(mealPriceDto));
        }
        return menu;
    }

    @Override
    public MenuResponseDto toDto(Menu entity) {
        MenuResponseDto menuDto = new MenuResponseDto(
                entity.getDateOfMenu(),
                restaurantMapper.toDto(entity.getRestaurant()),
                entity.getMealPrices().stream().map(mealPriceMapper::toDto).collect(Collectors.toSet()));
        menuDto.setId(entity.getId());
        return menuDto;
    }
}
