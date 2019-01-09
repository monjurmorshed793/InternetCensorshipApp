package org.censorship.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

import org.censorship.domain.enumeration.CensorshipType;

/**
 * A CensorshipStatus.
 */
@Document(collection = "censorship_status")
public class CensorshipStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("isp_id")
    private String ispId;

    @Field("isp_name")
    private String ispName;

    @Field("ooni_status")
    private CensorshipType ooniStatus;

    @Field("system_status")
    private CensorshipType systemStatus;

    @Field("description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIspId() {
        return ispId;
    }

    public CensorshipStatus ispId(String ispId) {
        this.ispId = ispId;
        return this;
    }

    public void setIspId(String ispId) {
        this.ispId = ispId;
    }

    public String getIspName() {
        return ispName;
    }

    public CensorshipStatus ispName(String ispName) {
        this.ispName = ispName;
        return this;
    }

    public void setIspName(String ispName) {
        this.ispName = ispName;
    }

    public CensorshipType getOoniStatus() {
        return ooniStatus;
    }

    public CensorshipStatus ooniStatus(CensorshipType ooniStatus) {
        this.ooniStatus = ooniStatus;
        return this;
    }

    public void setOoniStatus(CensorshipType ooniStatus) {
        this.ooniStatus = ooniStatus;
    }

    public CensorshipType getSystemStatus() {
        return systemStatus;
    }

    public CensorshipStatus systemStatus(CensorshipType systemStatus) {
        this.systemStatus = systemStatus;
        return this;
    }

    public void setSystemStatus(CensorshipType systemStatus) {
        this.systemStatus = systemStatus;
    }

    public String getDescription() {
        return description;
    }

    public CensorshipStatus description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
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
        CensorshipStatus censorshipStatus = (CensorshipStatus) o;
        if (censorshipStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), censorshipStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CensorshipStatus{" +
            "id=" + getId() +
            ", ispId='" + getIspId() + "'" +
            ", ispName='" + getIspName() + "'" +
            ", ooniStatus='" + getOoniStatus() + "'" +
            ", systemStatus='" + getSystemStatus() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
