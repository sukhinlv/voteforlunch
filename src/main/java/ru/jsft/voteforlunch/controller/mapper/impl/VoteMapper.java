package ru.jsft.voteforlunch.controller.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.controller.dto.VoteDto;
import ru.jsft.voteforlunch.model.Vote;
import ru.jsft.voteforlunch.repository.RestaurantRepository;
import ru.jsft.voteforlunch.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Objects;

@Component
public class VoteMapper extends AbstractMapper<Vote, VoteDto>{

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    VoteMapper(RestaurantRepository restaurantRepository, UserRepository userRepository) {
        super(Vote.class, VoteDto.class);
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Vote.class, VoteDto.class)
                .addMappings(m -> {
                    m.skip(VoteDto::setRestaurantId);
                    m.skip(VoteDto::setUserId);
                }).setPostConverter(toDtoConverter());

        mapper.createTypeMap(VoteDto.class, Vote.class)
                .addMappings(m -> {
                    m.skip(Vote::setRestaurant);
                    m.skip(Vote::setUser);
                }).setPostConverter(toEntityConverter());
    }

    @Override
    public void mapSpecificFields(Vote source, VoteDto destination) {
        destination.setRestaurantId(Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getRestaurant().getId());
        destination.setUserId(Objects.isNull(source) || Objects.isNull(source.getId()) ? null : source.getUser().getId());
    }

    @Override
    void mapSpecificFields(VoteDto source, Vote destination) {
        destination.setRestaurant(restaurantRepository.findById(source.getRestaurantId()).orElse(null));
        destination.setUser(userRepository.findById(source.getUserId()).orElse(null));
    }
}
