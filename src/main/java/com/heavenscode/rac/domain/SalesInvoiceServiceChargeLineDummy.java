package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A SalesInvoiceServiceChargeLineDummy.
 */
@Entity
@Table(name = "salesinvoiceservicechargelinedummy")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesInvoiceServiceChargeLineDummy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or SEQUENCE depending on your DB
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "invoiceid")
    private Integer invoiceId;

    @Column(name = "lineid")
    private Integer lineId;

    @Column(name = "optionid")
    private Integer optionId;

    @Column(name = "servicename")
    private String serviceName;

    @Column(name = "servicediscription")
    private String serviceDescription;

    @Column(name = "value")
    private Float value;

    @Column(name = "iscustomersrvice")
    private Boolean isCustomerService;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SalesInvoiceServiceChargeLineDummy id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInvoiceId() {
        return this.invoiceId;
    }

    public SalesInvoiceServiceChargeLineDummy invoiceId(Integer invoiceId) {
        this.setInvoiceId(invoiceId);
        return this;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getLineId() {
        return this.lineId;
    }

    public SalesInvoiceServiceChargeLineDummy lineId(Integer lineId) {
        this.setLineId(lineId);
        return this;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getOptionId() {
        return this.optionId;
    }

    public SalesInvoiceServiceChargeLineDummy optionId(Integer optionId) {
        this.setOptionId(optionId);
        return this;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public SalesInvoiceServiceChargeLineDummy serviceName(String serviceName) {
        this.setServiceName(serviceName);
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceDescription() {
        return this.serviceDescription;
    }

    public SalesInvoiceServiceChargeLineDummy serviceDescription(String serviceDescription) {
        this.setServiceDescription(serviceDescription);
        return this;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public Float getValue() {
        return this.value;
    }

    public SalesInvoiceServiceChargeLineDummy value(Float value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Boolean getIsCustomerService() {
        return this.isCustomerService;
    }

    public SalesInvoiceServiceChargeLineDummy isCustomerService(Boolean isCustomerService) {
        this.setIsCustomerService(isCustomerService);
        return this;
    }

    public void setIsCustomerService(Boolean isCustomerService) {
        this.isCustomerService = isCustomerService;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SalesInvoiceServiceChargeLineDummy)) {
            return false;
        }
        return getId() != null && getId().equals(((SalesInvoiceServiceChargeLineDummy) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesInvoiceServiceChargeLineDummy{" +
            "id=" + getId() +
            ", invoiceId=" + getInvoiceId() +
            ", lineId=" + getLineId() +
            ", optionId=" + getOptionId() +
            ", serviceName='" + getServiceName() + "'" +
            ", serviceDescription='" + getServiceDescription() + "'" +
            ", value=" + getValue() +
            ", isCustomerService='" + getIsCustomerService() + "'" +
            "}";
    }
}
