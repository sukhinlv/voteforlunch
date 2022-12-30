package ru.jsft.voteforlunch.web.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.model.MealPrice;
import ru.jsft.voteforlunch.service.MealService;
import ru.jsft.voteforlunch.web.controller.dto.MealPriceRequestDto;
import ru.jsft.voteforlunch.web.controller.dto.MealPriceResponseDto;

@Component
public class MealPriceMapper implements RequestResponseMapper<MealPrice, MealPriceRequestDto, MealPriceResponseDto> {
    private final MealMapper mealMapper;

    private final MealService mealService;

    public MealPriceMapper(MealMapper mealMapper, MealService mealService) {
        this.mealMapper = mealMapper;
        this.mealService = mealService;
    }

    @Override
    public MealPrice toEntity(MealPriceRequestDto dto) {
        MealPrice mealPrice = new MealPrice();
        mealPrice.setId(dto.getId());
        mealPrice.setMeal(mealService.findById(dto.getMealId()));
        mealPrice.setPrice(dto.getPrice());
        return mealPrice;
    }

    @Override
    public MealPriceResponseDto toDto(MealPrice entity) {
        return new MealPriceResponseDto(entity.getId(), mealMapper.toDto(entity.getMeal()), entity.getPrice());
    }
}
