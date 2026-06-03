package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A VoucherPaymentsDetails.
 */
@Entity
@Table(name = "voucherpaymentsdetails")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoucherPaymentsDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lineid")
    private Integer lineID;

    @Column(name = "paymentamount")
    private Float paymentAmount;

    @Column(name = "totalvoucheramount")
    private Float totalVoucherAmount;

    @Column(name = "checkqueamount")
    private Float checkqueAmount;

    @Column(name = "checkqueno")
    private String checkqueNo;

    @Column(name = "checkquedate")
    private Instant checkqueDate;

    @Column(name = "checkqueexpiredate")
    private Instant checkqueExpireDate;

    @Column(name = "bankname")
    private String bankName;

    @Column(name = "bankid")
    private Integer bankID;

    @Column(name = "creditcardno")
    private String creditCardNo;

    @Column(name = "creditcardamount")
    private Float creditCardAmount;

    @Column(name = "reference")
    private String reference;

    @Column(name = "otherdetails")
    private String otherDetails;

    @Column(name = "lmu")
    private String lmu;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "termid")
    private Integer termID;

    @Column(name = "termname")
    private String termName;

    @Column(name = "accountno")
    private Integer accountNo;

    @Column(name = "accountnumber")
    private Long accountNumber;

    @Column(name = "accountid")
    private Integer accountId;

    @Column(name = "accountcode")
    private String accountCode;

    @Column(name = "chequestatusid")
    private Integer chequeStatusId;

    @Column(name = "isdeposit")
    private Boolean isDeposit;

    @Column(name = "depositeddate")
    private Instant depositedDate;

    @Column(name = "companybankid")
    private Integer companyBankId;

    @Column(name = "isbankreconciliation")
    private Boolean isBankReconciliation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VoucherPaymentsDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLineID() {
        return this.lineID;
    }

    public VoucherPaymentsDetails lineID(Integer lineID) {
        this.setLineID(lineID);
        return this;
    }

    public void setLineID(Integer lineID) {
        this.lineID = lineID;
    }

    public Float getPaymentAmount() {
        return this.paymentAmount;
    }

    public VoucherPaymentsDetails paymentAmount(Float paymentAmount) {
        this.setPaymentAmount(paymentAmount);
        return this;
    }

    public void setPaymentAmount(Float paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Float getTotalVoucherAmount() {
        return this.totalVoucherAmount;
    }

    public VoucherPaymentsDetails totalVoucherAmount(Float totalVoucherAmount) {
        this.setTotalVoucherAmount(totalVoucherAmount);
        return this;
    }

    public void setTotalVoucherAmount(Float totalVoucherAmount) {
        this.totalVoucherAmount = totalVoucherAmount;
    }

    public Float getCheckqueAmount() {
        return this.checkqueAmount;
    }

    public VoucherPaymentsDetails checkqueAmount(Float checkqueAmount) {
        this.setCheckqueAmount(checkqueAmount);
        return this;
    }

    public void setCheckqueAmount(Float checkqueAmount) {
        this.checkqueAmount = checkqueAmount;
    }

    public String getCheckqueNo() {
        return this.checkqueNo;
    }

    public VoucherPaymentsDetails checkqueNo(String checkqueNo) {
        this.setCheckqueNo(checkqueNo);
        return this;
    }

    public void setCheckqueNo(String checkqueNo) {
        this.checkqueNo = checkqueNo;
    }

    public Instant getCheckqueDate() {
        return this.checkqueDate;
    }

    public VoucherPaymentsDetails checkqueDate(Instant checkqueDate) {
        this.setCheckqueDate(checkqueDate);
        return this;
    }

    public void setCheckqueDate(Instant checkqueDate) {
        this.checkqueDate = checkqueDate;
    }

    public Instant getCheckqueExpireDate() {
        return this.checkqueExpireDate;
    }

    public VoucherPaymentsDetails checkqueExpireDate(Instant checkqueExpireDate) {
        this.setCheckqueExpireDate(checkqueExpireDate);
        return this;
    }

    public void setCheckqueExpireDate(Instant checkqueExpireDate) {
        this.checkqueExpireDate = checkqueExpireDate;
    }

    public String getBankName() {
        return this.bankName;
    }

    public VoucherPaymentsDetails bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getBankID() {
        return this.bankID;
    }

    public VoucherPaymentsDetails bankID(Integer bankID) {
        this.setBankID(bankID);
        return this;
    }

    public void setBankID(Integer bankID) {
        this.bankID = bankID;
    }

    public String getCreditCardNo() {
        return this.creditCardNo;
    }

    public VoucherPaymentsDetails creditCardNo(String creditCardNo) {
        this.setCreditCardNo(creditCardNo);
        return this;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public Float getCreditCardAmount() {
        return this.creditCardAmount;
    }

    public VoucherPaymentsDetails creditCardAmount(Float creditCardAmount) {
        this.setCreditCardAmount(creditCardAmount);
        return this;
    }

    public void setCreditCardAmount(Float creditCardAmount) {
        this.creditCardAmount = creditCardAmount;
    }

    public String getReference() {
        return this.reference;
    }

    public VoucherPaymentsDetails reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getOtherDetails() {
        return this.otherDetails;
    }

    public VoucherPaymentsDetails otherDetails(String otherDetails) {
        this.setOtherDetails(otherDetails);
        return this;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public String getLmu() {
        return this.lmu;
    }

    public VoucherPaymentsDetails lmu(String lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(String lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public VoucherPaymentsDetails lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Integer getTermID() {
        return this.termID;
    }

    public VoucherPaymentsDetails termID(Integer termID) {
        this.setTermID(termID);
        return this;
    }

    public void setTermID(Integer termID) {
        this.termID = termID;
    }

    public String getTermName() {
        return this.termName;
    }

    public VoucherPaymentsDetails termName(String termName) {
        this.setTermName(termName);
        return this;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public Integer getAccountNo() {
        return this.accountNo;
    }

    public VoucherPaymentsDetails accountNo(Integer accountNo) {
        this.setAccountNo(accountNo);
        return this;
    }

    public void setAccountNo(Integer accountNo) {
        this.accountNo = accountNo;
    }

    public Long getAccountNumber() {
        return this.accountNumber;
    }

    public VoucherPaymentsDetails accountNumber(Long accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getAccountId() {
        return this.accountId;
    }

    public VoucherPaymentsDetails accountId(Integer accountId) {
        this.setAccountId(accountId);
        return this;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return this.accountCode;
    }

    public VoucherPaymentsDetails accountCode(String accountCode) {
        this.setAccountCode(accountCode);
        return this;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Integer getChequeStatusId() {
        return this.chequeStatusId;
    }

    public VoucherPaymentsDetails chequeStatusId(Integer chequeStatusId) {
        this.setChequeStatusId(chequeStatusId);
        return this;
    }

    public void setChequeStatusId(Integer chequeStatusId) {
        this.chequeStatusId = chequeStatusId;
    }

    public Boolean getIsDeposit() {
        return this.isDeposit;
    }

    public VoucherPaymentsDetails isDeposit(Boolean isDeposit) {
        this.setIsDeposit(isDeposit);
        return this;
    }

    public void setIsDeposit(Boolean isDeposit) {
        this.isDeposit = isDeposit;
    }

    public Instant getDepositedDate() {
        return this.depositedDate;
    }

    public VoucherPaymentsDetails depositedDate(Instant depositedDate) {
        this.setDepositedDate(depositedDate);
        return this;
    }

    public void setDepositedDate(Instant depositedDate) {
        this.depositedDate = depositedDate;
    }

    public Integer getCompanyBankId() {
        return this.companyBankId;
    }

    public VoucherPaymentsDetails companyBankId(Integer companyBankId) {
        this.setCompanyBankId(companyBankId);
        return this;
    }

    public void setCompanyBankId(Integer companyBankId) {
        this.companyBankId = companyBankId;
    }

    public Boolean getIsBankReconciliation() {
        return this.isBankReconciliation;
    }

    public VoucherPaymentsDetails isBankReconciliation(Boolean isBankReconciliation) {
        this.setIsBankReconciliation(isBankReconciliation);
        return this;
    }

    public void setIsBankReconciliation(Boolean isBankReconciliation) {
        this.isBankReconciliation = isBankReconciliation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoucherPaymentsDetails)) {
            return false;
        }
        return getId() != null && getId().equals(((VoucherPaymentsDetails) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherPaymentsDetails{" +
            "id=" + getId() +
            ", lineID=" + getLineID() +
            ", paymentAmount=" + getPaymentAmount() +
            ", totalVoucherAmount=" + getTotalVoucherAmount() +
            ", checkqueAmount=" + getCheckqueAmount() +
            ", checkqueNo='" + getCheckqueNo() + "'" +
            ", checkqueDate='" + getCheckqueDate() + "'" +
            ", checkqueExpireDate='" + getCheckqueExpireDate() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", bankID=" + getBankID() +
            ", creditCardNo='" + getCreditCardNo() + "'" +
            ", creditCardAmount=" + getCreditCardAmount() +
            ", reference='" + getReference() + "'" +
            ", otherDetails='" + getOtherDetails() + "'" +
            ", lmu='" + getLmu() + "'" +
            ", lmd='" + getLmd() + "'" +
            ", termID=" + getTermID() +
            ", termName='" + getTermName() + "'" +
            ", accountNo=" + getAccountNo() +
            ", accountNumber=" + getAccountNumber() +
            ", accountId=" + getAccountId() +
            ", accountCode='" + getAccountCode() + "'" +
            ", chequeStatusId=" + getChequeStatusId() +
            ", isDeposit='" + getIsDeposit() + "'" +
            ", depositedDate='" + getDepositedDate() + "'" +
            ", companyBankId=" + getCompanyBankId() +
            ", isBankReconciliation='" + getIsBankReconciliation() + "'" +
            "}";
    }
}
