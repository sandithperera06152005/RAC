package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A SaleInvoiceCommonServiceCharge.
 */
@Entity
@Table(name = "saleinvoicecommonservicecharge")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaleInvoiceCommonServiceCharge implements Serializable {

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

    @Column(name = "mainid")
    private Integer mainId;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private Float value;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "serviceprice")
    private Float servicePrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SaleInvoiceCommonServiceCharge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInvoiceId() {
        return this.invoiceId;
    }

    public SaleInvoiceCommonServiceCharge invoiceId(Integer invoiceId) {
        this.setInvoiceId(invoiceId);
        return this;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getLineId() {
        return this.lineId;
    }

    public SaleInvoiceCommonServiceCharge lineId(Integer lineId) {
        this.setLineId(lineId);
        return this;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getOptionId() {
        return this.optionId;
    }

    public SaleInvoiceCommonServiceCharge optionId(Integer optionId) {
        this.setOptionId(optionId);
        return this;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public Integer getMainId() {
        return this.mainId;
    }

    public SaleInvoiceCommonServiceCharge mainId(Integer mainId) {
        this.setMainId(mainId);
        return this;
    }

    public void setMainId(Integer mainId) {
        this.mainId = mainId;
    }

    public String getCode() {
        return this.code;
    }

    public SaleInvoiceCommonServiceCharge code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public SaleInvoiceCommonServiceCharge name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public SaleInvoiceCommonServiceCharge description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getValue() {
        return this.value;
    }

    public SaleInvoiceCommonServiceCharge value(Float value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public SaleInvoiceCommonServiceCharge discount(Float discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getServicePrice() {
        return this.servicePrice;
    }

    public SaleInvoiceCommonServiceCharge servicePrice(Float servicePrice) {
        this.setServicePrice(servicePrice);
        return this;
    }

    public void setServicePrice(Float servicePrice) {
        this.servicePrice = servicePrice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SaleInvoiceCommonServiceCharge)) {
            return false;
        }
        return getId() != null && getId().equals(((SaleInvoiceCommonServiceCharge) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SaleInvoiceCommonServiceCharge{" +
            "id=" + getId() +
            ", invoiceId=" + getInvoiceId() +
            ", lineId=" + getLineId() +
            ", optionId=" + getOptionId() +
            ", mainId=" + getMainId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", value=" + getValue() +
            ", discount=" + getDiscount() +
            ", servicePrice=" + getServicePrice() +
            "}";
    }
}
