package com.heavenscode.rac.service;

import com.heavenscode.rac.domain.Inventory;
import com.heavenscode.rac.repository.InventoryRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.heavenscode.rac.domain.Inventory}.
 */
@Service
@Transactional
public class InventoryService {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryService.class);

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    /**
     * Save a inventory.
     *
     * @param inventory the entity to save.
     * @return the persisted entity.
     */
    public Inventory save(Inventory inventory) {
        LOG.debug("Request to save Inventory : {}", inventory);
        return inventoryRepository.save(inventory);
    }

    /**
     * Update a inventory.
     *
     * @param inventory the entity to save.
     * @return the persisted entity.
     */
    public Inventory update(Inventory inventory) {
        LOG.debug("Request to update Inventory : {}", inventory);
        return inventoryRepository.save(inventory);
    }

    /**
     * Partially update a inventory.
     *
     * @param inventory the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Inventory> partialUpdate(Inventory inventory) {
        LOG.debug("Request to partially update Inventory : {}", inventory);

        return inventoryRepository
            .findById(inventory.getId())
            .map(existingInventory -> {
                if (inventory.getCode() != null) {
                    existingInventory.setCode(inventory.getCode());
                }
                if (inventory.getPartnumber() != null) {
                    existingInventory.setPartnumber(inventory.getPartnumber());
                }
                if (inventory.getName() != null) {
                    existingInventory.setName(inventory.getName());
                }
                if (inventory.getDescription() != null) {
                    existingInventory.setDescription(inventory.getDescription());
                }
                if (inventory.getType() != null) {
                    existingInventory.setType(inventory.getType());
                }
                if (inventory.getClassification1() != null) {
                    existingInventory.setClassification1(inventory.getClassification1());
                }
                if (inventory.getClassification2() != null) {
                    existingInventory.setClassification2(inventory.getClassification2());
                }
                if (inventory.getClassification3() != null) {
                    existingInventory.setClassification3(inventory.getClassification3());
                }
                if (inventory.getClassification4() != null) {
                    existingInventory.setClassification4(inventory.getClassification4());
                }
                if (inventory.getClassification5() != null) {
                    existingInventory.setClassification5(inventory.getClassification5());
                }
                if (inventory.getUnitofmeasurement() != null) {
                    existingInventory.setUnitofmeasurement(inventory.getUnitofmeasurement());
                }
                if (inventory.getDecimalplaces() != null) {
                    existingInventory.setDecimalplaces(inventory.getDecimalplaces());
                }
                if (inventory.getIsassemblyunit() != null) {
                    existingInventory.setIsassemblyunit(inventory.getIsassemblyunit());
                }
                if (inventory.getAssemblyunitof() != null) {
                    existingInventory.setAssemblyunitof(inventory.getAssemblyunitof());
                }
                if (inventory.getReorderlevel() != null) {
                    existingInventory.setReorderlevel(inventory.getReorderlevel());
                }
                if (inventory.getLastcost() != null) {
                    existingInventory.setLastcost(inventory.getLastcost());
                }
                if (inventory.getLastsellingprice() != null) {
                    existingInventory.setLastsellingprice(inventory.getLastsellingprice());
                }
                if (inventory.getLmu() != null) {
                    existingInventory.setLmu(inventory.getLmu());
                }
                if (inventory.getLmd() != null) {
                    existingInventory.setLmd(inventory.getLmd());
                }
                if (inventory.getAvailablequantity() != null) {
                    existingInventory.setAvailablequantity(inventory.getAvailablequantity());
                }
                if (inventory.getHasbatches() != null) {
                    existingInventory.setHasbatches(inventory.getHasbatches());
                }
                if (inventory.getItemspecfilepath() != null) {
                    existingInventory.setItemspecfilepath(inventory.getItemspecfilepath());
                }
                if (inventory.getItemimagepath() != null) {
                    existingInventory.setItemimagepath(inventory.getItemimagepath());
                }
                if (inventory.getReturnprice() != null) {
                    existingInventory.setReturnprice(inventory.getReturnprice());
                }
                if (inventory.getActiveitem() != null) {
                    existingInventory.setActiveitem(inventory.getActiveitem());
                }
                if (inventory.getMinstock() != null) {
                    existingInventory.setMinstock(inventory.getMinstock());
                }
                if (inventory.getMaxstock() != null) {
                    existingInventory.setMaxstock(inventory.getMaxstock());
                }
                if (inventory.getDailyaverage() != null) {
                    existingInventory.setDailyaverage(inventory.getDailyaverage());
                }
                if (inventory.getBufferlevel() != null) {
                    existingInventory.setBufferlevel(inventory.getBufferlevel());
                }
                if (inventory.getLeadtime() != null) {
                    existingInventory.setLeadtime(inventory.getLeadtime());
                }
                if (inventory.getBuffertime() != null) {
                    existingInventory.setBuffertime(inventory.getBuffertime());
                }
                if (inventory.getSaftydays() != null) {
                    existingInventory.setSaftydays(inventory.getSaftydays());
                }
                if (inventory.getAccountcode() != null) {
                    existingInventory.setAccountcode(inventory.getAccountcode());
                }
                if (inventory.getAccountid() != null) {
                    existingInventory.setAccountid(inventory.getAccountid());
                }
                if (inventory.getCasepackqty() != null) {
                    existingInventory.setCasepackqty(inventory.getCasepackqty());
                }
                if (inventory.getIsregistered() != null) {
                    existingInventory.setIsregistered(inventory.getIsregistered());
                }
                if (inventory.getDefaultstocklocationid() != null) {
                    existingInventory.setDefaultstocklocationid(inventory.getDefaultstocklocationid());
                }
                if (inventory.getRackno() != null) {
                    existingInventory.setRackno(inventory.getRackno());
                }
                if (inventory.getBarcodeimage() != null) {
                    existingInventory.setBarcodeimage(inventory.getBarcodeimage());
                }
                //    if (inventory.getBarcodeimageContentType() != null) {
                //      existingInventory.setBarcodeimageContentType(inventory.getBarcodeimageContentType());
                //   }
                if (inventory.getCommissionempid() != null) {
                    existingInventory.setCommissionempid(inventory.getCommissionempid());
                }
                if (inventory.getChecktypeid() != null) {
                    existingInventory.setChecktypeid(inventory.getChecktypeid());
                }
                if (inventory.getChecktype() != null) {
                    existingInventory.setChecktype(inventory.getChecktype());
                }
                if (inventory.getReorderqty() != null) {
                    existingInventory.setReorderqty(inventory.getReorderqty());
                }
                if (inventory.getNotininvoice() != null) {
                    existingInventory.setNotininvoice(inventory.getNotininvoice());
                }

                return existingInventory;
            })
            .map(inventoryRepository::save);
    }

    /**
     * Get one inventory by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Inventory> findOne(Long id) {
        LOG.debug("Request to get Inventory : {}", id);
        return inventoryRepository.findById(id);
    }

    /**
     * Delete the inventory by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Inventory : {}", id);
        inventoryRepository.deleteById(id);
    }
}
