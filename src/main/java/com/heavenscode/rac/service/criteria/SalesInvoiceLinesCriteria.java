package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.SalesInvoiceLines} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.SalesInvoiceLinesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sales-invoice-lines?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesInvoiceLinesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter invoiceid;

    private IntegerFilter lineid;

    private IntegerFilter itemid;

    private StringFilter itemcode;

    private StringFilter itemname;

    private StringFilter description;

    private StringFilter unitofmeasurement;

    private FloatFilter quantity;

    private FloatFilter itemcost;

    private FloatFilter itemprice;

    private FloatFilter discount;

    private FloatFilter tax;

    private FloatFilter sellingprice;

    private FloatFilter linetotal;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private BooleanFilter nbt;

    private BooleanFilter vat;

    private Boolean distinct;

    public SalesInvoiceLinesCriteria() {}

    public SalesInvoiceLinesCriteria(SalesInvoiceLinesCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.invoiceid = other.optionalInvoiceid().map(IntegerFilter::copy).orElse(null);
        this.lineid = other.optionalLineid().map(IntegerFilter::copy).orElse(null);
        this.itemid = other.optionalItemid().map(IntegerFilter::copy).orElse(null);
        this.itemcode = other.optionalItemcode().map(StringFilter::copy).orElse(null);
        this.itemname = other.optionalItemname().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.unitofmeasurement = other.optionalUnitofmeasurement().map(StringFilter::copy).orElse(null);
        this.quantity = other.optionalQuantity().map(FloatFilter::copy).orElse(null);
        this.itemcost = other.optionalItemcost().map(FloatFilter::copy).orElse(null);
        this.itemprice = other.optionalItemprice().map(FloatFilter::copy).orElse(null);
        this.discount = other.optionalDiscount().map(FloatFilter::copy).orElse(null);
        this.tax = other.optionalTax().map(FloatFilter::copy).orElse(null);
        this.sellingprice = other.optionalSellingprice().map(FloatFilter::copy).orElse(null);
        this.linetotal = other.optionalLinetotal().map(FloatFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.nbt = other.optionalNbt().map(BooleanFilter::copy).orElse(null);
        this.vat = other.optionalVat().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SalesInvoiceLinesCriteria copy() {
        return new SalesInvoiceLinesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getInvoiceid() {
        return invoiceid;
    }

    public Optional<IntegerFilter> optionalInvoiceid() {
        return Optional.ofNullable(invoiceid);
    }

    public IntegerFilter invoiceid() {
        if (invoiceid == null) {
            setInvoiceid(new IntegerFilter());
        }
        return invoiceid;
    }

    public void setInvoiceid(IntegerFilter invoiceid) {
        this.invoiceid = invoiceid;
    }

    public IntegerFilter getLineid() {
        return lineid;
    }

    public Optional<IntegerFilter> optionalLineid() {
        return Optional.ofNullable(lineid);
    }

    public IntegerFilter lineid() {
        if (lineid == null) {
            setLineid(new IntegerFilter());
        }
        return lineid;
    }

    public void setLineid(IntegerFilter lineid) {
        this.lineid = lineid;
    }

    public IntegerFilter getItemid() {
        return itemid;
    }

    public Optional<IntegerFilter> optionalItemid() {
        return Optional.ofNullable(itemid);
    }

    public IntegerFilter itemid() {
        if (itemid == null) {
            setItemid(new IntegerFilter());
        }
        return itemid;
    }

    public void setItemid(IntegerFilter itemid) {
        this.itemid = itemid;
    }

    public StringFilter getItemcode() {
        return itemcode;
    }

    public Optional<StringFilter> optionalItemcode() {
        return Optional.ofNullable(itemcode);
    }

    public StringFilter itemcode() {
        if (itemcode == null) {
            setItemcode(new StringFilter());
        }
        return itemcode;
    }

    public void setItemcode(StringFilter itemcode) {
        this.itemcode = itemcode;
    }

    public StringFilter getItemname() {
        return itemname;
    }

    public Optional<StringFilter> optionalItemname() {
        return Optional.ofNullable(itemname);
    }

    public StringFilter itemname() {
        if (itemname == null) {
            setItemname(new StringFilter());
        }
        return itemname;
    }

    public void setItemname(StringFilter itemname) {
        this.itemname = itemname;
    }

    public StringFilter getDescription() {
        return description;
    }

    public Optional<StringFilter> optionalDescription() {
        return Optional.ofNullable(description);
    }

    public StringFilter description() {
        if (description == null) {
            setDescription(new StringFilter());
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getUnitofmeasurement() {
        return unitofmeasurement;
    }

    public Optional<StringFilter> optionalUnitofmeasurement() {
        return Optional.ofNullable(unitofmeasurement);
    }

    public StringFilter unitofmeasurement() {
        if (unitofmeasurement == null) {
            setUnitofmeasurement(new StringFilter());
        }
        return unitofmeasurement;
    }

    public void setUnitofmeasurement(StringFilter unitofmeasurement) {
        this.unitofmeasurement = unitofmeasurement;
    }

    public FloatFilter getQuantity() {
        return quantity;
    }

    public Optional<FloatFilter> optionalQuantity() {
        return Optional.ofNullable(quantity);
    }

    public FloatFilter quantity() {
        if (quantity == null) {
            setQuantity(new FloatFilter());
        }
        return quantity;
    }

    public void setQuantity(FloatFilter quantity) {
        this.quantity = quantity;
    }

    public FloatFilter getItemcost() {
        return itemcost;
    }

    public Optional<FloatFilter> optionalItemcost() {
        return Optional.ofNullable(itemcost);
    }

    public FloatFilter itemcost() {
        if (itemcost == null) {
            setItemcost(new FloatFilter());
        }
        return itemcost;
    }

    public void setItemcost(FloatFilter itemcost) {
        this.itemcost = itemcost;
    }

    public FloatFilter getItemprice() {
        return itemprice;
    }

    public Optional<FloatFilter> optionalItemprice() {
        return Optional.ofNullable(itemprice);
    }

    public FloatFilter itemprice() {
        if (itemprice == null) {
            setItemprice(new FloatFilter());
        }
        return itemprice;
    }

    public void setItemprice(FloatFilter itemprice) {
        this.itemprice = itemprice;
    }

    public FloatFilter getDiscount() {
        return discount;
    }

    public Optional<FloatFilter> optionalDiscount() {
        return Optional.ofNullable(discount);
    }

    public FloatFilter discount() {
        if (discount == null) {
            setDiscount(new FloatFilter());
        }
        return discount;
    }

    public void setDiscount(FloatFilter discount) {
        this.discount = discount;
    }

    public FloatFilter getTax() {
        return tax;
    }

    public Optional<FloatFilter> optionalTax() {
        return Optional.ofNullable(tax);
    }

    public FloatFilter tax() {
        if (tax == null) {
            setTax(new FloatFilter());
        }
        return tax;
    }

    public void setTax(FloatFilter tax) {
        this.tax = tax;
    }

    public FloatFilter getSellingprice() {
        return sellingprice;
    }

    public Optional<FloatFilter> optionalSellingprice() {
        return Optional.ofNullable(sellingprice);
    }

    public FloatFilter sellingprice() {
        if (sellingprice == null) {
            setSellingprice(new FloatFilter());
        }
        return sellingprice;
    }

    public void setSellingprice(FloatFilter sellingprice) {
        this.sellingprice = sellingprice;
    }

    public FloatFilter getLinetotal() {
        return linetotal;
    }

    public Optional<FloatFilter> optionalLinetotal() {
        return Optional.ofNullable(linetotal);
    }

    public FloatFilter linetotal() {
        if (linetotal == null) {
            setLinetotal(new FloatFilter());
        }
        return linetotal;
    }

    public void setLinetotal(FloatFilter linetotal) {
        this.linetotal = linetotal;
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

    public BooleanFilter getNbt() {
        return nbt;
    }

    public Optional<BooleanFilter> optionalNbt() {
        return Optional.ofNullable(nbt);
    }

    public BooleanFilter nbt() {
        if (nbt == null) {
            setNbt(new BooleanFilter());
        }
        return nbt;
    }

    public void setNbt(BooleanFilter nbt) {
        this.nbt = nbt;
    }

    public BooleanFilter getVat() {
        return vat;
    }

    public Optional<BooleanFilter> optionalVat() {
        return Optional.ofNullable(vat);
    }

    public BooleanFilter vat() {
        if (vat == null) {
            setVat(new BooleanFilter());
        }
        return vat;
    }

    public void setVat(BooleanFilter vat) {
        this.vat = vat;
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
        final SalesInvoiceLinesCriteria that = (SalesInvoiceLinesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceid, that.invoiceid) &&
            Objects.equals(lineid, that.lineid) &&
            Objects.equals(itemid, that.itemid) &&
            Objects.equals(itemcode, that.itemcode) &&
            Objects.equals(itemname, that.itemname) &&
            Objects.equals(description, that.description) &&
            Objects.equals(unitofmeasurement, that.unitofmeasurement) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(itemcost, that.itemcost) &&
            Objects.equals(itemprice, that.itemprice) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(sellingprice, that.sellingprice) &&
            Objects.equals(linetotal, that.linetotal) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(nbt, that.nbt) &&
            Objects.equals(vat, that.vat) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            invoiceid,
            lineid,
            itemid,
            itemcode,
            itemname,
            description,
            unitofmeasurement,
            quantity,
            itemcost,
            itemprice,
            discount,
            tax,
            sellingprice,
            linetotal,
            lmu,
            lmd,
            nbt,
            vat,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SalesInvoiceLinesCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInvoiceid().map(f -> "invoiceid=" + f + ", ").orElse("") +
            optionalLineid().map(f -> "lineid=" + f + ", ").orElse("") +
            optionalItemid().map(f -> "itemid=" + f + ", ").orElse("") +
            optionalItemcode().map(f -> "itemcode=" + f + ", ").orElse("") +
            optionalItemname().map(f -> "itemname=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalUnitofmeasurement().map(f -> "unitofmeasurement=" + f + ", ").orElse("") +
            optionalQuantity().map(f -> "quantity=" + f + ", ").orElse("") +
            optionalItemcost().map(f -> "itemcost=" + f + ", ").orElse("") +
            optionalItemprice().map(f -> "itemprice=" + f + ", ").orElse("") +
            optionalDiscount().map(f -> "discount=" + f + ", ").orElse("") +
            optionalTax().map(f -> "tax=" + f + ", ").orElse("") +
            optionalSellingprice().map(f -> "sellingprice=" + f + ", ").orElse("") +
            optionalLinetotal().map(f -> "linetotal=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalNbt().map(f -> "nbt=" + f + ", ").orElse("") +
            optionalVat().map(f -> "vat=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
