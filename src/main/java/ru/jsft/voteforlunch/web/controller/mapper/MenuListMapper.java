package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.Menu;
import ru.jsft.voteforlunch.web.controller.dto.MenuListDto;

@Component
public class MenuListMapper implements Mapper<Menu, MenuListDto> {

    @Override
    public Menu toEntity(MenuListDto dto) {
        throw new UnsupportedOperationException("Convert MenuListDto to Menu is not supported");
    }

    @Override
    public MenuListDto toDto(Menu entity) {
        return new MenuListDto(
                entity.getId(),
                entity.getDateOfMenu(),
                entity.getRestaurant().getId(),
                entity.getRestaurant().getName()
        );
    }
}
