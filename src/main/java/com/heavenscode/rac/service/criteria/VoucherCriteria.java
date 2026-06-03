package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Voucher} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.VoucherResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vouchers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoucherCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private InstantFilter voucherDate;

    private StringFilter supplierName;

    private StringFilter supplierAddress;

    private FloatFilter totalAmount;

    private StringFilter totalAmountInWord;

    private StringFilter comments;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private IntegerFilter termId;

    private StringFilter term;

    private InstantFilter date;

    private FloatFilter amountPaid;

    private IntegerFilter supplierID;

    private BooleanFilter isActive;

    private IntegerFilter createdBy;

    private Boolean distinct;

    public VoucherCriteria() {}

    public VoucherCriteria(VoucherCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.voucherDate = other.optionalVoucherDate().map(InstantFilter::copy).orElse(null);
        this.supplierName = other.optionalSupplierName().map(StringFilter::copy).orElse(null);
        this.supplierAddress = other.optionalSupplierAddress().map(StringFilter::copy).orElse(null);
        this.totalAmount = other.optionalTotalAmount().map(FloatFilter::copy).orElse(null);
        this.totalAmountInWord = other.optionalTotalAmountInWord().map(StringFilter::copy).orElse(null);
        this.comments = other.optionalComments().map(StringFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.termId = other.optionalTermId().map(IntegerFilter::copy).orElse(null);
        this.term = other.optionalTerm().map(StringFilter::copy).orElse(null);
        this.date = other.optionalDate().map(InstantFilter::copy).orElse(null);
        this.amountPaid = other.optionalAmountPaid().map(FloatFilter::copy).orElse(null);
        this.supplierID = other.optionalSupplierID().map(IntegerFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.createdBy = other.optionalCreatedBy().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public VoucherCriteria copy() {
        return new VoucherCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public Optional<StringFilter> optionalCode() {
        return Optional.ofNullable(code);
    }

    public StringFilter code() {
        if (code == null) {
            setCode(new StringFilter());
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public InstantFilter getVoucherDate() {
        return voucherDate;
    }

    public Optional<InstantFilter> optionalVoucherDate() {
        return Optional.ofNullable(voucherDate);
    }

    public InstantFilter voucherDate() {
        if (voucherDate == null) {
            setVoucherDate(new InstantFilter());
        }
        return voucherDate;
    }

    public void setVoucherDate(InstantFilter voucherDate) {
        this.voucherDate = voucherDate;
    }

    public StringFilter getSupplierName() {
        return supplierName;
    }

    public Optional<StringFilter> optionalSupplierName() {
        return Optional.ofNullable(supplierName);
    }

    public StringFilter supplierName() {
        if (supplierName == null) {
            setSupplierName(new StringFilter());
        }
        return supplierName;
    }

    public void setSupplierName(StringFilter supplierName) {
        this.supplierName = supplierName;
    }

    public StringFilter getSupplierAddress() {
        return supplierAddress;
    }

    public Optional<StringFilter> optionalSupplierAddress() {
        return Optional.ofNullable(supplierAddress);
    }

    public StringFilter supplierAddress() {
        if (supplierAddress == null) {
            setSupplierAddress(new StringFilter());
        }
        return supplierAddress;
    }

    public void setSupplierAddress(StringFilter supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public FloatFilter getTotalAmount() {
        return totalAmount;
    }

    public Optional<FloatFilter> optionalTotalAmount() {
        return Optional.ofNullable(totalAmount);
    }

    public FloatFilter totalAmount() {
        if (totalAmount == null) {
            setTotalAmount(new FloatFilter());
        }
        return totalAmount;
    }

    public void setTotalAmount(FloatFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public StringFilter getTotalAmountInWord() {
        return totalAmountInWord;
    }

    public Optional<StringFilter> optionalTotalAmountInWord() {
        return Optional.ofNullable(totalAmountInWord);
    }

    public StringFilter totalAmountInWord() {
        if (totalAmountInWord == null) {
            setTotalAmountInWord(new StringFilter());
        }
        return totalAmountInWord;
    }

    public void setTotalAmountInWord(StringFilter totalAmountInWord) {
        this.totalAmountInWord = totalAmountInWord;
    }

    public StringFilter getComments() {
        return comments;
    }

    public Optional<StringFilter> optionalComments() {
        return Optional.ofNullable(comments);
    }

    public StringFilter comments() {
        if (comments == null) {
            setComments(new StringFilter());
        }
        return comments;
    }

    public void setComments(StringFilter comments) {
        this.comments = comments;
    }

    public IntegerFilter getLmu() {
        return lmu;
    }

    public Optional<IntegerFilter> optionalLmu() {
        return Optional.ofNullable(lmu);
    }

    public IntegerFilter lmu() {
        if (lmu == null) {
            setLmu(new IntegerFilter());
        }
        return lmu;
    }

    public void setLmu(IntegerFilter lmu) {
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

    public IntegerFilter getTermId() {
        return termId;
    }

    public Optional<IntegerFilter> optionalTermId() {
        return Optional.ofNullable(termId);
    }

    public IntegerFilter termId() {
        if (termId == null) {
            setTermId(new IntegerFilter());
        }
        return termId;
    }

    public void setTermId(IntegerFilter termId) {
        this.termId = termId;
    }

    public StringFilter getTerm() {
        return term;
    }

    public Optional<StringFilter> optionalTerm() {
        return Optional.ofNullable(term);
    }

    public StringFilter term() {
        if (term == null) {
            setTerm(new StringFilter());
        }
        return term;
    }

    public void setTerm(StringFilter term) {
        this.term = term;
    }

    public InstantFilter getDate() {
        return date;
    }

    public Optional<InstantFilter> optionalDate() {
        return Optional.ofNullable(date);
    }

    public InstantFilter date() {
        if (date == null) {
            setDate(new InstantFilter());
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
    }

    public FloatFilter getAmountPaid() {
        return amountPaid;
    }

    public Optional<FloatFilter> optionalAmountPaid() {
        return Optional.ofNullable(amountPaid);
    }

    public FloatFilter amountPaid() {
        if (amountPaid == null) {
            setAmountPaid(new FloatFilter());
        }
        return amountPaid;
    }

    public void setAmountPaid(FloatFilter amountPaid) {
        this.amountPaid = amountPaid;
    }

    public IntegerFilter getSupplierID() {
        return supplierID;
    }

    public Optional<IntegerFilter> optionalSupplierID() {
        return Optional.ofNullable(supplierID);
    }

    public IntegerFilter supplierID() {
        if (supplierID == null) {
            setSupplierID(new IntegerFilter());
        }
        return supplierID;
    }

    public void setSupplierID(IntegerFilter supplierID) {
        this.supplierID = supplierID;
    }

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public Optional<BooleanFilter> optionalIsActive() {
        return Optional.ofNullable(isActive);
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            setIsActive(new BooleanFilter());
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public IntegerFilter getCreatedBy() {
        return createdBy;
    }

    public Optional<IntegerFilter> optionalCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    public IntegerFilter createdBy() {
        if (createdBy == null) {
            setCreatedBy(new IntegerFilter());
        }
        return createdBy;
    }

    public void setCreatedBy(IntegerFilter createdBy) {
        this.createdBy = createdBy;
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
        final VoucherCriteria that = (VoucherCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(voucherDate, that.voucherDate) &&
            Objects.equals(supplierName, that.supplierName) &&
            Objects.equals(supplierAddress, that.supplierAddress) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(totalAmountInWord, that.totalAmountInWord) &&
            Objects.equals(comments, that.comments) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(termId, that.termId) &&
            Objects.equals(term, that.term) &&
            Objects.equals(date, that.date) &&
            Objects.equals(amountPaid, that.amountPaid) &&
            Objects.equals(supplierID, that.supplierID) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            voucherDate,
            supplierName,
            supplierAddress,
            totalAmount,
            totalAmountInWord,
            comments,
            lmu,
            lmd,
            termId,
            term,
            date,
            amountPaid,
            supplierID,
            isActive,
            createdBy,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalVoucherDate().map(f -> "voucherDate=" + f + ", ").orElse("") +
            optionalSupplierName().map(f -> "supplierName=" + f + ", ").orElse("") +
            optionalSupplierAddress().map(f -> "supplierAddress=" + f + ", ").orElse("") +
            optionalTotalAmount().map(f -> "totalAmount=" + f + ", ").orElse("") +
            optionalTotalAmountInWord().map(f -> "totalAmountInWord=" + f + ", ").orElse("") +
            optionalComments().map(f -> "comments=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalTermId().map(f -> "termId=" + f + ", ").orElse("") +
            optionalTerm().map(f -> "term=" + f + ", ").orElse("") +
            optionalDate().map(f -> "date=" + f + ", ").orElse("") +
            optionalAmountPaid().map(f -> "amountPaid=" + f + ", ").orElse("") +
            optionalSupplierID().map(f -> "supplierID=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalCreatedBy().map(f -> "createdBy=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
