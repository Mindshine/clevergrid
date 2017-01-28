package com.mindshine.clevergrid.repo;

import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mindshine.clevergrid.model.Game;
import com.mindshine.clevergrid.model.GameTag;
import com.mindshine.clevergrid.model.Tag;

@RepositoryRestResource
public interface GameTagRepository extends PagingAndSortingRepository<GameTag, ObjectId> {

	Iterable<GameTag> findByTagTagNameLike(@Param("q") String q);

	Iterable<Game> findGameByTagTagNameLike(@Param("q") String q);

	Iterable<GameTag> findByGameTitleLike(@Param("q") String q);

	Iterable<Tag> findTagByGameTitleLike(@Param("q") String q);
}
