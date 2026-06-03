package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Autojobsinvoicelinebatches.
 */
@Entity
@Table(name = "autojobsinvoicelinebatches")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Autojobsinvoicelinebatches implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "lineid")
    private Integer lineid;

    @Column(name = "batchlineid")
    private Integer batchlineid;

    @Column(name = "itemid")
    private Integer itemid;

    @Column(name = "code")
    private String code;

    @Column(name = "batchid")
    private Integer batchid;

    @Column(name = "batchcode")
    private String batchcode;

    @Column(name = "txdate")
    private Instant txdate;

    @Column(name = "manufacturedate")
    private Instant manufacturedate;

    @Column(name = "expireddate")
    private Instant expireddate;

    @Column(name = "qty")
    private Float qty;

    @Column(name = "cost")
    private Float cost;

    @Column(name = "price")
    private Float price;

    @Column(name = "notes")
    private String notes;

    @Column(name = "lmu")
    private Integer lmu;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "nbt")
    private Boolean nbt;

    @Column(name = "vat")
    private Boolean vat;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "total")
    private Float total;

    @Column(name = "issued")
    private Boolean issued;

    @Column(name = "issuedby")
    private Integer issuedby;

    @Column(name = "issueddatetime")
    private Instant issueddatetime;

    @Column(name = "addedbyid")
    private Integer addedbyid;

    @Column(name = "canceloptid")
    private Integer canceloptid;

    @Column(name = "cancelopt")
    private String cancelopt;

    @Column(name = "cancelby")
    private Integer cancelby;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public Autojobsinvoicelinebatches id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLineid() {
        return this.lineid;
    }

    public Autojobsinvoicelinebatches lineid(Integer lineid) {
        this.setLineid(lineid);
        return this;
    }

    public void setLineid(Integer lineid) {
        this.lineid = lineid;
    }

    public Integer getBatchlineid() {
        return this.batchlineid;
    }

    public Autojobsinvoicelinebatches batchlineid(Integer batchlineid) {
        this.setBatchlineid(batchlineid);
        return this;
    }

    public void setBatchlineid(Integer batchlineid) {
        this.batchlineid = batchlineid;
    }

    public Integer getItemid() {
        return this.itemid;
    }

    public Autojobsinvoicelinebatches itemid(Integer itemid) {
        this.setItemid(itemid);
        return this;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getCode() {
        return this.code;
    }

    public Autojobsinvoicelinebatches code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getBatchid() {
        return this.batchid;
    }

    public Autojobsinvoicelinebatches batchid(Integer batchid) {
        this.setBatchid(batchid);
        return this;
    }

    public void setBatchid(Integer batchid) {
        this.batchid = batchid;
    }

    public String getBatchcode() {
        return this.batchcode;
    }

    public Autojobsinvoicelinebatches batchcode(String batchcode) {
        this.setBatchcode(batchcode);
        return this;
    }

    public void setBatchcode(String batchcode) {
        this.batchcode = batchcode;
    }

    public Instant getTxdate() {
        return this.txdate;
    }

    public Autojobsinvoicelinebatches txdate(Instant txdate) {
        this.setTxdate(txdate);
        return this;
    }

    public void setTxdate(Instant txdate) {
        this.txdate = txdate;
    }

    public Instant getManufacturedate() {
        return this.manufacturedate;
    }

    public Autojobsinvoicelinebatches manufacturedate(Instant manufacturedate) {
        this.setManufacturedate(manufacturedate);
        return this;
    }

    public void setManufacturedate(Instant manufacturedate) {
        this.manufacturedate = manufacturedate;
    }

    public Instant getExpireddate() {
        return this.expireddate;
    }

    public Autojobsinvoicelinebatches expireddate(Instant expireddate) {
        this.setExpireddate(expireddate);
        return this;
    }

    public void setExpireddate(Instant expireddate) {
        this.expireddate = expireddate;
    }

    public Float getQty() {
        return this.qty;
    }

    public Autojobsinvoicelinebatches qty(Float qty) {
        this.setQty(qty);
        return this;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public Float getCost() {
        return this.cost;
    }

    public Autojobsinvoicelinebatches cost(Float cost) {
        this.setCost(cost);
        return this;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getPrice() {
        return this.price;
    }

    public Autojobsinvoicelinebatches price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getNotes() {
        return this.notes;
    }

    public Autojobsinvoicelinebatches notes(String notes) {
        this.setNotes(notes);
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public Autojobsinvoicelinebatches lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public Autojobsinvoicelinebatches lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Boolean getNbt() {
        return this.nbt;
    }

    public Autojobsinvoicelinebatches nbt(Boolean nbt) {
        this.setNbt(nbt);
        return this;
    }

    public void setNbt(Boolean nbt) {
        this.nbt = nbt;
    }

    public Boolean getVat() {
        return this.vat;
    }

    public Autojobsinvoicelinebatches vat(Boolean vat) {
        this.setVat(vat);
        return this;
    }

    public void setVat(Boolean vat) {
        this.vat = vat;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public Autojobsinvoicelinebatches discount(Float discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getTotal() {
        return this.total;
    }

    public Autojobsinvoicelinebatches total(Float total) {
        this.setTotal(total);
        return this;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Boolean getIssued() {
        return this.issued;
    }

    public Autojobsinvoicelinebatches issued(Boolean issued) {
        this.setIssued(issued);
        return this;
    }

    public void setIssued(Boolean issued) {
        this.issued = issued;
    }

    public Integer getIssuedby() {
        return this.issuedby;
    }

    public Autojobsinvoicelinebatches issuedby(Integer issuedby) {
        this.setIssuedby(issuedby);
        return this;
    }

    public void setIssuedby(Integer issuedby) {
        this.issuedby = issuedby;
    }

    public Instant getIssueddatetime() {
        return this.issueddatetime;
    }

    public Autojobsinvoicelinebatches issueddatetime(Instant issueddatetime) {
        this.setIssueddatetime(issueddatetime);
        return this;
    }

    public void setIssueddatetime(Instant issueddatetime) {
        this.issueddatetime = issueddatetime;
    }

    public Integer getAddedbyid() {
        return this.addedbyid;
    }

    public Autojobsinvoicelinebatches addedbyid(Integer addedbyid) {
        this.setAddedbyid(addedbyid);
        return this;
    }

    public void setAddedbyid(Integer addedbyid) {
        this.addedbyid = addedbyid;
    }

    public Integer getCanceloptid() {
        return this.canceloptid;
    }

    public Autojobsinvoicelinebatches canceloptid(Integer canceloptid) {
        this.setCanceloptid(canceloptid);
        return this;
    }

    public void setCanceloptid(Integer canceloptid) {
        this.canceloptid = canceloptid;
    }

    public String getCancelopt() {
        return this.cancelopt;
    }

    public Autojobsinvoicelinebatches cancelopt(String cancelopt) {
        this.setCancelopt(cancelopt);
        return this;
    }

    public void setCancelopt(String cancelopt) {
        this.cancelopt = cancelopt;
    }

    public Integer getCancelby() {
        return this.cancelby;
    }

    public Autojobsinvoicelinebatches cancelby(Integer cancelby) {
        this.setCancelby(cancelby);
        return this;
    }

    public void setCancelby(Integer cancelby) {
        this.cancelby = cancelby;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Autojobsinvoicelinebatches)) {
            return false;
        }
        return getId() != null && getId().equals(((Autojobsinvoicelinebatches) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Autojobsinvoicelinebatches{" +
            "id=" + getId() +
            ", lineid=" + getLineid() +
            ", batchlineid=" + getBatchlineid() +
            ", itemid=" + getItemid() +
            ", code='" + getCode() + "'" +
            ", batchid=" + getBatchid() +
            ", batchcode='" + getBatchcode() + "'" +
            ", txdate='" + getTxdate() + "'" +
            ", manufacturedate='" + getManufacturedate() + "'" +
            ", expireddate='" + getExpireddate() + "'" +
            ", qty=" + getQty() +
            ", cost=" + getCost() +
            ", price=" + getPrice() +
            ", notes='" + getNotes() + "'" +
            ", lmu=" + getLmu() +
            ", lmd='" + getLmd() + "'" +
            ", nbt='" + getNbt() + "'" +
            ", vat='" + getVat() + "'" +
            ", discount=" + getDiscount() +
            ", total=" + getTotal() +
            ", issued='" + getIssued() + "'" +
            ", issuedby=" + getIssuedby() +
            ", issueddatetime='" + getIssueddatetime() + "'" +
            ", addedbyid=" + getAddedbyid() +
            ", canceloptid=" + getCanceloptid() +
            ", cancelopt='" + getCancelopt() + "'" +
            ", cancelby=" + getCancelby() +
            "}";
    }
}
