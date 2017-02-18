package com.mindshine.clevergrid.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A QuestionAndAnswer.
 */

@Document(collection = "question_and_answer")
public class QuestionAndAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("question")
    private String question;

    @Field("answer")
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

    public QuestionAndAnswer question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public QuestionAndAnswer answer(String answer) {
        this.answer = answer;
        return this;
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
        QuestionAndAnswer questionAndAnswer = (QuestionAndAnswer) o;
        if (questionAndAnswer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, questionAndAnswer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "QuestionAndAnswer{" +
            "id=" + id +
            ", question='" + question + "'" +
            ", answer='" + answer + "'" +
            '}';
    }
}
