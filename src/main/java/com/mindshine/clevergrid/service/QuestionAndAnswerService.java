package com.mindshine.clevergrid.service;

import com.mindshine.clevergrid.service.dto.QuestionAndAnswerDTO;
import java.util.List;

/**
 * Service Interface for managing QuestionAndAnswer.
 */
public interface QuestionAndAnswerService {

    /**
     * Save a questionAndAnswer.
     *
     * @param questionAndAnswerDTO the entity to save
     * @return the persisted entity
     */
    QuestionAndAnswerDTO save(QuestionAndAnswerDTO questionAndAnswerDTO);

    /**
     *  Get all the questionAndAnswers.
     *  
     *  @return the list of entities
     */
    List<QuestionAndAnswerDTO> findAll();

    /**
     *  Get the "id" questionAndAnswer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    QuestionAndAnswerDTO findOne(String id);

    /**
     *  Delete the "id" questionAndAnswer.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
