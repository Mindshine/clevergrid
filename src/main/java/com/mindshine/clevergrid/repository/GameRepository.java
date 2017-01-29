package com.mindshine.clevergrid.repository;

import com.mindshine.clevergrid.domain.Game;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Game entity.
 */
@SuppressWarnings("unused")
public interface GameRepository extends MongoRepository<Game,String> {

}
