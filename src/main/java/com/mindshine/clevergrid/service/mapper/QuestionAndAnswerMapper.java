package com.mindshine.clevergrid.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.mindshine.clevergrid.domain.QuestionAndAnswer;
import com.mindshine.clevergrid.service.dto.QuestionAndAnswerDTO;

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
