package com.example.kurs.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@Entity
@IdClass(MicroreactorInSpaceship.class)
public class MicroreactorInSpaceship implements Serializable {
    @Id
    private Long microreactor_type_id;
    @Id
    private Long spaceship_id;
    private Date deploy_date;

    public MicroreactorInSpaceship() {
    }

    public Long getMicroreactor_type_id() {
        return microreactor_type_id;
    }

    public void setMicroreactor_type_id(Long microreactor_type_id) {
        this.microreactor_type_id = microreactor_type_id;
    }

    public Long getSpaceship_id() {
        return spaceship_id;
    }

    public void setSpaceship_id(Long spaceship_id) {
        this.spaceship_id = spaceship_id;
    }

    public Date getDeploy_date() {
        return deploy_date;
    }

    public void setDeploy_date(Date deploy_date) {
        this.deploy_date = deploy_date;
    }
}
