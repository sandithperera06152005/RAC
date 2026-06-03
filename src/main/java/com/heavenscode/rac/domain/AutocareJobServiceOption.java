package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A AutocareJobServiceOption.
 */
@Entity
@Table(name = "autocarejobserviceoption")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AutocareJobServiceOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "jobid")
    private Integer jobid;

    @Column(name = "servicesubcategoryid")
    private Integer servicesubcategoryid;

    @Column(name = "pendding")
    private Boolean pendding;

    @Column(name = "ongoing")
    private Boolean ongoing;

    @Column(name = "finished")
    private Boolean finished;

    @Column(name = "lmu")
    private Integer lmu;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "starttime")
    private Instant starttime;

    @Column(name = "endtime")
    private Instant endtime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AutocareJobServiceOption id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getJobid() {
        return this.jobid;
    }

    public AutocareJobServiceOption jobid(Integer jobid) {
        this.setJobid(jobid);
        return this;
    }

    public void setJobid(Integer jobid) {
        this.jobid = jobid;
    }

    public Integer getServicesubcategoryid() {
        return this.servicesubcategoryid;
    }

    public AutocareJobServiceOption servicesubcategoryid(Integer servicesubcategoryid) {
        this.setServicesubcategoryid(servicesubcategoryid);
        return this;
    }

    public void setServicesubcategoryid(Integer servicesubcategoryid) {
        this.servicesubcategoryid = servicesubcategoryid;
    }

    public Boolean getPendding() {
        return this.pendding;
    }

    public AutocareJobServiceOption pendding(Boolean pendding) {
        this.setPendding(pendding);
        return this;
    }

    public void setPendding(Boolean pendding) {
        this.pendding = pendding;
    }

    public Boolean getOngoing() {
        return this.ongoing;
    }

    public AutocareJobServiceOption ongoing(Boolean ongoing) {
        this.setOngoing(ongoing);
        return this;
    }

    public void setOngoing(Boolean ongoing) {
        this.ongoing = ongoing;
    }

    public Boolean getFinished() {
        return this.finished;
    }

    public AutocareJobServiceOption finished(Boolean finished) {
        this.setFinished(finished);
        return this;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public AutocareJobServiceOption lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public AutocareJobServiceOption lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Instant getStarttime() {
        return this.starttime;
    }

    public AutocareJobServiceOption starttime(Instant starttime) {
        this.setStarttime(starttime);
        return this;
    }

    public void setStarttime(Instant starttime) {
        this.starttime = starttime;
    }

    public Instant getEndtime() {
        return this.endtime;
    }

    public AutocareJobServiceOption endtime(Instant endtime) {
        this.setEndtime(endtime);
        return this;
    }

    public void setEndtime(Instant endtime) {
        this.endtime = endtime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AutocareJobServiceOption)) {
            return false;
        }
        return getId() != null && getId().equals(((AutocareJobServiceOption) o).getId());
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutocareJobServiceOption{" +
                "id=" + getId() +
                ", jobid=" + getJobid() +
                ", servicesubcategoryid=" + getServicesubcategoryid() +
                ", pendding='" + getPendding() + "'" +
                ", ongoing='" + getOngoing() + "'" +
                ", finished='" + getFinished() + "'" +
                ", lmu=" + getLmu() +
                ", lmd='" + getLmd() + "'" +
                ", starttime='" + getStarttime() + "'" +
                ", endtime='" + getEndtime() + "'" +
                "}";
    }
}
