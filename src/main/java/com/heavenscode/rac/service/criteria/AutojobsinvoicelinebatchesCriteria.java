package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Autojobsinvoicelinebatches} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.AutojobsinvoicelinebatchesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /autojobsinvoicelinebatches?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AutojobsinvoicelinebatchesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private IntegerFilter id;

    private IntegerFilter lineid;

    private IntegerFilter batchlineid;

    private IntegerFilter itemid;

    private StringFilter code;

    private IntegerFilter batchid;

    private StringFilter batchcode;

    private InstantFilter txdate;

    private InstantFilter manufacturedate;

    private InstantFilter expireddate;

    private FloatFilter qty;

    private FloatFilter cost;

    private FloatFilter price;

    private StringFilter notes;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private BooleanFilter nbt;

    private BooleanFilter vat;

    private FloatFilter discount;

    private FloatFilter total;

    private BooleanFilter issued;

    private IntegerFilter issuedby;

    private InstantFilter issueddatetime;

    private IntegerFilter addedbyid;

    private IntegerFilter canceloptid;

    private StringFilter cancelopt;

    private IntegerFilter cancelby;

    private Boolean distinct;

    public AutojobsinvoicelinebatchesCriteria() {}

    public AutojobsinvoicelinebatchesCriteria(AutojobsinvoicelinebatchesCriteria other) {
        this.id = other.optionalId().map(IntegerFilter::copy).orElse(null);
        this.lineid = other.optionalLineid().map(IntegerFilter::copy).orElse(null);
        this.batchlineid = other.optionalBatchlineid().map(IntegerFilter::copy).orElse(null);
        this.itemid = other.optionalItemid().map(IntegerFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.batchid = other.optionalBatchid().map(IntegerFilter::copy).orElse(null);
        this.batchcode = other.optionalBatchcode().map(StringFilter::copy).orElse(null);
        this.txdate = other.optionalTxdate().map(InstantFilter::copy).orElse(null);
        this.manufacturedate = other.optionalManufacturedate().map(InstantFilter::copy).orElse(null);
        this.expireddate = other.optionalExpireddate().map(InstantFilter::copy).orElse(null);
        this.qty = other.optionalQty().map(FloatFilter::copy).orElse(null);
        this.cost = other.optionalCost().map(FloatFilter::copy).orElse(null);
        this.price = other.optionalPrice().map(FloatFilter::copy).orElse(null);
        this.notes = other.optionalNotes().map(StringFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.nbt = other.optionalNbt().map(BooleanFilter::copy).orElse(null);
        this.vat = other.optionalVat().map(BooleanFilter::copy).orElse(null);
        this.discount = other.optionalDiscount().map(FloatFilter::copy).orElse(null);
        this.total = other.optionalTotal().map(FloatFilter::copy).orElse(null);
        this.issued = other.optionalIssued().map(BooleanFilter::copy).orElse(null);
        this.issuedby = other.optionalIssuedby().map(IntegerFilter::copy).orElse(null);
        this.issueddatetime = other.optionalIssueddatetime().map(InstantFilter::copy).orElse(null);
        this.addedbyid = other.optionalAddedbyid().map(IntegerFilter::copy).orElse(null);
        this.canceloptid = other.optionalCanceloptid().map(IntegerFilter::copy).orElse(null);
        this.cancelopt = other.optionalCancelopt().map(StringFilter::copy).orElse(null);
        this.cancelby = other.optionalCancelby().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AutojobsinvoicelinebatchesCriteria copy() {
        return new AutojobsinvoicelinebatchesCriteria(this);
    }

    public IntegerFilter getId() {
        return id;
    }

    public Optional<IntegerFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public IntegerFilter id() {
        if (id == null) {
            setId(new IntegerFilter());
        }
        return id;
    }

    public void setId(IntegerFilter id) {
        this.id = id;
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

    public IntegerFilter getBatchlineid() {
        return batchlineid;
    }

    public Optional<IntegerFilter> optionalBatchlineid() {
        return Optional.ofNullable(batchlineid);
    }

    public IntegerFilter batchlineid() {
        if (batchlineid == null) {
            setBatchlineid(new IntegerFilter());
        }
        return batchlineid;
    }

    public void setBatchlineid(IntegerFilter batchlineid) {
        this.batchlineid = batchlineid;
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

    public StringFilter getCode() {
        return code;
    }

    public Optional<StringFilter> optionalCode() {
        return Optional.ofNullable(code);
    }

    public StringFilter code() {
        if (code == null) {
            setCode(new StringFilter());
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
    }

    public IntegerFilter getBatchid() {
        return batchid;
    }

    public Optional<IntegerFilter> optionalBatchid() {
        return Optional.ofNullable(batchid);
    }

    public IntegerFilter batchid() {
        if (batchid == null) {
            setBatchid(new IntegerFilter());
        }
        return batchid;
    }

    public void setBatchid(IntegerFilter batchid) {
        this.batchid = batchid;
    }

    public StringFilter getBatchcode() {
        return batchcode;
    }

    public Optional<StringFilter> optionalBatchcode() {
        return Optional.ofNullable(batchcode);
    }

    public StringFilter batchcode() {
        if (batchcode == null) {
            setBatchcode(new StringFilter());
        }
        return batchcode;
    }

    public void setBatchcode(StringFilter batchcode) {
        this.batchcode = batchcode;
    }

    public InstantFilter getTxdate() {
        return txdate;
    }

    public Optional<InstantFilter> optionalTxdate() {
        return Optional.ofNullable(txdate);
    }

    public InstantFilter txdate() {
        if (txdate == null) {
            setTxdate(new InstantFilter());
        }
        return txdate;
    }

    public void setTxdate(InstantFilter txdate) {
        this.txdate = txdate;
    }

    public InstantFilter getManufacturedate() {
        return manufacturedate;
    }

    public Optional<InstantFilter> optionalManufacturedate() {
        return Optional.ofNullable(manufacturedate);
    }

    public InstantFilter manufacturedate() {
        if (manufacturedate == null) {
            setManufacturedate(new InstantFilter());
        }
        return manufacturedate;
    }

    public void setManufacturedate(InstantFilter manufacturedate) {
        this.manufacturedate = manufacturedate;
    }

    public InstantFilter getExpireddate() {
        return expireddate;
    }

    public Optional<InstantFilter> optionalExpireddate() {
        return Optional.ofNullable(expireddate);
    }

    public InstantFilter expireddate() {
        if (expireddate == null) {
            setExpireddate(new InstantFilter());
        }
        return expireddate;
    }

    public void setExpireddate(InstantFilter expireddate) {
        this.expireddate = expireddate;
    }

    public FloatFilter getQty() {
        return qty;
    }

    public Optional<FloatFilter> optionalQty() {
        return Optional.ofNullable(qty);
    }

    public FloatFilter qty() {
        if (qty == null) {
            setQty(new FloatFilter());
        }
        return qty;
    }

    public void setQty(FloatFilter qty) {
        this.qty = qty;
    }

    public FloatFilter getCost() {
        return cost;
    }

    public Optional<FloatFilter> optionalCost() {
        return Optional.ofNullable(cost);
    }

    public FloatFilter cost() {
        if (cost == null) {
            setCost(new FloatFilter());
        }
        return cost;
    }

    public void setCost(FloatFilter cost) {
        this.cost = cost;
    }

    public FloatFilter getPrice() {
        return price;
    }

    public Optional<FloatFilter> optionalPrice() {
        return Optional.ofNullable(price);
    }

    public FloatFilter price() {
        if (price == null) {
            setPrice(new FloatFilter());
        }
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
    }

    public StringFilter getNotes() {
        return notes;
    }

    public Optional<StringFilter> optionalNotes() {
        return Optional.ofNullable(notes);
    }

    public StringFilter notes() {
        if (notes == null) {
            setNotes(new StringFilter());
        }
        return notes;
    }

    public void setNotes(StringFilter notes) {
        this.notes = notes;
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

    public FloatFilter getTotal() {
        return total;
    }

    public Optional<FloatFilter> optionalTotal() {
        return Optional.ofNullable(total);
    }

    public FloatFilter total() {
        if (total == null) {
            setTotal(new FloatFilter());
        }
        return total;
    }

    public void setTotal(FloatFilter total) {
        this.total = total;
    }

    public BooleanFilter getIssued() {
        return issued;
    }

    public Optional<BooleanFilter> optionalIssued() {
        return Optional.ofNullable(issued);
    }

    public BooleanFilter issued() {
        if (issued == null) {
            setIssued(new BooleanFilter());
        }
        return issued;
    }

    public void setIssued(BooleanFilter issued) {
        this.issued = issued;
    }

    public IntegerFilter getIssuedby() {
        return issuedby;
    }

    public Optional<IntegerFilter> optionalIssuedby() {
        return Optional.ofNullable(issuedby);
    }

    public IntegerFilter issuedby() {
        if (issuedby == null) {
            setIssuedby(new IntegerFilter());
        }
        return issuedby;
    }

    public void setIssuedby(IntegerFilter issuedby) {
        this.issuedby = issuedby;
    }

    public InstantFilter getIssueddatetime() {
        return issueddatetime;
    }

    public Optional<InstantFilter> optionalIssueddatetime() {
        return Optional.ofNullable(issueddatetime);
    }

    public InstantFilter issueddatetime() {
        if (issueddatetime == null) {
            setIssueddatetime(new InstantFilter());
        }
        return issueddatetime;
    }

    public void setIssueddatetime(InstantFilter issueddatetime) {
        this.issueddatetime = issueddatetime;
    }

    public IntegerFilter getAddedbyid() {
        return addedbyid;
    }

    public Optional<IntegerFilter> optionalAddedbyid() {
        return Optional.ofNullable(addedbyid);
    }

    public IntegerFilter addedbyid() {
        if (addedbyid == null) {
            setAddedbyid(new IntegerFilter());
        }
        return addedbyid;
    }

    public void setAddedbyid(IntegerFilter addedbyid) {
        this.addedbyid = addedbyid;
    }

    public IntegerFilter getCanceloptid() {
        return canceloptid;
    }

    public Optional<IntegerFilter> optionalCanceloptid() {
        return Optional.ofNullable(canceloptid);
    }

    public IntegerFilter canceloptid() {
        if (canceloptid == null) {
            setCanceloptid(new IntegerFilter());
        }
        return canceloptid;
    }

    public void setCanceloptid(IntegerFilter canceloptid) {
        this.canceloptid = canceloptid;
    }

    public StringFilter getCancelopt() {
        return cancelopt;
    }

    public Optional<StringFilter> optionalCancelopt() {
        return Optional.ofNullable(cancelopt);
    }

    public StringFilter cancelopt() {
        if (cancelopt == null) {
            setCancelopt(new StringFilter());
        }
        return cancelopt;
    }

    public void setCancelopt(StringFilter cancelopt) {
        this.cancelopt = cancelopt;
    }

    public IntegerFilter getCancelby() {
        return cancelby;
    }

    public Optional<IntegerFilter> optionalCancelby() {
        return Optional.ofNullable(cancelby);
    }

    public IntegerFilter cancelby() {
        if (cancelby == null) {
            setCancelby(new IntegerFilter());
        }
        return cancelby;
    }

    public void setCancelby(IntegerFilter cancelby) {
        this.cancelby = cancelby;
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
        final AutojobsinvoicelinebatchesCriteria that = (AutojobsinvoicelinebatchesCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(lineid, that.lineid) &&
            Objects.equals(batchlineid, that.batchlineid) &&
            Objects.equals(itemid, that.itemid) &&
            Objects.equals(code, that.code) &&
            Objects.equals(batchid, that.batchid) &&
            Objects.equals(batchcode, that.batchcode) &&
            Objects.equals(txdate, that.txdate) &&
            Objects.equals(manufacturedate, that.manufacturedate) &&
            Objects.equals(expireddate, that.expireddate) &&
            Objects.equals(qty, that.qty) &&
            Objects.equals(cost, that.cost) &&
            Objects.equals(price, that.price) &&
            Objects.equals(notes, that.notes) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(nbt, that.nbt) &&
            Objects.equals(vat, that.vat) &&
            Objects.equals(discount, that.discount) &&
            Objects.equals(total, that.total) &&
            Objects.equals(issued, that.issued) &&
            Objects.equals(issuedby, that.issuedby) &&
            Objects.equals(issueddatetime, that.issueddatetime) &&
            Objects.equals(addedbyid, that.addedbyid) &&
            Objects.equals(canceloptid, that.canceloptid) &&
            Objects.equals(cancelopt, that.cancelopt) &&
            Objects.equals(cancelby, that.cancelby) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            lineid,
            batchlineid,
            itemid,
            code,
            batchid,
            batchcode,
            txdate,
            manufacturedate,
            expireddate,
            qty,
            cost,
            price,
            notes,
            lmu,
            lmd,
            nbt,
            vat,
            discount,
            total,
            issued,
            issuedby,
            issueddatetime,
            addedbyid,
            canceloptid,
            cancelopt,
            cancelby,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutojobsinvoicelinebatchesCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLineid().map(f -> "lineid=" + f + ", ").orElse("") +
            optionalBatchlineid().map(f -> "batchlineid=" + f + ", ").orElse("") +
            optionalItemid().map(f -> "itemid=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalBatchid().map(f -> "batchid=" + f + ", ").orElse("") +
            optionalBatchcode().map(f -> "batchcode=" + f + ", ").orElse("") +
            optionalTxdate().map(f -> "txdate=" + f + ", ").orElse("") +
            optionalManufacturedate().map(f -> "manufacturedate=" + f + ", ").orElse("") +
            optionalExpireddate().map(f -> "expireddate=" + f + ", ").orElse("") +
            optionalQty().map(f -> "qty=" + f + ", ").orElse("") +
            optionalCost().map(f -> "cost=" + f + ", ").orElse("") +
            optionalPrice().map(f -> "price=" + f + ", ").orElse("") +
            optionalNotes().map(f -> "notes=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalNbt().map(f -> "nbt=" + f + ", ").orElse("") +
            optionalVat().map(f -> "vat=" + f + ", ").orElse("") +
            optionalDiscount().map(f -> "discount=" + f + ", ").orElse("") +
            optionalTotal().map(f -> "total=" + f + ", ").orElse("") +
            optionalIssued().map(f -> "issued=" + f + ", ").orElse("") +
            optionalIssuedby().map(f -> "issuedby=" + f + ", ").orElse("") +
            optionalIssueddatetime().map(f -> "issueddatetime=" + f + ", ").orElse("") +
            optionalAddedbyid().map(f -> "addedbyid=" + f + ", ").orElse("") +
            optionalCanceloptid().map(f -> "canceloptid=" + f + ", ").orElse("") +
            optionalCancelopt().map(f -> "cancelopt=" + f + ", ").orElse("") +
            optionalCancelby().map(f -> "cancelby=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
