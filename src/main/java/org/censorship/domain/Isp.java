package org.censorship.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Isp.
 */
@Document(collection = "isp")
public class Isp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("short_name")
    private String shortName;

    @Field("long_name")
    private String longName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public Isp shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public Isp longName(String longName) {
        this.longName = longName;
        return this;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Isp isp = (Isp) o;
        if (isp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), isp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Isp{" +
            "id=" + getId() +
            ", shortName='" + getShortName() + "'" +
            ", longName='" + getLongName() + "'" +
            "}";
    }
}
