package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Transactions.
 */
@Entity
@Table(name = "transactions")
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "accountid")
    private Long accountId;

    @Column(name = "accountcode")
    private String accountCode;

    @Column(name = "debit")
    private Double debit;

    @Column(name = "credit")
    private Double credit;

    @Column(name = "date")
    private Instant date;

    @Column(name = "refdoc")
    private String refDoc;

    @Column(name = "refid")
    private Long refId;

    @Column(name = "subid")
    private String subId;

    @Column(name = "source")
    private String source;

    @Column(name = "paymenttermid")
    private Integer paymentTermId;

    @Column(name = "paymenttermname")
    private String paymentTermName;

    @Column(name = "lmu")
    private Integer lmu;

    @Column(name = "lmd")
    private Instant lmd;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Double getDebit() {
        return debit;
    }

    public void setDebit(Double debit) {
        this.debit = debit;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getRefDoc() {
        return refDoc;
    }

    public void setRefDoc(String refDoc) {
        this.refDoc = refDoc;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getPaymentTermId() {
        return paymentTermId;
    }

    public void setPaymentTermId(Integer paymentTermId) {
        this.paymentTermId = paymentTermId;
    }

    public String getPaymentTermName() {
        return paymentTermName;
    }

    public void setPaymentTermName(String paymentTermName) {
        this.paymentTermName = paymentTermName;
    }

    public Integer getLmu() {
        return lmu;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return lmd;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transactions)) {
            return false;
        }
        return id != null && id.equals(((Transactions) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Transactions{" +
            "id=" +
            id +
            ", accountId=" +
            accountId +
            ", accountCode='" +
            accountCode +
            '\'' +
            ", debit=" +
            debit +
            ", credit=" +
            credit +
            ", date=" +
            date +
            ", refDoc='" +
            refDoc +
            '\'' +
            ", refId=" +
            refId +
            ", subId='" +
            subId +
            '\'' +
            ", source='" +
            source +
            '\'' +
            ", paymentTermId=" +
            paymentTermId +
            ", paymentTermName='" +
            paymentTermName +
            '\'' +
            ", lmu=" +
            lmu +
            ", lmd=" +
            lmd +
            '}'
        );
    }
}
