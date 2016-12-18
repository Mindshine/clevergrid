package com.mindshine.clevergrid.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the qna database table.
 * 
 */
@Entity
@Table(name="qna")
@NamedQuery(name="QuestionAndAnswer.findAll", query="SELECT q FROM QuestionAndAnswer q")
public class QuestionAndAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Lob
	private String answer;

	@Lob
	private String question;

	//bi-directional many-to-one association to Topic
	@ManyToOne
	@JoinColumn(name="idtopic")
	private Topic topic;

	public QuestionAndAnswer() {
	}

	public int getId() {
		return this.id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Topic getTopic() {
		return this.topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	public static Builder getBuilder(String question, String answer) {
        return new Builder(question, answer);
    }
    
    public static class Builder {
        QuestionAndAnswer built;
 
        Builder(String question, String answer) {
            built = new QuestionAndAnswer();
            built.question = question;
            built.answer = answer;
        }
 
        public QuestionAndAnswer build() {
            return built;
        }
    }
}