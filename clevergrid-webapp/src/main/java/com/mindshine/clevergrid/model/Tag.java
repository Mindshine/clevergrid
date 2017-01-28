package com.mindshine.clevergrid.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The persistent class for the game database table.
 *
 */
// @Entity
// @Table(name="tag")
// @NamedQuery(name="Tag.findAll", query="SELECT t FROM Tag t")
@Document
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy=GenerationType.AUTO)
	private ObjectId id;

	@NotNull
	@NotEmpty
	@Size(min = 1, max = 20)
	// @Column(name="tagname")
	private String tagName;

	// @ManyToMany(fetch = FetchType.LAZY, mappedBy="tags")
	@DBRef
	private Set<Game> games = new HashSet<>();

	public Tag() {
	}

	public ObjectId getId() {
		return this.id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getTagName() {
		return this.tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Set<Game> getGames() {
		return this.games;
	}

	public void setGames(Set<Game> games) {
		this.games = games;
	}

	public Game addGame(Game game) {
		getGames().add(game);
		game.addTag(this);

		return game;
	}

	public Game removeGame(Game game) {
		getGames().remove(game);
		game.removeTag(this);

		return game;
	}

	public static Builder getBuilder(String tagName) {
		return new Builder(tagName);
	}

	public static class Builder {
		Tag built;

		Builder(String tagName) {
			this.built = new Tag();
			this.built.tagName = tagName;
		}

		public Tag build() {
			return this.built;
		}
	}

}
