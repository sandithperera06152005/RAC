package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.SalesInvoiceServiceChargeLineDummy} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.SalesInvoiceServiceChargeLineDummyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sales-invoice-service-charge-line-dummies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesInvoiceServiceChargeLineDummyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter invoiceId;

    private IntegerFilter lineId;

    private IntegerFilter optionId;

    private StringFilter serviceName;

    private StringFilter serviceDescription;

    private FloatFilter value;

    private BooleanFilter isCustomerService;

    private Boolean distinct;

    public SalesInvoiceServiceChargeLineDummyCriteria() {}

    public SalesInvoiceServiceChargeLineDummyCriteria(SalesInvoiceServiceChargeLineDummyCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.invoiceId = other.optionalInvoiceId().map(IntegerFilter::copy).orElse(null);
        this.lineId = other.optionalLineId().map(IntegerFilter::copy).orElse(null);
        this.optionId = other.optionalOptionId().map(IntegerFilter::copy).orElse(null);
        this.serviceName = other.optionalServiceName().map(StringFilter::copy).orElse(null);
        this.serviceDescription = other.optionalServiceDescription().map(StringFilter::copy).orElse(null);
        this.value = other.optionalValue().map(FloatFilter::copy).orElse(null);
        this.isCustomerService = other.optionalIsCustomerService().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SalesInvoiceServiceChargeLineDummyCriteria copy() {
        return new SalesInvoiceServiceChargeLineDummyCriteria(this);
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

    public StringFilter getServiceDescription() {
        return serviceDescription;
    }

    public Optional<StringFilter> optionalServiceDescription() {
        return Optional.ofNullable(serviceDescription);
    }

    public StringFilter serviceDescription() {
        if (serviceDescription == null) {
            setServiceDescription(new StringFilter());
        }
        return serviceDescription;
    }

    public void setServiceDescription(StringFilter serviceDescription) {
        this.serviceDescription = serviceDescription;
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

    public BooleanFilter getIsCustomerService() {
        return isCustomerService;
    }

    public Optional<BooleanFilter> optionalIsCustomerService() {
        return Optional.ofNullable(isCustomerService);
    }

    public BooleanFilter isCustomerService() {
        if (isCustomerService == null) {
            setIsCustomerService(new BooleanFilter());
        }
        return isCustomerService;
    }

    public void setIsCustomerService(BooleanFilter isCustomerService) {
        this.isCustomerService = isCustomerService;
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
        final SalesInvoiceServiceChargeLineDummyCriteria that = (SalesInvoiceServiceChargeLineDummyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(lineId, that.lineId) &&
            Objects.equals(optionId, that.optionId) &&
            Objects.equals(serviceName, that.serviceName) &&
            Objects.equals(serviceDescription, that.serviceDescription) &&
            Objects.equals(value, that.value) &&
            Objects.equals(isCustomerService, that.isCustomerService) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoiceId, lineId, optionId, serviceName, serviceDescription, value, isCustomerService, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesInvoiceServiceChargeLineDummyCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInvoiceId().map(f -> "invoiceId=" + f + ", ").orElse("") +
            optionalLineId().map(f -> "lineId=" + f + ", ").orElse("") +
            optionalOptionId().map(f -> "optionId=" + f + ", ").orElse("") +
            optionalServiceName().map(f -> "serviceName=" + f + ", ").orElse("") +
            optionalServiceDescription().map(f -> "serviceDescription=" + f + ", ").orElse("") +
            optionalValue().map(f -> "value=" + f + ", ").orElse("") +
            optionalIsCustomerService().map(f -> "isCustomerService=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
