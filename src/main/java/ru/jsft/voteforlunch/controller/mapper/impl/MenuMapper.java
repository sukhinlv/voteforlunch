package ru.jsft.voteforlunch.controller.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.controller.dto.MenuDto;
import ru.jsft.voteforlunch.model.Menu;

@Component
public class MenuMapper extends AbstractMapper<Menu, MenuDto>{

    @Autowired
    MenuMapper() {
        super(Menu.class, MenuDto.class);
    }
}
