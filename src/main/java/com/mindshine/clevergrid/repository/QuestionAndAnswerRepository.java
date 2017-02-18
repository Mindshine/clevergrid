package com.mindshine.clevergrid.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindshine.clevergrid.domain.QuestionAndAnswer;

/**
 * Spring Data MongoDB repository for the QuestionAndAnswer entity.
 */
@SuppressWarnings("unused")
public interface QuestionAndAnswerRepository extends MongoRepository<QuestionAndAnswer,String> {

}
