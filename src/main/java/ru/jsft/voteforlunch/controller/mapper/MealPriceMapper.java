package ru.jsft.voteforlunch.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.controller.dto.MealPriceDto;
import ru.jsft.voteforlunch.model.MealPrice;
import ru.jsft.voteforlunch.service.MealService;

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
        MealPriceDto mealPriceDto = new MealPriceDto();
        mealPriceDto.setId(entity.getId());
        mealPriceDto.setMeal(mealMapper.toDto(entity.getMeal()));
        mealPriceDto.setPrice(entity.getPrice());
        return mealPriceDto;
    }
}
