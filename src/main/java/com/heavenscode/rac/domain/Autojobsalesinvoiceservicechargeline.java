package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * A Autojobsalesinvoiceservicechargeline.
 */
@Entity
@Table(name = "autojobsalesinvoiceservicechargeline")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Autojobsalesinvoiceservicechargeline implements Serializable {

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

    @Column(name = "servicename")
    private String servicename;

    @Column(name = "servicediscription")
    private String servicediscription;

    @Column(name = "value")
    private Float value;

    @Column(name = "addedbyid")
    private Integer addedbyid;

    @Column(name = "iscustomersrvice")
    private Boolean iscustomersrvice;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "serviceprice")
    private Float serviceprice;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Autojobsalesinvoiceservicechargeline id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInvoiceid() {
        return this.invoiceid;
    }

    public Autojobsalesinvoiceservicechargeline invoiceid(Integer invoiceid) {
        this.setInvoiceid(invoiceid);
        return this;
    }

    public void setInvoiceid(Integer invoiceid) {
        this.invoiceid = invoiceid;
    }

    public Integer getLineid() {
        return this.lineid;
    }

    public Autojobsalesinvoiceservicechargeline lineid(Integer lineid) {
        this.setLineid(lineid);
        return this;
    }

    public void setLineid(Integer lineid) {
        this.lineid = lineid;
    }

    public Integer getOptionid() {
        return this.optionid;
    }

    public Autojobsalesinvoiceservicechargeline optionid(Integer optionid) {
        this.setOptionid(optionid);
        return this;
    }

    public void setOptionid(Integer optionid) {
        this.optionid = optionid;
    }

    public String getServicename() {
        return this.servicename;
    }

    public Autojobsalesinvoiceservicechargeline servicename(String servicename) {
        this.setServicename(servicename);
        return this;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getServicediscription() {
        return this.servicediscription;
    }

    public Autojobsalesinvoiceservicechargeline servicediscription(String servicediscription) {
        this.setServicediscription(servicediscription);
        return this;
    }

    public void setServicediscription(String servicediscription) {
        this.servicediscription = servicediscription;
    }

    public Float getValue() {
        return this.value;
    }

    public Autojobsalesinvoiceservicechargeline value(Float value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Integer getAddedbyid() {
        return this.addedbyid;
    }

    public Autojobsalesinvoiceservicechargeline addedbyid(Integer addedbyid) {
        this.setAddedbyid(addedbyid);
        return this;
    }

    public void setAddedbyid(Integer addedbyid) {
        this.addedbyid = addedbyid;
    }

    public Boolean getIscustomersrvice() {
        return this.iscustomersrvice;
    }

    public Autojobsalesinvoiceservicechargeline iscustomersrvice(Boolean iscustomersrvice) {
        this.setIscustomersrvice(iscustomersrvice);
        return this;
    }

    public void setIscustomersrvice(Boolean iscustomersrvice) {
        this.iscustomersrvice = iscustomersrvice;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public Autojobsalesinvoiceservicechargeline discount(Float discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getServiceprice() {
        return this.serviceprice;
    }

    public Autojobsalesinvoiceservicechargeline serviceprice(Float serviceprice) {
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
        if (!(o instanceof Autojobsalesinvoiceservicechargeline)) {
            return false;
        }
        return getId() != null && getId().equals(((Autojobsalesinvoiceservicechargeline) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Autojobsalesinvoiceservicechargeline{" +
            "id=" + getId() +
            ", invoiceid=" + getInvoiceid() +
            ", lineid=" + getLineid() +
            ", optionid=" + getOptionid() +
            ", servicename='" + getServicename() + "'" +
            ", servicediscription='" + getServicediscription() + "'" +
            ", value=" + getValue() +
            ", addedbyid=" + getAddedbyid() +
            ", iscustomersrvice='" + getIscustomersrvice() + "'" +
            ", discount=" + getDiscount() +
            ", serviceprice=" + getServiceprice() +
            "}";
    }
}
