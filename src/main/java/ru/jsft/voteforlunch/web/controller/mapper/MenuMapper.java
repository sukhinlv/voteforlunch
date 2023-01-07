package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.service.RestaurantService;
import ru.jsft.voteforlunch.web.controller.dto.MenuItemRequestDto;
import ru.jsft.voteforlunch.web.controller.dto.MenuRequestDto;
import ru.jsft.voteforlunch.web.controller.dto.MenuResponseDto;

import java.util.stream.Collectors;

@Component
public class MenuMapper implements RequestResponseMapper<Menu, MenuRequestDto, MenuResponseDto> {

    private final RestaurantMapper restaurantMapper;
    private final MenuItemMapper menuItemMapper;
    private final RestaurantService restaurantService;

    public MenuMapper(RestaurantMapper restaurantMapper, MenuItemMapper menuItemMapper, RestaurantService restaurantService) {
        this.restaurantMapper = restaurantMapper;
        this.menuItemMapper = menuItemMapper;
        this.restaurantService = restaurantService;
    }

    @Override
    public Menu toEntity(MenuRequestDto dto) {
        Menu menu = new Menu();
        menu.setId(dto.getId());
        menu.setDateOfMenu(dto.getDateOfMenu());
        menu.setRestaurant(restaurantService.findById(dto.getRestaurantId()));
        for (MenuItemRequestDto menuItemDto : dto.getMenuItems()) {
            menu.addMenuItem(menuItemMapper.toEntity(menuItemDto));
        }
        return menu;
    }

    @Override
    public MenuResponseDto toDto(Menu entity) {
        return new MenuResponseDto(
                entity.getId(),
                entity.getDateOfMenu(),
                restaurantMapper.toDto(entity.getRestaurant()),
                entity.getMenuItems().stream().map(menuItemMapper::toDto).collect(Collectors.toSet()));
    }
}
