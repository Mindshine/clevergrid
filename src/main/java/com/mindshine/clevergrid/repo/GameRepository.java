package com.mindshine.clevergrid.repo;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mindshine.clevergrid.model.Game;

@RepositoryRestResource
public interface GameRepository extends PagingAndSortingRepository<Game, ObjectId> {

	Iterable<Game> findByTitleLike(@Param("q") String q);
}
