package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A InvoiceServiceCommon.
 */
@Entity
@Table(name = "saleinvoicecommonservicecharge")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InvoiceServiceCommon implements Serializable {

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

    @Column(name = "mainid")
    private Long mainId;

    @Column(name = "code")
    private Double code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private Double value;

    @Column(name = "addedbyid")
    private Long addedById;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "serviceprice")
    private Double servicePrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InvoiceServiceCommon id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceId() {
        return this.invoiceId;
    }

    public InvoiceServiceCommon invoiceId(Long invoiceId) {
        this.setInvoiceId(invoiceId);
        return this;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getLineId() {
        return this.lineId;
    }

    public InvoiceServiceCommon lineId(Long lineId) {
        this.setLineId(lineId);
        return this;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getOptionId() {
        return this.optionId;
    }

    public InvoiceServiceCommon optionId(Long optionId) {
        this.setOptionId(optionId);
        return this;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public Long getMainId() {
        return this.mainId;
    }

    public InvoiceServiceCommon mainId(Long mainId) {
        this.setMainId(mainId);
        return this;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public Double getCode() {
        return this.code;
    }

    public InvoiceServiceCommon code(Double code) {
        this.setCode(code);
        return this;
    }

    public void setCode(Double code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public InvoiceServiceCommon name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public InvoiceServiceCommon description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getValue() {
        return this.value;
    }

    public InvoiceServiceCommon value(Double value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getAddedById() {
        return this.addedById;
    }

    public InvoiceServiceCommon addedById(Long addedById) {
        this.setAddedById(addedById);
        return this;
    }

    public void setAddedById(Long addedById) {
        this.addedById = addedById;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public InvoiceServiceCommon discount(Double discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getServicePrice() {
        return this.servicePrice;
    }

    public InvoiceServiceCommon servicePrice(Double servicePrice) {
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
        if (!(o instanceof InvoiceServiceCommon)) {
            return false;
        }
        return getId() != null && getId().equals(((InvoiceServiceCommon) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InvoiceServiceCommon{" +
            "id=" + getId() +
            ", invoiceId=" + getInvoiceId() +
            ", lineId=" + getLineId() +
            ", optionId=" + getOptionId() +
            ", mainId=" + getMainId() +
            ", code=" + getCode() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", value=" + getValue() +
            ", addedById=" + getAddedById() +
            ", discount=" + getDiscount() +
            ", servicePrice=" + getServicePrice() +
            "}";
    }
}
