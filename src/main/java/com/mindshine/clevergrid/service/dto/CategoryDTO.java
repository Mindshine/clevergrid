package com.mindshine.clevergrid.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Category entity.
 */
public class CategoryDTO implements Serializable {

    private String id;

    private String name;

    private String parent;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;

        if ( ! Objects.equals(id, categoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", parent='" + parent + "'" +
            '}';
    }
}
