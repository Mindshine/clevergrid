package com.mindshine.clevergrid.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mindshine.clevergrid.mongo.CascadeSave;

@Document
public class GameTag {

	private ObjectId id;
	@DBRef
	@CascadeSave
	private Game game;
	@DBRef
	@CascadeSave
	private Tag tag;

	public GameTag() {
	}

	public ObjectId getId() {
		return this.id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Game getGame() {
		return this.game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Tag getTag() {
		return this.tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}
