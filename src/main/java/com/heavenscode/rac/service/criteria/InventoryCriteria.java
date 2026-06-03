package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Inventory} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.InventoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /inventories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InventoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private StringFilter partnumber;

    private StringFilter name;

    private StringFilter description;

    private IntegerFilter type;

    private StringFilter classification1;

    private StringFilter classification2;

    private StringFilter classification3;

    private StringFilter classification4;

    private StringFilter classification5;

    private StringFilter unitofmeasurement;

    private IntegerFilter decimalplaces;

    private BooleanFilter isassemblyunit;

    private IntegerFilter assemblyunitof;

    private FloatFilter reorderlevel;

    private FloatFilter lastcost;

    private FloatFilter lastsellingprice;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private FloatFilter availablequantity;

    private BooleanFilter hasbatches;

    private StringFilter itemspecfilepath;

    private StringFilter itemimagepath;

    private FloatFilter returnprice;

    private BooleanFilter activeitem;

    private FloatFilter minstock;

    private FloatFilter maxstock;

    private FloatFilter dailyaverage;

    private FloatFilter bufferlevel;

    private FloatFilter leadtime;

    private FloatFilter buffertime;

    private FloatFilter saftydays;

    private StringFilter accountcode;

    private IntegerFilter accountid;

    private IntegerFilter casepackqty;

    private BooleanFilter isregistered;

    private IntegerFilter defaultstocklocationid;

    private StringFilter rackno;

    private IntegerFilter commissionempid;

    private IntegerFilter checktypeid;

    private StringFilter checktype;

    private FloatFilter reorderqty;

    private BooleanFilter notininvoice;

    private Boolean distinct;

    public InventoryCriteria() {}

    public InventoryCriteria(InventoryCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.partnumber = other.optionalPartnumber().map(StringFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.type = other.optionalType().map(IntegerFilter::copy).orElse(null);
        this.classification1 = other.optionalClassification1().map(StringFilter::copy).orElse(null);
        this.classification2 = other.optionalClassification2().map(StringFilter::copy).orElse(null);
        this.classification3 = other.optionalClassification3().map(StringFilter::copy).orElse(null);
        this.classification4 = other.optionalClassification4().map(StringFilter::copy).orElse(null);
        this.classification5 = other.optionalClassification5().map(StringFilter::copy).orElse(null);
        this.unitofmeasurement = other.optionalUnitofmeasurement().map(StringFilter::copy).orElse(null);
        this.decimalplaces = other.optionalDecimalplaces().map(IntegerFilter::copy).orElse(null);
        this.isassemblyunit = other.optionalIsassemblyunit().map(BooleanFilter::copy).orElse(null);
        this.assemblyunitof = other.optionalAssemblyunitof().map(IntegerFilter::copy).orElse(null);
        this.reorderlevel = other.optionalReorderlevel().map(FloatFilter::copy).orElse(null);
        this.lastcost = other.optionalLastcost().map(FloatFilter::copy).orElse(null);
        this.lastsellingprice = other.optionalLastsellingprice().map(FloatFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.availablequantity = other.optionalAvailablequantity().map(FloatFilter::copy).orElse(null);
        this.hasbatches = other.optionalHasbatches().map(BooleanFilter::copy).orElse(null);
        this.itemspecfilepath = other.optionalItemspecfilepath().map(StringFilter::copy).orElse(null);
        this.itemimagepath = other.optionalItemimagepath().map(StringFilter::copy).orElse(null);
        this.returnprice = other.optionalReturnprice().map(FloatFilter::copy).orElse(null);
        this.activeitem = other.optionalActiveitem().map(BooleanFilter::copy).orElse(null);
        this.minstock = other.optionalMinstock().map(FloatFilter::copy).orElse(null);
        this.maxstock = other.optionalMaxstock().map(FloatFilter::copy).orElse(null);
        this.dailyaverage = other.optionalDailyaverage().map(FloatFilter::copy).orElse(null);
        this.bufferlevel = other.optionalBufferlevel().map(FloatFilter::copy).orElse(null);
        this.leadtime = other.optionalLeadtime().map(FloatFilter::copy).orElse(null);
        this.buffertime = other.optionalBuffertime().map(FloatFilter::copy).orElse(null);
        this.saftydays = other.optionalSaftydays().map(FloatFilter::copy).orElse(null);
        this.accountcode = other.optionalAccountcode().map(StringFilter::copy).orElse(null);
        this.accountid = other.optionalAccountid().map(IntegerFilter::copy).orElse(null);
        this.casepackqty = other.optionalCasepackqty().map(IntegerFilter::copy).orElse(null);
        this.isregistered = other.optionalIsregistered().map(BooleanFilter::copy).orElse(null);
        this.defaultstocklocationid = other.optionalDefaultstocklocationid().map(IntegerFilter::copy).orElse(null);
        this.rackno = other.optionalRackno().map(StringFilter::copy).orElse(null);
        this.commissionempid = other.optionalCommissionempid().map(IntegerFilter::copy).orElse(null);
        this.checktypeid = other.optionalChecktypeid().map(IntegerFilter::copy).orElse(null);
        this.checktype = other.optionalChecktype().map(StringFilter::copy).orElse(null);
        this.reorderqty = other.optionalReorderqty().map(FloatFilter::copy).orElse(null);
        this.notininvoice = other.optionalNotininvoice().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public InventoryCriteria copy() {
        return new InventoryCriteria(this);
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

    public StringFilter getPartnumber() {
        return partnumber;
    }

    public Optional<StringFilter> optionalPartnumber() {
        return Optional.ofNullable(partnumber);
    }

    public StringFilter partnumber() {
        if (partnumber == null) {
            setPartnumber(new StringFilter());
        }
        return partnumber;
    }

    public void setPartnumber(StringFilter partnumber) {
        this.partnumber = partnumber;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
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

    public IntegerFilter getType() {
        return type;
    }

    public Optional<IntegerFilter> optionalType() {
        return Optional.ofNullable(type);
    }

    public IntegerFilter type() {
        if (type == null) {
            setType(new IntegerFilter());
        }
        return type;
    }

    public void setType(IntegerFilter type) {
        this.type = type;
    }

    public StringFilter getClassification1() {
        return classification1;
    }

    public Optional<StringFilter> optionalClassification1() {
        return Optional.ofNullable(classification1);
    }

    public StringFilter classification1() {
        if (classification1 == null) {
            setClassification1(new StringFilter());
        }
        return classification1;
    }

    public void setClassification1(StringFilter classification1) {
        this.classification1 = classification1;
    }

    public StringFilter getClassification2() {
        return classification2;
    }

    public Optional<StringFilter> optionalClassification2() {
        return Optional.ofNullable(classification2);
    }

    public StringFilter classification2() {
        if (classification2 == null) {
            setClassification2(new StringFilter());
        }
        return classification2;
    }

    public void setClassification2(StringFilter classification2) {
        this.classification2 = classification2;
    }

    public StringFilter getClassification3() {
        return classification3;
    }

    public Optional<StringFilter> optionalClassification3() {
        return Optional.ofNullable(classification3);
    }

    public StringFilter classification3() {
        if (classification3 == null) {
            setClassification3(new StringFilter());
        }
        return classification3;
    }

    public void setClassification3(StringFilter classification3) {
        this.classification3 = classification3;
    }

    public StringFilter getClassification4() {
        return classification4;
    }

    public Optional<StringFilter> optionalClassification4() {
        return Optional.ofNullable(classification4);
    }

    public StringFilter classification4() {
        if (classification4 == null) {
            setClassification4(new StringFilter());
        }
        return classification4;
    }

    public void setClassification4(StringFilter classification4) {
        this.classification4 = classification4;
    }

    public StringFilter getClassification5() {
        return classification5;
    }

    public Optional<StringFilter> optionalClassification5() {
        return Optional.ofNullable(classification5);
    }

    public StringFilter classification5() {
        if (classification5 == null) {
            setClassification5(new StringFilter());
        }
        return classification5;
    }

    public void setClassification5(StringFilter classification5) {
        this.classification5 = classification5;
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

    public IntegerFilter getDecimalplaces() {
        return decimalplaces;
    }

    public Optional<IntegerFilter> optionalDecimalplaces() {
        return Optional.ofNullable(decimalplaces);
    }

    public IntegerFilter decimalplaces() {
        if (decimalplaces == null) {
            setDecimalplaces(new IntegerFilter());
        }
        return decimalplaces;
    }

    public void setDecimalplaces(IntegerFilter decimalplaces) {
        this.decimalplaces = decimalplaces;
    }

    public BooleanFilter getIsassemblyunit() {
        return isassemblyunit;
    }

    public Optional<BooleanFilter> optionalIsassemblyunit() {
        return Optional.ofNullable(isassemblyunit);
    }

    public BooleanFilter isassemblyunit() {
        if (isassemblyunit == null) {
            setIsassemblyunit(new BooleanFilter());
        }
        return isassemblyunit;
    }

    public void setIsassemblyunit(BooleanFilter isassemblyunit) {
        this.isassemblyunit = isassemblyunit;
    }

    public IntegerFilter getAssemblyunitof() {
        return assemblyunitof;
    }

    public Optional<IntegerFilter> optionalAssemblyunitof() {
        return Optional.ofNullable(assemblyunitof);
    }

    public IntegerFilter assemblyunitof() {
        if (assemblyunitof == null) {
            setAssemblyunitof(new IntegerFilter());
        }
        return assemblyunitof;
    }

    public void setAssemblyunitof(IntegerFilter assemblyunitof) {
        this.assemblyunitof = assemblyunitof;
    }

    public FloatFilter getReorderlevel() {
        return reorderlevel;
    }

    public Optional<FloatFilter> optionalReorderlevel() {
        return Optional.ofNullable(reorderlevel);
    }

    public FloatFilter reorderlevel() {
        if (reorderlevel == null) {
            setReorderlevel(new FloatFilter());
        }
        return reorderlevel;
    }

    public void setReorderlevel(FloatFilter reorderlevel) {
        this.reorderlevel = reorderlevel;
    }

    public FloatFilter getLastcost() {
        return lastcost;
    }

    public Optional<FloatFilter> optionalLastcost() {
        return Optional.ofNullable(lastcost);
    }

    public FloatFilter lastcost() {
        if (lastcost == null) {
            setLastcost(new FloatFilter());
        }
        return lastcost;
    }

    public void setLastcost(FloatFilter lastcost) {
        this.lastcost = lastcost;
    }

    public FloatFilter getLastsellingprice() {
        return lastsellingprice;
    }

    public Optional<FloatFilter> optionalLastsellingprice() {
        return Optional.ofNullable(lastsellingprice);
    }

    public FloatFilter lastsellingprice() {
        if (lastsellingprice == null) {
            setLastsellingprice(new FloatFilter());
        }
        return lastsellingprice;
    }

    public void setLastsellingprice(FloatFilter lastsellingprice) {
        this.lastsellingprice = lastsellingprice;
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

    public FloatFilter getAvailablequantity() {
        return availablequantity;
    }

    public Optional<FloatFilter> optionalAvailablequantity() {
        return Optional.ofNullable(availablequantity);
    }

    public FloatFilter availablequantity() {
        if (availablequantity == null) {
            setAvailablequantity(new FloatFilter());
        }
        return availablequantity;
    }

    public void setAvailablequantity(FloatFilter availablequantity) {
        this.availablequantity = availablequantity;
    }

    public BooleanFilter getHasbatches() {
        return hasbatches;
    }

    public Optional<BooleanFilter> optionalHasbatches() {
        return Optional.ofNullable(hasbatches);
    }

    public BooleanFilter hasbatches() {
        if (hasbatches == null) {
            setHasbatches(new BooleanFilter());
        }
        return hasbatches;
    }

    public void setHasbatches(BooleanFilter hasbatches) {
        this.hasbatches = hasbatches;
    }

    public StringFilter getItemspecfilepath() {
        return itemspecfilepath;
    }

    public Optional<StringFilter> optionalItemspecfilepath() {
        return Optional.ofNullable(itemspecfilepath);
    }

    public StringFilter itemspecfilepath() {
        if (itemspecfilepath == null) {
            setItemspecfilepath(new StringFilter());
        }
        return itemspecfilepath;
    }

    public void setItemspecfilepath(StringFilter itemspecfilepath) {
        this.itemspecfilepath = itemspecfilepath;
    }

    public StringFilter getItemimagepath() {
        return itemimagepath;
    }

    public Optional<StringFilter> optionalItemimagepath() {
        return Optional.ofNullable(itemimagepath);
    }

    public StringFilter itemimagepath() {
        if (itemimagepath == null) {
            setItemimagepath(new StringFilter());
        }
        return itemimagepath;
    }

    public void setItemimagepath(StringFilter itemimagepath) {
        this.itemimagepath = itemimagepath;
    }

    public FloatFilter getReturnprice() {
        return returnprice;
    }

    public Optional<FloatFilter> optionalReturnprice() {
        return Optional.ofNullable(returnprice);
    }

    public FloatFilter returnprice() {
        if (returnprice == null) {
            setReturnprice(new FloatFilter());
        }
        return returnprice;
    }

    public void setReturnprice(FloatFilter returnprice) {
        this.returnprice = returnprice;
    }

    public BooleanFilter getActiveitem() {
        return activeitem;
    }

    public Optional<BooleanFilter> optionalActiveitem() {
        return Optional.ofNullable(activeitem);
    }

    public BooleanFilter activeitem() {
        if (activeitem == null) {
            setActiveitem(new BooleanFilter());
        }
        return activeitem;
    }

    public void setActiveitem(BooleanFilter activeitem) {
        this.activeitem = activeitem;
    }

    public FloatFilter getMinstock() {
        return minstock;
    }

    public Optional<FloatFilter> optionalMinstock() {
        return Optional.ofNullable(minstock);
    }

    public FloatFilter minstock() {
        if (minstock == null) {
            setMinstock(new FloatFilter());
        }
        return minstock;
    }

    public void setMinstock(FloatFilter minstock) {
        this.minstock = minstock;
    }

    public FloatFilter getMaxstock() {
        return maxstock;
    }

    public Optional<FloatFilter> optionalMaxstock() {
        return Optional.ofNullable(maxstock);
    }

    public FloatFilter maxstock() {
        if (maxstock == null) {
            setMaxstock(new FloatFilter());
        }
        return maxstock;
    }

    public void setMaxstock(FloatFilter maxstock) {
        this.maxstock = maxstock;
    }

    public FloatFilter getDailyaverage() {
        return dailyaverage;
    }

    public Optional<FloatFilter> optionalDailyaverage() {
        return Optional.ofNullable(dailyaverage);
    }

    public FloatFilter dailyaverage() {
        if (dailyaverage == null) {
            setDailyaverage(new FloatFilter());
        }
        return dailyaverage;
    }

    public void setDailyaverage(FloatFilter dailyaverage) {
        this.dailyaverage = dailyaverage;
    }

    public FloatFilter getBufferlevel() {
        return bufferlevel;
    }

    public Optional<FloatFilter> optionalBufferlevel() {
        return Optional.ofNullable(bufferlevel);
    }

    public FloatFilter bufferlevel() {
        if (bufferlevel == null) {
            setBufferlevel(new FloatFilter());
        }
        return bufferlevel;
    }

    public void setBufferlevel(FloatFilter bufferlevel) {
        this.bufferlevel = bufferlevel;
    }

    public FloatFilter getLeadtime() {
        return leadtime;
    }

    public Optional<FloatFilter> optionalLeadtime() {
        return Optional.ofNullable(leadtime);
    }

    public FloatFilter leadtime() {
        if (leadtime == null) {
            setLeadtime(new FloatFilter());
        }
        return leadtime;
    }

    public void setLeadtime(FloatFilter leadtime) {
        this.leadtime = leadtime;
    }

    public FloatFilter getBuffertime() {
        return buffertime;
    }

    public Optional<FloatFilter> optionalBuffertime() {
        return Optional.ofNullable(buffertime);
    }

    public FloatFilter buffertime() {
        if (buffertime == null) {
            setBuffertime(new FloatFilter());
        }
        return buffertime;
    }

    public void setBuffertime(FloatFilter buffertime) {
        this.buffertime = buffertime;
    }

    public FloatFilter getSaftydays() {
        return saftydays;
    }

    public Optional<FloatFilter> optionalSaftydays() {
        return Optional.ofNullable(saftydays);
    }

    public FloatFilter saftydays() {
        if (saftydays == null) {
            setSaftydays(new FloatFilter());
        }
        return saftydays;
    }

    public void setSaftydays(FloatFilter saftydays) {
        this.saftydays = saftydays;
    }

    public StringFilter getAccountcode() {
        return accountcode;
    }

    public Optional<StringFilter> optionalAccountcode() {
        return Optional.ofNullable(accountcode);
    }

    public StringFilter accountcode() {
        if (accountcode == null) {
            setAccountcode(new StringFilter());
        }
        return accountcode;
    }

    public void setAccountcode(StringFilter accountcode) {
        this.accountcode = accountcode;
    }

    public IntegerFilter getAccountid() {
        return accountid;
    }

    public Optional<IntegerFilter> optionalAccountid() {
        return Optional.ofNullable(accountid);
    }

    public IntegerFilter accountid() {
        if (accountid == null) {
            setAccountid(new IntegerFilter());
        }
        return accountid;
    }

    public void setAccountid(IntegerFilter accountid) {
        this.accountid = accountid;
    }

    public IntegerFilter getCasepackqty() {
        return casepackqty;
    }

    public Optional<IntegerFilter> optionalCasepackqty() {
        return Optional.ofNullable(casepackqty);
    }

    public IntegerFilter casepackqty() {
        if (casepackqty == null) {
            setCasepackqty(new IntegerFilter());
        }
        return casepackqty;
    }

    public void setCasepackqty(IntegerFilter casepackqty) {
        this.casepackqty = casepackqty;
    }

    public BooleanFilter getIsregistered() {
        return isregistered;
    }

    public Optional<BooleanFilter> optionalIsregistered() {
        return Optional.ofNullable(isregistered);
    }

    public BooleanFilter isregistered() {
        if (isregistered == null) {
            setIsregistered(new BooleanFilter());
        }
        return isregistered;
    }

    public void setIsregistered(BooleanFilter isregistered) {
        this.isregistered = isregistered;
    }

    public IntegerFilter getDefaultstocklocationid() {
        return defaultstocklocationid;
    }

    public Optional<IntegerFilter> optionalDefaultstocklocationid() {
        return Optional.ofNullable(defaultstocklocationid);
    }

    public IntegerFilter defaultstocklocationid() {
        if (defaultstocklocationid == null) {
            setDefaultstocklocationid(new IntegerFilter());
        }
        return defaultstocklocationid;
    }

    public void setDefaultstocklocationid(IntegerFilter defaultstocklocationid) {
        this.defaultstocklocationid = defaultstocklocationid;
    }

    public StringFilter getRackno() {
        return rackno;
    }

    public Optional<StringFilter> optionalRackno() {
        return Optional.ofNullable(rackno);
    }

    public StringFilter rackno() {
        if (rackno == null) {
            setRackno(new StringFilter());
        }
        return rackno;
    }

    public void setRackno(StringFilter rackno) {
        this.rackno = rackno;
    }

    public IntegerFilter getCommissionempid() {
        return commissionempid;
    }

    public Optional<IntegerFilter> optionalCommissionempid() {
        return Optional.ofNullable(commissionempid);
    }

    public IntegerFilter commissionempid() {
        if (commissionempid == null) {
            setCommissionempid(new IntegerFilter());
        }
        return commissionempid;
    }

    public void setCommissionempid(IntegerFilter commissionempid) {
        this.commissionempid = commissionempid;
    }

    public IntegerFilter getChecktypeid() {
        return checktypeid;
    }

    public Optional<IntegerFilter> optionalChecktypeid() {
        return Optional.ofNullable(checktypeid);
    }

    public IntegerFilter checktypeid() {
        if (checktypeid == null) {
            setChecktypeid(new IntegerFilter());
        }
        return checktypeid;
    }

    public void setChecktypeid(IntegerFilter checktypeid) {
        this.checktypeid = checktypeid;
    }

    public StringFilter getChecktype() {
        return checktype;
    }

    public Optional<StringFilter> optionalChecktype() {
        return Optional.ofNullable(checktype);
    }

    public StringFilter checktype() {
        if (checktype == null) {
            setChecktype(new StringFilter());
        }
        return checktype;
    }

    public void setChecktype(StringFilter checktype) {
        this.checktype = checktype;
    }

    public FloatFilter getReorderqty() {
        return reorderqty;
    }

    public Optional<FloatFilter> optionalReorderqty() {
        return Optional.ofNullable(reorderqty);
    }

    public FloatFilter reorderqty() {
        if (reorderqty == null) {
            setReorderqty(new FloatFilter());
        }
        return reorderqty;
    }

    public void setReorderqty(FloatFilter reorderqty) {
        this.reorderqty = reorderqty;
    }

    public BooleanFilter getNotininvoice() {
        return notininvoice;
    }

    public Optional<BooleanFilter> optionalNotininvoice() {
        return Optional.ofNullable(notininvoice);
    }

    public BooleanFilter notininvoice() {
        if (notininvoice == null) {
            setNotininvoice(new BooleanFilter());
        }
        return notininvoice;
    }

    public void setNotininvoice(BooleanFilter notininvoice) {
        this.notininvoice = notininvoice;
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
        final InventoryCriteria that = (InventoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(partnumber, that.partnumber) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(classification1, that.classification1) &&
            Objects.equals(classification2, that.classification2) &&
            Objects.equals(classification3, that.classification3) &&
            Objects.equals(classification4, that.classification4) &&
            Objects.equals(classification5, that.classification5) &&
            Objects.equals(unitofmeasurement, that.unitofmeasurement) &&
            Objects.equals(decimalplaces, that.decimalplaces) &&
            Objects.equals(isassemblyunit, that.isassemblyunit) &&
            Objects.equals(assemblyunitof, that.assemblyunitof) &&
            Objects.equals(reorderlevel, that.reorderlevel) &&
            Objects.equals(lastcost, that.lastcost) &&
            Objects.equals(lastsellingprice, that.lastsellingprice) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(availablequantity, that.availablequantity) &&
            Objects.equals(hasbatches, that.hasbatches) &&
            Objects.equals(itemspecfilepath, that.itemspecfilepath) &&
            Objects.equals(itemimagepath, that.itemimagepath) &&
            Objects.equals(returnprice, that.returnprice) &&
            Objects.equals(activeitem, that.activeitem) &&
            Objects.equals(minstock, that.minstock) &&
            Objects.equals(maxstock, that.maxstock) &&
            Objects.equals(dailyaverage, that.dailyaverage) &&
            Objects.equals(bufferlevel, that.bufferlevel) &&
            Objects.equals(leadtime, that.leadtime) &&
            Objects.equals(buffertime, that.buffertime) &&
            Objects.equals(saftydays, that.saftydays) &&
            Objects.equals(accountcode, that.accountcode) &&
            Objects.equals(accountid, that.accountid) &&
            Objects.equals(casepackqty, that.casepackqty) &&
            Objects.equals(isregistered, that.isregistered) &&
            Objects.equals(defaultstocklocationid, that.defaultstocklocationid) &&
            Objects.equals(rackno, that.rackno) &&
            Objects.equals(commissionempid, that.commissionempid) &&
            Objects.equals(checktypeid, that.checktypeid) &&
            Objects.equals(checktype, that.checktype) &&
            Objects.equals(reorderqty, that.reorderqty) &&
            Objects.equals(notininvoice, that.notininvoice) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            partnumber,
            name,
            description,
            type,
            classification1,
            classification2,
            classification3,
            classification4,
            classification5,
            unitofmeasurement,
            decimalplaces,
            isassemblyunit,
            assemblyunitof,
            reorderlevel,
            lastcost,
            lastsellingprice,
            lmu,
            lmd,
            availablequantity,
            hasbatches,
            itemspecfilepath,
            itemimagepath,
            returnprice,
            activeitem,
            minstock,
            maxstock,
            dailyaverage,
            bufferlevel,
            leadtime,
            buffertime,
            saftydays,
            accountcode,
            accountid,
            casepackqty,
            isregistered,
            defaultstocklocationid,
            rackno,
            commissionempid,
            checktypeid,
            checktype,
            reorderqty,
            notininvoice,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InventoryCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalPartnumber().map(f -> "partnumber=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalType().map(f -> "type=" + f + ", ").orElse("") +
            optionalClassification1().map(f -> "classification1=" + f + ", ").orElse("") +
            optionalClassification2().map(f -> "classification2=" + f + ", ").orElse("") +
            optionalClassification3().map(f -> "classification3=" + f + ", ").orElse("") +
            optionalClassification4().map(f -> "classification4=" + f + ", ").orElse("") +
            optionalClassification5().map(f -> "classification5=" + f + ", ").orElse("") +
            optionalUnitofmeasurement().map(f -> "unitofmeasurement=" + f + ", ").orElse("") +
            optionalDecimalplaces().map(f -> "decimalplaces=" + f + ", ").orElse("") +
            optionalIsassemblyunit().map(f -> "isassemblyunit=" + f + ", ").orElse("") +
            optionalAssemblyunitof().map(f -> "assemblyunitof=" + f + ", ").orElse("") +
            optionalReorderlevel().map(f -> "reorderlevel=" + f + ", ").orElse("") +
            optionalLastcost().map(f -> "lastcost=" + f + ", ").orElse("") +
            optionalLastsellingprice().map(f -> "lastsellingprice=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalAvailablequantity().map(f -> "availablequantity=" + f + ", ").orElse("") +
            optionalHasbatches().map(f -> "hasbatches=" + f + ", ").orElse("") +
            optionalItemspecfilepath().map(f -> "itemspecfilepath=" + f + ", ").orElse("") +
            optionalItemimagepath().map(f -> "itemimagepath=" + f + ", ").orElse("") +
            optionalReturnprice().map(f -> "returnprice=" + f + ", ").orElse("") +
            optionalActiveitem().map(f -> "activeitem=" + f + ", ").orElse("") +
            optionalMinstock().map(f -> "minstock=" + f + ", ").orElse("") +
            optionalMaxstock().map(f -> "maxstock=" + f + ", ").orElse("") +
            optionalDailyaverage().map(f -> "dailyaverage=" + f + ", ").orElse("") +
            optionalBufferlevel().map(f -> "bufferlevel=" + f + ", ").orElse("") +
            optionalLeadtime().map(f -> "leadtime=" + f + ", ").orElse("") +
            optionalBuffertime().map(f -> "buffertime=" + f + ", ").orElse("") +
            optionalSaftydays().map(f -> "saftydays=" + f + ", ").orElse("") +
            optionalAccountcode().map(f -> "accountcode=" + f + ", ").orElse("") +
            optionalAccountid().map(f -> "accountid=" + f + ", ").orElse("") +
            optionalCasepackqty().map(f -> "casepackqty=" + f + ", ").orElse("") +
            optionalIsregistered().map(f -> "isregistered=" + f + ", ").orElse("") +
            optionalDefaultstocklocationid().map(f -> "defaultstocklocationid=" + f + ", ").orElse("") +
            optionalRackno().map(f -> "rackno=" + f + ", ").orElse("") +
            optionalCommissionempid().map(f -> "commissionempid=" + f + ", ").orElse("") +
            optionalChecktypeid().map(f -> "checktypeid=" + f + ", ").orElse("") +
            optionalChecktype().map(f -> "checktype=" + f + ", ").orElse("") +
            optionalReorderqty().map(f -> "reorderqty=" + f + ", ").orElse("") +
            optionalNotininvoice().map(f -> "notininvoice=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
