package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.SalesInvoiceDummy} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.SalesInvoiceDummyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sales-invoice-dummies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesInvoiceDummyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private IntegerFilter id;

    private IntegerFilter originalInvoiceId;

    private StringFilter code;

    private InstantFilter invoiceDate;

    private InstantFilter createdDate;

    private IntegerFilter quoteID;

    private IntegerFilter orderID;

    private InstantFilter deliveryDate;

    private IntegerFilter salesRepID;

    private StringFilter salesRepName;

    private StringFilter deliverFrom;

    private IntegerFilter customerID;

    private StringFilter customerName;

    private StringFilter customerAddress;

    private StringFilter deliveryAddress;

    private FloatFilter subTotal;

    private FloatFilter totalTax;

    private FloatFilter totalDiscount;

    private FloatFilter netTotal;

    private StringFilter message;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private FloatFilter paidAmount;

    private FloatFilter amountOwing;

    private BooleanFilter isActive;

    private IntegerFilter locationID;

    private StringFilter locationCode;

    private StringFilter referenceCode;

    private IntegerFilter createdById;

    private StringFilter createdByName;

    private FloatFilter autoCareCharges;

    private IntegerFilter autoCareJobId;

    private StringFilter vehicleNo;

    private FloatFilter nbtAmount;

    private FloatFilter vatAmount;

    private FloatFilter dummyCommission;

    private InstantFilter commissionPaidDate;

    private FloatFilter paidCommission;

    private IntegerFilter paidBy;

    private StringFilter originalInvoiceCode;

    private Boolean distinct;

    public SalesInvoiceDummyCriteria() {}

    public SalesInvoiceDummyCriteria(SalesInvoiceDummyCriteria other) {
        this.id = other.optionalId().map(IntegerFilter::copy).orElse(null);
        this.originalInvoiceId = other.optionalOriginalInvoiceId().map(IntegerFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.invoiceDate = other.optionalInvoiceDate().map(InstantFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(InstantFilter::copy).orElse(null);
        this.quoteID = other.optionalQuoteID().map(IntegerFilter::copy).orElse(null);
        this.orderID = other.optionalOrderID().map(IntegerFilter::copy).orElse(null);
        this.deliveryDate = other.optionalDeliveryDate().map(InstantFilter::copy).orElse(null);
        this.salesRepID = other.optionalSalesRepID().map(IntegerFilter::copy).orElse(null);
        this.salesRepName = other.optionalSalesRepName().map(StringFilter::copy).orElse(null);
        this.deliverFrom = other.optionalDeliverFrom().map(StringFilter::copy).orElse(null);
        this.customerID = other.optionalCustomerID().map(IntegerFilter::copy).orElse(null);
        this.customerName = other.optionalCustomerName().map(StringFilter::copy).orElse(null);
        this.customerAddress = other.optionalCustomerAddress().map(StringFilter::copy).orElse(null);
        this.deliveryAddress = other.optionalDeliveryAddress().map(StringFilter::copy).orElse(null);
        this.subTotal = other.optionalSubTotal().map(FloatFilter::copy).orElse(null);
        this.totalTax = other.optionalTotalTax().map(FloatFilter::copy).orElse(null);
        this.totalDiscount = other.optionalTotalDiscount().map(FloatFilter::copy).orElse(null);
        this.netTotal = other.optionalNetTotal().map(FloatFilter::copy).orElse(null);
        this.message = other.optionalMessage().map(StringFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.paidAmount = other.optionalPaidAmount().map(FloatFilter::copy).orElse(null);
        this.amountOwing = other.optionalAmountOwing().map(FloatFilter::copy).orElse(null);
        this.isActive = other.optionalIsActive().map(BooleanFilter::copy).orElse(null);
        this.locationID = other.optionalLocationID().map(IntegerFilter::copy).orElse(null);
        this.locationCode = other.optionalLocationCode().map(StringFilter::copy).orElse(null);
        this.referenceCode = other.optionalReferenceCode().map(StringFilter::copy).orElse(null);
        this.createdById = other.optionalCreatedById().map(IntegerFilter::copy).orElse(null);
        this.createdByName = other.optionalCreatedByName().map(StringFilter::copy).orElse(null);
        this.autoCareCharges = other.optionalAutoCareCharges().map(FloatFilter::copy).orElse(null);
        this.autoCareJobId = other.optionalAutoCareJobId().map(IntegerFilter::copy).orElse(null);
        this.vehicleNo = other.optionalVehicleNo().map(StringFilter::copy).orElse(null);
        this.nbtAmount = other.optionalNbtAmount().map(FloatFilter::copy).orElse(null);
        this.vatAmount = other.optionalVatAmount().map(FloatFilter::copy).orElse(null);
        this.dummyCommission = other.optionalDummyCommission().map(FloatFilter::copy).orElse(null);
        this.commissionPaidDate = other.optionalCommissionPaidDate().map(InstantFilter::copy).orElse(null);
        this.paidCommission = other.optionalPaidCommission().map(FloatFilter::copy).orElse(null);
        this.paidBy = other.optionalPaidBy().map(IntegerFilter::copy).orElse(null);
        this.originalInvoiceCode = other.optionalOriginalInvoiceCode().map(StringFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SalesInvoiceDummyCriteria copy() {
        return new SalesInvoiceDummyCriteria(this);
    }

    public IntegerFilter getId() {
        return id;
    }

    public Optional<IntegerFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public IntegerFilter id() {
        if (id == null) {
            setId(new IntegerFilter());
        }
        return id;
    }

    public void setId(IntegerFilter id) {
        this.id = id;
    }

    public IntegerFilter getOriginalInvoiceId() {
        return originalInvoiceId;
    }

    public Optional<IntegerFilter> optionalOriginalInvoiceId() {
        return Optional.ofNullable(originalInvoiceId);
    }

    public IntegerFilter originalInvoiceId() {
        if (originalInvoiceId == null) {
            setOriginalInvoiceId(new IntegerFilter());
        }
        return originalInvoiceId;
    }

    public void setOriginalInvoiceId(IntegerFilter originalInvoiceId) {
        this.originalInvoiceId = originalInvoiceId;
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

    public InstantFilter getInvoiceDate() {
        return invoiceDate;
    }

    public Optional<InstantFilter> optionalInvoiceDate() {
        return Optional.ofNullable(invoiceDate);
    }

    public InstantFilter invoiceDate() {
        if (invoiceDate == null) {
            setInvoiceDate(new InstantFilter());
        }
        return invoiceDate;
    }

    public void setInvoiceDate(InstantFilter invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public Optional<InstantFilter> optionalCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            setCreatedDate(new InstantFilter());
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public IntegerFilter getQuoteID() {
        return quoteID;
    }

    public Optional<IntegerFilter> optionalQuoteID() {
        return Optional.ofNullable(quoteID);
    }

    public IntegerFilter quoteID() {
        if (quoteID == null) {
            setQuoteID(new IntegerFilter());
        }
        return quoteID;
    }

    public void setQuoteID(IntegerFilter quoteID) {
        this.quoteID = quoteID;
    }

    public IntegerFilter getOrderID() {
        return orderID;
    }

    public Optional<IntegerFilter> optionalOrderID() {
        return Optional.ofNullable(orderID);
    }

    public IntegerFilter orderID() {
        if (orderID == null) {
            setOrderID(new IntegerFilter());
        }
        return orderID;
    }

    public void setOrderID(IntegerFilter orderID) {
        this.orderID = orderID;
    }

    public InstantFilter getDeliveryDate() {
        return deliveryDate;
    }

    public Optional<InstantFilter> optionalDeliveryDate() {
        return Optional.ofNullable(deliveryDate);
    }

    public InstantFilter deliveryDate() {
        if (deliveryDate == null) {
            setDeliveryDate(new InstantFilter());
        }
        return deliveryDate;
    }

    public void setDeliveryDate(InstantFilter deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public IntegerFilter getSalesRepID() {
        return salesRepID;
    }

    public Optional<IntegerFilter> optionalSalesRepID() {
        return Optional.ofNullable(salesRepID);
    }

    public IntegerFilter salesRepID() {
        if (salesRepID == null) {
            setSalesRepID(new IntegerFilter());
        }
        return salesRepID;
    }

    public void setSalesRepID(IntegerFilter salesRepID) {
        this.salesRepID = salesRepID;
    }

    public StringFilter getSalesRepName() {
        return salesRepName;
    }

    public Optional<StringFilter> optionalSalesRepName() {
        return Optional.ofNullable(salesRepName);
    }

    public StringFilter salesRepName() {
        if (salesRepName == null) {
            setSalesRepName(new StringFilter());
        }
        return salesRepName;
    }

    public void setSalesRepName(StringFilter salesRepName) {
        this.salesRepName = salesRepName;
    }

    public StringFilter getDeliverFrom() {
        return deliverFrom;
    }

    public Optional<StringFilter> optionalDeliverFrom() {
        return Optional.ofNullable(deliverFrom);
    }

    public StringFilter deliverFrom() {
        if (deliverFrom == null) {
            setDeliverFrom(new StringFilter());
        }
        return deliverFrom;
    }

    public void setDeliverFrom(StringFilter deliverFrom) {
        this.deliverFrom = deliverFrom;
    }

    public IntegerFilter getCustomerID() {
        return customerID;
    }

    public Optional<IntegerFilter> optionalCustomerID() {
        return Optional.ofNullable(customerID);
    }

    public IntegerFilter customerID() {
        if (customerID == null) {
            setCustomerID(new IntegerFilter());
        }
        return customerID;
    }

    public void setCustomerID(IntegerFilter customerID) {
        this.customerID = customerID;
    }

    public StringFilter getCustomerName() {
        return customerName;
    }

    public Optional<StringFilter> optionalCustomerName() {
        return Optional.ofNullable(customerName);
    }

    public StringFilter customerName() {
        if (customerName == null) {
            setCustomerName(new StringFilter());
        }
        return customerName;
    }

    public void setCustomerName(StringFilter customerName) {
        this.customerName = customerName;
    }

    public StringFilter getCustomerAddress() {
        return customerAddress;
    }

    public Optional<StringFilter> optionalCustomerAddress() {
        return Optional.ofNullable(customerAddress);
    }

    public StringFilter customerAddress() {
        if (customerAddress == null) {
            setCustomerAddress(new StringFilter());
        }
        return customerAddress;
    }

    public void setCustomerAddress(StringFilter customerAddress) {
        this.customerAddress = customerAddress;
    }

    public StringFilter getDeliveryAddress() {
        return deliveryAddress;
    }

    public Optional<StringFilter> optionalDeliveryAddress() {
        return Optional.ofNullable(deliveryAddress);
    }

    public StringFilter deliveryAddress() {
        if (deliveryAddress == null) {
            setDeliveryAddress(new StringFilter());
        }
        return deliveryAddress;
    }

    public void setDeliveryAddress(StringFilter deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public FloatFilter getSubTotal() {
        return subTotal;
    }

    public Optional<FloatFilter> optionalSubTotal() {
        return Optional.ofNullable(subTotal);
    }

    public FloatFilter subTotal() {
        if (subTotal == null) {
            setSubTotal(new FloatFilter());
        }
        return subTotal;
    }

    public void setSubTotal(FloatFilter subTotal) {
        this.subTotal = subTotal;
    }

    public FloatFilter getTotalTax() {
        return totalTax;
    }

    public Optional<FloatFilter> optionalTotalTax() {
        return Optional.ofNullable(totalTax);
    }

    public FloatFilter totalTax() {
        if (totalTax == null) {
            setTotalTax(new FloatFilter());
        }
        return totalTax;
    }

    public void setTotalTax(FloatFilter totalTax) {
        this.totalTax = totalTax;
    }

    public FloatFilter getTotalDiscount() {
        return totalDiscount;
    }

    public Optional<FloatFilter> optionalTotalDiscount() {
        return Optional.ofNullable(totalDiscount);
    }

    public FloatFilter totalDiscount() {
        if (totalDiscount == null) {
            setTotalDiscount(new FloatFilter());
        }
        return totalDiscount;
    }

    public void setTotalDiscount(FloatFilter totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public FloatFilter getNetTotal() {
        return netTotal;
    }

    public Optional<FloatFilter> optionalNetTotal() {
        return Optional.ofNullable(netTotal);
    }

    public FloatFilter netTotal() {
        if (netTotal == null) {
            setNetTotal(new FloatFilter());
        }
        return netTotal;
    }

    public void setNetTotal(FloatFilter netTotal) {
        this.netTotal = netTotal;
    }

    public StringFilter getMessage() {
        return message;
    }

    public Optional<StringFilter> optionalMessage() {
        return Optional.ofNullable(message);
    }

    public StringFilter message() {
        if (message == null) {
            setMessage(new StringFilter());
        }
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
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

    public FloatFilter getPaidAmount() {
        return paidAmount;
    }

    public Optional<FloatFilter> optionalPaidAmount() {
        return Optional.ofNullable(paidAmount);
    }

    public FloatFilter paidAmount() {
        if (paidAmount == null) {
            setPaidAmount(new FloatFilter());
        }
        return paidAmount;
    }

    public void setPaidAmount(FloatFilter paidAmount) {
        this.paidAmount = paidAmount;
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

    public BooleanFilter getIsActive() {
        return isActive;
    }

    public Optional<BooleanFilter> optionalIsActive() {
        return Optional.ofNullable(isActive);
    }

    public BooleanFilter isActive() {
        if (isActive == null) {
            setIsActive(new BooleanFilter());
        }
        return isActive;
    }

    public void setIsActive(BooleanFilter isActive) {
        this.isActive = isActive;
    }

    public IntegerFilter getLocationID() {
        return locationID;
    }

    public Optional<IntegerFilter> optionalLocationID() {
        return Optional.ofNullable(locationID);
    }

    public IntegerFilter locationID() {
        if (locationID == null) {
            setLocationID(new IntegerFilter());
        }
        return locationID;
    }

    public void setLocationID(IntegerFilter locationID) {
        this.locationID = locationID;
    }

    public StringFilter getLocationCode() {
        return locationCode;
    }

    public Optional<StringFilter> optionalLocationCode() {
        return Optional.ofNullable(locationCode);
    }

    public StringFilter locationCode() {
        if (locationCode == null) {
            setLocationCode(new StringFilter());
        }
        return locationCode;
    }

    public void setLocationCode(StringFilter locationCode) {
        this.locationCode = locationCode;
    }

    public StringFilter getReferenceCode() {
        return referenceCode;
    }

    public Optional<StringFilter> optionalReferenceCode() {
        return Optional.ofNullable(referenceCode);
    }

    public StringFilter referenceCode() {
        if (referenceCode == null) {
            setReferenceCode(new StringFilter());
        }
        return referenceCode;
    }

    public void setReferenceCode(StringFilter referenceCode) {
        this.referenceCode = referenceCode;
    }

    public IntegerFilter getCreatedById() {
        return createdById;
    }

    public Optional<IntegerFilter> optionalCreatedById() {
        return Optional.ofNullable(createdById);
    }

    public IntegerFilter createdById() {
        if (createdById == null) {
            setCreatedById(new IntegerFilter());
        }
        return createdById;
    }

    public void setCreatedById(IntegerFilter createdById) {
        this.createdById = createdById;
    }

    public StringFilter getCreatedByName() {
        return createdByName;
    }

    public Optional<StringFilter> optionalCreatedByName() {
        return Optional.ofNullable(createdByName);
    }

    public StringFilter createdByName() {
        if (createdByName == null) {
            setCreatedByName(new StringFilter());
        }
        return createdByName;
    }

    public void setCreatedByName(StringFilter createdByName) {
        this.createdByName = createdByName;
    }

    public FloatFilter getAutoCareCharges() {
        return autoCareCharges;
    }

    public Optional<FloatFilter> optionalAutoCareCharges() {
        return Optional.ofNullable(autoCareCharges);
    }

    public FloatFilter autoCareCharges() {
        if (autoCareCharges == null) {
            setAutoCareCharges(new FloatFilter());
        }
        return autoCareCharges;
    }

    public void setAutoCareCharges(FloatFilter autoCareCharges) {
        this.autoCareCharges = autoCareCharges;
    }

    public IntegerFilter getAutoCareJobId() {
        return autoCareJobId;
    }

    public Optional<IntegerFilter> optionalAutoCareJobId() {
        return Optional.ofNullable(autoCareJobId);
    }

    public IntegerFilter autoCareJobId() {
        if (autoCareJobId == null) {
            setAutoCareJobId(new IntegerFilter());
        }
        return autoCareJobId;
    }

    public void setAutoCareJobId(IntegerFilter autoCareJobId) {
        this.autoCareJobId = autoCareJobId;
    }

    public StringFilter getVehicleNo() {
        return vehicleNo;
    }

    public Optional<StringFilter> optionalVehicleNo() {
        return Optional.ofNullable(vehicleNo);
    }

    public StringFilter vehicleNo() {
        if (vehicleNo == null) {
            setVehicleNo(new StringFilter());
        }
        return vehicleNo;
    }

    public void setVehicleNo(StringFilter vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public FloatFilter getNbtAmount() {
        return nbtAmount;
    }

    public Optional<FloatFilter> optionalNbtAmount() {
        return Optional.ofNullable(nbtAmount);
    }

    public FloatFilter nbtAmount() {
        if (nbtAmount == null) {
            setNbtAmount(new FloatFilter());
        }
        return nbtAmount;
    }

    public void setNbtAmount(FloatFilter nbtAmount) {
        this.nbtAmount = nbtAmount;
    }

    public FloatFilter getVatAmount() {
        return vatAmount;
    }

    public Optional<FloatFilter> optionalVatAmount() {
        return Optional.ofNullable(vatAmount);
    }

    public FloatFilter vatAmount() {
        if (vatAmount == null) {
            setVatAmount(new FloatFilter());
        }
        return vatAmount;
    }

    public void setVatAmount(FloatFilter vatAmount) {
        this.vatAmount = vatAmount;
    }

    public FloatFilter getDummyCommission() {
        return dummyCommission;
    }

    public Optional<FloatFilter> optionalDummyCommission() {
        return Optional.ofNullable(dummyCommission);
    }

    public FloatFilter dummyCommission() {
        if (dummyCommission == null) {
            setDummyCommission(new FloatFilter());
        }
        return dummyCommission;
    }

    public void setDummyCommission(FloatFilter dummyCommission) {
        this.dummyCommission = dummyCommission;
    }

    public InstantFilter getCommissionPaidDate() {
        return commissionPaidDate;
    }

    public Optional<InstantFilter> optionalCommissionPaidDate() {
        return Optional.ofNullable(commissionPaidDate);
    }

    public InstantFilter commissionPaidDate() {
        if (commissionPaidDate == null) {
            setCommissionPaidDate(new InstantFilter());
        }
        return commissionPaidDate;
    }

    public void setCommissionPaidDate(InstantFilter commissionPaidDate) {
        this.commissionPaidDate = commissionPaidDate;
    }

    public FloatFilter getPaidCommission() {
        return paidCommission;
    }

    public Optional<FloatFilter> optionalPaidCommission() {
        return Optional.ofNullable(paidCommission);
    }

    public FloatFilter paidCommission() {
        if (paidCommission == null) {
            setPaidCommission(new FloatFilter());
        }
        return paidCommission;
    }

    public void setPaidCommission(FloatFilter paidCommission) {
        this.paidCommission = paidCommission;
    }

    public IntegerFilter getPaidBy() {
        return paidBy;
    }

    public Optional<IntegerFilter> optionalPaidBy() {
        return Optional.ofNullable(paidBy);
    }

    public IntegerFilter paidBy() {
        if (paidBy == null) {
            setPaidBy(new IntegerFilter());
        }
        return paidBy;
    }

    public void setPaidBy(IntegerFilter paidBy) {
        this.paidBy = paidBy;
    }

    public StringFilter getOriginalInvoiceCode() {
        return originalInvoiceCode;
    }

    public Optional<StringFilter> optionalOriginalInvoiceCode() {
        return Optional.ofNullable(originalInvoiceCode);
    }

    public StringFilter originalInvoiceCode() {
        if (originalInvoiceCode == null) {
            setOriginalInvoiceCode(new StringFilter());
        }
        return originalInvoiceCode;
    }

    public void setOriginalInvoiceCode(StringFilter originalInvoiceCode) {
        this.originalInvoiceCode = originalInvoiceCode;
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
        final SalesInvoiceDummyCriteria that = (SalesInvoiceDummyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(originalInvoiceId, that.originalInvoiceId) &&
            Objects.equals(code, that.code) &&
            Objects.equals(invoiceDate, that.invoiceDate) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(quoteID, that.quoteID) &&
            Objects.equals(orderID, that.orderID) &&
            Objects.equals(deliveryDate, that.deliveryDate) &&
            Objects.equals(salesRepID, that.salesRepID) &&
            Objects.equals(salesRepName, that.salesRepName) &&
            Objects.equals(deliverFrom, that.deliverFrom) &&
            Objects.equals(customerID, that.customerID) &&
            Objects.equals(customerName, that.customerName) &&
            Objects.equals(customerAddress, that.customerAddress) &&
            Objects.equals(deliveryAddress, that.deliveryAddress) &&
            Objects.equals(subTotal, that.subTotal) &&
            Objects.equals(totalTax, that.totalTax) &&
            Objects.equals(totalDiscount, that.totalDiscount) &&
            Objects.equals(netTotal, that.netTotal) &&
            Objects.equals(message, that.message) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(paidAmount, that.paidAmount) &&
            Objects.equals(amountOwing, that.amountOwing) &&
            Objects.equals(isActive, that.isActive) &&
            Objects.equals(locationID, that.locationID) &&
            Objects.equals(locationCode, that.locationCode) &&
            Objects.equals(referenceCode, that.referenceCode) &&
            Objects.equals(createdById, that.createdById) &&
            Objects.equals(createdByName, that.createdByName) &&
            Objects.equals(autoCareCharges, that.autoCareCharges) &&
            Objects.equals(autoCareJobId, that.autoCareJobId) &&
            Objects.equals(vehicleNo, that.vehicleNo) &&
            Objects.equals(nbtAmount, that.nbtAmount) &&
            Objects.equals(vatAmount, that.vatAmount) &&
            Objects.equals(dummyCommission, that.dummyCommission) &&
            Objects.equals(commissionPaidDate, that.commissionPaidDate) &&
            Objects.equals(paidCommission, that.paidCommission) &&
            Objects.equals(paidBy, that.paidBy) &&
            Objects.equals(originalInvoiceCode, that.originalInvoiceCode) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            originalInvoiceId,
            code,
            invoiceDate,
            createdDate,
            quoteID,
            orderID,
            deliveryDate,
            salesRepID,
            salesRepName,
            deliverFrom,
            customerID,
            customerName,
            customerAddress,
            deliveryAddress,
            subTotal,
            totalTax,
            totalDiscount,
            netTotal,
            message,
            lmu,
            lmd,
            paidAmount,
            amountOwing,
            isActive,
            locationID,
            locationCode,
            referenceCode,
            createdById,
            createdByName,
            autoCareCharges,
            autoCareJobId,
            vehicleNo,
            nbtAmount,
            vatAmount,
            dummyCommission,
            commissionPaidDate,
            paidCommission,
            paidBy,
            originalInvoiceCode,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesInvoiceDummyCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalOriginalInvoiceId().map(f -> "originalInvoiceId=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalInvoiceDate().map(f -> "invoiceDate=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalQuoteID().map(f -> "quoteID=" + f + ", ").orElse("") +
            optionalOrderID().map(f -> "orderID=" + f + ", ").orElse("") +
            optionalDeliveryDate().map(f -> "deliveryDate=" + f + ", ").orElse("") +
            optionalSalesRepID().map(f -> "salesRepID=" + f + ", ").orElse("") +
            optionalSalesRepName().map(f -> "salesRepName=" + f + ", ").orElse("") +
            optionalDeliverFrom().map(f -> "deliverFrom=" + f + ", ").orElse("") +
            optionalCustomerID().map(f -> "customerID=" + f + ", ").orElse("") +
            optionalCustomerName().map(f -> "customerName=" + f + ", ").orElse("") +
            optionalCustomerAddress().map(f -> "customerAddress=" + f + ", ").orElse("") +
            optionalDeliveryAddress().map(f -> "deliveryAddress=" + f + ", ").orElse("") +
            optionalSubTotal().map(f -> "subTotal=" + f + ", ").orElse("") +
            optionalTotalTax().map(f -> "totalTax=" + f + ", ").orElse("") +
            optionalTotalDiscount().map(f -> "totalDiscount=" + f + ", ").orElse("") +
            optionalNetTotal().map(f -> "netTotal=" + f + ", ").orElse("") +
            optionalMessage().map(f -> "message=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalPaidAmount().map(f -> "paidAmount=" + f + ", ").orElse("") +
            optionalAmountOwing().map(f -> "amountOwing=" + f + ", ").orElse("") +
            optionalIsActive().map(f -> "isActive=" + f + ", ").orElse("") +
            optionalLocationID().map(f -> "locationID=" + f + ", ").orElse("") +
            optionalLocationCode().map(f -> "locationCode=" + f + ", ").orElse("") +
            optionalReferenceCode().map(f -> "referenceCode=" + f + ", ").orElse("") +
            optionalCreatedById().map(f -> "createdById=" + f + ", ").orElse("") +
            optionalCreatedByName().map(f -> "createdByName=" + f + ", ").orElse("") +
            optionalAutoCareCharges().map(f -> "autoCareCharges=" + f + ", ").orElse("") +
            optionalAutoCareJobId().map(f -> "autoCareJobId=" + f + ", ").orElse("") +
            optionalVehicleNo().map(f -> "vehicleNo=" + f + ", ").orElse("") +
            optionalNbtAmount().map(f -> "nbtAmount=" + f + ", ").orElse("") +
            optionalVatAmount().map(f -> "vatAmount=" + f + ", ").orElse("") +
            optionalDummyCommission().map(f -> "dummyCommission=" + f + ", ").orElse("") +
            optionalCommissionPaidDate().map(f -> "commissionPaidDate=" + f + ", ").orElse("") +
            optionalPaidCommission().map(f -> "paidCommission=" + f + ", ").orElse("") +
            optionalPaidBy().map(f -> "paidBy=" + f + ", ").orElse("") +
            optionalOriginalInvoiceCode().map(f -> "originalInvoiceCode=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
