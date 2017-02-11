package com.mindshine.clevergrid.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Game.
 */

@Document(collection = "game")
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Field("title")
	private String title;

	@Field("created_date")
	@CreatedDate
	private ZonedDateTime createdDate;

	@Field("created_by")
	@CreatedBy
	private String createdBy;

	@Field("last_modified_date")
	@LastModifiedDate
	private ZonedDateTime lastModifiedDate;

	@Field("last_modified_by")
	@LastModifiedBy
	private String lastModifiedBy;

	@Field("is_public")
	private Boolean isPublic;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public Game title(String title) {
		this.title = title;
		return this;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ZonedDateTime getCreatedDate() {
		return this.createdDate;
	}

	public Game createdDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
		return this;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public Game createdBy(String createdBy) {
		this.createdBy = createdBy;
		return this;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public ZonedDateTime getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public Game lastModifiedDate(ZonedDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
		return this;
	}

	public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	public Game lastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
		return this;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Boolean isIsPublic() {
		return this.isPublic;
	}

	public Game isPublic(Boolean isPublic) {
		this.isPublic = isPublic;
		return this;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Game game = (Game) o;
		if (game.id == null || this.id == null) {
			return false;
		}
		return Objects.equals(this.id, game.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}

	@Override
	public String toString() {
		return "Game{" +
				"id=" + this.id +
				", title='" + this.title + "'" +
				", createdDate='" + this.createdDate + "'" +
				", createdBy='" + this.createdBy + "'" +
				", lastModifiedDate='" + this.lastModifiedDate + "'" +
				", lastModifiedBy='" + this.lastModifiedBy + "'" +
				", isPublic='" + this.isPublic + "'" +
				'}';
	}
}
