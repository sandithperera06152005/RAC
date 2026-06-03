package com.heavenscode.rac.domain;

import java.io.Serializable;
import java.util.Objects;

public class WorkshopVehicleWorkListId implements Serializable {

    private Integer vehicleworkid;

    private Integer lineid;

    public WorkshopVehicleWorkListId() {}

    public WorkshopVehicleWorkListId(Integer vehicleworkid, Integer lineid) {
        this.vehicleworkid = vehicleworkid;
        this.lineid = lineid;
    }

    public Integer getVehicleworkid() {
        return vehicleworkid;
    }

    public void setVehicleworkid(Integer vehicleworkid) {
        this.vehicleworkid = vehicleworkid;
    }

    public Integer getLineid() {
        return lineid;
    }

    public void setLineid(Integer lineid) {
        this.lineid = lineid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkshopVehicleWorkListId)) {
            return false;
        }
        WorkshopVehicleWorkListId that = (WorkshopVehicleWorkListId) o;
        return Objects.equals(vehicleworkid, that.vehicleworkid) && Objects.equals(lineid, that.lineid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleworkid, lineid);
    }
}
