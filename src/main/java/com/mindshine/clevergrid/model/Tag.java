package com.mindshine.clevergrid.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The persistent class for the game database table.
 * 
 */
@Entity
@Table(name="tag")
@NamedQuery(name="Tag.findAll", query="SELECT t FROM Tag t")
public class Tag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

    @NotNull
    @NotEmpty
    @Size(min=1, max=20)
    @Column(name="tagname")
    private String tagName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy="tags")
    private Set<Game> games = new HashSet<Game>();

	public Tag() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Set<Game> getGames() {
		return games;
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
            built = new Tag();
            built.tagName = tagName;
        }
 
        public Tag build() {
            return built;
        }
    }
    
}
