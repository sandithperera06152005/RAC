package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Autojobsinvoice} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.AutojobsinvoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /autojobsinvoices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AutojobsinvoiceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private InstantFilter invoicedate;

    private InstantFilter createddate;

    private IntegerFilter jobid;

    private IntegerFilter quoteid;

    private IntegerFilter orderid;

    private InstantFilter delieverydate;

    private IntegerFilter autojobsrepid;

    private StringFilter autojobsrepname;

    private StringFilter delieverfrom;

    private IntegerFilter customerid;

    private StringFilter customername;

    private StringFilter customeraddress;

    private StringFilter deliveryaddress;

    private FloatFilter subtotal;

    private FloatFilter totaltax;

    private FloatFilter totaldiscount;

    private FloatFilter nettotal;

    private StringFilter message;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private FloatFilter paidamount;

    private FloatFilter amountowing;

    private BooleanFilter isactive;

    private IntegerFilter locationid;

    private StringFilter locationcode;

    private StringFilter referencecode;

    private IntegerFilter createdbyid;

    private StringFilter createdbyname;

    private IntegerFilter autocarecompanyid;

    private Boolean distinct;

    public AutojobsinvoiceCriteria() {}

    public AutojobsinvoiceCriteria(AutojobsinvoiceCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.invoicedate = other.optionalInvoicedate().map(InstantFilter::copy).orElse(null);
        this.createddate = other.optionalCreateddate().map(InstantFilter::copy).orElse(null);
        this.jobid = other.optionalJobid().map(IntegerFilter::copy).orElse(null);
        this.quoteid = other.optionalQuoteid().map(IntegerFilter::copy).orElse(null);
        this.orderid = other.optionalOrderid().map(IntegerFilter::copy).orElse(null);
        this.delieverydate = other.optionalDelieverydate().map(InstantFilter::copy).orElse(null);
        this.autojobsrepid = other.optionalAutojobsrepid().map(IntegerFilter::copy).orElse(null);
        this.autojobsrepname = other.optionalAutojobsrepname().map(StringFilter::copy).orElse(null);
        this.delieverfrom = other.optionalDelieverfrom().map(StringFilter::copy).orElse(null);
        this.customerid = other.optionalCustomerid().map(IntegerFilter::copy).orElse(null);
        this.customername = other.optionalCustomername().map(StringFilter::copy).orElse(null);
        this.customeraddress = other.optionalCustomeraddress().map(StringFilter::copy).orElse(null);
        this.deliveryaddress = other.optionalDeliveryaddress().map(StringFilter::copy).orElse(null);
        this.subtotal = other.optionalSubtotal().map(FloatFilter::copy).orElse(null);
        this.totaltax = other.optionalTotaltax().map(FloatFilter::copy).orElse(null);
        this.totaldiscount = other.optionalTotaldiscount().map(FloatFilter::copy).orElse(null);
        this.nettotal = other.optionalNettotal().map(FloatFilter::copy).orElse(null);
        this.message = other.optionalMessage().map(StringFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.paidamount = other.optionalPaidamount().map(FloatFilter::copy).orElse(null);
        this.amountowing = other.optionalAmountowing().map(FloatFilter::copy).orElse(null);
        this.isactive = other.optionalIsactive().map(BooleanFilter::copy).orElse(null);
        this.locationid = other.optionalLocationid().map(IntegerFilter::copy).orElse(null);
        this.locationcode = other.optionalLocationcode().map(StringFilter::copy).orElse(null);
        this.referencecode = other.optionalReferencecode().map(StringFilter::copy).orElse(null);
        this.createdbyid = other.optionalCreatedbyid().map(IntegerFilter::copy).orElse(null);
        this.createdbyname = other.optionalCreatedbyname().map(StringFilter::copy).orElse(null);
        this.autocarecompanyid = other.optionalAutocarecompanyid().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AutojobsinvoiceCriteria copy() {
        return new AutojobsinvoiceCriteria(this);
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

    public InstantFilter getInvoicedate() {
        return invoicedate;
    }

    public Optional<InstantFilter> optionalInvoicedate() {
        return Optional.ofNullable(invoicedate);
    }

    public InstantFilter invoicedate() {
        if (invoicedate == null) {
            setInvoicedate(new InstantFilter());
        }
        return invoicedate;
    }

    public void setInvoicedate(InstantFilter invoicedate) {
        this.invoicedate = invoicedate;
    }

    public InstantFilter getCreateddate() {
        return createddate;
    }

    public Optional<InstantFilter> optionalCreateddate() {
        return Optional.ofNullable(createddate);
    }

    public InstantFilter createddate() {
        if (createddate == null) {
            setCreateddate(new InstantFilter());
        }
        return createddate;
    }

    public void setCreateddate(InstantFilter createddate) {
        this.createddate = createddate;
    }

    public IntegerFilter getJobid() {
        return jobid;
    }

    public Optional<IntegerFilter> optionalJobid() {
        return Optional.ofNullable(jobid);
    }

    public IntegerFilter jobid() {
        if (jobid == null) {
            setJobid(new IntegerFilter());
        }
        return jobid;
    }

    public void setJobid(IntegerFilter jobid) {
        this.jobid = jobid;
    }

    public IntegerFilter getQuoteid() {
        return quoteid;
    }

    public Optional<IntegerFilter> optionalQuoteid() {
        return Optional.ofNullable(quoteid);
    }

    public IntegerFilter quoteid() {
        if (quoteid == null) {
            setQuoteid(new IntegerFilter());
        }
        return quoteid;
    }

    public void setQuoteid(IntegerFilter quoteid) {
        this.quoteid = quoteid;
    }

    public IntegerFilter getOrderid() {
        return orderid;
    }

    public Optional<IntegerFilter> optionalOrderid() {
        return Optional.ofNullable(orderid);
    }

    public IntegerFilter orderid() {
        if (orderid == null) {
            setOrderid(new IntegerFilter());
        }
        return orderid;
    }

    public void setOrderid(IntegerFilter orderid) {
        this.orderid = orderid;
    }

    public InstantFilter getDelieverydate() {
        return delieverydate;
    }

    public Optional<InstantFilter> optionalDelieverydate() {
        return Optional.ofNullable(delieverydate);
    }

    public InstantFilter delieverydate() {
        if (delieverydate == null) {
            setDelieverydate(new InstantFilter());
        }
        return delieverydate;
    }

    public void setDelieverydate(InstantFilter delieverydate) {
        this.delieverydate = delieverydate;
    }

    public IntegerFilter getAutojobsrepid() {
        return autojobsrepid;
    }

    public Optional<IntegerFilter> optionalAutojobsrepid() {
        return Optional.ofNullable(autojobsrepid);
    }

    public IntegerFilter autojobsrepid() {
        if (autojobsrepid == null) {
            setAutojobsrepid(new IntegerFilter());
        }
        return autojobsrepid;
    }

    public void setAutojobsrepid(IntegerFilter autojobsrepid) {
        this.autojobsrepid = autojobsrepid;
    }

    public StringFilter getAutojobsrepname() {
        return autojobsrepname;
    }

    public Optional<StringFilter> optionalAutojobsrepname() {
        return Optional.ofNullable(autojobsrepname);
    }

    public StringFilter autojobsrepname() {
        if (autojobsrepname == null) {
            setAutojobsrepname(new StringFilter());
        }
        return autojobsrepname;
    }

    public void setAutojobsrepname(StringFilter autojobsrepname) {
        this.autojobsrepname = autojobsrepname;
    }

    public StringFilter getDelieverfrom() {
        return delieverfrom;
    }

    public Optional<StringFilter> optionalDelieverfrom() {
        return Optional.ofNullable(delieverfrom);
    }

    public StringFilter delieverfrom() {
        if (delieverfrom == null) {
            setDelieverfrom(new StringFilter());
        }
        return delieverfrom;
    }

    public void setDelieverfrom(StringFilter delieverfrom) {
        this.delieverfrom = delieverfrom;
    }

    public IntegerFilter getCustomerid() {
        return customerid;
    }

    public Optional<IntegerFilter> optionalCustomerid() {
        return Optional.ofNullable(customerid);
    }

    public IntegerFilter customerid() {
        if (customerid == null) {
            setCustomerid(new IntegerFilter());
        }
        return customerid;
    }

    public void setCustomerid(IntegerFilter customerid) {
        this.customerid = customerid;
    }

    public StringFilter getCustomername() {
        return customername;
    }

    public Optional<StringFilter> optionalCustomername() {
        return Optional.ofNullable(customername);
    }

    public StringFilter customername() {
        if (customername == null) {
            setCustomername(new StringFilter());
        }
        return customername;
    }

    public void setCustomername(StringFilter customername) {
        this.customername = customername;
    }

    public StringFilter getCustomeraddress() {
        return customeraddress;
    }

    public Optional<StringFilter> optionalCustomeraddress() {
        return Optional.ofNullable(customeraddress);
    }

    public StringFilter customeraddress() {
        if (customeraddress == null) {
            setCustomeraddress(new StringFilter());
        }
        return customeraddress;
    }

    public void setCustomeraddress(StringFilter customeraddress) {
        this.customeraddress = customeraddress;
    }

    public StringFilter getDeliveryaddress() {
        return deliveryaddress;
    }

    public Optional<StringFilter> optionalDeliveryaddress() {
        return Optional.ofNullable(deliveryaddress);
    }

    public StringFilter deliveryaddress() {
        if (deliveryaddress == null) {
            setDeliveryaddress(new StringFilter());
        }
        return deliveryaddress;
    }

    public void setDeliveryaddress(StringFilter deliveryaddress) {
        this.deliveryaddress = deliveryaddress;
    }

    public FloatFilter getSubtotal() {
        return subtotal;
    }

    public Optional<FloatFilter> optionalSubtotal() {
        return Optional.ofNullable(subtotal);
    }

    public FloatFilter subtotal() {
        if (subtotal == null) {
            setSubtotal(new FloatFilter());
        }
        return subtotal;
    }

    public void setSubtotal(FloatFilter subtotal) {
        this.subtotal = subtotal;
    }

    public FloatFilter getTotaltax() {
        return totaltax;
    }

    public Optional<FloatFilter> optionalTotaltax() {
        return Optional.ofNullable(totaltax);
    }

    public FloatFilter totaltax() {
        if (totaltax == null) {
            setTotaltax(new FloatFilter());
        }
        return totaltax;
    }

    public void setTotaltax(FloatFilter totaltax) {
        this.totaltax = totaltax;
    }

    public FloatFilter getTotaldiscount() {
        return totaldiscount;
    }

    public Optional<FloatFilter> optionalTotaldiscount() {
        return Optional.ofNullable(totaldiscount);
    }

    public FloatFilter totaldiscount() {
        if (totaldiscount == null) {
            setTotaldiscount(new FloatFilter());
        }
        return totaldiscount;
    }

    public void setTotaldiscount(FloatFilter totaldiscount) {
        this.totaldiscount = totaldiscount;
    }

    public FloatFilter getNettotal() {
        return nettotal;
    }

    public Optional<FloatFilter> optionalNettotal() {
        return Optional.ofNullable(nettotal);
    }

    public FloatFilter nettotal() {
        if (nettotal == null) {
            setNettotal(new FloatFilter());
        }
        return nettotal;
    }

    public void setNettotal(FloatFilter nettotal) {
        this.nettotal = nettotal;
    }

    public StringFilter getMessage() {
        return message;
    }

    public Optional<StringFilter> optionalMessage() {
        return Optional.ofNullable(message);
    }

    public StringFilter message() {
        if (message == null) {
            setMessage(new StringFilter());
        }
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
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

    public FloatFilter getPaidamount() {
        return paidamount;
    }

    public Optional<FloatFilter> optionalPaidamount() {
        return Optional.ofNullable(paidamount);
    }

    public FloatFilter paidamount() {
        if (paidamount == null) {
            setPaidamount(new FloatFilter());
        }
        return paidamount;
    }

    public void setPaidamount(FloatFilter paidamount) {
        this.paidamount = paidamount;
    }

    public FloatFilter getAmountowing() {
        return amountowing;
    }

    public Optional<FloatFilter> optionalAmountowing() {
        return Optional.ofNullable(amountowing);
    }

    public FloatFilter amountowing() {
        if (amountowing == null) {
            setAmountowing(new FloatFilter());
        }
        return amountowing;
    }

    public void setAmountowing(FloatFilter amountowing) {
        this.amountowing = amountowing;
    }

    public BooleanFilter getIsactive() {
        return isactive;
    }

    public Optional<BooleanFilter> optionalIsactive() {
        return Optional.ofNullable(isactive);
    }

    public BooleanFilter isactive() {
        if (isactive == null) {
            setIsactive(new BooleanFilter());
        }
        return isactive;
    }

    public void setIsactive(BooleanFilter isactive) {
        this.isactive = isactive;
    }

    public IntegerFilter getLocationid() {
        return locationid;
    }

    public Optional<IntegerFilter> optionalLocationid() {
        return Optional.ofNullable(locationid);
    }

    public IntegerFilter locationid() {
        if (locationid == null) {
            setLocationid(new IntegerFilter());
        }
        return locationid;
    }

    public void setLocationid(IntegerFilter locationid) {
        this.locationid = locationid;
    }

    public StringFilter getLocationcode() {
        return locationcode;
    }

    public Optional<StringFilter> optionalLocationcode() {
        return Optional.ofNullable(locationcode);
    }

    public StringFilter locationcode() {
        if (locationcode == null) {
            setLocationcode(new StringFilter());
        }
        return locationcode;
    }

    public void setLocationcode(StringFilter locationcode) {
        this.locationcode = locationcode;
    }

    public StringFilter getReferencecode() {
        return referencecode;
    }

    public Optional<StringFilter> optionalReferencecode() {
        return Optional.ofNullable(referencecode);
    }

    public StringFilter referencecode() {
        if (referencecode == null) {
            setReferencecode(new StringFilter());
        }
        return referencecode;
    }

    public void setReferencecode(StringFilter referencecode) {
        this.referencecode = referencecode;
    }

    public IntegerFilter getCreatedbyid() {
        return createdbyid;
    }

    public Optional<IntegerFilter> optionalCreatedbyid() {
        return Optional.ofNullable(createdbyid);
    }

    public IntegerFilter createdbyid() {
        if (createdbyid == null) {
            setCreatedbyid(new IntegerFilter());
        }
        return createdbyid;
    }

    public void setCreatedbyid(IntegerFilter createdbyid) {
        this.createdbyid = createdbyid;
    }

    public StringFilter getCreatedbyname() {
        return createdbyname;
    }

    public Optional<StringFilter> optionalCreatedbyname() {
        return Optional.ofNullable(createdbyname);
    }

    public StringFilter createdbyname() {
        if (createdbyname == null) {
            setCreatedbyname(new StringFilter());
        }
        return createdbyname;
    }

    public void setCreatedbyname(StringFilter createdbyname) {
        this.createdbyname = createdbyname;
    }

    public IntegerFilter getAutocarecompanyid() {
        return autocarecompanyid;
    }

    public Optional<IntegerFilter> optionalAutocarecompanyid() {
        return Optional.ofNullable(autocarecompanyid);
    }

    public IntegerFilter autocarecompanyid() {
        if (autocarecompanyid == null) {
            setAutocarecompanyid(new IntegerFilter());
        }
        return autocarecompanyid;
    }

    public void setAutocarecompanyid(IntegerFilter autocarecompanyid) {
        this.autocarecompanyid = autocarecompanyid;
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
        final AutojobsinvoiceCriteria that = (AutojobsinvoiceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(invoicedate, that.invoicedate) &&
            Objects.equals(createddate, that.createddate) &&
            Objects.equals(jobid, that.jobid) &&
            Objects.equals(quoteid, that.quoteid) &&
            Objects.equals(orderid, that.orderid) &&
            Objects.equals(delieverydate, that.delieverydate) &&
            Objects.equals(autojobsrepid, that.autojobsrepid) &&
            Objects.equals(autojobsrepname, that.autojobsrepname) &&
            Objects.equals(delieverfrom, that.delieverfrom) &&
            Objects.equals(customerid, that.customerid) &&
            Objects.equals(customername, that.customername) &&
            Objects.equals(customeraddress, that.customeraddress) &&
            Objects.equals(deliveryaddress, that.deliveryaddress) &&
            Objects.equals(subtotal, that.subtotal) &&
            Objects.equals(totaltax, that.totaltax) &&
            Objects.equals(totaldiscount, that.totaldiscount) &&
            Objects.equals(nettotal, that.nettotal) &&
            Objects.equals(message, that.message) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(paidamount, that.paidamount) &&
            Objects.equals(amountowing, that.amountowing) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(locationid, that.locationid) &&
            Objects.equals(locationcode, that.locationcode) &&
            Objects.equals(referencecode, that.referencecode) &&
            Objects.equals(createdbyid, that.createdbyid) &&
            Objects.equals(createdbyname, that.createdbyname) &&
            Objects.equals(autocarecompanyid, that.autocarecompanyid) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            invoicedate,
            createddate,
            jobid,
            quoteid,
            orderid,
            delieverydate,
            autojobsrepid,
            autojobsrepname,
            delieverfrom,
            customerid,
            customername,
            customeraddress,
            deliveryaddress,
            subtotal,
            totaltax,
            totaldiscount,
            nettotal,
            message,
            lmu,
            lmd,
            paidamount,
            amountowing,
            isactive,
            locationid,
            locationcode,
            referencecode,
            createdbyid,
            createdbyname,
            autocarecompanyid,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AutojobsinvoiceCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalInvoicedate().map(f -> "invoicedate=" + f + ", ").orElse("") +
            optionalCreateddate().map(f -> "createddate=" + f + ", ").orElse("") +
            optionalJobid().map(f -> "jobid=" + f + ", ").orElse("") +
            optionalQuoteid().map(f -> "quoteid=" + f + ", ").orElse("") +
            optionalOrderid().map(f -> "orderid=" + f + ", ").orElse("") +
            optionalDelieverydate().map(f -> "delieverydate=" + f + ", ").orElse("") +
            optionalAutojobsrepid().map(f -> "autojobsrepid=" + f + ", ").orElse("") +
            optionalAutojobsrepname().map(f -> "autojobsrepname=" + f + ", ").orElse("") +
            optionalDelieverfrom().map(f -> "delieverfrom=" + f + ", ").orElse("") +
            optionalCustomerid().map(f -> "customerid=" + f + ", ").orElse("") +
            optionalCustomername().map(f -> "customername=" + f + ", ").orElse("") +
            optionalCustomeraddress().map(f -> "customeraddress=" + f + ", ").orElse("") +
            optionalDeliveryaddress().map(f -> "deliveryaddress=" + f + ", ").orElse("") +
            optionalSubtotal().map(f -> "subtotal=" + f + ", ").orElse("") +
            optionalTotaltax().map(f -> "totaltax=" + f + ", ").orElse("") +
            optionalTotaldiscount().map(f -> "totaldiscount=" + f + ", ").orElse("") +
            optionalNettotal().map(f -> "nettotal=" + f + ", ").orElse("") +
            optionalMessage().map(f -> "message=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalPaidamount().map(f -> "paidamount=" + f + ", ").orElse("") +
            optionalAmountowing().map(f -> "amountowing=" + f + ", ").orElse("") +
            optionalIsactive().map(f -> "isactive=" + f + ", ").orElse("") +
            optionalLocationid().map(f -> "locationid=" + f + ", ").orElse("") +
            optionalLocationcode().map(f -> "locationcode=" + f + ", ").orElse("") +
            optionalReferencecode().map(f -> "referencecode=" + f + ", ").orElse("") +
            optionalCreatedbyid().map(f -> "createdbyid=" + f + ", ").orElse("") +
            optionalCreatedbyname().map(f -> "createdbyname=" + f + ", ").orElse("") +
            optionalAutocarecompanyid().map(f -> "autocarecompanyid=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
