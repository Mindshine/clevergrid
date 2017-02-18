package com.mindshine.clevergrid.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;


/**
 * A DTO for the Game entity.
 */
public class GameDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String title;

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

	public void setTitle(String title) {
		this.title = title;
	}
	public Boolean getIsPublic() {
		return this.isPublic;
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

		GameDTO gameDTO = (GameDTO) o;

		if ( ! Objects.equals(this.id, gameDTO.id)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}

	@Override
	public String toString() {
		return "GameDTO{" +
				"id=" + this.id +
				", title='" + this.title + "'" +
				", isPublic='" + this.isPublic + "'" +
				'}';
	}
}
