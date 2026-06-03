package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Autojobsalesinvoiceservicechargeline} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.AutojobsalesinvoiceservicechargelineResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /autojobsalesinvoiceservicechargelines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AutojobsalesinvoiceservicechargelineCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter invoiceid;

    private IntegerFilter lineid;

    private IntegerFilter optionid;

    private StringFilter servicename;

    private StringFilter servicediscription;

    private FloatFilter value;

    private IntegerFilter addedbyid;

    private BooleanFilter iscustomersrvice;

    private FloatFilter discount;

    private FloatFilter serviceprice;

    private Boolean distinct;

    public AutojobsalesinvoiceservicechargelineCriteria() {}

    public AutojobsalesinvoiceservicechargelineCriteria(AutojobsalesinvoiceservicechargelineCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.invoiceid = other.optionalInvoiceid().map(IntegerFilter::copy).orElse(null);
        this.lineid = other.optionalLineid().map(IntegerFilter::copy).orElse(null);
        this.optionid = other.optionalOptionid().map(IntegerFilter::copy).orElse(null);
        this.servicename = other.optionalServicename().map(StringFilter::copy).orElse(null);
        this.servicediscription = other.optionalServicediscription().map(StringFilter::copy).orElse(null);
        this.value = other.optionalValue().map(FloatFilter::copy).orElse(null);
        this.addedbyid = other.optionalAddedbyid().map(IntegerFilter::copy).orElse(null);
        this.iscustomersrvice = other.optionalIscustomersrvice().map(BooleanFilter::copy).orElse(null);
        this.discount = other.optionalDiscount().map(FloatFilter::copy).orElse(null);
        this.serviceprice = other.optionalServiceprice().map(FloatFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AutojobsalesinvoiceservicechargelineCriteria copy() {
        return new AutojobsalesinvoiceservicechargelineCriteria(this);
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

    public IntegerFilter getInvoiceid() {
        return invoiceid;
    }

    public Optional<IntegerFilter> optionalInvoiceid() {
        return Optional.ofNullable(invoiceid);
    }

    public IntegerFilter invoiceid() {
        if (invoiceid == null) {
            setInvoiceid(new IntegerFilter());
        }
        return invoiceid;
    }

    public void setInvoiceid(IntegerFilter invoiceid) {
        this.invoiceid = invoiceid;
    }

    public IntegerFilter getLineid() {
        return lineid;
    }

    public Optional<IntegerFilter> optionalLineid() {
        return Optional.ofNullable(lineid);
    }

    public IntegerFilter lineid() {
        if (lineid == null) {
            setLineid(new IntegerFilter());
        }
        return lineid;
    }

    public void setLineid(IntegerFilter lineid) {
        this.lineid = lineid;
    }

    public IntegerFilter getOptionid() {
        return optionid;
    }

    public Optional<IntegerFilter> optionalOptionid() {
        return Optional.ofNullable(optionid);
    }

    public IntegerFilter optionid() {
        if (optionid == null) {
            setOptionid(new IntegerFilter());
        }
        return optionid;
    }

    public void setOptionid(IntegerFilter optionid) {
        this.optionid = optionid;
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

    public IntegerFilter getAddedbyid() {
        return addedbyid;
    }

    public Optional<IntegerFilter> optionalAddedbyid() {
        return Optional.ofNullable(addedbyid);
    }

    public IntegerFilter addedbyid() {
        if (addedbyid == null) {
            setAddedbyid(new IntegerFilter());
        }
        return addedbyid;
    }

    public void setAddedbyid(IntegerFilter addedbyid) {
        this.addedbyid = addedbyid;
    }

    public BooleanFilter getIscustomersrvice() {
        return iscustomersrvice;
    }

    public Optional<BooleanFilter> optionalIscustomersrvice() {
        return Optional.ofNullable(iscustomersrvice);
    }

    public BooleanFilter iscustomersrvice() {
        if (iscustomersrvice == null) {
            setIscustomersrvice(new BooleanFilter());
        }
        return iscustomersrvice;
    }

    public void setIscustomersrvice(BooleanFilter iscustomersrvice) {
        this.iscustomersrvice = iscustomersrvice;
    }

    public FloatFilter getDiscount() {
        return discount;
    }

    public Optional<FloatFilter> optionalDiscount() {
        return Optional.ofNullable(discount);
    }

    public FloatFilter discount() {
        if (discount == null) {
            setDiscount(new FloatFilter());
        }
        return discount;
    }

    public void setDiscount(FloatFilter discount) {
        this.discount = discount;
    }

    public FloatFilter getServiceprice() {
        return serviceprice;
    }

    public Optional<FloatFilter> optionalServiceprice() {
        return Optional.ofNullable(serviceprice);
    }

    public FloatFilter serviceprice() {
        if (serviceprice == null) {
            setServiceprice(new FloatFilter());
        }
        return serviceprice;
    }

    public void setServiceprice(FloatFilter serviceprice) {
        this.serviceprice = serviceprice;
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
        final AutojobsalesinvoiceservicechargelineCriteria that = (AutojobsalesinvoiceservicechargelineCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceid, that.invoiceid) &&
            Objects.equals(lineid, that.lineid) &&
            Objects.equals(optionid, that.optionid) &&
            Objects.equals(servicename, that.servicename) &&
            Objects.equals(servicediscription, that.servicediscription) &&
            Objects.equals(value, that.value) &&
            Objects.equals(addedbyid, that.addedbyid) &&
            Objects.equals(iscustomersrvice, that.iscustomersrvice) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(serviceprice, that.serviceprice) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            invoiceid,
            lineid,
            optionid,
            servicename,
            servicediscription,
            value,
            addedbyid,
            iscustomersrvice,
            discount,
            serviceprice,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutojobsalesinvoiceservicechargelineCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInvoiceid().map(f -> "invoiceid=" + f + ", ").orElse("") +
            optionalLineid().map(f -> "lineid=" + f + ", ").orElse("") +
            optionalOptionid().map(f -> "optionid=" + f + ", ").orElse("") +
            optionalServicename().map(f -> "servicename=" + f + ", ").orElse("") +
            optionalServicediscription().map(f -> "servicediscription=" + f + ", ").orElse("") +
            optionalValue().map(f -> "value=" + f + ", ").orElse("") +
            optionalAddedbyid().map(f -> "addedbyid=" + f + ", ").orElse("") +
            optionalIscustomersrvice().map(f -> "iscustomersrvice=" + f + ", ").orElse("") +
            optionalDiscount().map(f -> "discount=" + f + ", ").orElse("") +
            optionalServiceprice().map(f -> "serviceprice=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
