package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A BinCard.
 */
@Entity
@Table(name = "bincard")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BinCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "itemid")
    private Long itemID;

    @Column(name = "itemcode")
    private String itemCode;

    @Column(name = "reference")
    private String reference;

    @Column(name = "txdate")
    private Instant txDate;

    @Column(name = "qtyin")
    private Double qtyIn;

    @Column(name = "qtyout")
    private Double qtyOut;

    @Column(name = "price")
    private Double price;

    @Column(name = "lmu")
    private Integer lMU;

    @Column(name = "lmd")
    private Instant lMD;

    @Column(name = "referencecode")
    private String referenceCode;

    @Column(name = "recorddate")
    private Instant recordDate;

    @Column(name = "batchid")
    private Long batchId;

    @Column(name = "locationid")
    private Long locationID;

    @Column(name = "locationcode")
    private String locationCode;

    @Column(name = "opening")
    private Double opening;

    @Column(name = "description")
    private String description;

    @Column(name = "referencedoc")
    private String referenceDoc;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BinCard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemID() {
        return this.itemID;
    }

    public BinCard itemID(Long itemID) {
        this.setItemID(itemID);
        return this;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public BinCard itemCode(String itemCode) {
        this.setItemCode(itemCode);
        return this;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getReference() {
        return this.reference;
    }

    public BinCard reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Instant getTxDate() {
        return this.txDate;
    }

    public BinCard txDate(Instant txDate) {
        this.setTxDate(txDate);
        return this;
    }

    public void setTxDate(Instant txDate) {
        this.txDate = txDate;
    }

    public Double getQtyIn() {
        return this.qtyIn;
    }

    public BinCard qtyIn(Double qtyIn) {
        this.setQtyIn(qtyIn);
        return this;
    }

    public void setQtyIn(Double qtyIn) {
        this.qtyIn = qtyIn;
    }

    public Double getQtyOut() {
        return this.qtyOut;
    }

    public BinCard qtyOut(Double qtyOut) {
        this.setQtyOut(qtyOut);
        return this;
    }

    public void setQtyOut(Double qtyOut) {
        this.qtyOut = qtyOut;
    }

    public Double getPrice() {
        return this.price;
    }

    public BinCard price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getlMU() {
        return this.lMU;
    }

    public BinCard lMU(Integer lMU) {
        this.setlMU(lMU);
        return this;
    }

    public void setlMU(Integer lMU) {
        this.lMU = lMU;
    }

    public Instant getlMD() {
        return this.lMD;
    }

    public BinCard lMD(Instant lMD) {
        this.setlMD(lMD);
        return this;
    }

    public void setlMD(Instant lMD) {
        this.lMD = lMD;
    }

    public String getReferenceCode() {
        return this.referenceCode;
    }

    public BinCard referenceCode(String referenceCode) {
        this.setReferenceCode(referenceCode);
        return this;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public Instant getRecordDate() {
        return this.recordDate;
    }

    public BinCard recordDate(Instant recordDate) {
        this.setRecordDate(recordDate);
        return this;
    }

    public void setRecordDate(Instant recordDate) {
        this.recordDate = recordDate;
    }

    public Long getBatchId() {
        return this.batchId;
    }

    public BinCard batchId(Long batchId) {
        this.setBatchId(batchId);
        return this;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public Long getLocationID() {
        return this.locationID;
    }

    public BinCard locationID(Long locationID) {
        this.setLocationID(locationID);
        return this;
    }

    public void setLocationID(Long locationID) {
        this.locationID = locationID;
    }

    public String getLocationCode() {
        return this.locationCode;
    }

    public BinCard locationCode(String locationCode) {
        this.setLocationCode(locationCode);
        return this;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public Double getOpening() {
        return this.opening;
    }

    public BinCard opening(Double opening) {
        this.setOpening(opening);
        return this;
    }

    public void setOpening(Double opening) {
        this.opening = opening;
    }

    public String getDescription() {
        return this.description;
    }

    public BinCard description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceDoc() {
        return this.referenceDoc;
    }

    public BinCard referenceDoc(String referenceDoc) {
        this.setReferenceDoc(referenceDoc);
        return this;
    }

    public void setReferenceDoc(String referenceDoc) {
        this.referenceDoc = referenceDoc;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BinCard)) {
            return false;
        }
        return getId() != null && getId().equals(((BinCard) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BinCard{" +
            "id=" + getId() +
            ", itemID=" + getItemID() +
            ", itemCode='" + getItemCode() + "'" +
            ", reference='" + getReference() + "'" +
            ", txDate='" + getTxDate() + "'" +
            ", qtyIn=" + getQtyIn() +
            ", qtyOut=" + getQtyOut() +
            ", price=" + getPrice() +
            ", lMU=" + getlMU() +
            ", lMD='" + getlMD() + "'" +
            ", referenceCode='" + getReferenceCode() + "'" +
            ", recordDate='" + getRecordDate() + "'" +
            ", batchId=" + getBatchId() +
            ", locationID=" + getLocationID() +
            ", locationCode='" + getLocationCode() + "'" +
            ", opening=" + getOpening() +
            ", description='" + getDescription() + "'" +
            ", referenceDoc='" + getReferenceDoc() + "'" +
            "}";
    }
}
