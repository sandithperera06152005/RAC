package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Voucher.
 */
@Entity
@Table(name = "voucher")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Voucher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "voucherdate")
    private Instant voucherDate;

    @Column(name = "suppliername")
    private String supplierName;

    @Column(name = "supplieraddress")
    private String supplierAddress;

    @Column(name = "totalamount")
    private Float totalAmount;

    @Column(name = "totalamountinword")
    private String totalAmountInWord;

    @Column(name = "comments")
    private String comments;

    @Column(name = "lmu")
    private Integer lmu;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "termid")
    private Integer termId;

    @Column(name = "term")
    private String term;

    @Column(name = "date")
    private Instant date;

    @Column(name = "amountpaid")
    private Float amountPaid;

    @Column(name = "supplierid")
    private Integer supplierID;

    @Column(name = "isactive")
    private Boolean isActive;

    @Column(name = "createdby")
    private Integer createdBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Voucher id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Voucher code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getVoucherDate() {
        return this.voucherDate;
    }

    public Voucher voucherDate(Instant voucherDate) {
        this.setVoucherDate(voucherDate);
        return this;
    }

    public void setVoucherDate(Instant voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getSupplierName() {
        return this.supplierName;
    }

    public Voucher supplierName(String supplierName) {
        this.setSupplierName(supplierName);
        return this;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierAddress() {
        return this.supplierAddress;
    }

    public Voucher supplierAddress(String supplierAddress) {
        this.setSupplierAddress(supplierAddress);
        return this;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public Float getTotalAmount() {
        return this.totalAmount;
    }

    public Voucher totalAmount(Float totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalAmountInWord() {
        return this.totalAmountInWord;
    }

    public Voucher totalAmountInWord(String totalAmountInWord) {
        this.setTotalAmountInWord(totalAmountInWord);
        return this;
    }

    public void setTotalAmountInWord(String totalAmountInWord) {
        this.totalAmountInWord = totalAmountInWord;
    }

    public String getComments() {
        return this.comments;
    }

    public Voucher comments(String comments) {
        this.setComments(comments);
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public Voucher lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public Voucher lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Integer getTermId() {
        return this.termId;
    }

    public Voucher termId(Integer termId) {
        this.setTermId(termId);
        return this;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    public String getTerm() {
        return this.term;
    }

    public Voucher term(String term) {
        this.setTerm(term);
        return this;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Instant getDate() {
        return this.date;
    }

    public Voucher date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Float getAmountPaid() {
        return this.amountPaid;
    }

    public Voucher amountPaid(Float amountPaid) {
        this.setAmountPaid(amountPaid);
        return this;
    }

    public void setAmountPaid(Float amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Integer getSupplierID() {
        return this.supplierID;
    }

    public Voucher supplierID(Integer supplierID) {
        this.setSupplierID(supplierID);
        return this;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Voucher isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public Voucher createdBy(Integer createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Voucher)) {
            return false;
        }
        return getId() != null && getId().equals(((Voucher) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Voucher{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", voucherDate='" + getVoucherDate() + "'" +
            ", supplierName='" + getSupplierName() + "'" +
            ", supplierAddress='" + getSupplierAddress() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", totalAmountInWord='" + getTotalAmountInWord() + "'" +
            ", comments='" + getComments() + "'" +
            ", lmu=" + getLmu() +
            ", lmd='" + getLmd() + "'" +
            ", termId=" + getTermId() +
            ", term='" + getTerm() + "'" +
            ", date='" + getDate() + "'" +
            ", amountPaid=" + getAmountPaid() +
            ", supplierID=" + getSupplierID() +
            ", isActive='" + getIsActive() + "'" +
            ", createdBy=" + getCreatedBy() +
            "}";
    }
}
