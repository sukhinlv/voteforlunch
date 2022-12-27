package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.MealPrice;
import ru.jsft.voteforlunch.service.MealService;
import ru.jsft.voteforlunch.web.controller.dto.MealPriceDto;

@Component
public class MealPriceMapper implements Mapper<MealPrice, MealPriceDto> {

    private final MealMapper mealMapper;

    private final MealService mealService;

    public MealPriceMapper(MealMapper mealMapper, MealService mealService) {
        this.mealMapper = mealMapper;
        this.mealService = mealService;
    }

    @Override
    public MealPrice toEntity(MealPriceDto dto) {
        MealPrice mealPrice = new MealPrice();
        mealPrice.setId(dto.getId());
        mealPrice.setMeal(mealService.findById(dto.getMeal().getId()));
        mealPrice.setPrice(dto.getPrice());
        return mealPrice;
    }

    @Override
    public MealPriceDto toDto(MealPrice entity) {
        MealPriceDto mealPriceDto = new MealPriceDto(mealMapper.toDto(entity.getMeal()), entity.getPrice());
        mealPriceDto.setId(entity.getId());
        return mealPriceDto;
    }
}
