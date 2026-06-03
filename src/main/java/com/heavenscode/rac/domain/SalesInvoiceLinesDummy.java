package com.heavenscode.rac.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A SalesInvoiceLinesDummy.
 */
@Entity
@Table(name = "salesinvoicelinesdummy")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesInvoiceLinesDummy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // or SEQUENCE depending on your DB
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "invocieid")
    private Integer invoiceId;

    @Column(name = "lineid")
    private Integer lineId;

    @Column(name = "itemid")
    private Integer itemId;

    @Column(name = "itemcode")
    private String itemCode;

    @Column(name = "itemname")
    private String itemName;

    @Column(name = "description")
    private String description;

    @Column(name = "unitofmeasurement")
    private String unitOfMeasurement;

    @Column(name = "quantity")
    private Float quantity;

    @Column(name = "itemcost")
    private Float itemCost;

    @Column(name = "itemprice")
    private Float itemPrice;

    @Column(name = "discount")
    private Float discount;

    @Column(name = "tax")
    private Float tax;

    @Column(name = "sellingprice")
    private Float sellingPrice;

    @Column(name = "linetotal")
    private Float lineTotal;

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

    public SalesInvoiceLinesDummy id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInvoiceId() {
        return this.invoiceId;
    }

    public SalesInvoiceLinesDummy invoiceId(Integer invoiceId) {
        this.setInvoiceId(invoiceId);
        return this;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getLineId() {
        return this.lineId;
    }

    public SalesInvoiceLinesDummy lineId(Integer lineId) {
        this.setLineId(lineId);
        return this;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getItemId() {
        return this.itemId;
    }

    public SalesInvoiceLinesDummy itemId(Integer itemId) {
        this.setItemId(itemId);
        return this;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public SalesInvoiceLinesDummy itemCode(String itemCode) {
        this.setItemCode(itemCode);
        return this;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return this.itemName;
    }

    public SalesInvoiceLinesDummy itemName(String itemName) {
        this.setItemName(itemName);
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return this.description;
    }

    public SalesInvoiceLinesDummy description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitOfMeasurement() {
        return this.unitOfMeasurement;
    }

    public SalesInvoiceLinesDummy unitOfMeasurement(String unitOfMeasurement) {
        this.setUnitOfMeasurement(unitOfMeasurement);
        return this;
    }

    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public Float getQuantity() {
        return this.quantity;
    }

    public SalesInvoiceLinesDummy quantity(Float quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }

    public Float getItemCost() {
        return this.itemCost;
    }

    public SalesInvoiceLinesDummy itemCost(Float itemCost) {
        this.setItemCost(itemCost);
        return this;
    }

    public void setItemCost(Float itemCost) {
        this.itemCost = itemCost;
    }

    public Float getItemPrice() {
        return this.itemPrice;
    }

    public SalesInvoiceLinesDummy itemPrice(Float itemPrice) {
        this.setItemPrice(itemPrice);
        return this;
    }

    public void setItemPrice(Float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Float getDiscount() {
        return this.discount;
    }

    public SalesInvoiceLinesDummy discount(Float discount) {
        this.setDiscount(discount);
        return this;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getTax() {
        return this.tax;
    }

    public SalesInvoiceLinesDummy tax(Float tax) {
        this.setTax(tax);
        return this;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getSellingPrice() {
        return this.sellingPrice;
    }

    public SalesInvoiceLinesDummy sellingPrice(Float sellingPrice) {
        this.setSellingPrice(sellingPrice);
        return this;
    }

    public void setSellingPrice(Float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Float getLineTotal() {
        return this.lineTotal;
    }

    public SalesInvoiceLinesDummy lineTotal(Float lineTotal) {
        this.setLineTotal(lineTotal);
        return this;
    }

    public void setLineTotal(Float lineTotal) {
        this.lineTotal = lineTotal;
    }

    public Integer getLmu() {
        return this.lmu;
    }

    public SalesInvoiceLinesDummy lmu(Integer lmu) {
        this.setLmu(lmu);
        return this;
    }

    public void setLmu(Integer lmu) {
        this.lmu = lmu;
    }

    public Instant getLmd() {
        return this.lmd;
    }

    public SalesInvoiceLinesDummy lmd(Instant lmd) {
        this.setLmd(lmd);
        return this;
    }

    public void setLmd(Instant lmd) {
        this.lmd = lmd;
    }

    public Boolean getNbt() {
        return this.nbt;
    }

    public SalesInvoiceLinesDummy nbt(Boolean nbt) {
        this.setNbt(nbt);
        return this;
    }

    public void setNbt(Boolean nbt) {
        this.nbt = nbt;
    }

    public Boolean getVat() {
        return this.vat;
    }

    public SalesInvoiceLinesDummy vat(Boolean vat) {
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
        if (!(o instanceof SalesInvoiceLinesDummy)) {
            return false;
        }
        return getId() != null && getId().equals(((SalesInvoiceLinesDummy) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesInvoiceLinesDummy{" +
            "id=" + getId() +
            ", invoiceId=" + getInvoiceId() +
            ", lineId=" + getLineId() +
            ", itemId=" + getItemId() +
            ", itemCode='" + getItemCode() + "'" +
            ", itemName='" + getItemName() + "'" +
            ", description='" + getDescription() + "'" +
            ", unitOfMeasurement='" + getUnitOfMeasurement() + "'" +
            ", quantity=" + getQuantity() +
            ", itemCost=" + getItemCost() +
            ", itemPrice=" + getItemPrice() +
            ", discount=" + getDiscount() +
            ", tax=" + getTax() +
            ", sellingPrice=" + getSellingPrice() +
            ", lineTotal=" + getLineTotal() +
            ", lmu=" + getLmu() +
            ", lmd='" + getLmd() + "'" +
            ", nbt='" + getNbt() + "'" +
            ", vat='" + getVat() + "'" +
            "}";
    }
}
