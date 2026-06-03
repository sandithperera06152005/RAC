package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Receiptpaymentsdetails.
 */
@Entity
@IdClass(ReceiptpaymentsdetailsId.class)
@Table(name = "receiptpaymentsdetails")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Receiptpaymentsdetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Id
    @Column(name = "lineid")
    private Long lineid;

    @Column(name = "paymentamount")
    private Float paymentamount;

    @Column(name = "totalreceiptamount")
    private Float totalreceiptamount;

    @Column(name = "checkqueamount")
    private Float checkqueamount;

    @Column(name = "checkqueno")
    private String checkqueno;

    @Column(name = "checkquedate")
    private Instant checkquedate;

    @Column(name = "checkqueexpiredate")
    private Instant checkqueexpiredate;

    @Column(name = "bankname")
    private String bankname;

    @Column(name = "bankid")
    private Integer bankid;

    @Column(name = "bankbranchname")
    private String bankbranchname;

    @Column(name = "bankbranchid")
    private Integer bankbranchid;

    @Column(name = "creditcardno")
    private String creditcardno;

    @Column(name = "creditcardamount")
    private Float creditcardamount;

    @Column(name = "reference")
    private String reference;

    @Column(name = "otherdetails")
    private String otherdetails;

    @Column(name = "lmu")
    private Long lmu;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "termid")
    private Integer termid;

    @Column(name = "termname")
    private String termname;

    @Column(name = "accountno")
    private String accountno;

    @Column(name = "accountnumber")
    private String accountnumber;

    @Column(name = "chequereturndate")
    private Instant chequereturndate;

    @Column(name = "isdeposit")
    private Boolean isdeposit;

    @Column(name = "depositeddate")
    private Instant depositeddate;

    @Column(name = "chequestatuschangeddate")
    private Instant chequestatuschangeddate;

    @Column(name = "returnchequesttledate")
    private Instant returnchequesttledate;

    @Column(name = "chequestatusid")
    private Integer chequestatusid;

    @Column(name = "is_pd_cheque")
    private Boolean isPdCheque;

    @Column(name = "depositdate")
    private Instant depositdate;

    @Column(name = "accountid")
    private Integer accountid;

    @Column(name = "accountcode")
    private String accountcode;

    @Column(name = "bankdepositbankname")
    private String bankdepositbankname;

    @Column(name = "bankdepositbankid")
    private Integer bankdepositbankid;

    @Column(name = "bankdepositbankbranchname")
    private String bankdepositbankbranchname;

    @Column(name = "bankdepositbankbranchid")
    private Integer bankdepositbankbranchid;

    @Column(name = "returnchequefine")
    private Float returnchequefine;

    @Column(name = "companybankid")
    private Integer companybankid;

    @Column(name = "isbankreconciliation")
    private Boolean isbankreconciliation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Receiptpaymentsdetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLineid() {
        return this.lineid;
    }

    public Receiptpaymentsdetails lineid(Long lineid) {
        this.setLineid(lineid);
        return this;
    }

    public void setLineid(Long lineid) {
        this.lineid = lineid;
    }

    public Float getPaymentamount() {
        return this.paymentamount;
    }

    public Receiptpaymentsdetails paymentamount(Float paymentamount) {
        this.setPaymentamount(paymentamount);
        return this;
    }

    public void setPaymentamount(Float paymentamount) {
        this.paymentamount = paymentamount;
    }

    public Float getTotalreceiptamount() {
        return this.totalreceiptamount;
    }

    public Receiptpaymentsdetails totalreceiptamount(Float totalreceiptamount) {
        this.setTotalreceiptamount(totalreceiptamount);
        return this;
    }

    public void setTotalreceiptamount(Float totalreceiptamount) {
        this.totalreceiptamount = totalreceiptamount;
    }

    public Float getCheckqueamount() {
        return this.checkqueamount;
    }

    public Receiptpaymentsdetails checkqueamount(Float checkqueamount) {
        this.setCheckqueamount(checkqueamount);
        return this;
    }

    public void setCheckqueamount(Float checkqueamount) {
        this.checkqueamount = checkqueamount;
    }

    public String getCheckqueno() {
        return this.checkqueno;
    }

    public Receiptpaymentsdetails checkqueno(String checkqueno) {
        this.setCheckqueno(checkqueno);
        return this;
    }

    public void setCheckqueno(String checkqueno) {
        this.checkqueno = checkqueno;
    }

    public Instant getCheckquedate() {
        return this.checkquedate;
    }

    public Receiptpaymentsdetails checkquedate(Instant checkquedate) {
        this.setCheckquedate(checkquedate);
        return this;
    }

    public void setCheckquedate(Instant checkquedate) {
        this.checkquedate = checkquedate;
    }

    public Instant getCheckqueexpiredate() {
        return this.checkqueexpiredate;
    }

    public Receiptpaymentsdetails checkqueexpiredate(Instant checkqueexpiredate) {
        this.setCheckqueexpiredate(checkqueexpiredate);
        return this;
    }

    public void setCheckqueexpiredate(Instant checkqueexpiredate) {
        this.checkqueexpiredate = checkqueexpiredate;
    }

    public String getBankname() {
        return this.bankname;
    }

    public Receiptpaymentsdetails bankname(String bankname) {
        this.setBankname(bankname);
        return this;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public Integer getBankid() {
        return this.bankid;
    }

    public Receiptpaymentsdetails bankid(Integer bankid) {
        this.setBankid(bankid);
        return this;
    }

    public void setBankid(Integer bankid) {
        this.bankid = bankid;
    }

    public String getBankbranchname() {
        return this.bankbranchname;
    }

    public Receiptpaymentsdetails bankbranchname(String bankbranchname) {
        this.setBankbranchname(bankbranchname);
        return this;
    }

    public void setBankbranchname(String bankbranchname) {
        this.bankbranchname = bankbranchname;
    }

    public Integer getBankbranchid() {
        return this.bankbranchid;
    }

    public Receiptpaymentsdetails bankbranchid(Integer bankbranchid) {
        this.setBankbranchid(bankbranchid);
        return this;
    }

    public void setBankbranchid(Integer bankbranchid) {
        this.bankbranchid = bankbranchid;
    }

    public String getCreditcardno() {
        return this.creditcardno;
    }

    public Receiptpaymentsdetails creditcardno(String creditcardno) {
        this.setCreditcardno(creditcardno);
        return this;
    }

    public void setCreditcardno(String creditcardno) {
        this.creditcardno = creditcardno;
    }

    public Float getCreditcardamount() {
        return this.creditcardamount;
    }

    public Receiptpaymentsdetails creditcardamount(Float creditcardamount) {
        this.setCreditcardamount(creditcardamount);
        return this;
    }

    public void setCreditcardamount(Float creditcardamount) {
        this.creditcardamount = creditcardamount;
    }

    public String getReference() {
        return this.reference;
    }

    public Receiptpaymentsdetails reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getOtherdetails() {
        return this.otherdetails;
    }

    public Receiptpaymentsdetails otherdetails(String otherdetails) {
        this.setOtherdetails(otherdetails);
        return this;
    }

    public void setOtherdetails(String otherdetails) {
        this.otherdetails = otherdetails;
    }

    public Long getLmu() {
        return this.lmu;
    }

    public Receiptpaymentsdetails lmu(Long lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Long lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public Receiptpaymentsdetails lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Integer getTermid() {
        return this.termid;
    }

    public Receiptpaymentsdetails termid(Integer termid) {
        this.setTermid(termid);
        return this;
    }

    public void setTermid(Integer termid) {
        this.termid = termid;
    }

    public String getTermname() {
        return this.termname;
    }

    public Receiptpaymentsdetails termname(String termname) {
        this.setTermname(termname);
        return this;
    }

    public void setTermname(String termname) {
        this.termname = termname;
    }

    public String getAccountno() {
        return this.accountno;
    }

    public Receiptpaymentsdetails accountno(String accountno) {
        this.setAccountno(accountno);
        return this;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getAccountnumber() {
        return this.accountnumber;
    }

    public Receiptpaymentsdetails accountnumber(String accountnumber) {
        this.setAccountnumber(accountnumber);
        return this;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public Instant getChequereturndate() {
        return this.chequereturndate;
    }

    public Receiptpaymentsdetails chequereturndate(Instant chequereturndate) {
        this.setChequereturndate(chequereturndate);
        return this;
    }

    public void setChequereturndate(Instant chequereturndate) {
        this.chequereturndate = chequereturndate;
    }

    public Boolean getIsdeposit() {
        return this.isdeposit;
    }

    public Receiptpaymentsdetails isdeposit(Boolean isdeposit) {
        this.setIsdeposit(isdeposit);
        return this;
    }

    public void setIsdeposit(Boolean isdeposit) {
        this.isdeposit = isdeposit;
    }

    public Instant getDepositeddate() {
        return this.depositeddate;
    }

    public Receiptpaymentsdetails depositeddate(Instant depositeddate) {
        this.setDepositeddate(depositeddate);
        return this;
    }

    public void setDepositeddate(Instant depositeddate) {
        this.depositeddate = depositeddate;
    }

    public Instant getChequestatuschangeddate() {
        return this.chequestatuschangeddate;
    }

    public Receiptpaymentsdetails chequestatuschangeddate(Instant chequestatuschangeddate) {
        this.setChequestatuschangeddate(chequestatuschangeddate);
        return this;
    }

    public void setChequestatuschangeddate(Instant chequestatuschangeddate) {
        this.chequestatuschangeddate = chequestatuschangeddate;
    }

    public Instant getReturnchequesttledate() {
        return this.returnchequesttledate;
    }

    public Receiptpaymentsdetails returnchequesttledate(Instant returnchequesttledate) {
        this.setReturnchequesttledate(returnchequesttledate);
        return this;
    }

    public void setReturnchequesttledate(Instant returnchequesttledate) {
        this.returnchequesttledate = returnchequesttledate;
    }

    public Integer getChequestatusid() {
        return this.chequestatusid;
    }

    public Receiptpaymentsdetails chequestatusid(Integer chequestatusid) {
        this.setChequestatusid(chequestatusid);
        return this;
    }

    public void setChequestatusid(Integer chequestatusid) {
        this.chequestatusid = chequestatusid;
    }

    public Boolean getIsPdCheque() {
        return this.isPdCheque;
    }

    public Receiptpaymentsdetails isPdCheque(Boolean isPdCheque) {
        this.setIsPdCheque(isPdCheque);
        return this;
    }

    public void setIsPdCheque(Boolean isPdCheque) {
        this.isPdCheque = isPdCheque;
    }

    public Instant getDepositdate() {
        return this.depositdate;
    }

    public Receiptpaymentsdetails depositdate(Instant depositdate) {
        this.setDepositdate(depositdate);
        return this;
    }

    public void setDepositdate(Instant depositdate) {
        this.depositdate = depositdate;
    }

    public Integer getAccountid() {
        return this.accountid;
    }

    public Receiptpaymentsdetails accountid(Integer accountid) {
        this.setAccountid(accountid);
        return this;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public String getAccountcode() {
        return this.accountcode;
    }

    public Receiptpaymentsdetails accountcode(String accountcode) {
        this.setAccountcode(accountcode);
        return this;
    }

    public void setAccountcode(String accountcode) {
        this.accountcode = accountcode;
    }

    public String getBankdepositbankname() {
        return this.bankdepositbankname;
    }

    public Receiptpaymentsdetails bankdepositbankname(String bankdepositbankname) {
        this.setBankdepositbankname(bankdepositbankname);
        return this;
    }

    public void setBankdepositbankname(String bankdepositbankname) {
        this.bankdepositbankname = bankdepositbankname;
    }

    public Integer getBankdepositbankid() {
        return this.bankdepositbankid;
    }

    public Receiptpaymentsdetails bankdepositbankid(Integer bankdepositbankid) {
        this.setBankdepositbankid(bankdepositbankid);
        return this;
    }

    public void setBankdepositbankid(Integer bankdepositbankid) {
        this.bankdepositbankid = bankdepositbankid;
    }

    public String getBankdepositbankbranchname() {
        return this.bankdepositbankbranchname;
    }

    public Receiptpaymentsdetails bankdepositbankbranchname(String bankdepositbankbranchname) {
        this.setBankdepositbankbranchname(bankdepositbankbranchname);
        return this;
    }

    public void setBankdepositbankbranchname(String bankdepositbankbranchname) {
        this.bankdepositbankbranchname = bankdepositbankbranchname;
    }

    public Integer getBankdepositbankbranchid() {
        return this.bankdepositbankbranchid;
    }

    public Receiptpaymentsdetails bankdepositbankbranchid(Integer bankdepositbankbranchid) {
        this.setBankdepositbankbranchid(bankdepositbankbranchid);
        return this;
    }

    public void setBankdepositbankbranchid(Integer bankdepositbankbranchid) {
        this.bankdepositbankbranchid = bankdepositbankbranchid;
    }

    public Float getReturnchequefine() {
        return this.returnchequefine;
    }

    public Receiptpaymentsdetails returnchequefine(Float returnchequefine) {
        this.setReturnchequefine(returnchequefine);
        return this;
    }

    public void setReturnchequefine(Float returnchequefine) {
        this.returnchequefine = returnchequefine;
    }

    public Integer getCompanybankid() {
        return this.companybankid;
    }

    public Receiptpaymentsdetails companybankid(Integer companybankid) {
        this.setCompanybankid(companybankid);
        return this;
    }

    public void setCompanybankid(Integer companybankid) {
        this.companybankid = companybankid;
    }

    public Boolean getIsbankreconciliation() {
        return this.isbankreconciliation;
    }

    public Receiptpaymentsdetails isbankreconciliation(Boolean isbankreconciliation) {
        this.setIsbankreconciliation(isbankreconciliation);
        return this;
    }

    public void setIsbankreconciliation(Boolean isbankreconciliation) {
        this.isbankreconciliation = isbankreconciliation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Receiptpaymentsdetails)) {
            return false;
        }
        Receiptpaymentsdetails that = (Receiptpaymentsdetails) o;
        return Objects.equals(id, that.id) && Objects.equals(lineid, that.lineid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lineid);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Receiptpaymentsdetails{" +
            "id=" + getId() +
            ", lineid=" + getLineid() +
            ", paymentamount=" + getPaymentamount() +
            ", totalreceiptamount=" + getTotalreceiptamount() +
            ", checkqueamount=" + getCheckqueamount() +
            ", checkqueno='" + getCheckqueno() + "'" +
            ", checkquedate='" + getCheckquedate() + "'" +
            ", checkqueexpiredate='" + getCheckqueexpiredate() + "'" +
            ", bankname='" + getBankname() + "'" +
            ", bankid=" + getBankid() +
            ", bankbranchname='" + getBankbranchname() + "'" +
            ", bankbranchid=" + getBankbranchid() +
            ", creditcardno='" + getCreditcardno() + "'" +
            ", creditcardamount=" + getCreditcardamount() +
            ", reference='" + getReference() + "'" +
            ", otherdetails='" + getOtherdetails() + "'" +
            ", lmu='" + getLmu() + "'" +
            ", lmd='" + getLmd() + "'" +
            ", termid=" + getTermid() +
            ", termname='" + getTermname() + "'" +
            ", accountno='" + getAccountno() + "'" +
            ", accountnumber='" + getAccountnumber() + "'" +
            ", chequereturndate='" + getChequereturndate() + "'" +
            ", isdeposit='" + getIsdeposit() + "'" +
            ", depositeddate='" + getDepositeddate() + "'" +
            ", chequestatuschangeddate='" + getChequestatuschangeddate() + "'" +
            ", returnchequesttledate='" + getReturnchequesttledate() + "'" +
            ", chequestatusid=" + getChequestatusid() +
            ", isPdCheque='" + getIsPdCheque() + "'" +
            ", depositdate='" + getDepositdate() + "'" +
            ", accountid=" + getAccountid() +
            ", accountcode='" + getAccountcode() + "'" +
            ", bankdepositbankname='" + getBankdepositbankname() + "'" +
            ", bankdepositbankid=" + getBankdepositbankid() +
            ", bankdepositbankbranchname='" + getBankdepositbankbranchname() + "'" +
            ", bankdepositbankbranchid=" + getBankdepositbankbranchid() +
            ", returnchequefine=" + getReturnchequefine() +
            ", companybankid=" + getCompanybankid() +
            ", isbankreconciliation='" + getIsbankreconciliation() + "'" +
            "}";
    }
}
