package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.InvoiceServiceCharge} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.InvoiceServiceChargeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /invoice-service-charges?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceServiceChargeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter invoiceId;

    private LongFilter lineId;

    private LongFilter optionId;

    private StringFilter serviceName;

    private StringFilter serviceDiscription;

    private DoubleFilter value;

    private LongFilter addedById;

    private BooleanFilter isCustomerSrvice;

    private DoubleFilter discount;

    private DoubleFilter servicePrice;

    private Boolean distinct;

    public InvoiceServiceChargeCriteria() {}

    public InvoiceServiceChargeCriteria(InvoiceServiceChargeCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.invoiceId = other.optionalInvoiceId().map(LongFilter::copy).orElse(null);
        this.lineId = other.optionalLineId().map(LongFilter::copy).orElse(null);
        this.optionId = other.optionalOptionId().map(LongFilter::copy).orElse(null);
        this.serviceName = other.optionalServiceName().map(StringFilter::copy).orElse(null);
        this.serviceDiscription = other.optionalServiceDiscription().map(StringFilter::copy).orElse(null);
        this.value = other.optionalValue().map(DoubleFilter::copy).orElse(null);
        this.addedById = other.optionalAddedById().map(LongFilter::copy).orElse(null);
        this.isCustomerSrvice = other.optionalIsCustomerSrvice().map(BooleanFilter::copy).orElse(null);
        this.discount = other.optionalDiscount().map(DoubleFilter::copy).orElse(null);
        this.servicePrice = other.optionalServicePrice().map(DoubleFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public InvoiceServiceChargeCriteria copy() {
        return new InvoiceServiceChargeCriteria(this);
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

    public StringFilter getServiceName() {
        return serviceName;
    }

    public Optional<StringFilter> optionalServiceName() {
        return Optional.ofNullable(serviceName);
    }

    public StringFilter serviceName() {
        if (serviceName == null) {
            setServiceName(new StringFilter());
        }
        return serviceName;
    }

    public void setServiceName(StringFilter serviceName) {
        this.serviceName = serviceName;
    }

    public StringFilter getServiceDiscription() {
        return serviceDiscription;
    }

    public Optional<StringFilter> optionalServiceDiscription() {
        return Optional.ofNullable(serviceDiscription);
    }

    public StringFilter serviceDiscription() {
        if (serviceDiscription == null) {
            setServiceDiscription(new StringFilter());
        }
        return serviceDiscription;
    }

    public void setServiceDiscription(StringFilter serviceDiscription) {
        this.serviceDiscription = serviceDiscription;
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

    public BooleanFilter getIsCustomerSrvice() {
        return isCustomerSrvice;
    }

    public Optional<BooleanFilter> optionalIsCustomerSrvice() {
        return Optional.ofNullable(isCustomerSrvice);
    }

    public BooleanFilter isCustomerSrvice() {
        if (isCustomerSrvice == null) {
            setIsCustomerSrvice(new BooleanFilter());
        }
        return isCustomerSrvice;
    }

    public void setIsCustomerSrvice(BooleanFilter isCustomerSrvice) {
        this.isCustomerSrvice = isCustomerSrvice;
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
        final InvoiceServiceChargeCriteria that = (InvoiceServiceChargeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(lineId, that.lineId) &&
            Objects.equals(optionId, that.optionId) &&
            Objects.equals(serviceName, that.serviceName) &&
            Objects.equals(serviceDiscription, that.serviceDiscription) &&
            Objects.equals(value, that.value) &&
            Objects.equals(addedById, that.addedById) &&
            Objects.equals(isCustomerSrvice, that.isCustomerSrvice) &&
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
            serviceName,
            serviceDiscription,
            value,
            addedById,
            isCustomerSrvice,
            discount,
            servicePrice,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceServiceChargeCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInvoiceId().map(f -> "invoiceId=" + f + ", ").orElse("") +
            optionalLineId().map(f -> "lineId=" + f + ", ").orElse("") +
            optionalOptionId().map(f -> "optionId=" + f + ", ").orElse("") +
            optionalServiceName().map(f -> "serviceName=" + f + ", ").orElse("") +
            optionalServiceDiscription().map(f -> "serviceDiscription=" + f + ", ").orElse("") +
            optionalValue().map(f -> "value=" + f + ", ").orElse("") +
            optionalAddedById().map(f -> "addedById=" + f + ", ").orElse("") +
            optionalIsCustomerSrvice().map(f -> "isCustomerSrvice=" + f + ", ").orElse("") +
            optionalDiscount().map(f -> "discount=" + f + ", ").orElse("") +
            optionalServicePrice().map(f -> "servicePrice=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
