package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class AccountsCriteriaTest {

    @Test
    void newAccountsCriteriaHasAllFiltersNullTest() {
        var accountsCriteria = new AccountsCriteria();
        assertThat(accountsCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void accountsCriteriaFluentMethodsCreatesFiltersTest() {
        var accountsCriteria = new AccountsCriteria();

        setAllFilters(accountsCriteria);

        assertThat(accountsCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void accountsCriteriaCopyCreatesNullFilterTest() {
        var accountsCriteria = new AccountsCriteria();
        var copy = accountsCriteria.copy();

        assertThat(accountsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(accountsCriteria)
        );
    }

    @Test
    void accountsCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var accountsCriteria = new AccountsCriteria();
        setAllFilters(accountsCriteria);

        var copy = accountsCriteria.copy();

        assertThat(accountsCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(accountsCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var accountsCriteria = new AccountsCriteria();

        assertThat(accountsCriteria).hasToString("AccountsCriteria{}");
    }

    private static void setAllFilters(AccountsCriteria accountsCriteria) {
        accountsCriteria.id();
        accountsCriteria.code();
        accountsCriteria.date();
        accountsCriteria.name();
        accountsCriteria.description();
        accountsCriteria.type();
        accountsCriteria.parent();
        accountsCriteria.balance();
        accountsCriteria.lmu();
        accountsCriteria.lmd();
        accountsCriteria.hasbatches();
        accountsCriteria.accountvalue();
        accountsCriteria.accountlevel();
        accountsCriteria.accountsnumberingsystem();
        accountsCriteria.subparentid();
        accountsCriteria.canedit();
        accountsCriteria.amount();
        accountsCriteria.creditamount();
        accountsCriteria.debitamount();
        accountsCriteria.debitorcredit();
        accountsCriteria.reporttype();
        accountsCriteria.distinct();
    }

    private static Condition<AccountsCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getDate()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getType()) &&
                condition.apply(criteria.getParent()) &&
                condition.apply(criteria.getBalance()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getHasbatches()) &&
                condition.apply(criteria.getAccountvalue()) &&
                condition.apply(criteria.getAccountlevel()) &&
                condition.apply(criteria.getAccountsnumberingsystem()) &&
                condition.apply(criteria.getSubparentid()) &&
                condition.apply(criteria.getCanedit()) &&
                condition.apply(criteria.getAmount()) &&
                condition.apply(criteria.getCreditamount()) &&
                condition.apply(criteria.getDebitamount()) &&
                condition.apply(criteria.getDebitorcredit()) &&
                condition.apply(criteria.getReporttype()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<AccountsCriteria> copyFiltersAre(AccountsCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getDate(), copy.getDate()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getType(), copy.getType()) &&
                condition.apply(criteria.getParent(), copy.getParent()) &&
                condition.apply(criteria.getBalance(), copy.getBalance()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getHasbatches(), copy.getHasbatches()) &&
                condition.apply(criteria.getAccountvalue(), copy.getAccountvalue()) &&
                condition.apply(criteria.getAccountlevel(), copy.getAccountlevel()) &&
                condition.apply(criteria.getAccountsnumberingsystem(), copy.getAccountsnumberingsystem()) &&
                condition.apply(criteria.getSubparentid(), copy.getSubparentid()) &&
                condition.apply(criteria.getCanedit(), copy.getCanedit()) &&
                condition.apply(criteria.getAmount(), copy.getAmount()) &&
                condition.apply(criteria.getCreditamount(), copy.getCreditamount()) &&
                condition.apply(criteria.getDebitamount(), copy.getDebitamount()) &&
                condition.apply(criteria.getDebitorcredit(), copy.getDebitorcredit()) &&
                condition.apply(criteria.getReporttype(), copy.getReporttype()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
