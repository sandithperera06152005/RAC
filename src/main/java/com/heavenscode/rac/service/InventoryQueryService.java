package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.*; // for static metamodels
import com.heavenscode.rac.domain.Inventory;
import com.heavenscode.rac.repository.InventoryRepository;
import com.heavenscode.rac.service.criteria.InventoryCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Inventory} entities in the database.
 * The main input is a {@link InventoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link Inventory} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InventoryQueryService extends QueryService<Inventory> {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryQueryService.class);

    private final InventoryRepository inventoryRepository;

    public InventoryQueryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Return a {@link Page} of {@link Inventory} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Inventory> findByCriteria(InventoryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Inventory> specification = createSpecification(criteria);
        return inventoryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(InventoryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Inventory> specification = createSpecification(criteria);
        return inventoryRepository.count(specification);
    }

    /**
     * Function to convert {@link InventoryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Inventory> createSpecification(InventoryCriteria criteria) {
        Specification<Inventory> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Inventory_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Inventory_.code));
            }
            if (criteria.getPartnumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPartnumber(), Inventory_.partnumber));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Inventory_.name));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Inventory_.description));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getType(), Inventory_.type));
            }
            if (criteria.getClassification1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassification1(), Inventory_.classification1));
            }
            if (criteria.getClassification2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassification2(), Inventory_.classification2));
            }
            if (criteria.getClassification3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassification3(), Inventory_.classification3));
            }
            if (criteria.getClassification4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassification4(), Inventory_.classification4));
            }
            if (criteria.getClassification5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClassification5(), Inventory_.classification5));
            }
            if (criteria.getUnitofmeasurement() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnitofmeasurement(), Inventory_.unitofmeasurement));
            }
            if (criteria.getDecimalplaces() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDecimalplaces(), Inventory_.decimalplaces));
            }
            if (criteria.getIsassemblyunit() != null) {
                specification = specification.and(buildSpecification(criteria.getIsassemblyunit(), Inventory_.isassemblyunit));
            }
            if (criteria.getAssemblyunitof() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssemblyunitof(), Inventory_.assemblyunitof));
            }
            if (criteria.getReorderlevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReorderlevel(), Inventory_.reorderlevel));
            }
            if (criteria.getLastcost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastcost(), Inventory_.lastcost));
            }
            if (criteria.getLastsellingprice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastsellingprice(), Inventory_.lastsellingprice));
            }
            if (criteria.getLmu() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmu(), Inventory_.lmu));
            }
            if (criteria.getLmd() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLmd(), Inventory_.lmd));
            }
            if (criteria.getAvailablequantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailablequantity(), Inventory_.availablequantity));
            }
            if (criteria.getHasbatches() != null) {
                specification = specification.and(buildSpecification(criteria.getHasbatches(), Inventory_.hasbatches));
            }
            if (criteria.getItemspecfilepath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemspecfilepath(), Inventory_.itemspecfilepath));
            }
            if (criteria.getItemimagepath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getItemimagepath(), Inventory_.itemimagepath));
            }
            if (criteria.getReturnprice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReturnprice(), Inventory_.returnprice));
            }
            if (criteria.getActiveitem() != null) {
                specification = specification.and(buildSpecification(criteria.getActiveitem(), Inventory_.activeitem));
            }
            if (criteria.getMinstock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMinstock(), Inventory_.minstock));
            }
            if (criteria.getMaxstock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getMaxstock(), Inventory_.maxstock));
            }
            if (criteria.getDailyaverage() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDailyaverage(), Inventory_.dailyaverage));
            }
            if (criteria.getBufferlevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBufferlevel(), Inventory_.bufferlevel));
            }
            if (criteria.getLeadtime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLeadtime(), Inventory_.leadtime));
            }
            if (criteria.getBuffertime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBuffertime(), Inventory_.buffertime));
            }
            if (criteria.getSaftydays() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSaftydays(), Inventory_.saftydays));
            }
            if (criteria.getAccountcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountcode(), Inventory_.accountcode));
            }
            if (criteria.getAccountid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountid(), Inventory_.accountid));
            }
            if (criteria.getCasepackqty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCasepackqty(), Inventory_.casepackqty));
            }
            if (criteria.getIsregistered() != null) {
                specification = specification.and(buildSpecification(criteria.getIsregistered(), Inventory_.isregistered));
            }
            if (criteria.getDefaultstocklocationid() != null) {
                specification = specification.and(
                    buildRangeSpecification(criteria.getDefaultstocklocationid(), Inventory_.defaultstocklocationid)
                );
            }
            if (criteria.getRackno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRackno(), Inventory_.rackno));
            }
            if (criteria.getCommissionempid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCommissionempid(), Inventory_.commissionempid));
            }
            if (criteria.getChecktypeid() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getChecktypeid(), Inventory_.checktypeid));
            }
            if (criteria.getChecktype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChecktype(), Inventory_.checktype));
            }
            if (criteria.getReorderqty() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReorderqty(), Inventory_.reorderqty));
            }
            if (criteria.getNotininvoice() != null) {
                specification = specification.and(buildSpecification(criteria.getNotininvoice(), Inventory_.notininvoice));
            }
        }
        return specification;
    }
}
