package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Billingserviceoptionvalues} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.BillingserviceoptionvaluesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /billingserviceoptionvalues?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BillingserviceoptionvaluesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter vehicletypeid;

    private IntegerFilter billingserviceoptionid;

    private FloatFilter value;

    private InstantFilter lmd;

    private IntegerFilter lmu;

    private Boolean distinct;

    public BillingserviceoptionvaluesCriteria() {}

    public BillingserviceoptionvaluesCriteria(BillingserviceoptionvaluesCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.vehicletypeid = other.optionalVehicletypeid().map(IntegerFilter::copy).orElse(null);
        this.billingserviceoptionid = other.optionalBillingserviceoptionid().map(IntegerFilter::copy).orElse(null);
        this.value = other.optionalValue().map(FloatFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public BillingserviceoptionvaluesCriteria copy() {
        return new BillingserviceoptionvaluesCriteria(this);
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

    public IntegerFilter getVehicletypeid() {
        return vehicletypeid;
    }

    public Optional<IntegerFilter> optionalVehicletypeid() {
        return Optional.ofNullable(vehicletypeid);
    }

    public IntegerFilter vehicletypeid() {
        if (vehicletypeid == null) {
            setVehicletypeid(new IntegerFilter());
        }
        return vehicletypeid;
    }

    public void setVehicletypeid(IntegerFilter vehicletypeid) {
        this.vehicletypeid = vehicletypeid;
    }

    public IntegerFilter getBillingserviceoptionid() {
        return billingserviceoptionid;
    }

    public Optional<IntegerFilter> optionalBillingserviceoptionid() {
        return Optional.ofNullable(billingserviceoptionid);
    }

    public IntegerFilter billingserviceoptionid() {
        if (billingserviceoptionid == null) {
            setBillingserviceoptionid(new IntegerFilter());
        }
        return billingserviceoptionid;
    }

    public void setBillingserviceoptionid(IntegerFilter billingserviceoptionid) {
        this.billingserviceoptionid = billingserviceoptionid;
    }

    public FloatFilter getValue() {
        return value;
    }

    public Optional<FloatFilter> optionalValue() {
        return Optional.ofNullable(value);
    }

    public FloatFilter value() {
        if (value == null) {
            setValue(new FloatFilter());
        }
        return value;
    }

    public void setValue(FloatFilter value) {
        this.value = value;
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
        final BillingserviceoptionvaluesCriteria that = (BillingserviceoptionvaluesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(vehicletypeid, that.vehicletypeid) &&
            Objects.equals(billingserviceoptionid, that.billingserviceoptionid) &&
            Objects.equals(value, that.value) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicletypeid, billingserviceoptionid, value, lmd, lmu, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillingserviceoptionvaluesCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalVehicletypeid().map(f -> "vehicletypeid=" + f + ", ").orElse("") +
            optionalBillingserviceoptionid().map(f -> "billingserviceoptionid=" + f + ", ").orElse("") +
            optionalValue().map(f -> "value=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
