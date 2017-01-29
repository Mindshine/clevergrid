package com.mindshine.clevergrid.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Tag entity.
 */
public class TagDTO implements Serializable {

    private String id;

    private String tagName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TagDTO tagDTO = (TagDTO) o;

        if ( ! Objects.equals(id, tagDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TagDTO{" +
            "id=" + id +
            ", tagName='" + tagName + "'" +
            '}';
    }
}
