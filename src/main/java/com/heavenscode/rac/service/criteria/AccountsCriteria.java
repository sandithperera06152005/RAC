package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Accounts} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.AccountsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /accounts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AccountsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter code;

    private InstantFilter date;

    private StringFilter name;

    private StringFilter description;

    private IntegerFilter type;

    private IntegerFilter parent;

    private FloatFilter balance;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private BooleanFilter hasbatches;

    private FloatFilter accountvalue;

    private IntegerFilter accountlevel;

    private LongFilter accountsnumberingsystem;

    private IntegerFilter subparentid;

    private BooleanFilter canedit;

    private FloatFilter amount;

    private FloatFilter creditamount;

    private FloatFilter debitamount;

    private StringFilter debitorcredit;

    private IntegerFilter reporttype;

    private Boolean distinct;

    public AccountsCriteria() {}

    public AccountsCriteria(AccountsCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.code = other.optionalCode().map(StringFilter::copy).orElse(null);
        this.date = other.optionalDate().map(InstantFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.type = other.optionalType().map(IntegerFilter::copy).orElse(null);
        this.parent = other.optionalParent().map(IntegerFilter::copy).orElse(null);
        this.balance = other.optionalBalance().map(FloatFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.hasbatches = other.optionalHasbatches().map(BooleanFilter::copy).orElse(null);
        this.accountvalue = other.optionalAccountvalue().map(FloatFilter::copy).orElse(null);
        this.accountlevel = other.optionalAccountlevel().map(IntegerFilter::copy).orElse(null);
        this.accountsnumberingsystem = other.optionalAccountsnumberingsystem().map(LongFilter::copy).orElse(null);
        this.subparentid = other.optionalSubparentid().map(IntegerFilter::copy).orElse(null);
        this.canedit = other.optionalCanedit().map(BooleanFilter::copy).orElse(null);
        this.amount = other.optionalAmount().map(FloatFilter::copy).orElse(null);
        this.creditamount = other.optionalCreditamount().map(FloatFilter::copy).orElse(null);
        this.debitamount = other.optionalDebitamount().map(FloatFilter::copy).orElse(null);
        this.debitorcredit = other.optionalDebitorcredit().map(StringFilter::copy).orElse(null);
        this.reporttype = other.optionalReporttype().map(IntegerFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public AccountsCriteria copy() {
        return new AccountsCriteria(this);
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

    public InstantFilter getDate() {
        return date;
    }

    public Optional<InstantFilter> optionalDate() {
        return Optional.ofNullable(date);
    }

    public InstantFilter date() {
        if (date == null) {
            setDate(new InstantFilter());
        }
        return date;
    }

    public void setDate(InstantFilter date) {
        this.date = date;
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

    public IntegerFilter getParent() {
        return parent;
    }

    public Optional<IntegerFilter> optionalParent() {
        return Optional.ofNullable(parent);
    }

    public IntegerFilter parent() {
        if (parent == null) {
            setParent(new IntegerFilter());
        }
        return parent;
    }

    public void setParent(IntegerFilter parent) {
        this.parent = parent;
    }

    public FloatFilter getBalance() {
        return balance;
    }

    public Optional<FloatFilter> optionalBalance() {
        return Optional.ofNullable(balance);
    }

    public FloatFilter balance() {
        if (balance == null) {
            setBalance(new FloatFilter());
        }
        return balance;
    }

    public void setBalance(FloatFilter balance) {
        this.balance = balance;
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

    public FloatFilter getAccountvalue() {
        return accountvalue;
    }

    public Optional<FloatFilter> optionalAccountvalue() {
        return Optional.ofNullable(accountvalue);
    }

    public FloatFilter accountvalue() {
        if (accountvalue == null) {
            setAccountvalue(new FloatFilter());
        }
        return accountvalue;
    }

    public void setAccountvalue(FloatFilter accountvalue) {
        this.accountvalue = accountvalue;
    }

    public IntegerFilter getAccountlevel() {
        return accountlevel;
    }

    public Optional<IntegerFilter> optionalAccountlevel() {
        return Optional.ofNullable(accountlevel);
    }

    public IntegerFilter accountlevel() {
        if (accountlevel == null) {
            setAccountlevel(new IntegerFilter());
        }
        return accountlevel;
    }

    public void setAccountlevel(IntegerFilter accountlevel) {
        this.accountlevel = accountlevel;
    }

    public LongFilter getAccountsnumberingsystem() {
        return accountsnumberingsystem;
    }

    public Optional<LongFilter> optionalAccountsnumberingsystem() {
        return Optional.ofNullable(accountsnumberingsystem);
    }

    public LongFilter accountsnumberingsystem() {
        if (accountsnumberingsystem == null) {
            setAccountsnumberingsystem(new LongFilter());
        }
        return accountsnumberingsystem;
    }

    public void setAccountsnumberingsystem(LongFilter accountsnumberingsystem) {
        this.accountsnumberingsystem = accountsnumberingsystem;
    }

    public IntegerFilter getSubparentid() {
        return subparentid;
    }

    public Optional<IntegerFilter> optionalSubparentid() {
        return Optional.ofNullable(subparentid);
    }

    public IntegerFilter subparentid() {
        if (subparentid == null) {
            setSubparentid(new IntegerFilter());
        }
        return subparentid;
    }

    public void setSubparentid(IntegerFilter subparentid) {
        this.subparentid = subparentid;
    }

    public BooleanFilter getCanedit() {
        return canedit;
    }

    public Optional<BooleanFilter> optionalCanedit() {
        return Optional.ofNullable(canedit);
    }

    public BooleanFilter canedit() {
        if (canedit == null) {
            setCanedit(new BooleanFilter());
        }
        return canedit;
    }

    public void setCanedit(BooleanFilter canedit) {
        this.canedit = canedit;
    }

    public FloatFilter getAmount() {
        return amount;
    }

    public Optional<FloatFilter> optionalAmount() {
        return Optional.ofNullable(amount);
    }

    public FloatFilter amount() {
        if (amount == null) {
            setAmount(new FloatFilter());
        }
        return amount;
    }

    public void setAmount(FloatFilter amount) {
        this.amount = amount;
    }

    public FloatFilter getCreditamount() {
        return creditamount;
    }

    public Optional<FloatFilter> optionalCreditamount() {
        return Optional.ofNullable(creditamount);
    }

    public FloatFilter creditamount() {
        if (creditamount == null) {
            setCreditamount(new FloatFilter());
        }
        return creditamount;
    }

    public void setCreditamount(FloatFilter creditamount) {
        this.creditamount = creditamount;
    }

    public FloatFilter getDebitamount() {
        return debitamount;
    }

    public Optional<FloatFilter> optionalDebitamount() {
        return Optional.ofNullable(debitamount);
    }

    public FloatFilter debitamount() {
        if (debitamount == null) {
            setDebitamount(new FloatFilter());
        }
        return debitamount;
    }

    public void setDebitamount(FloatFilter debitamount) {
        this.debitamount = debitamount;
    }

    public StringFilter getDebitorcredit() {
        return debitorcredit;
    }

    public Optional<StringFilter> optionalDebitorcredit() {
        return Optional.ofNullable(debitorcredit);
    }

    public StringFilter debitorcredit() {
        if (debitorcredit == null) {
            setDebitorcredit(new StringFilter());
        }
        return debitorcredit;
    }

    public void setDebitorcredit(StringFilter debitorcredit) {
        this.debitorcredit = debitorcredit;
    }

    public IntegerFilter getReporttype() {
        return reporttype;
    }

    public Optional<IntegerFilter> optionalReporttype() {
        return Optional.ofNullable(reporttype);
    }

    public IntegerFilter reporttype() {
        if (reporttype == null) {
            setReporttype(new IntegerFilter());
        }
        return reporttype;
    }

    public void setReporttype(IntegerFilter reporttype) {
        this.reporttype = reporttype;
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
        final AccountsCriteria that = (AccountsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(code, that.code) &&
            Objects.equals(date, that.date) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(type, that.type) &&
            Objects.equals(parent, that.parent) &&
            Objects.equals(balance, that.balance) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(hasbatches, that.hasbatches) &&
            Objects.equals(accountvalue, that.accountvalue) &&
            Objects.equals(accountlevel, that.accountlevel) &&
            Objects.equals(accountsnumberingsystem, that.accountsnumberingsystem) &&
            Objects.equals(subparentid, that.subparentid) &&
            Objects.equals(canedit, that.canedit) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(creditamount, that.creditamount) &&
            Objects.equals(debitamount, that.debitamount) &&
            Objects.equals(debitorcredit, that.debitorcredit) &&
            Objects.equals(reporttype, that.reporttype) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            code,
            date,
            name,
            description,
            type,
            parent,
            balance,
            lmu,
            lmd,
            hasbatches,
            accountvalue,
            accountlevel,
            accountsnumberingsystem,
            subparentid,
            canedit,
            amount,
            creditamount,
            debitamount,
            debitorcredit,
            reporttype,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountsCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalDate().map(f -> "date=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalType().map(f -> "type=" + f + ", ").orElse("") +
            optionalParent().map(f -> "parent=" + f + ", ").orElse("") +
            optionalBalance().map(f -> "balance=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalHasbatches().map(f -> "hasbatches=" + f + ", ").orElse("") +
            optionalAccountvalue().map(f -> "accountvalue=" + f + ", ").orElse("") +
            optionalAccountlevel().map(f -> "accountlevel=" + f + ", ").orElse("") +
            optionalAccountsnumberingsystem().map(f -> "accountsnumberingsystem=" + f + ", ").orElse("") +
            optionalSubparentid().map(f -> "subparentid=" + f + ", ").orElse("") +
            optionalCanedit().map(f -> "canedit=" + f + ", ").orElse("") +
            optionalAmount().map(f -> "amount=" + f + ", ").orElse("") +
            optionalCreditamount().map(f -> "creditamount=" + f + ", ").orElse("") +
            optionalDebitamount().map(f -> "debitamount=" + f + ", ").orElse("") +
            optionalDebitorcredit().map(f -> "debitorcredit=" + f + ", ").orElse("") +
            optionalReporttype().map(f -> "reporttype=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
