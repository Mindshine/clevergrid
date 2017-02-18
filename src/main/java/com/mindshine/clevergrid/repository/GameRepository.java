package com.mindshine.clevergrid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindshine.clevergrid.domain.Game;

/**
 * Spring Data MongoDB repository for the Game entity.
 */
@SuppressWarnings("unused")
public interface GameRepository extends MongoRepository<Game,String> {

}
