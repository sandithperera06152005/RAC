package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A VoucherLines.
 */
@Entity
@Table(name = "voucherlines")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoucherLines implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lineid")
    private Integer lineID;

    @Column(name = "grncode")
    private String grnCode;

    @Column(name = "grntype")
    private String grnType;

    @Column(name = "originalamount")
    private Float originalAmount;

    @Column(name = "amountowing")
    private Float amountOwing;

    @Column(name = "discountavailable")
    private Float discountAvailable;

    @Column(name = "discounttaken")
    private Float discountTaken;

    @Column(name = "amountreceived")
    private Float amountReceived;

    @Column(name = "lmu")
    private Integer lmu;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "accountid")
    private Integer accountId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VoucherLines id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLineID() {
        return this.lineID;
    }

    public VoucherLines lineID(Integer lineID) {
        this.setLineID(lineID);
        return this;
    }

    public void setLineID(Integer lineID) {
        this.lineID = lineID;
    }

    public String getGrnCode() {
        return this.grnCode;
    }

    public VoucherLines grnCode(String grnCode) {
        this.setGrnCode(grnCode);
        return this;
    }

    public void setGrnCode(String grnCode) {
        this.grnCode = grnCode;
    }

    public String getGrnType() {
        return this.grnType;
    }

    public VoucherLines grnType(String grnType) {
        this.setGrnType(grnType);
        return this;
    }

    public void setGrnType(String grnType) {
        this.grnType = grnType;
    }

    public Float getOriginalAmount() {
        return this.originalAmount;
    }

    public VoucherLines originalAmount(Float originalAmount) {
        this.setOriginalAmount(originalAmount);
        return this;
    }

    public void setOriginalAmount(Float originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Float getAmountOwing() {
        return this.amountOwing;
    }

    public VoucherLines amountOwing(Float amountOwing) {
        this.setAmountOwing(amountOwing);
        return this;
    }

    public void setAmountOwing(Float amountOwing) {
        this.amountOwing = amountOwing;
    }

    public Float getDiscountAvailable() {
        return this.discountAvailable;
    }

    public VoucherLines discountAvailable(Float discountAvailable) {
        this.setDiscountAvailable(discountAvailable);
        return this;
    }

    public void setDiscountAvailable(Float discountAvailable) {
        this.discountAvailable = discountAvailable;
    }

    public Float getDiscountTaken() {
        return this.discountTaken;
    }

    public VoucherLines discountTaken(Float discountTaken) {
        this.setDiscountTaken(discountTaken);
        return this;
    }

    public void setDiscountTaken(Float discountTaken) {
        this.discountTaken = discountTaken;
    }

    public Float getAmountReceived() {
        return this.amountReceived;
    }

    public VoucherLines amountReceived(Float amountReceived) {
        this.setAmountReceived(amountReceived);
        return this;
    }

    public void setAmountReceived(Float amountReceived) {
        this.amountReceived = amountReceived;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public VoucherLines lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public VoucherLines lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Integer getAccountId() {
        return this.accountId;
    }

    public VoucherLines accountId(Integer accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoucherLines)) {
            return false;
        }
        return getId() != null && getId().equals(((VoucherLines) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherLines{" +
            "id=" + getId() +
            ", lineID=" + getLineID() +
            ", grnCode='" + getGrnCode() + "'" +
            ", grnType='" + getGrnType() + "'" +
            ", originalAmount=" + getOriginalAmount() +
            ", amountOwing=" + getAmountOwing() +
            ", discountAvailable=" + getDiscountAvailable() +
            ", discountTaken=" + getDiscountTaken() +
            ", amountReceived=" + getAmountReceived() +
            ", lmu=" + getLmu() +
            ", lmd='" + getLmd() + "'" +
            ", accountId=" + getAccountId() +
            "}";
    }
}
