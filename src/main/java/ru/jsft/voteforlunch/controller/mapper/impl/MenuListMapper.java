package ru.jsft.voteforlunch.controller.mapper.impl;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.controller.dto.MenuListDto;
import ru.jsft.voteforlunch.controller.mapper.Mapper;
import ru.jsft.voteforlunch.model.Menu;

@Component
public class MenuListMapper implements Mapper<Menu, MenuListDto> {

    @Override
    public Menu toEntity(MenuListDto dto) {
        throw new UnsupportedOperationException("Convert MenuListDto to Menu ia not supported");
    }

    @Override
    public MenuListDto toDto(Menu entity) {
        MenuListDto menuListDto = new MenuListDto();
        menuListDto.setId(entity.getId());
        menuListDto.setDateOfMenu(entity.getDateOfMenu());
        menuListDto.setRestaurantId(entity.getRestaurant().getId());
        menuListDto.setRestaurantName(entity.getRestaurant().getName());
        return menuListDto;
    }
}
