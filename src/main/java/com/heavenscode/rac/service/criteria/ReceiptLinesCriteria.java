package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.ReceiptLines} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.ReceiptLinesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /receipt-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReceiptLinesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter lineid;

    private StringFilter invoicecode;

    private StringFilter invoicetype;

    private DoubleFilter originalamount;

    private DoubleFilter amountowing;

    private DoubleFilter discountavailable;

    private DoubleFilter discounttaken;

    private DoubleFilter amountreceived;

    private LongFilter lmu;

    private InstantFilter lmd;

    private LongFilter accountid;

    private Boolean distinct;

    public ReceiptLinesCriteria() {}

    public ReceiptLinesCriteria(ReceiptLinesCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.lineid = other.optionalLineid().map(LongFilter::copy).orElse(null);
        this.invoicecode = other.optionalInvoicecode().map(StringFilter::copy).orElse(null);
        this.invoicetype = other.optionalInvoicetype().map(StringFilter::copy).orElse(null);
        this.originalamount = other.optionalOriginalamount().map(DoubleFilter::copy).orElse(null);
        this.amountowing = other.optionalAmountowing().map(DoubleFilter::copy).orElse(null);
        this.discountavailable = other.optionalDiscountavailable().map(DoubleFilter::copy).orElse(null);
        this.discounttaken = other.optionalDiscounttaken().map(DoubleFilter::copy).orElse(null);
        this.amountreceived = other.optionalAmountreceived().map(DoubleFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(LongFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.accountid = other.optionalAccountid().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ReceiptLinesCriteria copy() {
        return new ReceiptLinesCriteria(this);
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

    public LongFilter getLineid() {
        return lineid;
    }

    public Optional<LongFilter> optionalLineid() {
        return Optional.ofNullable(lineid);
    }

    public LongFilter lineid() {
        if (lineid == null) {
            setLineid(new LongFilter());
        }
        return lineid;
    }

    public void setLineid(LongFilter lineid) {
        this.lineid = lineid;
    }

    public StringFilter getInvoicecode() {
        return invoicecode;
    }

    public Optional<StringFilter> optionalInvoicecode() {
        return Optional.ofNullable(invoicecode);
    }

    public StringFilter invoicecode() {
        if (invoicecode == null) {
            setInvoicecode(new StringFilter());
        }
        return invoicecode;
    }

    public void setInvoicecode(StringFilter invoicecode) {
        this.invoicecode = invoicecode;
    }

    public StringFilter getInvoicetype() {
        return invoicetype;
    }

    public Optional<StringFilter> optionalInvoicetype() {
        return Optional.ofNullable(invoicetype);
    }

    public StringFilter invoicetype() {
        if (invoicetype == null) {
            setInvoicetype(new StringFilter());
        }
        return invoicetype;
    }

    public void setInvoicetype(StringFilter invoicetype) {
        this.invoicetype = invoicetype;
    }

    public DoubleFilter getOriginalamount() {
        return originalamount;
    }

    public Optional<DoubleFilter> optionalOriginalamount() {
        return Optional.ofNullable(originalamount);
    }

    public DoubleFilter originalamount() {
        if (originalamount == null) {
            setOriginalamount(new DoubleFilter());
        }
        return originalamount;
    }

    public void setOriginalamount(DoubleFilter originalamount) {
        this.originalamount = originalamount;
    }

    public DoubleFilter getAmountowing() {
        return amountowing;
    }

    public Optional<DoubleFilter> optionalAmountowing() {
        return Optional.ofNullable(amountowing);
    }

    public DoubleFilter amountowing() {
        if (amountowing == null) {
            setAmountowing(new DoubleFilter());
        }
        return amountowing;
    }

    public void setAmountowing(DoubleFilter amountowing) {
        this.amountowing = amountowing;
    }

    public DoubleFilter getDiscountavailable() {
        return discountavailable;
    }

    public Optional<DoubleFilter> optionalDiscountavailable() {
        return Optional.ofNullable(discountavailable);
    }

    public DoubleFilter discountavailable() {
        if (discountavailable == null) {
            setDiscountavailable(new DoubleFilter());
        }
        return discountavailable;
    }

    public void setDiscountavailable(DoubleFilter discountavailable) {
        this.discountavailable = discountavailable;
    }

    public DoubleFilter getDiscounttaken() {
        return discounttaken;
    }

    public Optional<DoubleFilter> optionalDiscounttaken() {
        return Optional.ofNullable(discounttaken);
    }

    public DoubleFilter discounttaken() {
        if (discounttaken == null) {
            setDiscounttaken(new DoubleFilter());
        }
        return discounttaken;
    }

    public void setDiscounttaken(DoubleFilter discounttaken) {
        this.discounttaken = discounttaken;
    }

    public DoubleFilter getAmountreceived() {
        return amountreceived;
    }

    public Optional<DoubleFilter> optionalAmountreceived() {
        return Optional.ofNullable(amountreceived);
    }

    public DoubleFilter amountreceived() {
        if (amountreceived == null) {
            setAmountreceived(new DoubleFilter());
        }
        return amountreceived;
    }

    public void setAmountreceived(DoubleFilter amountreceived) {
        this.amountreceived = amountreceived;
    }

    public LongFilter getLmu() {
        return lmu;
    }

    public Optional<LongFilter> optionalLmu() {
        return Optional.ofNullable(lmu);
    }

    public LongFilter lmu() {
        if (lmu == null) {
            setLmu(new LongFilter());
        }
        return lmu;
    }

    public void setLmu(LongFilter lmu) {
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

    public LongFilter getAccountid() {
        return accountid;
    }

    public Optional<LongFilter> optionalAccountid() {
        return Optional.ofNullable(accountid);
    }

    public LongFilter accountid() {
        if (accountid == null) {
            setAccountid(new LongFilter());
        }
        return accountid;
    }

    public void setAccountid(LongFilter accountid) {
        this.accountid = accountid;
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
        final ReceiptLinesCriteria that = (ReceiptLinesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(lineid, that.lineid) &&
            Objects.equals(invoicecode, that.invoicecode) &&
            Objects.equals(invoicetype, that.invoicetype) &&
            Objects.equals(originalamount, that.originalamount) &&
            Objects.equals(amountowing, that.amountowing) &&
            Objects.equals(discountavailable, that.discountavailable) &&
            Objects.equals(discounttaken, that.discounttaken) &&
            Objects.equals(amountreceived, that.amountreceived) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(accountid, that.accountid) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            lineid,
            invoicecode,
            invoicetype,
            originalamount,
            amountowing,
            discountavailable,
            discounttaken,
            amountreceived,
            lmu,
            lmd,
            accountid,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReceiptLinesCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLineid().map(f -> "lineid=" + f + ", ").orElse("") +
            optionalInvoicecode().map(f -> "invoicecode=" + f + ", ").orElse("") +
            optionalInvoicetype().map(f -> "invoicetype=" + f + ", ").orElse("") +
            optionalOriginalamount().map(f -> "originalamount=" + f + ", ").orElse("") +
            optionalAmountowing().map(f -> "amountowing=" + f + ", ").orElse("") +
            optionalDiscountavailable().map(f -> "discountavailable=" + f + ", ").orElse("") +
            optionalDiscounttaken().map(f -> "discounttaken=" + f + ", ").orElse("") +
            optionalAmountreceived().map(f -> "amountreceived=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalAccountid().map(f -> "accountid=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
