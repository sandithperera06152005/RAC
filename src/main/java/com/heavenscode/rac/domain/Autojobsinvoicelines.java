package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Autojobsinvoicelines.
 */
@Entity
@Table(name = "autojobsinvoicelines")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Autojobsinvoicelines implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "invocieid")
    private Integer invocieid;

    @Column(name = "lineid")
    private Integer lineid;

    @Column(name = "itemid")
    private Integer itemid;

    @Column(name = "itemcode")
    private String itemcode;

    @Column(name = "itemname")
    private String itemname;

    @Column(name = "description")
    private String description;

    @Column(name = "unitofmeasurement")
    private String unitofmeasurement;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "itemcost")
    private Float itemcost;

    @Column(name = "itemprice")
    private Float itemprice;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "tax")
    private Float tax;

    @Column(name = "sellingprice")
    private Float sellingprice;

    @Column(name = "linetotal")
    private Float linetotal;

    @Column(name = "lmu")
    private Integer lmu;

    @Column(name = "lmd")
    private Instant lmd;

    @Column(name = "nbt")
    private Boolean nbt;

    @Column(name = "vat")
    private Boolean vat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Autojobsinvoicelines id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInvocieid() {
        return this.invocieid;
    }

    public Autojobsinvoicelines invocieid(Integer invocieid) {
        this.setInvocieid(invocieid);
        return this;
    }

    public void setInvocieid(Integer invocieid) {
        this.invocieid = invocieid;
    }

    public Integer getLineid() {
        return this.lineid;
    }

    public Autojobsinvoicelines lineid(Integer lineid) {
        this.setLineid(lineid);
        return this;
    }

    public void setLineid(Integer lineid) {
        this.lineid = lineid;
    }

    public Integer getItemid() {
        return this.itemid;
    }

    public Autojobsinvoicelines itemid(Integer itemid) {
        this.setItemid(itemid);
        return this;
    }

    public void setItemid(Integer itemid) {
        this.itemid = itemid;
    }

    public String getItemcode() {
        return this.itemcode;
    }

    public Autojobsinvoicelines itemcode(String itemcode) {
        this.setItemcode(itemcode);
        return this;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemname() {
        return this.itemname;
    }

    public Autojobsinvoicelines itemname(String itemname) {
        this.setItemname(itemname);
        return this;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getDescription() {
        return this.description;
    }

    public Autojobsinvoicelines description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitofmeasurement() {
        return this.unitofmeasurement;
    }

    public Autojobsinvoicelines unitofmeasurement(String unitofmeasurement) {
        this.setUnitofmeasurement(unitofmeasurement);
        return this;
    }

    public void setUnitofmeasurement(String unitofmeasurement) {
        this.unitofmeasurement = unitofmeasurement;
    }

    public Float getQuantity() {
        return this.quantity;
    }

    public Autojobsinvoicelines quantity(Float quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getItemcost() {
        return this.itemcost;
    }

    public Autojobsinvoicelines itemcost(Float itemcost) {
        this.setItemcost(itemcost);
        return this;
    }

    public void setItemcost(Float itemcost) {
        this.itemcost = itemcost;
    }

    public Float getItemprice() {
        return this.itemprice;
    }

    public Autojobsinvoicelines itemprice(Float itemprice) {
        this.setItemprice(itemprice);
        return this;
    }

    public void setItemprice(Float itemprice) {
        this.itemprice = itemprice;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public Autojobsinvoicelines discount(Float discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getTax() {
        return this.tax;
    }

    public Autojobsinvoicelines tax(Float tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getSellingprice() {
        return this.sellingprice;
    }

    public Autojobsinvoicelines sellingprice(Float sellingprice) {
        this.setSellingprice(sellingprice);
        return this;
    }

    public void setSellingprice(Float sellingprice) {
        this.sellingprice = sellingprice;
    }

    public Float getLinetotal() {
        return this.linetotal;
    }

    public Autojobsinvoicelines linetotal(Float linetotal) {
        this.setLinetotal(linetotal);
        return this;
    }

    public void setLinetotal(Float linetotal) {
        this.linetotal = linetotal;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public Autojobsinvoicelines lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public Autojobsinvoicelines lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Boolean getNbt() {
        return this.nbt;
    }

    public Autojobsinvoicelines nbt(Boolean nbt) {
        this.setNbt(nbt);
        return this;
    }

    public void setNbt(Boolean nbt) {
        this.nbt = nbt;
    }

    public Boolean getVat() {
        return this.vat;
    }

    public Autojobsinvoicelines vat(Boolean vat) {
        this.setVat(vat);
        return this;
    }

    public void setVat(Boolean vat) {
        this.vat = vat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Autojobsinvoicelines)) {
            return false;
        }
        return getId() != null && getId().equals(((Autojobsinvoicelines) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Autojobsinvoicelines{" +
            "id=" + getId() +
            ", invocieid=" + getInvocieid() +
            ", lineid=" + getLineid() +
            ", itemid=" + getItemid() +
            ", itemcode='" + getItemcode() + "'" +
            ", itemname='" + getItemname() + "'" +
            ", description='" + getDescription() + "'" +
            ", unitofmeasurement='" + getUnitofmeasurement() + "'" +
            ", quantity=" + getQuantity() +
            ", itemcost=" + getItemcost() +
            ", itemprice=" + getItemprice() +
            ", discount=" + getDiscount() +
            ", tax=" + getTax() +
            ", sellingprice=" + getSellingprice() +
            ", linetotal=" + getLinetotal() +
            ", lmu=" + getLmu() +
            ", lmd='" + getLmd() + "'" +
            ", nbt='" + getNbt() + "'" +
            ", vat='" + getVat() + "'" +
            "}";
    }
}
