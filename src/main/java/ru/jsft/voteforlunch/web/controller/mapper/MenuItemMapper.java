package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.MenuItem;
import ru.jsft.voteforlunch.repository.DishRepository;
import ru.jsft.voteforlunch.web.controller.dto.MenuItemRequestDto;
import ru.jsft.voteforlunch.web.controller.dto.MenuItemResponseDto;

@Component
public class MenuItemMapper implements RequestResponseMapper<MenuItem, MenuItemRequestDto, MenuItemResponseDto> {
    private final DishMapper dishMapper;

    private final DishRepository dishRepository;

    public MenuItemMapper(DishMapper dishMapper, DishRepository dishRepository) {
        this.dishMapper = dishMapper;
        this.dishRepository = dishRepository;
    }

    @Override
    public MenuItem toEntity(MenuItemRequestDto dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(dto.getId());
        menuItem.setDish(dishRepository.getReferenceById(dto.getDishId()));
        menuItem.setPrice(dto.getPrice());
        return menuItem;
    }

    @Override
    public MenuItemResponseDto toDto(MenuItem entity) {
        return new MenuItemResponseDto(entity.getId(), dishMapper.toDto(entity.getDish()), entity.getPrice());
    }
}
