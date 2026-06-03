package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Autojobsaleinvoicecommonservicecharge} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.AutojobsaleinvoicecommonservicechargeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /autojobsaleinvoicecommonservicecharges?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AutojobsaleinvoicecommonservicechargeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter invoiceid;

    private IntegerFilter lineid;

    private IntegerFilter optionid;

    private IntegerFilter mainid;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private FloatFilter value;

    private IntegerFilter addedbyid;

    private FloatFilter discount;

    private FloatFilter serviceprice;

    private Boolean distinct;

    public AutojobsaleinvoicecommonservicechargeCriteria() {}

    public AutojobsaleinvoicecommonservicechargeCriteria(AutojobsaleinvoicecommonservicechargeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.invoiceid = other.optionalInvoiceid().map(IntegerFilter::copy).orElse(null);
        this.lineid = other.optionalLineid().map(IntegerFilter::copy).orElse(null);
        this.optionid = other.optionalOptionid().map(IntegerFilter::copy).orElse(null);
        this.mainid = other.optionalMainid().map(IntegerFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.value = other.optionalValue().map(FloatFilter::copy).orElse(null);
        this.addedbyid = other.optionalAddedbyid().map(IntegerFilter::copy).orElse(null);
        this.discount = other.optionalDiscount().map(FloatFilter::copy).orElse(null);
        this.serviceprice = other.optionalServiceprice().map(FloatFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AutojobsaleinvoicecommonservicechargeCriteria copy() {
        return new AutojobsaleinvoicecommonservicechargeCriteria(this);
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

    public IntegerFilter getMainid() {
        return mainid;
    }

    public Optional<IntegerFilter> optionalMainid() {
        return Optional.ofNullable(mainid);
    }

    public IntegerFilter mainid() {
        if (mainid == null) {
            setMainid(new IntegerFilter());
        }
        return mainid;
    }

    public void setMainid(IntegerFilter mainid) {
        this.mainid = mainid;
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

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public StringFilter description() {
        if (description == null) {
            setDescription(new StringFilter());
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
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
        final AutojobsaleinvoicecommonservicechargeCriteria that = (AutojobsaleinvoicecommonservicechargeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceid, that.invoiceid) &&
            Objects.equals(lineid, that.lineid) &&
            Objects.equals(optionid, that.optionid) &&
            Objects.equals(mainid, that.mainid) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(value, that.value) &&
            Objects.equals(addedbyid, that.addedbyid) &&
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
            mainid,
            code,
            name,
            description,
            value,
            addedbyid,
            discount,
            serviceprice,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutojobsaleinvoicecommonservicechargeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInvoiceid().map(f -> "invoiceid=" + f + ", ").orElse("") +
            optionalLineid().map(f -> "lineid=" + f + ", ").orElse("") +
            optionalOptionid().map(f -> "optionid=" + f + ", ").orElse("") +
            optionalMainid().map(f -> "mainid=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalValue().map(f -> "value=" + f + ", ").orElse("") +
            optionalAddedbyid().map(f -> "addedbyid=" + f + ", ").orElse("") +
            optionalDiscount().map(f -> "discount=" + f + ", ").orElse("") +
            optionalServiceprice().map(f -> "serviceprice=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
