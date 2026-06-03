package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A WorkshopVehicleWorkList.
 */
@Entity
@IdClass(WorkshopVehicleWorkListId.class)
@Table(name = "workshopvehicleworklist")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkshopVehicleWorkList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "vehicleworkid")
    private Integer vehicleworkid;

    @Id
    @Column(name = "lineid")
    private Integer lineid;

    @Column(name = "workid")
    private Integer workid;

    @Column(name = "workshopwork")
    private String workshopwork;

    @Column(name = "isjobdone")
    private Boolean isjobdone;

    @Column(name = "jobdonedate")
    private Instant jobdonedate;

    @Column(name = "jobnumber")
    private String jobnumber;

    @Column(name = "jobvalue")
    private Float jobvalue;

    @Column(name = "estimatevalue")
    private Float estimatevalue;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Transient
    public String getId() {
        if (this.vehicleworkid == null || this.lineid == null) {
            return null;
        }
        return this.vehicleworkid + "-" + this.lineid;
    }

    public Integer getVehicleworkid() {
        return this.vehicleworkid;
    }

    public WorkshopVehicleWorkList vehicleworkid(Integer vehicleworkid) {
        this.setVehicleworkid(vehicleworkid);
        return this;
    }

    public void setVehicleworkid(Integer vehicleworkid) {
        this.vehicleworkid = vehicleworkid;
    }

    public Integer getLineid() {
        return this.lineid;
    }

    public WorkshopVehicleWorkList lineid(Integer lineid) {
        this.setLineid(lineid);
        return this;
    }

    public void setLineid(Integer lineid) {
        this.lineid = lineid;
    }

    public Integer getWorkid() {
        return this.workid;
    }

    public WorkshopVehicleWorkList workid(Integer workid) {
        this.setWorkid(workid);
        return this;
    }

    public void setWorkid(Integer workid) {
        this.workid = workid;
    }

    public String getWorkshopwork() {
        return this.workshopwork;
    }

    public WorkshopVehicleWorkList workshopwork(String workshopwork) {
        this.setWorkshopwork(workshopwork);
        return this;
    }

    public void setWorkshopwork(String workshopwork) {
        this.workshopwork = workshopwork;
    }

    public Boolean getIsjobdone() {
        return this.isjobdone;
    }

    public WorkshopVehicleWorkList isjobdone(Boolean isjobdone) {
        this.setIsjobdone(isjobdone);
        return this;
    }

    public void setIsjobdone(Boolean isjobdone) {
        this.isjobdone = isjobdone;
    }

    public Instant getJobdonedate() {
        return this.jobdonedate;
    }

    public WorkshopVehicleWorkList jobdonedate(Instant jobdonedate) {
        this.setJobdonedate(jobdonedate);
        return this;
    }

    public void setJobdonedate(Instant jobdonedate) {
        this.jobdonedate = jobdonedate;
    }

    public String getJobnumber() {
        return this.jobnumber;
    }

    public WorkshopVehicleWorkList jobnumber(String jobnumber) {
        this.setJobnumber(jobnumber);
        return this;
    }

    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    public Float getJobvalue() {
        return this.jobvalue;
    }

    public WorkshopVehicleWorkList jobvalue(Float jobvalue) {
        this.setJobvalue(jobvalue);
        return this;
    }

    public void setJobvalue(Float jobvalue) {
        this.jobvalue = jobvalue;
    }

    public Float getEstimatevalue() {
        return this.estimatevalue;
    }

    public WorkshopVehicleWorkList estimatevalue(Float estimatevalue) {
        this.setEstimatevalue(estimatevalue);
        return this;
    }

    public void setEstimatevalue(Float estimatevalue) {
        this.estimatevalue = estimatevalue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkshopVehicleWorkList)) {
            return false;
        }
        return (
            getVehicleworkid() != null &&
            getLineid() != null &&
            getVehicleworkid().equals(((WorkshopVehicleWorkList) o).getVehicleworkid()) &&
            getLineid().equals(((WorkshopVehicleWorkList) o).getLineid())
        );
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkshopVehicleWorkList{" +
            "vehicleworkid=" + getVehicleworkid() +
            ", lineid=" + getLineid() +
            ", workid=" + getWorkid() +
            ", workshopwork='" + getWorkshopwork() + "'" +
            ", isjobdone='" + getIsjobdone() + "'" +
            ", jobdonedate='" + getJobdonedate() + "'" +
            ", jobnumber='" + getJobnumber() + "'" +
            ", jobvalue=" + getJobvalue() +
            ", estimatevalue=" + getEstimatevalue() +
            "}";
    }
}
