package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Servicesubcategory} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.ServicesubcategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /servicesubcategories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicesubcategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private IntegerFilter mainid;

    private StringFilter mainname;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private BooleanFilter isactive;

    private Boolean distinct;

    public ServicesubcategoryCriteria() {}

    public ServicesubcategoryCriteria(ServicesubcategoryCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.mainid = other.optionalMainid().map(IntegerFilter::copy).orElse(null);
        this.mainname = other.optionalMainname().map(StringFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.isactive = other.optionalIsactive().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ServicesubcategoryCriteria copy() {
        return new ServicesubcategoryCriteria(this);
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

    public IntegerFilter getMainid() {
        return mainid;
    }

    public Optional<IntegerFilter> optionalMainid() {
        return Optional.ofNullable(mainid);
    }

    public IntegerFilter mainid() {
        if (mainid == null) {
            setMainid(new IntegerFilter());
        }
        return mainid;
    }

    public void setMainid(IntegerFilter mainid) {
        this.mainid = mainid;
    }

    public StringFilter getMainname() {
        return mainname;
    }

    public Optional<StringFilter> optionalMainname() {
        return Optional.ofNullable(mainname);
    }

    public StringFilter mainname() {
        if (mainname == null) {
            setMainname(new StringFilter());
        }
        return mainname;
    }

    public void setMainname(StringFilter mainname) {
        this.mainname = mainname;
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
        final ServicesubcategoryCriteria that = (ServicesubcategoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(mainid, that.mainid) &&
            Objects.equals(mainname, that.mainname) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, mainid, mainname, lmu, lmd, isactive, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicesubcategoryCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalMainid().map(f -> "mainid=" + f + ", ").orElse("") +
            optionalMainname().map(f -> "mainname=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalIsactive().map(f -> "isactive=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
