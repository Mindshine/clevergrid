package com.mindshine.clevergrid.service.impl;

import com.mindshine.clevergrid.service.QuestionAndAnswerService;
import com.mindshine.clevergrid.domain.QuestionAndAnswer;
import com.mindshine.clevergrid.repository.QuestionAndAnswerRepository;
import com.mindshine.clevergrid.service.dto.QuestionAndAnswerDTO;
import com.mindshine.clevergrid.service.mapper.QuestionAndAnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing QuestionAndAnswer.
 */
@Service
public class QuestionAndAnswerServiceImpl implements QuestionAndAnswerService{

    private final Logger log = LoggerFactory.getLogger(QuestionAndAnswerServiceImpl.class);
    
    @Inject
    private QuestionAndAnswerRepository questionAndAnswerRepository;

    @Inject
    private QuestionAndAnswerMapper questionAndAnswerMapper;

    /**
     * Save a questionAndAnswer.
     *
     * @param questionAndAnswerDTO the entity to save
     * @return the persisted entity
     */
    public QuestionAndAnswerDTO save(QuestionAndAnswerDTO questionAndAnswerDTO) {
        log.debug("Request to save QuestionAndAnswer : {}", questionAndAnswerDTO);
        QuestionAndAnswer questionAndAnswer = questionAndAnswerMapper.questionAndAnswerDTOToQuestionAndAnswer(questionAndAnswerDTO);
        questionAndAnswer = questionAndAnswerRepository.save(questionAndAnswer);
        QuestionAndAnswerDTO result = questionAndAnswerMapper.questionAndAnswerToQuestionAndAnswerDTO(questionAndAnswer);
        return result;
    }

    /**
     *  Get all the questionAndAnswers.
     *  
     *  @return the list of entities
     */
    public List<QuestionAndAnswerDTO> findAll() {
        log.debug("Request to get all QuestionAndAnswers");
        List<QuestionAndAnswerDTO> result = questionAndAnswerRepository.findAll().stream()
            .map(questionAndAnswerMapper::questionAndAnswerToQuestionAndAnswerDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one questionAndAnswer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public QuestionAndAnswerDTO findOne(String id) {
        log.debug("Request to get QuestionAndAnswer : {}", id);
        QuestionAndAnswer questionAndAnswer = questionAndAnswerRepository.findOne(id);
        QuestionAndAnswerDTO questionAndAnswerDTO = questionAndAnswerMapper.questionAndAnswerToQuestionAndAnswerDTO(questionAndAnswer);
        return questionAndAnswerDTO;
    }

    /**
     *  Delete the  questionAndAnswer by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete QuestionAndAnswer : {}", id);
        questionAndAnswerRepository.delete(id);
    }
}
