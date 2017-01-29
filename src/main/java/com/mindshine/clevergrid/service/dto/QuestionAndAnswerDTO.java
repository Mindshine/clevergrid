package com.mindshine.clevergrid.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the QuestionAndAnswer entity.
 */
public class QuestionAndAnswerDTO implements Serializable {

    private String id;

    private String question;

    private String answer;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        QuestionAndAnswerDTO questionAndAnswerDTO = (QuestionAndAnswerDTO) o;

        if ( ! Objects.equals(id, questionAndAnswerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QuestionAndAnswerDTO{" +
            "id=" + id +
            ", question='" + question + "'" +
            ", answer='" + answer + "'" +
            '}';
    }
}
