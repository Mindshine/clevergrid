package com.mindshine.clevergrid.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Topic entity.
 */
public class TopicDTO implements Serializable {

    private String id;

    private String title;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TopicDTO topicDTO = (TopicDTO) o;

        if ( ! Objects.equals(id, topicDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TopicDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            '}';
    }
}
