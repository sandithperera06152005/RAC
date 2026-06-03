package com.heavenscode.rac.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class InventoryCriteriaTest {

    @Test
    void newInventoryCriteriaHasAllFiltersNullTest() {
        var inventoryCriteria = new InventoryCriteria();
        assertThat(inventoryCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void inventoryCriteriaFluentMethodsCreatesFiltersTest() {
        var inventoryCriteria = new InventoryCriteria();

        setAllFilters(inventoryCriteria);

        assertThat(inventoryCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void inventoryCriteriaCopyCreatesNullFilterTest() {
        var inventoryCriteria = new InventoryCriteria();
        var copy = inventoryCriteria.copy();

        assertThat(inventoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(inventoryCriteria)
        );
    }

    @Test
    void inventoryCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var inventoryCriteria = new InventoryCriteria();
        setAllFilters(inventoryCriteria);

        var copy = inventoryCriteria.copy();

        assertThat(inventoryCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(inventoryCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var inventoryCriteria = new InventoryCriteria();

        assertThat(inventoryCriteria).hasToString("InventoryCriteria{}");
    }

    private static void setAllFilters(InventoryCriteria inventoryCriteria) {
        inventoryCriteria.id();
        inventoryCriteria.code();
        inventoryCriteria.partnumber();
        inventoryCriteria.name();
        inventoryCriteria.description();
        inventoryCriteria.type();
        inventoryCriteria.classification1();
        inventoryCriteria.classification2();
        inventoryCriteria.classification3();
        inventoryCriteria.classification4();
        inventoryCriteria.classification5();
        inventoryCriteria.unitofmeasurement();
        inventoryCriteria.decimalplaces();
        inventoryCriteria.isassemblyunit();
        inventoryCriteria.assemblyunitof();
        inventoryCriteria.reorderlevel();
        inventoryCriteria.lastcost();
        inventoryCriteria.lastsellingprice();
        inventoryCriteria.lmu();
        inventoryCriteria.lmd();
        inventoryCriteria.availablequantity();
        inventoryCriteria.hasbatches();
        inventoryCriteria.itemspecfilepath();
        inventoryCriteria.itemimagepath();
        inventoryCriteria.returnprice();
        inventoryCriteria.activeitem();
        inventoryCriteria.minstock();
        inventoryCriteria.maxstock();
        inventoryCriteria.dailyaverage();
        inventoryCriteria.bufferlevel();
        inventoryCriteria.leadtime();
        inventoryCriteria.buffertime();
        inventoryCriteria.saftydays();
        inventoryCriteria.accountcode();
        inventoryCriteria.accountid();
        inventoryCriteria.casepackqty();
        inventoryCriteria.isregistered();
        inventoryCriteria.defaultstocklocationid();
        inventoryCriteria.rackno();
        inventoryCriteria.commissionempid();
        inventoryCriteria.checktypeid();
        inventoryCriteria.checktype();
        inventoryCriteria.reorderqty();
        inventoryCriteria.notininvoice();
        inventoryCriteria.distinct();
    }

    private static Condition<InventoryCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getPartnumber()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getDescription()) &&
                condition.apply(criteria.getType()) &&
                condition.apply(criteria.getClassification1()) &&
                condition.apply(criteria.getClassification2()) &&
                condition.apply(criteria.getClassification3()) &&
                condition.apply(criteria.getClassification4()) &&
                condition.apply(criteria.getClassification5()) &&
                condition.apply(criteria.getUnitofmeasurement()) &&
                condition.apply(criteria.getDecimalplaces()) &&
                condition.apply(criteria.getIsassemblyunit()) &&
                condition.apply(criteria.getAssemblyunitof()) &&
                condition.apply(criteria.getReorderlevel()) &&
                condition.apply(criteria.getLastcost()) &&
                condition.apply(criteria.getLastsellingprice()) &&
                condition.apply(criteria.getLmu()) &&
                condition.apply(criteria.getLmd()) &&
                condition.apply(criteria.getAvailablequantity()) &&
                condition.apply(criteria.getHasbatches()) &&
                condition.apply(criteria.getItemspecfilepath()) &&
                condition.apply(criteria.getItemimagepath()) &&
                condition.apply(criteria.getReturnprice()) &&
                condition.apply(criteria.getActiveitem()) &&
                condition.apply(criteria.getMinstock()) &&
                condition.apply(criteria.getMaxstock()) &&
                condition.apply(criteria.getDailyaverage()) &&
                condition.apply(criteria.getBufferlevel()) &&
                condition.apply(criteria.getLeadtime()) &&
                condition.apply(criteria.getBuffertime()) &&
                condition.apply(criteria.getSaftydays()) &&
                condition.apply(criteria.getAccountcode()) &&
                condition.apply(criteria.getAccountid()) &&
                condition.apply(criteria.getCasepackqty()) &&
                condition.apply(criteria.getIsregistered()) &&
                condition.apply(criteria.getDefaultstocklocationid()) &&
                condition.apply(criteria.getRackno()) &&
                condition.apply(criteria.getCommissionempid()) &&
                condition.apply(criteria.getChecktypeid()) &&
                condition.apply(criteria.getChecktype()) &&
                condition.apply(criteria.getReorderqty()) &&
                condition.apply(criteria.getNotininvoice()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<InventoryCriteria> copyFiltersAre(InventoryCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getPartnumber(), copy.getPartnumber()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getDescription(), copy.getDescription()) &&
                condition.apply(criteria.getType(), copy.getType()) &&
                condition.apply(criteria.getClassification1(), copy.getClassification1()) &&
                condition.apply(criteria.getClassification2(), copy.getClassification2()) &&
                condition.apply(criteria.getClassification3(), copy.getClassification3()) &&
                condition.apply(criteria.getClassification4(), copy.getClassification4()) &&
                condition.apply(criteria.getClassification5(), copy.getClassification5()) &&
                condition.apply(criteria.getUnitofmeasurement(), copy.getUnitofmeasurement()) &&
                condition.apply(criteria.getDecimalplaces(), copy.getDecimalplaces()) &&
                condition.apply(criteria.getIsassemblyunit(), copy.getIsassemblyunit()) &&
                condition.apply(criteria.getAssemblyunitof(), copy.getAssemblyunitof()) &&
                condition.apply(criteria.getReorderlevel(), copy.getReorderlevel()) &&
                condition.apply(criteria.getLastcost(), copy.getLastcost()) &&
                condition.apply(criteria.getLastsellingprice(), copy.getLastsellingprice()) &&
                condition.apply(criteria.getLmu(), copy.getLmu()) &&
                condition.apply(criteria.getLmd(), copy.getLmd()) &&
                condition.apply(criteria.getAvailablequantity(), copy.getAvailablequantity()) &&
                condition.apply(criteria.getHasbatches(), copy.getHasbatches()) &&
                condition.apply(criteria.getItemspecfilepath(), copy.getItemspecfilepath()) &&
                condition.apply(criteria.getItemimagepath(), copy.getItemimagepath()) &&
                condition.apply(criteria.getReturnprice(), copy.getReturnprice()) &&
                condition.apply(criteria.getActiveitem(), copy.getActiveitem()) &&
                condition.apply(criteria.getMinstock(), copy.getMinstock()) &&
                condition.apply(criteria.getMaxstock(), copy.getMaxstock()) &&
                condition.apply(criteria.getDailyaverage(), copy.getDailyaverage()) &&
                condition.apply(criteria.getBufferlevel(), copy.getBufferlevel()) &&
                condition.apply(criteria.getLeadtime(), copy.getLeadtime()) &&
                condition.apply(criteria.getBuffertime(), copy.getBuffertime()) &&
                condition.apply(criteria.getSaftydays(), copy.getSaftydays()) &&
                condition.apply(criteria.getAccountcode(), copy.getAccountcode()) &&
                condition.apply(criteria.getAccountid(), copy.getAccountid()) &&
                condition.apply(criteria.getCasepackqty(), copy.getCasepackqty()) &&
                condition.apply(criteria.getIsregistered(), copy.getIsregistered()) &&
                condition.apply(criteria.getDefaultstocklocationid(), copy.getDefaultstocklocationid()) &&
                condition.apply(criteria.getRackno(), copy.getRackno()) &&
                condition.apply(criteria.getCommissionempid(), copy.getCommissionempid()) &&
                condition.apply(criteria.getChecktypeid(), copy.getChecktypeid()) &&
                condition.apply(criteria.getChecktype(), copy.getChecktype()) &&
                condition.apply(criteria.getReorderqty(), copy.getReorderqty()) &&
                condition.apply(criteria.getNotininvoice(), copy.getNotininvoice()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
