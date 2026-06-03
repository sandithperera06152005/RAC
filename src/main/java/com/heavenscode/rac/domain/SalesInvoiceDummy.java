package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A SalesInvoiceDummy.
 */
@Entity
@Table(name = "salesinvoicedummy")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesInvoiceDummy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or SEQUENCE depending on your DB
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "originalinvoiceid")
    private Integer originalInvoiceId;

    @Column(name = "code")
    private String code;

    @Column(name = "invoicedate")
    private Instant invoiceDate;

    @Column(name = "createddate")
    private Instant createdDate;

    @Column(name = "quoteid")
    private Integer quoteID;

    @Column(name = "orderid")
    private Integer orderID;

    @Column(name = "delieverydate")
    private Instant deliveryDate;

    @Column(name = "salesrepid")
    private Integer salesRepID;

    @Column(name = "salesrepname")
    private String salesRepName;

    @Column(name = "delieverfrom")
    private String deliverFrom;

    @Column(name = "customerid")
    private Integer customerID;

    @Column(name = "customername")
    private String customerName;

    @Column(name = "customeraddress")
    private String customerAddress;

    @Column(name = "deliveryaddress")
    private String deliveryAddress;

    @Column(name = "subtotal")
    private Float subTotal;

    @Column(name = "totaltax")
    private Float totalTax;

    @Column(name = "totaldiscount")
    private Float totalDiscount;

    @Column(name = "nettotal")
    private Float netTotal;

    @Column(name = "message")
    private String message;

    @Column(name = "lmu")
    private Integer lmu;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "paidamount")
    private Float paidAmount;

    @Column(name = "amountowing")
    private Float amountOwing;

    @Column(name = "isactive")
    private Boolean isActive;

    @Column(name = "locationid")
    private Integer locationID;

    @Column(name = "locationcode")
    private String locationCode;

    @Column(name = "referencecode")
    private String referenceCode;

    @Column(name = "createdbyid")
    private Integer createdById;

    @Column(name = "createdbyname")
    private String createdByName;

    @Column(name = "autocarecharges")
    private Float autoCareCharges;

    @Column(name = "autocarejobid")
    private Integer autoCareJobId;

    @Column(name = "vehicleno")
    private String vehicleNo;

    @Column(name = "nbtamount")
    private Float nbtAmount;

    @Column(name = "vatamount")
    private Float vatAmount;

    @Column(name = "dummycommision")
    private Float dummyCommission;

    @Column(name = "commisionpaiddate")
    private Instant commissionPaidDate;

    @Column(name = "paidcommision")
    private Float paidCommission;

    @Column(name = "paidby")
    private Integer paidBy;

    @Column(name = "originalinvoicecode")
    private String originalInvoiceCode;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Integer getId() {
        return this.id;
    }

    public SalesInvoiceDummy id(Integer id) {
        this.setId(id);
        return this;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOriginalInvoiceId() {
        return this.originalInvoiceId;
    }

    public SalesInvoiceDummy originalInvoiceId(Integer originalInvoiceId) {
        this.setOriginalInvoiceId(originalInvoiceId);
        return this;
    }

    public void setOriginalInvoiceId(Integer originalInvoiceId) {
        this.originalInvoiceId = originalInvoiceId;
    }

    public String getCode() {
        return this.code;
    }

    public SalesInvoiceDummy code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getInvoiceDate() {
        return this.invoiceDate;
    }

    public SalesInvoiceDummy invoiceDate(Instant invoiceDate) {
        this.setInvoiceDate(invoiceDate);
        return this;
    }

    public void setInvoiceDate(Instant invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public SalesInvoiceDummy createdDate(Instant createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getQuoteID() {
        return this.quoteID;
    }

    public SalesInvoiceDummy quoteID(Integer quoteID) {
        this.setQuoteID(quoteID);
        return this;
    }

    public void setQuoteID(Integer quoteID) {
        this.quoteID = quoteID;
    }

    public Integer getOrderID() {
        return this.orderID;
    }

    public SalesInvoiceDummy orderID(Integer orderID) {
        this.setOrderID(orderID);
        return this;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Instant getDeliveryDate() {
        return this.deliveryDate;
    }

    public SalesInvoiceDummy deliveryDate(Instant deliveryDate) {
        this.setDeliveryDate(deliveryDate);
        return this;
    }

    public void setDeliveryDate(Instant deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getSalesRepID() {
        return this.salesRepID;
    }

    public SalesInvoiceDummy salesRepID(Integer salesRepID) {
        this.setSalesRepID(salesRepID);
        return this;
    }

    public void setSalesRepID(Integer salesRepID) {
        this.salesRepID = salesRepID;
    }

    public String getSalesRepName() {
        return this.salesRepName;
    }

    public SalesInvoiceDummy salesRepName(String salesRepName) {
        this.setSalesRepName(salesRepName);
        return this;
    }

    public void setSalesRepName(String salesRepName) {
        this.salesRepName = salesRepName;
    }

    public String getDeliverFrom() {
        return this.deliverFrom;
    }

    public SalesInvoiceDummy deliverFrom(String deliverFrom) {
        this.setDeliverFrom(deliverFrom);
        return this;
    }

    public void setDeliverFrom(String deliverFrom) {
        this.deliverFrom = deliverFrom;
    }

    public Integer getCustomerID() {
        return this.customerID;
    }

    public SalesInvoiceDummy customerID(Integer customerID) {
        this.setCustomerID(customerID);
        return this;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public SalesInvoiceDummy customerName(String customerName) {
        this.setCustomerName(customerName);
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return this.customerAddress;
    }

    public SalesInvoiceDummy customerAddress(String customerAddress) {
        this.setCustomerAddress(customerAddress);
        return this;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public SalesInvoiceDummy deliveryAddress(String deliveryAddress) {
        this.setDeliveryAddress(deliveryAddress);
        return this;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Float getSubTotal() {
        return this.subTotal;
    }

    public SalesInvoiceDummy subTotal(Float subTotal) {
        this.setSubTotal(subTotal);
        return this;
    }

    public void setSubTotal(Float subTotal) {
        this.subTotal = subTotal;
    }

    public Float getTotalTax() {
        return this.totalTax;
    }

    public SalesInvoiceDummy totalTax(Float totalTax) {
        this.setTotalTax(totalTax);
        return this;
    }

    public void setTotalTax(Float totalTax) {
        this.totalTax = totalTax;
    }

    public Float getTotalDiscount() {
        return this.totalDiscount;
    }

    public SalesInvoiceDummy totalDiscount(Float totalDiscount) {
        this.setTotalDiscount(totalDiscount);
        return this;
    }

    public void setTotalDiscount(Float totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Float getNetTotal() {
        return this.netTotal;
    }

    public SalesInvoiceDummy netTotal(Float netTotal) {
        this.setNetTotal(netTotal);
        return this;
    }

    public void setNetTotal(Float netTotal) {
        this.netTotal = netTotal;
    }

    public String getMessage() {
        return this.message;
    }

    public SalesInvoiceDummy message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public SalesInvoiceDummy lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public SalesInvoiceDummy lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Float getPaidAmount() {
        return this.paidAmount;
    }

    public SalesInvoiceDummy paidAmount(Float paidAmount) {
        this.setPaidAmount(paidAmount);
        return this;
    }

    public void setPaidAmount(Float paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Float getAmountOwing() {
        return this.amountOwing;
    }

    public SalesInvoiceDummy amountOwing(Float amountOwing) {
        this.setAmountOwing(amountOwing);
        return this;
    }

    public void setAmountOwing(Float amountOwing) {
        this.amountOwing = amountOwing;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public SalesInvoiceDummy isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getLocationID() {
        return this.locationID;
    }

    public SalesInvoiceDummy locationID(Integer locationID) {
        this.setLocationID(locationID);
        return this;
    }

    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    public String getLocationCode() {
        return this.locationCode;
    }

    public SalesInvoiceDummy locationCode(String locationCode) {
        this.setLocationCode(locationCode);
        return this;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getReferenceCode() {
        return this.referenceCode;
    }

    public SalesInvoiceDummy referenceCode(String referenceCode) {
        this.setReferenceCode(referenceCode);
        return this;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public Integer getCreatedById() {
        return this.createdById;
    }

    public SalesInvoiceDummy createdById(Integer createdById) {
        this.setCreatedById(createdById);
        return this;
    }

    public void setCreatedById(Integer createdById) {
        this.createdById = createdById;
    }

    public String getCreatedByName() {
        return this.createdByName;
    }

    public SalesInvoiceDummy createdByName(String createdByName) {
        this.setCreatedByName(createdByName);
        return this;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public Float getAutoCareCharges() {
        return this.autoCareCharges;
    }

    public SalesInvoiceDummy autoCareCharges(Float autoCareCharges) {
        this.setAutoCareCharges(autoCareCharges);
        return this;
    }

    public void setAutoCareCharges(Float autoCareCharges) {
        this.autoCareCharges = autoCareCharges;
    }

    public Integer getAutoCareJobId() {
        return this.autoCareJobId;
    }

    public SalesInvoiceDummy autoCareJobId(Integer autoCareJobId) {
        this.setAutoCareJobId(autoCareJobId);
        return this;
    }

    public void setAutoCareJobId(Integer autoCareJobId) {
        this.autoCareJobId = autoCareJobId;
    }

    public String getVehicleNo() {
        return this.vehicleNo;
    }

    public SalesInvoiceDummy vehicleNo(String vehicleNo) {
        this.setVehicleNo(vehicleNo);
        return this;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Float getNbtAmount() {
        return this.nbtAmount;
    }

    public SalesInvoiceDummy nbtAmount(Float nbtAmount) {
        this.setNbtAmount(nbtAmount);
        return this;
    }

    public void setNbtAmount(Float nbtAmount) {
        this.nbtAmount = nbtAmount;
    }

    public Float getVatAmount() {
        return this.vatAmount;
    }

    public SalesInvoiceDummy vatAmount(Float vatAmount) {
        this.setVatAmount(vatAmount);
        return this;
    }

    public void setVatAmount(Float vatAmount) {
        this.vatAmount = vatAmount;
    }

    public Float getDummyCommission() {
        return this.dummyCommission;
    }

    public SalesInvoiceDummy dummyCommission(Float dummyCommission) {
        this.setDummyCommission(dummyCommission);
        return this;
    }

    public void setDummyCommission(Float dummyCommission) {
        this.dummyCommission = dummyCommission;
    }

    public Instant getCommissionPaidDate() {
        return this.commissionPaidDate;
    }

    public SalesInvoiceDummy commissionPaidDate(Instant commissionPaidDate) {
        this.setCommissionPaidDate(commissionPaidDate);
        return this;
    }

    public void setCommissionPaidDate(Instant commissionPaidDate) {
        this.commissionPaidDate = commissionPaidDate;
    }

    public Float getPaidCommission() {
        return this.paidCommission;
    }

    public SalesInvoiceDummy paidCommission(Float paidCommission) {
        this.setPaidCommission(paidCommission);
        return this;
    }

    public void setPaidCommission(Float paidCommission) {
        this.paidCommission = paidCommission;
    }

    public Integer getPaidBy() {
        return this.paidBy;
    }

    public SalesInvoiceDummy paidBy(Integer paidBy) {
        this.setPaidBy(paidBy);
        return this;
    }

    public void setPaidBy(Integer paidBy) {
        this.paidBy = paidBy;
    }

    public String getOriginalInvoiceCode() {
        return this.originalInvoiceCode;
    }

    public SalesInvoiceDummy originalInvoiceCode(String originalInvoiceCode) {
        this.setOriginalInvoiceCode(originalInvoiceCode);
        return this;
    }

    public void setOriginalInvoiceCode(String originalInvoiceCode) {
        this.originalInvoiceCode = originalInvoiceCode;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesInvoiceDummy)) {
            return false;
        }
        return getId() != null && getId().equals(((SalesInvoiceDummy) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesInvoiceDummy{" +
            "id=" + getId() +
            ", originalInvoiceId=" + getOriginalInvoiceId() +
            ", code='" + getCode() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", quoteID=" + getQuoteID() +
            ", orderID=" + getOrderID() +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", salesRepID=" + getSalesRepID() +
            ", salesRepName='" + getSalesRepName() + "'" +
            ", deliverFrom='" + getDeliverFrom() + "'" +
            ", customerID=" + getCustomerID() +
            ", customerName='" + getCustomerName() + "'" +
            ", customerAddress='" + getCustomerAddress() + "'" +
            ", deliveryAddress='" + getDeliveryAddress() + "'" +
            ", subTotal=" + getSubTotal() +
            ", totalTax=" + getTotalTax() +
            ", totalDiscount=" + getTotalDiscount() +
            ", netTotal=" + getNetTotal() +
            ", message='" + getMessage() + "'" +
            ", lmu=" + getLmu() +
            ", lmd='" + getLmd() + "'" +
            ", paidAmount=" + getPaidAmount() +
            ", amountOwing=" + getAmountOwing() +
            ", isActive='" + getIsActive() + "'" +
            ", locationID=" + getLocationID() +
            ", locationCode='" + getLocationCode() + "'" +
            ", referenceCode='" + getReferenceCode() + "'" +
            ", createdById=" + getCreatedById() +
            ", createdByName='" + getCreatedByName() + "'" +
            ", autoCareCharges=" + getAutoCareCharges() +
            ", autoCareJobId=" + getAutoCareJobId() +
            ", vehicleNo='" + getVehicleNo() + "'" +
            ", nbtAmount=" + getNbtAmount() +
            ", vatAmount=" + getVatAmount() +
            ", dummyCommission=" + getDummyCommission() +
            ", commissionPaidDate='" + getCommissionPaidDate() + "'" +
            ", paidCommission=" + getPaidCommission() +
            ", paidBy=" + getPaidBy() +
            ", originalInvoiceCode='" + getOriginalInvoiceCode() + "'" +
            "}";
    }
}
