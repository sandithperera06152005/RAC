package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.SaleInvoiceCommonServiceCharge} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.SaleInvoiceCommonServiceChargeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sale-invoice-common-service-charges?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaleInvoiceCommonServiceChargeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter invoiceId;

    private IntegerFilter lineId;

    private IntegerFilter optionId;

    private IntegerFilter mainId;

    private StringFilter code;

    private StringFilter name;

    private StringFilter description;

    private FloatFilter value;

    private FloatFilter discount;

    private FloatFilter servicePrice;

    private Boolean distinct;

    public SaleInvoiceCommonServiceChargeCriteria() {}

    public SaleInvoiceCommonServiceChargeCriteria(SaleInvoiceCommonServiceChargeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.invoiceId = other.optionalInvoiceId().map(IntegerFilter::copy).orElse(null);
        this.lineId = other.optionalLineId().map(IntegerFilter::copy).orElse(null);
        this.optionId = other.optionalOptionId().map(IntegerFilter::copy).orElse(null);
        this.mainId = other.optionalMainId().map(IntegerFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.value = other.optionalValue().map(FloatFilter::copy).orElse(null);
        this.discount = other.optionalDiscount().map(FloatFilter::copy).orElse(null);
        this.servicePrice = other.optionalServicePrice().map(FloatFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SaleInvoiceCommonServiceChargeCriteria copy() {
        return new SaleInvoiceCommonServiceChargeCriteria(this);
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

    public IntegerFilter getInvoiceId() {
        return invoiceId;
    }

    public Optional<IntegerFilter> optionalInvoiceId() {
        return Optional.ofNullable(invoiceId);
    }

    public IntegerFilter invoiceId() {
        if (invoiceId == null) {
            setInvoiceId(new IntegerFilter());
        }
        return invoiceId;
    }

    public void setInvoiceId(IntegerFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    public IntegerFilter getLineId() {
        return lineId;
    }

    public Optional<IntegerFilter> optionalLineId() {
        return Optional.ofNullable(lineId);
    }

    public IntegerFilter lineId() {
        if (lineId == null) {
            setLineId(new IntegerFilter());
        }
        return lineId;
    }

    public void setLineId(IntegerFilter lineId) {
        this.lineId = lineId;
    }

    public IntegerFilter getOptionId() {
        return optionId;
    }

    public Optional<IntegerFilter> optionalOptionId() {
        return Optional.ofNullable(optionId);
    }

    public IntegerFilter optionId() {
        if (optionId == null) {
            setOptionId(new IntegerFilter());
        }
        return optionId;
    }

    public void setOptionId(IntegerFilter optionId) {
        this.optionId = optionId;
    }

    public IntegerFilter getMainId() {
        return mainId;
    }

    public Optional<IntegerFilter> optionalMainId() {
        return Optional.ofNullable(mainId);
    }

    public IntegerFilter mainId() {
        if (mainId == null) {
            setMainId(new IntegerFilter());
        }
        return mainId;
    }

    public void setMainId(IntegerFilter mainId) {
        this.mainId = mainId;
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

    public FloatFilter getServicePrice() {
        return servicePrice;
    }

    public Optional<FloatFilter> optionalServicePrice() {
        return Optional.ofNullable(servicePrice);
    }

    public FloatFilter servicePrice() {
        if (servicePrice == null) {
            setServicePrice(new FloatFilter());
        }
        return servicePrice;
    }

    public void setServicePrice(FloatFilter servicePrice) {
        this.servicePrice = servicePrice;
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
        final SaleInvoiceCommonServiceChargeCriteria that = (SaleInvoiceCommonServiceChargeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(lineId, that.lineId) &&
            Objects.equals(optionId, that.optionId) &&
            Objects.equals(mainId, that.mainId) &&
            Objects.equals(code, that.code) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(value, that.value) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(servicePrice, that.servicePrice) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceId, lineId, optionId, mainId, code, name, description, value, discount, servicePrice, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleInvoiceCommonServiceChargeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInvoiceId().map(f -> "invoiceId=" + f + ", ").orElse("") +
            optionalLineId().map(f -> "lineId=" + f + ", ").orElse("") +
            optionalOptionId().map(f -> "optionId=" + f + ", ").orElse("") +
            optionalMainId().map(f -> "mainId=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalValue().map(f -> "value=" + f + ", ").orElse("") +
            optionalDiscount().map(f -> "discount=" + f + ", ").orElse("") +
            optionalServicePrice().map(f -> "servicePrice=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
