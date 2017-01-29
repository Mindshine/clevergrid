package com.mindshine.clevergrid.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindshine.clevergrid.service.QuestionAndAnswerService;
import com.mindshine.clevergrid.web.rest.util.HeaderUtil;
import com.mindshine.clevergrid.service.dto.QuestionAndAnswerDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing QuestionAndAnswer.
 */
@RestController
@RequestMapping("/api")
public class QuestionAndAnswerResource {

    private final Logger log = LoggerFactory.getLogger(QuestionAndAnswerResource.class);
        
    @Inject
    private QuestionAndAnswerService questionAndAnswerService;

    /**
     * POST  /question-and-answers : Create a new questionAndAnswer.
     *
     * @param questionAndAnswerDTO the questionAndAnswerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new questionAndAnswerDTO, or with status 400 (Bad Request) if the questionAndAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/question-and-answers")
    @Timed
    public ResponseEntity<QuestionAndAnswerDTO> createQuestionAndAnswer(@RequestBody QuestionAndAnswerDTO questionAndAnswerDTO) throws URISyntaxException {
        log.debug("REST request to save QuestionAndAnswer : {}", questionAndAnswerDTO);
        if (questionAndAnswerDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("questionAndAnswer", "idexists", "A new questionAndAnswer cannot already have an ID")).body(null);
        }
        QuestionAndAnswerDTO result = questionAndAnswerService.save(questionAndAnswerDTO);
        return ResponseEntity.created(new URI("/api/question-and-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("questionAndAnswer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /question-and-answers : Updates an existing questionAndAnswer.
     *
     * @param questionAndAnswerDTO the questionAndAnswerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated questionAndAnswerDTO,
     * or with status 400 (Bad Request) if the questionAndAnswerDTO is not valid,
     * or with status 500 (Internal Server Error) if the questionAndAnswerDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/question-and-answers")
    @Timed
    public ResponseEntity<QuestionAndAnswerDTO> updateQuestionAndAnswer(@RequestBody QuestionAndAnswerDTO questionAndAnswerDTO) throws URISyntaxException {
        log.debug("REST request to update QuestionAndAnswer : {}", questionAndAnswerDTO);
        if (questionAndAnswerDTO.getId() == null) {
            return createQuestionAndAnswer(questionAndAnswerDTO);
        }
        QuestionAndAnswerDTO result = questionAndAnswerService.save(questionAndAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("questionAndAnswer", questionAndAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /question-and-answers : get all the questionAndAnswers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of questionAndAnswers in body
     */
    @GetMapping("/question-and-answers")
    @Timed
    public List<QuestionAndAnswerDTO> getAllQuestionAndAnswers() {
        log.debug("REST request to get all QuestionAndAnswers");
        return questionAndAnswerService.findAll();
    }

    /**
     * GET  /question-and-answers/:id : get the "id" questionAndAnswer.
     *
     * @param id the id of the questionAndAnswerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the questionAndAnswerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/question-and-answers/{id}")
    @Timed
    public ResponseEntity<QuestionAndAnswerDTO> getQuestionAndAnswer(@PathVariable String id) {
        log.debug("REST request to get QuestionAndAnswer : {}", id);
        QuestionAndAnswerDTO questionAndAnswerDTO = questionAndAnswerService.findOne(id);
        return Optional.ofNullable(questionAndAnswerDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /question-and-answers/:id : delete the "id" questionAndAnswer.
     *
     * @param id the id of the questionAndAnswerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/question-and-answers/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuestionAndAnswer(@PathVariable String id) {
        log.debug("REST request to delete QuestionAndAnswer : {}", id);
        questionAndAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("questionAndAnswer", id.toString())).build();
    }

}
