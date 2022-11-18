package ru.jsft.voteforlunch.controller.mapper;

import org.springframework.stereotype.Component;
import ru.jsft.voteforlunch.controller.dto.VoteDto;
import ru.jsft.voteforlunch.model.Vote;

@Component
public class VoteMapper implements Mapper<Vote, VoteDto> {

    private final RestaurantMapper restaurantMapper;

    public VoteMapper(RestaurantMapper restaurantMapper) {
        this.restaurantMapper = restaurantMapper;
    }

    @Override
    public Vote toEntity(VoteDto dto) {
        Vote vote = new Vote();
        vote.setId(dto.getId());
        vote.setVoteDate(dto.getVoteDate());
        vote.setVoteTime(dto.getVoteTime());
        vote.setRestaurant(restaurantMapper.toEntity(dto.getRestaurant()));
        return vote;
    }

    @Override
    public VoteDto toDto(Vote entity) {
        VoteDto voteDto = new VoteDto();
        voteDto.setId(entity.getId());
        voteDto.setVoteDate(entity.getVoteDate());
        voteDto.setVoteTime(entity.getVoteTime());
        voteDto.setRestaurant(restaurantMapper.toDto(entity.getRestaurant()));
        return voteDto;
    }
}
