package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A InvoiceServiceCharge.
 */
@Entity
@Table(name = "salesinvoiceservicechargeline")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceServiceCharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "invoiceid")
    private Long invoiceId;

    @Column(name = "lineid")
    private Long lineId;

    @Column(name = "optionid")
    private Long optionId;

    @Column(name = "servicename")
    private String serviceName;

    @Column(name = "servicediscription")
    private String serviceDiscription;

    @Column(name = "value")
    private Double value;

    @Column(name = "addedbyid")
    private Long addedById;

    @Column(name = "iscustomersrvice")
    private Boolean isCustomerSrvice;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "serviceprice")
    private Double servicePrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InvoiceServiceCharge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceId() {
        return this.invoiceId;
    }

    public InvoiceServiceCharge invoiceId(Long invoiceId) {
        this.setInvoiceId(invoiceId);
        return this;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getLineId() {
        return this.lineId;
    }

    public InvoiceServiceCharge lineId(Long lineId) {
        this.setLineId(lineId);
        return this;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getOptionId() {
        return this.optionId;
    }

    public InvoiceServiceCharge optionId(Long optionId) {
        this.setOptionId(optionId);
        return this;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public InvoiceServiceCharge serviceName(String serviceName) {
        this.setServiceName(serviceName);
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDiscription() {
        return this.serviceDiscription;
    }

    public InvoiceServiceCharge serviceDiscription(String serviceDiscription) {
        this.setServiceDiscription(serviceDiscription);
        return this;
    }

    public void setServiceDiscription(String serviceDiscription) {
        this.serviceDiscription = serviceDiscription;
    }

    public Double getValue() {
        return this.value;
    }

    public InvoiceServiceCharge value(Double value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getAddedById() {
        return this.addedById;
    }

    public InvoiceServiceCharge addedById(Long addedById) {
        this.setAddedById(addedById);
        return this;
    }

    public void setAddedById(Long addedById) {
        this.addedById = addedById;
    }

    public Boolean getIsCustomerSrvice() {
        return this.isCustomerSrvice;
    }

    public InvoiceServiceCharge isCustomerSrvice(Boolean isCustomerSrvice) {
        this.setIsCustomerSrvice(isCustomerSrvice);
        return this;
    }

    public void setIsCustomerSrvice(Boolean isCustomerSrvice) {
        this.isCustomerSrvice = isCustomerSrvice;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public InvoiceServiceCharge discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getServicePrice() {
        return this.servicePrice;
    }

    public InvoiceServiceCharge servicePrice(Double servicePrice) {
        this.setServicePrice(servicePrice);
        return this;
    }

    public void setServicePrice(Double servicePrice) {
        this.servicePrice = servicePrice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InvoiceServiceCharge)) {
            return false;
        }
        return getId() != null && getId().equals(((InvoiceServiceCharge) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceServiceCharge{" +
            "id=" + getId() +
            ", invoiceId=" + getInvoiceId() +
            ", lineId=" + getLineId() +
            ", optionId=" + getOptionId() +
            ", serviceName='" + getServiceName() + "'" +
            ", serviceDiscription='" + getServiceDiscription() + "'" +
            ", value=" + getValue() +
            ", addedById=" + getAddedById() +
            ", isCustomerSrvice='" + getIsCustomerSrvice() + "'" +
            ", discount=" + getDiscount() +
            ", servicePrice=" + getServicePrice() +
            "}";
    }
}
