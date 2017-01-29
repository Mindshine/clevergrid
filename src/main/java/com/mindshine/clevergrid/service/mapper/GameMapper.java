package com.mindshine.clevergrid.service.mapper;

import com.mindshine.clevergrid.domain.*;
import com.mindshine.clevergrid.service.dto.GameDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Game and its DTO GameDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GameMapper {

    GameDTO gameToGameDTO(Game game);

    List<GameDTO> gamesToGameDTOs(List<Game> games);

    Game gameDTOToGame(GameDTO gameDTO);

    List<Game> gameDTOsToGames(List<GameDTO> gameDTOs);
}
