package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.MenuItem;
import ru.jsft.voteforlunch.repository.MealRepository;
import ru.jsft.voteforlunch.web.controller.dto.MenuItemRequestDto;
import ru.jsft.voteforlunch.web.controller.dto.MenuItemResponseDto;

@Component
public class MenuItemMapper implements RequestResponseMapper<MenuItem, MenuItemRequestDto, MenuItemResponseDto> {
    private final MealMapper mealMapper;

    private final MealRepository mealRepository;

    public MenuItemMapper(MealMapper mealMapper, MealRepository mealRepository) {
        this.mealMapper = mealMapper;
        this.mealRepository = mealRepository;
    }

    @Override
    public MenuItem toEntity(MenuItemRequestDto dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(dto.getId());
        menuItem.setMeal(mealRepository.getReferenceById(dto.getMealId()));
        menuItem.setPrice(dto.getPrice());
        return menuItem;
    }

    @Override
    public MenuItemResponseDto toDto(MenuItem entity) {
        return new MenuItemResponseDto(entity.getId(), mealMapper.toDto(entity.getMeal()), entity.getPrice());
    }
}
