package com.mindshine.clevergrid.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the qna database table.
 *
 */
// @Entity
// @Table(name="qna")
// @NamedQuery(name="QuestionAndAnswer.findAll", query="SELECT q FROM
// QuestionAndAnswer q")
@Document
public class QuestionAndAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy=GenerationType.AUTO)
	private ObjectId id;

	// @Lob
	private String answer;

	// @Lob
	private String question;

	// bi-directional many-to-one association to Topic
	// @ManyToOne
	// @JoinColumn(name="idtopic")
	@DBRef
	private Topic topic;

	public QuestionAndAnswer() {
	}

	public ObjectId getId() {
		return this.id;
	}

	public void setId(ObjectId id) {
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
			this.built = new QuestionAndAnswer();
			this.built.question = question;
			this.built.answer = answer;
		}

		public QuestionAndAnswer build() {
			return this.built;
		}
	}
}