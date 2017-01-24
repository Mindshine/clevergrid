package com.mindshine.clevergrid.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

/**
 * The persistent class for the game database table.
 *
 */
// @Entity
// @EntityListeners(AuditingEntityListener.class)
// @Table(name = "game")
@Document
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private ObjectId id;

	private String title;

	// @Column(name = "created_date")
	@CreatedDate
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime createdDate = LocalDateTime.now();

	// @Column(name = "created_by")
	@CreatedBy
	private String createdBy;

	// @Column(name = "last_modified_date")
	@LastModifiedDate
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime lastModifiedDate;

	// @Column(name = "last_modified_by")
	@LastModifiedBy
	private String latsModifiedBy;

	// @Column(name = "is_public")
	private boolean isPublic;

	// bi-directional many-to-one association to Topic
	// @OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE)
	@DBRef
	private List<Topic> topics;

	// @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	// @JoinTable(name = "game_tags", joinColumns = { @JoinColumn(name =
	// "idgame") }, inverseJoinColumns = {
	// @JoinColumn(name = "idtag") })
	@DBRef
	private Set<Tag> tags = new HashSet<>();

	public Game() {
	}

	public ObjectId getId() {
		return this.id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDateTime getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLatsModifiedBy() {
		return this.latsModifiedBy;
	}

	public void setLatsModifiedBy(String latsModifiedBy) {
		this.latsModifiedBy = latsModifiedBy;
	}

	public boolean isPublic() {
		return this.isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public List<Topic> getTopics() {
		return this.topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Topic addTopic(Topic topic) {
		getTopics().add(topic);
		topic.setGame(this);

		return topic;
	}

	public Topic removeTopic(Topic topic) {
		getTopics().remove(topic);
		topic.setGame(null);

		return topic;
	}

	public Set<Tag> getTags() {
		return this.tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Tag addTag(Tag tag) {
		getTags().add(tag);
		tag.addGame(this);

		return tag;
	}

	public Tag removeTag(Tag tag) {
		getTags().remove(tag);
		tag.removeGame(this);

		return tag;
	}

	public static Builder getBuilder(String title) {
		return new Builder(title);
	}

	public static class Builder {
		Game built;

		Builder(String title) {
			this.built = new Game();
			this.built.title = title;
		}

		public Game build() {

			return this.built;
		}
	}

}