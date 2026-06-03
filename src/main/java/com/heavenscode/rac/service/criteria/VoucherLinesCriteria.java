package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.VoucherLines} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.VoucherLinesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /voucher-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VoucherLinesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter lineID;

    private StringFilter grnCode;

    private StringFilter grnType;

    private FloatFilter originalAmount;

    private FloatFilter amountOwing;

    private FloatFilter discountAvailable;

    private FloatFilter discountTaken;

    private FloatFilter amountReceived;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private IntegerFilter accountId;

    private Boolean distinct;

    public VoucherLinesCriteria() {}

    public VoucherLinesCriteria(VoucherLinesCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.lineID = other.optionalLineID().map(IntegerFilter::copy).orElse(null);
        this.grnCode = other.optionalGrnCode().map(StringFilter::copy).orElse(null);
        this.grnType = other.optionalGrnType().map(StringFilter::copy).orElse(null);
        this.originalAmount = other.optionalOriginalAmount().map(FloatFilter::copy).orElse(null);
        this.amountOwing = other.optionalAmountOwing().map(FloatFilter::copy).orElse(null);
        this.discountAvailable = other.optionalDiscountAvailable().map(FloatFilter::copy).orElse(null);
        this.discountTaken = other.optionalDiscountTaken().map(FloatFilter::copy).orElse(null);
        this.amountReceived = other.optionalAmountReceived().map(FloatFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.accountId = other.optionalAccountId().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public VoucherLinesCriteria copy() {
        return new VoucherLinesCriteria(this);
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

    public StringFilter getGrnCode() {
        return grnCode;
    }

    public Optional<StringFilter> optionalGrnCode() {
        return Optional.ofNullable(grnCode);
    }

    public StringFilter grnCode() {
        if (grnCode == null) {
            setGrnCode(new StringFilter());
        }
        return grnCode;
    }

    public void setGrnCode(StringFilter grnCode) {
        this.grnCode = grnCode;
    }

    public StringFilter getGrnType() {
        return grnType;
    }

    public Optional<StringFilter> optionalGrnType() {
        return Optional.ofNullable(grnType);
    }

    public StringFilter grnType() {
        if (grnType == null) {
            setGrnType(new StringFilter());
        }
        return grnType;
    }

    public void setGrnType(StringFilter grnType) {
        this.grnType = grnType;
    }

    public FloatFilter getOriginalAmount() {
        return originalAmount;
    }

    public Optional<FloatFilter> optionalOriginalAmount() {
        return Optional.ofNullable(originalAmount);
    }

    public FloatFilter originalAmount() {
        if (originalAmount == null) {
            setOriginalAmount(new FloatFilter());
        }
        return originalAmount;
    }

    public void setOriginalAmount(FloatFilter originalAmount) {
        this.originalAmount = originalAmount;
    }

    public FloatFilter getAmountOwing() {
        return amountOwing;
    }

    public Optional<FloatFilter> optionalAmountOwing() {
        return Optional.ofNullable(amountOwing);
    }

    public FloatFilter amountOwing() {
        if (amountOwing == null) {
            setAmountOwing(new FloatFilter());
        }
        return amountOwing;
    }

    public void setAmountOwing(FloatFilter amountOwing) {
        this.amountOwing = amountOwing;
    }

    public FloatFilter getDiscountAvailable() {
        return discountAvailable;
    }

    public Optional<FloatFilter> optionalDiscountAvailable() {
        return Optional.ofNullable(discountAvailable);
    }

    public FloatFilter discountAvailable() {
        if (discountAvailable == null) {
            setDiscountAvailable(new FloatFilter());
        }
        return discountAvailable;
    }

    public void setDiscountAvailable(FloatFilter discountAvailable) {
        this.discountAvailable = discountAvailable;
    }

    public FloatFilter getDiscountTaken() {
        return discountTaken;
    }

    public Optional<FloatFilter> optionalDiscountTaken() {
        return Optional.ofNullable(discountTaken);
    }

    public FloatFilter discountTaken() {
        if (discountTaken == null) {
            setDiscountTaken(new FloatFilter());
        }
        return discountTaken;
    }

    public void setDiscountTaken(FloatFilter discountTaken) {
        this.discountTaken = discountTaken;
    }

    public FloatFilter getAmountReceived() {
        return amountReceived;
    }

    public Optional<FloatFilter> optionalAmountReceived() {
        return Optional.ofNullable(amountReceived);
    }

    public FloatFilter amountReceived() {
        if (amountReceived == null) {
            setAmountReceived(new FloatFilter());
        }
        return amountReceived;
    }

    public void setAmountReceived(FloatFilter amountReceived) {
        this.amountReceived = amountReceived;
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
        final VoucherLinesCriteria that = (VoucherLinesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(lineID, that.lineID) &&
            Objects.equals(grnCode, that.grnCode) &&
            Objects.equals(grnType, that.grnType) &&
            Objects.equals(originalAmount, that.originalAmount) &&
            Objects.equals(amountOwing, that.amountOwing) &&
            Objects.equals(discountAvailable, that.discountAvailable) &&
            Objects.equals(discountTaken, that.discountTaken) &&
            Objects.equals(amountReceived, that.amountReceived) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(accountId, that.accountId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            lineID,
            grnCode,
            grnType,
            originalAmount,
            amountOwing,
            discountAvailable,
            discountTaken,
            amountReceived,
            lmu,
            lmd,
            accountId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VoucherLinesCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLineID().map(f -> "lineID=" + f + ", ").orElse("") +
            optionalGrnCode().map(f -> "grnCode=" + f + ", ").orElse("") +
            optionalGrnType().map(f -> "grnType=" + f + ", ").orElse("") +
            optionalOriginalAmount().map(f -> "originalAmount=" + f + ", ").orElse("") +
            optionalAmountOwing().map(f -> "amountOwing=" + f + ", ").orElse("") +
            optionalDiscountAvailable().map(f -> "discountAvailable=" + f + ", ").orElse("") +
            optionalDiscountTaken().map(f -> "discountTaken=" + f + ", ").orElse("") +
            optionalAmountReceived().map(f -> "amountReceived=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalAccountId().map(f -> "accountId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
