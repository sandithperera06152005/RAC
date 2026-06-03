package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.InvoiceServiceCommon} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.InvoiceServiceCommonResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoice-service-commons?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceServiceCommonCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter invoiceId;

    private LongFilter lineId;

    private LongFilter optionId;

    private LongFilter mainId;

    private DoubleFilter code;

    private StringFilter name;

    private StringFilter description;

    private DoubleFilter value;

    private LongFilter addedById;

    private DoubleFilter discount;

    private DoubleFilter servicePrice;

    private Boolean distinct;

    public InvoiceServiceCommonCriteria() {}

    public InvoiceServiceCommonCriteria(InvoiceServiceCommonCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.invoiceId = other.optionalInvoiceId().map(LongFilter::copy).orElse(null);
        this.lineId = other.optionalLineId().map(LongFilter::copy).orElse(null);
        this.optionId = other.optionalOptionId().map(LongFilter::copy).orElse(null);
        this.mainId = other.optionalMainId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(DoubleFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.value = other.optionalValue().map(DoubleFilter::copy).orElse(null);
        this.addedById = other.optionalAddedById().map(LongFilter::copy).orElse(null);
        this.discount = other.optionalDiscount().map(DoubleFilter::copy).orElse(null);
        this.servicePrice = other.optionalServicePrice().map(DoubleFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public InvoiceServiceCommonCriteria copy() {
        return new InvoiceServiceCommonCriteria(this);
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

    public LongFilter getInvoiceId() {
        return invoiceId;
    }

    public Optional<LongFilter> optionalInvoiceId() {
        return Optional.ofNullable(invoiceId);
    }

    public LongFilter invoiceId() {
        if (invoiceId == null) {
            setInvoiceId(new LongFilter());
        }
        return invoiceId;
    }

    public void setInvoiceId(LongFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    public LongFilter getLineId() {
        return lineId;
    }

    public Optional<LongFilter> optionalLineId() {
        return Optional.ofNullable(lineId);
    }

    public LongFilter lineId() {
        if (lineId == null) {
            setLineId(new LongFilter());
        }
        return lineId;
    }

    public void setLineId(LongFilter lineId) {
        this.lineId = lineId;
    }

    public LongFilter getOptionId() {
        return optionId;
    }

    public Optional<LongFilter> optionalOptionId() {
        return Optional.ofNullable(optionId);
    }

    public LongFilter optionId() {
        if (optionId == null) {
            setOptionId(new LongFilter());
        }
        return optionId;
    }

    public void setOptionId(LongFilter optionId) {
        this.optionId = optionId;
    }

    public LongFilter getMainId() {
        return mainId;
    }

    public Optional<LongFilter> optionalMainId() {
        return Optional.ofNullable(mainId);
    }

    public LongFilter mainId() {
        if (mainId == null) {
            setMainId(new LongFilter());
        }
        return mainId;
    }

    public void setMainId(LongFilter mainId) {
        this.mainId = mainId;
    }

    public DoubleFilter getCode() {
        return code;
    }

    public Optional<DoubleFilter> optionalCode() {
        return Optional.ofNullable(code);
    }

    public DoubleFilter code() {
        if (code == null) {
            setCode(new DoubleFilter());
        }
        return code;
    }

    public void setCode(DoubleFilter code) {
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

    public DoubleFilter getValue() {
        return value;
    }

    public Optional<DoubleFilter> optionalValue() {
        return Optional.ofNullable(value);
    }

    public DoubleFilter value() {
        if (value == null) {
            setValue(new DoubleFilter());
        }
        return value;
    }

    public void setValue(DoubleFilter value) {
        this.value = value;
    }

    public LongFilter getAddedById() {
        return addedById;
    }

    public Optional<LongFilter> optionalAddedById() {
        return Optional.ofNullable(addedById);
    }

    public LongFilter addedById() {
        if (addedById == null) {
            setAddedById(new LongFilter());
        }
        return addedById;
    }

    public void setAddedById(LongFilter addedById) {
        this.addedById = addedById;
    }

    public DoubleFilter getDiscount() {
        return discount;
    }

    public Optional<DoubleFilter> optionalDiscount() {
        return Optional.ofNullable(discount);
    }

    public DoubleFilter discount() {
        if (discount == null) {
            setDiscount(new DoubleFilter());
        }
        return discount;
    }

    public void setDiscount(DoubleFilter discount) {
        this.discount = discount;
    }

    public DoubleFilter getServicePrice() {
        return servicePrice;
    }

    public Optional<DoubleFilter> optionalServicePrice() {
        return Optional.ofNullable(servicePrice);
    }

    public DoubleFilter servicePrice() {
        if (servicePrice == null) {
            setServicePrice(new DoubleFilter());
        }
        return servicePrice;
    }

    public void setServicePrice(DoubleFilter servicePrice) {
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
        final InvoiceServiceCommonCriteria that = (InvoiceServiceCommonCriteria) o;
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
            Objects.equals(addedById, that.addedById) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(servicePrice, that.servicePrice) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            invoiceId,
            lineId,
            optionId,
            mainId,
            code,
            name,
            description,
            value,
            addedById,
            discount,
            servicePrice,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceServiceCommonCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInvoiceId().map(f -> "invoiceId=" + f + ", ").orElse("") +
            optionalLineId().map(f -> "lineId=" + f + ", ").orElse("") +
            optionalOptionId().map(f -> "optionId=" + f + ", ").orElse("") +
            optionalMainId().map(f -> "mainId=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalValue().map(f -> "value=" + f + ", ").orElse("") +
            optionalAddedById().map(f -> "addedById=" + f + ", ").orElse("") +
            optionalDiscount().map(f -> "discount=" + f + ", ").orElse("") +
            optionalServicePrice().map(f -> "servicePrice=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
