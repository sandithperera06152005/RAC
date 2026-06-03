package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.VoucherPaymentsDetails} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.VoucherPaymentsDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /voucher-payments-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoucherPaymentsDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter lineID;

    private FloatFilter paymentAmount;

    private FloatFilter totalVoucherAmount;

    private FloatFilter checkqueAmount;

    private StringFilter checkqueNo;

    private InstantFilter checkqueDate;

    private InstantFilter checkqueExpireDate;

    private StringFilter bankName;

    private IntegerFilter bankID;

    private StringFilter creditCardNo;

    private FloatFilter creditCardAmount;

    private StringFilter reference;

    private StringFilter otherDetails;

    private StringFilter lmu;

    private InstantFilter lmd;

    private IntegerFilter termID;

    private StringFilter termName;

    private IntegerFilter accountNo;

    private LongFilter accountNumber;

    private IntegerFilter accountId;

    private StringFilter accountCode;

    private IntegerFilter chequeStatusId;

    private BooleanFilter isDeposit;

    private InstantFilter depositedDate;

    private IntegerFilter companyBankId;

    private BooleanFilter isBankReconciliation;

    private Boolean distinct;

    public VoucherPaymentsDetailsCriteria() {}

    public VoucherPaymentsDetailsCriteria(VoucherPaymentsDetailsCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.lineID = other.optionalLineID().map(IntegerFilter::copy).orElse(null);
        this.paymentAmount = other.optionalPaymentAmount().map(FloatFilter::copy).orElse(null);
        this.totalVoucherAmount = other.optionalTotalVoucherAmount().map(FloatFilter::copy).orElse(null);
        this.checkqueAmount = other.optionalCheckqueAmount().map(FloatFilter::copy).orElse(null);
        this.checkqueNo = other.optionalCheckqueNo().map(StringFilter::copy).orElse(null);
        this.checkqueDate = other.optionalCheckqueDate().map(InstantFilter::copy).orElse(null);
        this.checkqueExpireDate = other.optionalCheckqueExpireDate().map(InstantFilter::copy).orElse(null);
        this.bankName = other.optionalBankName().map(StringFilter::copy).orElse(null);
        this.bankID = other.optionalBankID().map(IntegerFilter::copy).orElse(null);
        this.creditCardNo = other.optionalCreditCardNo().map(StringFilter::copy).orElse(null);
        this.creditCardAmount = other.optionalCreditCardAmount().map(FloatFilter::copy).orElse(null);
        this.reference = other.optionalReference().map(StringFilter::copy).orElse(null);
        this.otherDetails = other.optionalOtherDetails().map(StringFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(StringFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.termID = other.optionalTermID().map(IntegerFilter::copy).orElse(null);
        this.termName = other.optionalTermName().map(StringFilter::copy).orElse(null);
        this.accountNo = other.optionalAccountNo().map(IntegerFilter::copy).orElse(null);
        this.accountNumber = other.optionalAccountNumber().map(LongFilter::copy).orElse(null);
        this.accountId = other.optionalAccountId().map(IntegerFilter::copy).orElse(null);
        this.accountCode = other.optionalAccountCode().map(StringFilter::copy).orElse(null);
        this.chequeStatusId = other.optionalChequeStatusId().map(IntegerFilter::copy).orElse(null);
        this.isDeposit = other.optionalIsDeposit().map(BooleanFilter::copy).orElse(null);
        this.depositedDate = other.optionalDepositedDate().map(InstantFilter::copy).orElse(null);
        this.companyBankId = other.optionalCompanyBankId().map(IntegerFilter::copy).orElse(null);
        this.isBankReconciliation = other.optionalIsBankReconciliation().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public VoucherPaymentsDetailsCriteria copy() {
        return new VoucherPaymentsDetailsCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getLineID() {
        return lineID;
    }

    public Optional<IntegerFilter> optionalLineID() {
        return Optional.ofNullable(lineID);
    }

    public IntegerFilter lineID() {
        if (lineID == null) {
            setLineID(new IntegerFilter());
        }
        return lineID;
    }

    public void setLineID(IntegerFilter lineID) {
        this.lineID = lineID;
    }

    public FloatFilter getPaymentAmount() {
        return paymentAmount;
    }

    public Optional<FloatFilter> optionalPaymentAmount() {
        return Optional.ofNullable(paymentAmount);
    }

    public FloatFilter paymentAmount() {
        if (paymentAmount == null) {
            setPaymentAmount(new FloatFilter());
        }
        return paymentAmount;
    }

    public void setPaymentAmount(FloatFilter paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public FloatFilter getTotalVoucherAmount() {
        return totalVoucherAmount;
    }

    public Optional<FloatFilter> optionalTotalVoucherAmount() {
        return Optional.ofNullable(totalVoucherAmount);
    }

    public FloatFilter totalVoucherAmount() {
        if (totalVoucherAmount == null) {
            setTotalVoucherAmount(new FloatFilter());
        }
        return totalVoucherAmount;
    }

    public void setTotalVoucherAmount(FloatFilter totalVoucherAmount) {
        this.totalVoucherAmount = totalVoucherAmount;
    }

    public FloatFilter getCheckqueAmount() {
        return checkqueAmount;
    }

    public Optional<FloatFilter> optionalCheckqueAmount() {
        return Optional.ofNullable(checkqueAmount);
    }

    public FloatFilter checkqueAmount() {
        if (checkqueAmount == null) {
            setCheckqueAmount(new FloatFilter());
        }
        return checkqueAmount;
    }

    public void setCheckqueAmount(FloatFilter checkqueAmount) {
        this.checkqueAmount = checkqueAmount;
    }

    public StringFilter getCheckqueNo() {
        return checkqueNo;
    }

    public Optional<StringFilter> optionalCheckqueNo() {
        return Optional.ofNullable(checkqueNo);
    }

    public StringFilter checkqueNo() {
        if (checkqueNo == null) {
            setCheckqueNo(new StringFilter());
        }
        return checkqueNo;
    }

    public void setCheckqueNo(StringFilter checkqueNo) {
        this.checkqueNo = checkqueNo;
    }

    public InstantFilter getCheckqueDate() {
        return checkqueDate;
    }

    public Optional<InstantFilter> optionalCheckqueDate() {
        return Optional.ofNullable(checkqueDate);
    }

    public InstantFilter checkqueDate() {
        if (checkqueDate == null) {
            setCheckqueDate(new InstantFilter());
        }
        return checkqueDate;
    }

    public void setCheckqueDate(InstantFilter checkqueDate) {
        this.checkqueDate = checkqueDate;
    }

    public InstantFilter getCheckqueExpireDate() {
        return checkqueExpireDate;
    }

    public Optional<InstantFilter> optionalCheckqueExpireDate() {
        return Optional.ofNullable(checkqueExpireDate);
    }

    public InstantFilter checkqueExpireDate() {
        if (checkqueExpireDate == null) {
            setCheckqueExpireDate(new InstantFilter());
        }
        return checkqueExpireDate;
    }

    public void setCheckqueExpireDate(InstantFilter checkqueExpireDate) {
        this.checkqueExpireDate = checkqueExpireDate;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public Optional<StringFilter> optionalBankName() {
        return Optional.ofNullable(bankName);
    }

    public StringFilter bankName() {
        if (bankName == null) {
            setBankName(new StringFilter());
        }
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public IntegerFilter getBankID() {
        return bankID;
    }

    public Optional<IntegerFilter> optionalBankID() {
        return Optional.ofNullable(bankID);
    }

    public IntegerFilter bankID() {
        if (bankID == null) {
            setBankID(new IntegerFilter());
        }
        return bankID;
    }

    public void setBankID(IntegerFilter bankID) {
        this.bankID = bankID;
    }

    public StringFilter getCreditCardNo() {
        return creditCardNo;
    }

    public Optional<StringFilter> optionalCreditCardNo() {
        return Optional.ofNullable(creditCardNo);
    }

    public StringFilter creditCardNo() {
        if (creditCardNo == null) {
            setCreditCardNo(new StringFilter());
        }
        return creditCardNo;
    }

    public void setCreditCardNo(StringFilter creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public FloatFilter getCreditCardAmount() {
        return creditCardAmount;
    }

    public Optional<FloatFilter> optionalCreditCardAmount() {
        return Optional.ofNullable(creditCardAmount);
    }

    public FloatFilter creditCardAmount() {
        if (creditCardAmount == null) {
            setCreditCardAmount(new FloatFilter());
        }
        return creditCardAmount;
    }

    public void setCreditCardAmount(FloatFilter creditCardAmount) {
        this.creditCardAmount = creditCardAmount;
    }

    public StringFilter getReference() {
        return reference;
    }

    public Optional<StringFilter> optionalReference() {
        return Optional.ofNullable(reference);
    }

    public StringFilter reference() {
        if (reference == null) {
            setReference(new StringFilter());
        }
        return reference;
    }

    public void setReference(StringFilter reference) {
        this.reference = reference;
    }

    public StringFilter getOtherDetails() {
        return otherDetails;
    }

    public Optional<StringFilter> optionalOtherDetails() {
        return Optional.ofNullable(otherDetails);
    }

    public StringFilter otherDetails() {
        if (otherDetails == null) {
            setOtherDetails(new StringFilter());
        }
        return otherDetails;
    }

    public void setOtherDetails(StringFilter otherDetails) {
        this.otherDetails = otherDetails;
    }

    public StringFilter getLmu() {
        return lmu;
    }

    public Optional<StringFilter> optionalLmu() {
        return Optional.ofNullable(lmu);
    }

    public StringFilter lmu() {
        if (lmu == null) {
            setLmu(new StringFilter());
        }
        return lmu;
    }

    public void setLmu(StringFilter lmu) {
        this.lmu = lmu;
    }

    public InstantFilter getLmd() {
        return lmd;
    }

    public Optional<InstantFilter> optionalLmd() {
        return Optional.ofNullable(lmd);
    }

    public InstantFilter lmd() {
        if (lmd == null) {
            setLmd(new InstantFilter());
        }
        return lmd;
    }

    public void setLmd(InstantFilter lmd) {
        this.lmd = lmd;
    }

    public IntegerFilter getTermID() {
        return termID;
    }

    public Optional<IntegerFilter> optionalTermID() {
        return Optional.ofNullable(termID);
    }

    public IntegerFilter termID() {
        if (termID == null) {
            setTermID(new IntegerFilter());
        }
        return termID;
    }

    public void setTermID(IntegerFilter termID) {
        this.termID = termID;
    }

    public StringFilter getTermName() {
        return termName;
    }

    public Optional<StringFilter> optionalTermName() {
        return Optional.ofNullable(termName);
    }

    public StringFilter termName() {
        if (termName == null) {
            setTermName(new StringFilter());
        }
        return termName;
    }

    public void setTermName(StringFilter termName) {
        this.termName = termName;
    }

    public IntegerFilter getAccountNo() {
        return accountNo;
    }

    public Optional<IntegerFilter> optionalAccountNo() {
        return Optional.ofNullable(accountNo);
    }

    public IntegerFilter accountNo() {
        if (accountNo == null) {
            setAccountNo(new IntegerFilter());
        }
        return accountNo;
    }

    public void setAccountNo(IntegerFilter accountNo) {
        this.accountNo = accountNo;
    }

    public LongFilter getAccountNumber() {
        return accountNumber;
    }

    public Optional<LongFilter> optionalAccountNumber() {
        return Optional.ofNullable(accountNumber);
    }

    public LongFilter accountNumber() {
        if (accountNumber == null) {
            setAccountNumber(new LongFilter());
        }
        return accountNumber;
    }

    public void setAccountNumber(LongFilter accountNumber) {
        this.accountNumber = accountNumber;
    }

    public IntegerFilter getAccountId() {
        return accountId;
    }

    public Optional<IntegerFilter> optionalAccountId() {
        return Optional.ofNullable(accountId);
    }

    public IntegerFilter accountId() {
        if (accountId == null) {
            setAccountId(new IntegerFilter());
        }
        return accountId;
    }

    public void setAccountId(IntegerFilter accountId) {
        this.accountId = accountId;
    }

    public StringFilter getAccountCode() {
        return accountCode;
    }

    public Optional<StringFilter> optionalAccountCode() {
        return Optional.ofNullable(accountCode);
    }

    public StringFilter accountCode() {
        if (accountCode == null) {
            setAccountCode(new StringFilter());
        }
        return accountCode;
    }

    public void setAccountCode(StringFilter accountCode) {
        this.accountCode = accountCode;
    }

    public IntegerFilter getChequeStatusId() {
        return chequeStatusId;
    }

    public Optional<IntegerFilter> optionalChequeStatusId() {
        return Optional.ofNullable(chequeStatusId);
    }

    public IntegerFilter chequeStatusId() {
        if (chequeStatusId == null) {
            setChequeStatusId(new IntegerFilter());
        }
        return chequeStatusId;
    }

    public void setChequeStatusId(IntegerFilter chequeStatusId) {
        this.chequeStatusId = chequeStatusId;
    }

    public BooleanFilter getIsDeposit() {
        return isDeposit;
    }

    public Optional<BooleanFilter> optionalIsDeposit() {
        return Optional.ofNullable(isDeposit);
    }

    public BooleanFilter isDeposit() {
        if (isDeposit == null) {
            setIsDeposit(new BooleanFilter());
        }
        return isDeposit;
    }

    public void setIsDeposit(BooleanFilter isDeposit) {
        this.isDeposit = isDeposit;
    }

    public InstantFilter getDepositedDate() {
        return depositedDate;
    }

    public Optional<InstantFilter> optionalDepositedDate() {
        return Optional.ofNullable(depositedDate);
    }

    public InstantFilter depositedDate() {
        if (depositedDate == null) {
            setDepositedDate(new InstantFilter());
        }
        return depositedDate;
    }

    public void setDepositedDate(InstantFilter depositedDate) {
        this.depositedDate = depositedDate;
    }

    public IntegerFilter getCompanyBankId() {
        return companyBankId;
    }

    public Optional<IntegerFilter> optionalCompanyBankId() {
        return Optional.ofNullable(companyBankId);
    }

    public IntegerFilter companyBankId() {
        if (companyBankId == null) {
            setCompanyBankId(new IntegerFilter());
        }
        return companyBankId;
    }

    public void setCompanyBankId(IntegerFilter companyBankId) {
        this.companyBankId = companyBankId;
    }

    public BooleanFilter getIsBankReconciliation() {
        return isBankReconciliation;
    }

    public Optional<BooleanFilter> optionalIsBankReconciliation() {
        return Optional.ofNullable(isBankReconciliation);
    }

    public BooleanFilter isBankReconciliation() {
        if (isBankReconciliation == null) {
            setIsBankReconciliation(new BooleanFilter());
        }
        return isBankReconciliation;
    }

    public void setIsBankReconciliation(BooleanFilter isBankReconciliation) {
        this.isBankReconciliation = isBankReconciliation;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VoucherPaymentsDetailsCriteria that = (VoucherPaymentsDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(lineID, that.lineID) &&
            Objects.equals(paymentAmount, that.paymentAmount) &&
            Objects.equals(totalVoucherAmount, that.totalVoucherAmount) &&
            Objects.equals(checkqueAmount, that.checkqueAmount) &&
            Objects.equals(checkqueNo, that.checkqueNo) &&
            Objects.equals(checkqueDate, that.checkqueDate) &&
            Objects.equals(checkqueExpireDate, that.checkqueExpireDate) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(bankID, that.bankID) &&
            Objects.equals(creditCardNo, that.creditCardNo) &&
            Objects.equals(creditCardAmount, that.creditCardAmount) &&
            Objects.equals(reference, that.reference) &&
            Objects.equals(otherDetails, that.otherDetails) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(termID, that.termID) &&
            Objects.equals(termName, that.termName) &&
            Objects.equals(accountNo, that.accountNo) &&
            Objects.equals(accountNumber, that.accountNumber) &&
            Objects.equals(accountId, that.accountId) &&
            Objects.equals(accountCode, that.accountCode) &&
            Objects.equals(chequeStatusId, that.chequeStatusId) &&
            Objects.equals(isDeposit, that.isDeposit) &&
            Objects.equals(depositedDate, that.depositedDate) &&
            Objects.equals(companyBankId, that.companyBankId) &&
            Objects.equals(isBankReconciliation, that.isBankReconciliation) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            lineID,
            paymentAmount,
            totalVoucherAmount,
            checkqueAmount,
            checkqueNo,
            checkqueDate,
            checkqueExpireDate,
            bankName,
            bankID,
            creditCardNo,
            creditCardAmount,
            reference,
            otherDetails,
            lmu,
            lmd,
            termID,
            termName,
            accountNo,
            accountNumber,
            accountId,
            accountCode,
            chequeStatusId,
            isDeposit,
            depositedDate,
            companyBankId,
            isBankReconciliation,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherPaymentsDetailsCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLineID().map(f -> "lineID=" + f + ", ").orElse("") +
            optionalPaymentAmount().map(f -> "paymentAmount=" + f + ", ").orElse("") +
            optionalTotalVoucherAmount().map(f -> "totalVoucherAmount=" + f + ", ").orElse("") +
            optionalCheckqueAmount().map(f -> "checkqueAmount=" + f + ", ").orElse("") +
            optionalCheckqueNo().map(f -> "checkqueNo=" + f + ", ").orElse("") +
            optionalCheckqueDate().map(f -> "checkqueDate=" + f + ", ").orElse("") +
            optionalCheckqueExpireDate().map(f -> "checkqueExpireDate=" + f + ", ").orElse("") +
            optionalBankName().map(f -> "bankName=" + f + ", ").orElse("") +
            optionalBankID().map(f -> "bankID=" + f + ", ").orElse("") +
            optionalCreditCardNo().map(f -> "creditCardNo=" + f + ", ").orElse("") +
            optionalCreditCardAmount().map(f -> "creditCardAmount=" + f + ", ").orElse("") +
            optionalReference().map(f -> "reference=" + f + ", ").orElse("") +
            optionalOtherDetails().map(f -> "otherDetails=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalTermID().map(f -> "termID=" + f + ", ").orElse("") +
            optionalTermName().map(f -> "termName=" + f + ", ").orElse("") +
            optionalAccountNo().map(f -> "accountNo=" + f + ", ").orElse("") +
            optionalAccountNumber().map(f -> "accountNumber=" + f + ", ").orElse("") +
            optionalAccountId().map(f -> "accountId=" + f + ", ").orElse("") +
            optionalAccountCode().map(f -> "accountCode=" + f + ", ").orElse("") +
            optionalChequeStatusId().map(f -> "chequeStatusId=" + f + ", ").orElse("") +
            optionalIsDeposit().map(f -> "isDeposit=" + f + ", ").orElse("") +
            optionalDepositedDate().map(f -> "depositedDate=" + f + ", ").orElse("") +
            optionalCompanyBankId().map(f -> "companyBankId=" + f + ", ").orElse("") +
            optionalIsBankReconciliation().map(f -> "isBankReconciliation=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
