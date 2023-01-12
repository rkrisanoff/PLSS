package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@Entity
public class MicroreactorInSpaceship implements Serializable {
    @Id
    private Long microreactorTypeId;
    private Long spaceshipId;
    private Date deploy_date;

    public MicroreactorInSpaceship() {
    }

    public Long getMicroreactorTypeId() {
        return microreactorTypeId;
    }

    public void setMicroreactorTypeId(Long microreactorTypeId) {
        this.microreactorTypeId = microreactorTypeId;
    }

    public Long getSpaceshipId() {
        return spaceshipId;
    }

    public void setSpaceshipId(Long spaceshipId) {
        this.spaceshipId = spaceshipId;
    }

    public Date getDeploy_date() {
        return deploy_date;
    }

    public void setDeploy_date(Date deploy_date) {
        this.deploy_date = deploy_date;
    }
}
