package com.mindshine.clevergrid.repository;

import com.mindshine.clevergrid.domain.QuestionAndAnswer;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the QuestionAndAnswer entity.
 */
@SuppressWarnings("unused")
public interface QuestionAndAnswerRepository extends MongoRepository<QuestionAndAnswer,String> {

}
