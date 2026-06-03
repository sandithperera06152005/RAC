package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Autojobsaleinvoicecommonservicecharge.
 */
@Entity
@Table(name = "autojobsaleinvoicecommonservicecharge")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Autojobsaleinvoicecommonservicecharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "invoiceid")
    private Integer invoiceid;

    @Column(name = "lineid")
    private Integer lineid;

    @Column(name = "optionid")
    private Integer optionid;

    @Column(name = "mainid")
    private Integer mainid;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private Float value;

    @Column(name = "addedbyid")
    private Integer addedbyid;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "serviceprice")
    private Float serviceprice;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Autojobsaleinvoicecommonservicecharge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInvoiceid() {
        return this.invoiceid;
    }

    public Autojobsaleinvoicecommonservicecharge invoiceid(Integer invoiceid) {
        this.setInvoiceid(invoiceid);
        return this;
    }

    public void setInvoiceid(Integer invoiceid) {
        this.invoiceid = invoiceid;
    }

    public Integer getLineid() {
        return this.lineid;
    }

    public Autojobsaleinvoicecommonservicecharge lineid(Integer lineid) {
        this.setLineid(lineid);
        return this;
    }

    public void setLineid(Integer lineid) {
        this.lineid = lineid;
    }

    public Integer getOptionid() {
        return this.optionid;
    }

    public Autojobsaleinvoicecommonservicecharge optionid(Integer optionid) {
        this.setOptionid(optionid);
        return this;
    }

    public void setOptionid(Integer optionid) {
        this.optionid = optionid;
    }

    public Integer getMainid() {
        return this.mainid;
    }

    public Autojobsaleinvoicecommonservicecharge mainid(Integer mainid) {
        this.setMainid(mainid);
        return this;
    }

    public void setMainid(Integer mainid) {
        this.mainid = mainid;
    }

    public String getCode() {
        return this.code;
    }

    public Autojobsaleinvoicecommonservicecharge code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Autojobsaleinvoicecommonservicecharge name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Autojobsaleinvoicecommonservicecharge description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getValue() {
        return this.value;
    }

    public Autojobsaleinvoicecommonservicecharge value(Float value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getAddedbyid() {
        return this.addedbyid;
    }

    public Autojobsaleinvoicecommonservicecharge addedbyid(Integer addedbyid) {
        this.setAddedbyid(addedbyid);
        return this;
    }

    public void setAddedbyid(Integer addedbyid) {
        this.addedbyid = addedbyid;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public Autojobsaleinvoicecommonservicecharge discount(Float discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getServiceprice() {
        return this.serviceprice;
    }

    public Autojobsaleinvoicecommonservicecharge serviceprice(Float serviceprice) {
        this.setServiceprice(serviceprice);
        return this;
    }

    public void setServiceprice(Float serviceprice) {
        this.serviceprice = serviceprice;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Autojobsaleinvoicecommonservicecharge)) {
            return false;
        }
        return getId() != null && getId().equals(((Autojobsaleinvoicecommonservicecharge) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Autojobsaleinvoicecommonservicecharge{" +
            "id=" + getId() +
            ", invoiceid=" + getInvoiceid() +
            ", lineid=" + getLineid() +
            ", optionid=" + getOptionid() +
            ", mainid=" + getMainid() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", value=" + getValue() +
            ", addedbyid=" + getAddedbyid() +
            ", discount=" + getDiscount() +
            ", serviceprice=" + getServiceprice() +
            "}";
    }
}
