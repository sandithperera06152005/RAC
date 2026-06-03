package com.heavenscode.rac.domain;

import java.io.Serializable;
import java.util.Objects;

public class ReceiptpaymentsdetailsId implements Serializable {

    private Long id;

    private Long lineid;

    public ReceiptpaymentsdetailsId() {}

    public ReceiptpaymentsdetailsId(Long id, Long lineid) {
        this.id = id;
        this.lineid = lineid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLineid() {
        return lineid;
    }

    public void setLineid(Long lineid) {
        this.lineid = lineid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReceiptpaymentsdetailsId)) {
            return false;
        }
        ReceiptpaymentsdetailsId that = (ReceiptpaymentsdetailsId) o;
        return Objects.equals(id, that.id) && Objects.equals(lineid, that.lineid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lineid);
    }
}
