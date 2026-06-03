package com.heavenscode.rac.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.heavenscode.rac.domain.Servicecategory} entity. This class is used
 * in {@link com.heavenscode.rac.web.rest.ServicecategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /servicecategories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicecategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter description;

    private IntegerFilter lmu;

    private InstantFilter lmd;

    private BooleanFilter showsecurity;

    private IntegerFilter sortorder;

    private BooleanFilter isactive;

    private Boolean distinct;

    public ServicecategoryCriteria() {}

    public ServicecategoryCriteria(ServicecategoryCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.description = other.optionalDescription().map(StringFilter::copy).orElse(null);
        this.lmu = other.optionalLmu().map(IntegerFilter::copy).orElse(null);
        this.lmd = other.optionalLmd().map(InstantFilter::copy).orElse(null);
        this.showsecurity = other.optionalShowsecurity().map(BooleanFilter::copy).orElse(null);
        this.sortorder = other.optionalSortorder().map(IntegerFilter::copy).orElse(null);
        this.isactive = other.optionalIsactive().map(BooleanFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public ServicecategoryCriteria copy() {
        return new ServicecategoryCriteria(this);
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

    public BooleanFilter getShowsecurity() {
        return showsecurity;
    }

    public Optional<BooleanFilter> optionalShowsecurity() {
        return Optional.ofNullable(showsecurity);
    }

    public BooleanFilter showsecurity() {
        if (showsecurity == null) {
            setShowsecurity(new BooleanFilter());
        }
        return showsecurity;
    }

    public void setShowsecurity(BooleanFilter showsecurity) {
        this.showsecurity = showsecurity;
    }

    public IntegerFilter getSortorder() {
        return sortorder;
    }

    public Optional<IntegerFilter> optionalSortorder() {
        return Optional.ofNullable(sortorder);
    }

    public IntegerFilter sortorder() {
        if (sortorder == null) {
            setSortorder(new IntegerFilter());
        }
        return sortorder;
    }

    public void setSortorder(IntegerFilter sortorder) {
        this.sortorder = sortorder;
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
        final ServicecategoryCriteria that = (ServicecategoryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(lmu, that.lmu) &&
            Objects.equals(lmd, that.lmd) &&
            Objects.equals(showsecurity, that.showsecurity) &&
            Objects.equals(sortorder, that.sortorder) &&
            Objects.equals(isactive, that.isactive) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, lmu, lmd, showsecurity, sortorder, isactive, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicecategoryCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalDescription().map(f -> "description=" + f + ", ").orElse("") +
            optionalLmu().map(f -> "lmu=" + f + ", ").orElse("") +
            optionalLmd().map(f -> "lmd=" + f + ", ").orElse("") +
            optionalShowsecurity().map(f -> "showsecurity=" + f + ", ").orElse("") +
            optionalSortorder().map(f -> "sortorder=" + f + ", ").orElse("") +
            optionalIsactive().map(f -> "isactive=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
