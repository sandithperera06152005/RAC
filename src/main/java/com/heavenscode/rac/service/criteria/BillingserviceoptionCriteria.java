package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Billingserviceoption} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.BillingserviceoptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /billingserviceoptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BillingserviceoptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter servicename;

    private StringFilter servicediscription;

    private BooleanFilter isactive;

    private InstantFilter lmd;

    private IntegerFilter lmu;

    private IntegerFilter orderby;

    private BooleanFilter billtocustomer;

    private Boolean distinct;

    public BillingserviceoptionCriteria() {}

    public BillingserviceoptionCriteria(BillingserviceoptionCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.servicename = other.optionalServicename().map(StringFilter::copy).orElse(null);
        this.servicediscription = other.optionalServicediscription().map(StringFilter::copy).orElse(null);
        this.isactive = other.optionalIsactive().map(BooleanFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.orderby = other.optionalOrderby().map(IntegerFilter::copy).orElse(null);
        this.billtocustomer = other.optionalBilltocustomer().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BillingserviceoptionCriteria copy() {
        return new BillingserviceoptionCriteria(this);
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

    public StringFilter getServicename() {
        return servicename;
    }

    public Optional<StringFilter> optionalServicename() {
        return Optional.ofNullable(servicename);
    }

    public StringFilter servicename() {
        if (servicename == null) {
            setServicename(new StringFilter());
        }
        return servicename;
    }

    public void setServicename(StringFilter servicename) {
        this.servicename = servicename;
    }

    public StringFilter getServicediscription() {
        return servicediscription;
    }

    public Optional<StringFilter> optionalServicediscription() {
        return Optional.ofNullable(servicediscription);
    }

    public StringFilter servicediscription() {
        if (servicediscription == null) {
            setServicediscription(new StringFilter());
        }
        return servicediscription;
    }

    public void setServicediscription(StringFilter servicediscription) {
        this.servicediscription = servicediscription;
    }

    public BooleanFilter getIsactive() {
        return isactive;
    }

    public Optional<BooleanFilter> optionalIsactive() {
        return Optional.ofNullable(isactive);
    }

    public BooleanFilter isactive() {
        if (isactive == null) {
            setIsactive(new BooleanFilter());
        }
        return isactive;
    }

    public void setIsactive(BooleanFilter isactive) {
        this.isactive = isactive;
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

    public IntegerFilter getOrderby() {
        return orderby;
    }

    public Optional<IntegerFilter> optionalOrderby() {
        return Optional.ofNullable(orderby);
    }

    public IntegerFilter orderby() {
        if (orderby == null) {
            setOrderby(new IntegerFilter());
        }
        return orderby;
    }

    public void setOrderby(IntegerFilter orderby) {
        this.orderby = orderby;
    }

    public BooleanFilter getBilltocustomer() {
        return billtocustomer;
    }

    public Optional<BooleanFilter> optionalBilltocustomer() {
        return Optional.ofNullable(billtocustomer);
    }

    public BooleanFilter billtocustomer() {
        if (billtocustomer == null) {
            setBilltocustomer(new BooleanFilter());
        }
        return billtocustomer;
    }

    public void setBilltocustomer(BooleanFilter billtocustomer) {
        this.billtocustomer = billtocustomer;
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
        final BillingserviceoptionCriteria that = (BillingserviceoptionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(servicename, that.servicename) &&
            Objects.equals(servicediscription, that.servicediscription) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(orderby, that.orderby) &&
            Objects.equals(billtocustomer, that.billtocustomer) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, servicename, servicediscription, isactive, lmd, lmu, orderby, billtocustomer, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillingserviceoptionCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalServicename().map(f -> "servicename=" + f + ", ").orElse("") +
            optionalServicediscription().map(f -> "servicediscription=" + f + ", ").orElse("") +
            optionalIsactive().map(f -> "isactive=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalOrderby().map(f -> "orderby=" + f + ", ").orElse("") +
            optionalBilltocustomer().map(f -> "billtocustomer=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
