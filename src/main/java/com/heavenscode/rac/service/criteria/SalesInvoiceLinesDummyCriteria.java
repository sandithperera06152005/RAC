package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.SalesInvoiceLinesDummy} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.SalesInvoiceLinesDummyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sales-invoice-lines-dummies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SalesInvoiceLinesDummyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter invoiceId;

    private IntegerFilter lineId;

    private IntegerFilter itemId;

    private StringFilter itemCode;

    private StringFilter itemName;

    private StringFilter description;

    private StringFilter unitOfMeasurement;

    private FloatFilter quantity;

    private FloatFilter itemCost;

    private FloatFilter itemPrice;

    private FloatFilter discount;

    private FloatFilter tax;

    private FloatFilter sellingPrice;

    private FloatFilter lineTotal;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private BooleanFilter nbt;

    private BooleanFilter vat;

    private Boolean distinct;

    public SalesInvoiceLinesDummyCriteria() {}

    public SalesInvoiceLinesDummyCriteria(SalesInvoiceLinesDummyCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.invoiceId = other.optionalInvoiceId().map(IntegerFilter::copy).orElse(null);
        this.lineId = other.optionalLineId().map(IntegerFilter::copy).orElse(null);
        this.itemId = other.optionalItemId().map(IntegerFilter::copy).orElse(null);
        this.itemCode = other.optionalItemCode().map(StringFilter::copy).orElse(null);
        this.itemName = other.optionalItemName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.unitOfMeasurement = other.optionalUnitOfMeasurement().map(StringFilter::copy).orElse(null);
        this.quantity = other.optionalQuantity().map(FloatFilter::copy).orElse(null);
        this.itemCost = other.optionalItemCost().map(FloatFilter::copy).orElse(null);
        this.itemPrice = other.optionalItemPrice().map(FloatFilter::copy).orElse(null);
        this.discount = other.optionalDiscount().map(FloatFilter::copy).orElse(null);
        this.tax = other.optionalTax().map(FloatFilter::copy).orElse(null);
        this.sellingPrice = other.optionalSellingPrice().map(FloatFilter::copy).orElse(null);
        this.lineTotal = other.optionalLineTotal().map(FloatFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.nbt = other.optionalNbt().map(BooleanFilter::copy).orElse(null);
        this.vat = other.optionalVat().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SalesInvoiceLinesDummyCriteria copy() {
        return new SalesInvoiceLinesDummyCriteria(this);
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

    public IntegerFilter getInvoiceId() {
        return invoiceId;
    }

    public Optional<IntegerFilter> optionalInvoiceId() {
        return Optional.ofNullable(invoiceId);
    }

    public IntegerFilter invoiceId() {
        if (invoiceId == null) {
            setInvoiceId(new IntegerFilter());
        }
        return invoiceId;
    }

    public void setInvoiceId(IntegerFilter invoiceId) {
        this.invoiceId = invoiceId;
    }

    public IntegerFilter getLineId() {
        return lineId;
    }

    public Optional<IntegerFilter> optionalLineId() {
        return Optional.ofNullable(lineId);
    }

    public IntegerFilter lineId() {
        if (lineId == null) {
            setLineId(new IntegerFilter());
        }
        return lineId;
    }

    public void setLineId(IntegerFilter lineId) {
        this.lineId = lineId;
    }

    public IntegerFilter getItemId() {
        return itemId;
    }

    public Optional<IntegerFilter> optionalItemId() {
        return Optional.ofNullable(itemId);
    }

    public IntegerFilter itemId() {
        if (itemId == null) {
            setItemId(new IntegerFilter());
        }
        return itemId;
    }

    public void setItemId(IntegerFilter itemId) {
        this.itemId = itemId;
    }

    public StringFilter getItemCode() {
        return itemCode;
    }

    public Optional<StringFilter> optionalItemCode() {
        return Optional.ofNullable(itemCode);
    }

    public StringFilter itemCode() {
        if (itemCode == null) {
            setItemCode(new StringFilter());
        }
        return itemCode;
    }

    public void setItemCode(StringFilter itemCode) {
        this.itemCode = itemCode;
    }

    public StringFilter getItemName() {
        return itemName;
    }

    public Optional<StringFilter> optionalItemName() {
        return Optional.ofNullable(itemName);
    }

    public StringFilter itemName() {
        if (itemName == null) {
            setItemName(new StringFilter());
        }
        return itemName;
    }

    public void setItemName(StringFilter itemName) {
        this.itemName = itemName;
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

    public StringFilter getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    public Optional<StringFilter> optionalUnitOfMeasurement() {
        return Optional.ofNullable(unitOfMeasurement);
    }

    public StringFilter unitOfMeasurement() {
        if (unitOfMeasurement == null) {
            setUnitOfMeasurement(new StringFilter());
        }
        return unitOfMeasurement;
    }

    public void setUnitOfMeasurement(StringFilter unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
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

    public FloatFilter getItemCost() {
        return itemCost;
    }

    public Optional<FloatFilter> optionalItemCost() {
        return Optional.ofNullable(itemCost);
    }

    public FloatFilter itemCost() {
        if (itemCost == null) {
            setItemCost(new FloatFilter());
        }
        return itemCost;
    }

    public void setItemCost(FloatFilter itemCost) {
        this.itemCost = itemCost;
    }

    public FloatFilter getItemPrice() {
        return itemPrice;
    }

    public Optional<FloatFilter> optionalItemPrice() {
        return Optional.ofNullable(itemPrice);
    }

    public FloatFilter itemPrice() {
        if (itemPrice == null) {
            setItemPrice(new FloatFilter());
        }
        return itemPrice;
    }

    public void setItemPrice(FloatFilter itemPrice) {
        this.itemPrice = itemPrice;
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

    public FloatFilter getSellingPrice() {
        return sellingPrice;
    }

    public Optional<FloatFilter> optionalSellingPrice() {
        return Optional.ofNullable(sellingPrice);
    }

    public FloatFilter sellingPrice() {
        if (sellingPrice == null) {
            setSellingPrice(new FloatFilter());
        }
        return sellingPrice;
    }

    public void setSellingPrice(FloatFilter sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public FloatFilter getLineTotal() {
        return lineTotal;
    }

    public Optional<FloatFilter> optionalLineTotal() {
        return Optional.ofNullable(lineTotal);
    }

    public FloatFilter lineTotal() {
        if (lineTotal == null) {
            setLineTotal(new FloatFilter());
        }
        return lineTotal;
    }

    public void setLineTotal(FloatFilter lineTotal) {
        this.lineTotal = lineTotal;
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
        final SalesInvoiceLinesDummyCriteria that = (SalesInvoiceLinesDummyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceId, that.invoiceId) &&
            Objects.equals(lineId, that.lineId) &&
            Objects.equals(itemId, that.itemId) &&
            Objects.equals(itemCode, that.itemCode) &&
            Objects.equals(itemName, that.itemName) &&
            Objects.equals(description, that.description) &&
            Objects.equals(unitOfMeasurement, that.unitOfMeasurement) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(itemCost, that.itemCost) &&
            Objects.equals(itemPrice, that.itemPrice) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(tax, that.tax) &&
            Objects.equals(sellingPrice, that.sellingPrice) &&
            Objects.equals(lineTotal, that.lineTotal) &&
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
            invoiceId,
            lineId,
            itemId,
            itemCode,
            itemName,
            description,
            unitOfMeasurement,
            quantity,
            itemCost,
            itemPrice,
            discount,
            tax,
            sellingPrice,
            lineTotal,
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
        return "SalesInvoiceLinesDummyCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalInvoiceId().map(f -> "invoiceId=" + f + ", ").orElse("") +
            optionalLineId().map(f -> "lineId=" + f + ", ").orElse("") +
            optionalItemId().map(f -> "itemId=" + f + ", ").orElse("") +
            optionalItemCode().map(f -> "itemCode=" + f + ", ").orElse("") +
            optionalItemName().map(f -> "itemName=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalUnitOfMeasurement().map(f -> "unitOfMeasurement=" + f + ", ").orElse("") +
            optionalQuantity().map(f -> "quantity=" + f + ", ").orElse("") +
            optionalItemCost().map(f -> "itemCost=" + f + ", ").orElse("") +
            optionalItemPrice().map(f -> "itemPrice=" + f + ", ").orElse("") +
            optionalDiscount().map(f -> "discount=" + f + ", ").orElse("") +
            optionalTax().map(f -> "tax=" + f + ", ").orElse("") +
            optionalSellingPrice().map(f -> "sellingPrice=" + f + ", ").orElse("") +
            optionalLineTotal().map(f -> "lineTotal=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalNbt().map(f -> "nbt=" + f + ", ").orElse("") +
            optionalVat().map(f -> "vat=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
