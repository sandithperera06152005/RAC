package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A ReceiptLines.
 */
@Entity
@IdClass(ReceiptLinesId.class)
@Table(name = "receiptlines")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReceiptLines implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Id
    @Column(name = "lineid")
    private Long lineid;

    @Column(name = "invoicecode")
    private String invoicecode;

    @Column(name = "invoicetype")
    private String invoicetype;

    @Column(name = "originalamount")
    private Double originalamount;

    @Column(name = "amountowing")
    private Double amountowing;

    @Column(name = "discountavailable")
    private Double discountavailable;

    @Column(name = "discounttaken")
    private Double discounttaken;

    @Column(name = "amountreceived")
    private Double amountreceived;

    @Column(name = "lmu")
    private Long lmu;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "accountid")
    private Long accountid;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReceiptLines id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLineid() {
        return this.lineid;
    }

    public ReceiptLines lineid(Long lineid) {
        this.setLineid(lineid);
        return this;
    }

    public void setLineid(Long lineid) {
        this.lineid = lineid;
    }

    public String getInvoicecode() {
        return this.invoicecode;
    }

    public ReceiptLines invoicecode(String invoicecode) {
        this.setInvoicecode(invoicecode);
        return this;
    }

    public void setInvoicecode(String invoicecode) {
        this.invoicecode = invoicecode;
    }

    public String getInvoicetype() {
        return this.invoicetype;
    }

    public ReceiptLines invoicetype(String invoicetype) {
        this.setInvoicetype(invoicetype);
        return this;
    }

    public void setInvoicetype(String invoicetype) {
        this.invoicetype = invoicetype;
    }

    public Double getOriginalamount() {
        return this.originalamount;
    }

    public ReceiptLines originalamount(Double originalamount) {
        this.setOriginalamount(originalamount);
        return this;
    }

    public void setOriginalamount(Double originalamount) {
        this.originalamount = originalamount;
    }

    public Double getAmountowing() {
        return this.amountowing;
    }

    public ReceiptLines amountowing(Double amountowing) {
        this.setAmountowing(amountowing);
        return this;
    }

    public void setAmountowing(Double amountowing) {
        this.amountowing = amountowing;
    }

    public Double getDiscountavailable() {
        return this.discountavailable;
    }

    public ReceiptLines discountavailable(Double discountavailable) {
        this.setDiscountavailable(discountavailable);
        return this;
    }

    public void setDiscountavailable(Double discountavailable) {
        this.discountavailable = discountavailable;
    }

    public Double getDiscounttaken() {
        return this.discounttaken;
    }

    public ReceiptLines discounttaken(Double discounttaken) {
        this.setDiscounttaken(discounttaken);
        return this;
    }

    public void setDiscounttaken(Double discounttaken) {
        this.discounttaken = discounttaken;
    }

    public Double getAmountreceived() {
        return this.amountreceived;
    }

    public ReceiptLines amountreceived(Double amountreceived) {
        this.setAmountreceived(amountreceived);
        return this;
    }

    public void setAmountreceived(Double amountreceived) {
        this.amountreceived = amountreceived;
    }

    public Long getLmu() {
        return this.lmu;
    }

    public ReceiptLines lmu(Long lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Long lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public ReceiptLines lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Long getAccountid() {
        return this.accountid;
    }

    public ReceiptLines accountid(Long accountid) {
        this.setAccountid(accountid);
        return this;
    }

    public void setAccountid(Long accountid) {
        this.accountid = accountid;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReceiptLines)) {
            return false;
        }
        ReceiptLines that = (ReceiptLines) o;
        return Objects.equals(id, that.id) && Objects.equals(lineid, that.lineid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lineid);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReceiptLines{" +
                "id=" + getId() +
                ", lineid=" + getLineid() +
                ", invoicecode='" + getInvoicecode() + "'" +
                ", invoicetype='" + getInvoicetype() + "'" +
                ", originalamount=" + getOriginalamount() +
                ", amountowing=" + getAmountowing() +
                ", discountavailable=" + getDiscountavailable() +
                ", discounttaken=" + getDiscounttaken() +
                ", amountreceived=" + getAmountreceived() +
                ", lmu=" + getLmu() +
                ", lmd='" + getLmd() + "'" +
                ", accountid=" + getAccountid() +
                "}";
    }
}
