package com.mindshine.clevergrid.service.mapper;

import com.mindshine.clevergrid.domain.*;
import com.mindshine.clevergrid.service.dto.QuestionAndAnswerDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity QuestionAndAnswer and its DTO QuestionAndAnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface QuestionAndAnswerMapper {

    QuestionAndAnswerDTO questionAndAnswerToQuestionAndAnswerDTO(QuestionAndAnswer questionAndAnswer);

    List<QuestionAndAnswerDTO> questionAndAnswersToQuestionAndAnswerDTOs(List<QuestionAndAnswer> questionAndAnswers);

    QuestionAndAnswer questionAndAnswerDTOToQuestionAndAnswer(QuestionAndAnswerDTO questionAndAnswerDTO);

    List<QuestionAndAnswer> questionAndAnswerDTOsToQuestionAndAnswers(List<QuestionAndAnswerDTO> questionAndAnswerDTOs);
}
