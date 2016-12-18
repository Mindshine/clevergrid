package com.mindshine.clevergrid.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the topic database table.
 *
 */
// @Entity
// @Table(name="topic")
// @NamedQuery(name="Topic.findAll", query="SELECT t FROM Topic t")
@Document
public class Topic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String title;

	// bi-directional many-to-one association to QuestionAndAnswer
	// @OneToMany(mappedBy="topic", cascade = CascadeType.REMOVE)
	private List<QuestionAndAnswer> qnas;

	// bi-directional many-to-one association to Game
	// @ManyToOne
	// @JoinColumn(name="idgame")
	private Game game;

	public Topic() {
	}

	public int getId() {
		return this.id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<QuestionAndAnswer> getQnas() {
		return this.qnas;
	}

	public void setQnas(List<QuestionAndAnswer> qnas) {
		this.qnas = qnas;
	}

	public QuestionAndAnswer addQna(QuestionAndAnswer qna) {
		getQnas().add(qna);
		qna.setTopic(this);

		return qna;
	}

	public QuestionAndAnswer removeQna(QuestionAndAnswer qna) {
		getQnas().remove(qna);
		qna.setTopic(null);

		return qna;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public static Builder getBuilder(String title) {
		return new Builder(title);
	}

	public static class Builder {
		Topic built;

		Builder(String title) {
			this.built = new Topic();
			this.built.title = title;
		}

		public Topic build() {
			return this.built;
		}
	}
}