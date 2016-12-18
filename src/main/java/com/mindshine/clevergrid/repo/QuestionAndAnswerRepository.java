package com.mindshine.clevergrid.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mindshine.clevergrid.model.QuestionAndAnswer;

@RepositoryRestResource
public interface QuestionAndAnswerRepository extends CrudRepository<QuestionAndAnswer, Integer> {

	Iterable<QuestionAndAnswer> findByQuestionLike(@Param("q")String q);
	Iterable<QuestionAndAnswer> findByAnswerLike(@Param("q")String q);
}
