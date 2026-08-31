import { Component, inject, OnInit, ChangeDetectorRef, ViewChild, Input } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, forkJoin } from 'rxjs';
import { finalize } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';

import { IAutocarejob } from '../autocarejob.model';
import { ICustomervehicle } from 'app/entities/customervehicle/customervehicle.model';
import { CustomervehicleService } from 'app/entities/customervehicle/service/customervehicle.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { VehiclebrandmodelService } from 'app/entities/vehiclebrandmodel/service/vehiclebrandmodel.service';
import { IVehiclebrandmodel } from 'app/entities/vehiclebrandmodel/vehiclebrandmodel.model';
import { VehiclebrandnameService } from 'app/entities/vehiclebrandname/service/vehiclebrandname.service';
import { IVehiclebrandname } from 'app/entities/vehiclebrandname/vehiclebrandname.model';
import { ServicecategoryService } from 'app/entities/servicecategory/service/servicecategory.service';
import { IServicecategory } from 'app/entities/servicecategory/servicecategory.model';
import { ServicesubcategoryService } from 'app/entities/servicesubcategory/service/servicesubcategory.service';
import { IServicesubcategory } from 'app/entities/servicesubcategory/servicesubcategory.model';
import { InventoryService } from 'app/entities/inventory/service/inventory.service';
import { AutocarejobService } from '../service/autocarejob.service';
import { AutocarejobFormService, AutocarejobFormGroup } from './autocarejob-form.service';
import { IInventory } from 'app/entities/inventory/inventory.model';
import { ICommonserviceoption } from 'app/entities/commonserviceoption/commonserviceoption.model';
import { CommonserviceoptionService } from 'app/entities/commonserviceoption/service/commonserviceoption.service';
import { IBillingserviceoption } from 'app/entities/billingserviceoption/billingserviceoption.model';
import { BillingserviceoptionService } from 'app/entities/billingserviceoption/service/billingserviceoption.service';
import { IBillingserviceoptionvalues } from 'app/entities/billingserviceoptionvalues/billingserviceoptionvalues.model';
import { IAutojobsinvoice } from 'app/entities/autojobsinvoice/autojobsinvoice.model';
import { BillingserviceoptionvaluesService } from 'app/entities/billingserviceoptionvalues/service/billingserviceoptionvalues.service';
import { IVehicletype } from 'app/entities/vehicletype/vehicletype.model';
import { VehicletypeService } from 'app/entities/vehicletype/service/vehicletype.service';
import { AutojobsinvoicelinesService } from 'app/entities/autojobsinvoicelines/service/autojobsinvoicelines.service';
import { AutojobsinvoicelinebatchesService } from 'app/entities/autojobsinvoicelinebatches/service/autojobsinvoicelinebatches.service';
import { IAutojobsinvoicelinebatches } from 'app/entities/autojobsinvoicelinebatches/autojobsinvoicelinebatches.model';
import { AutojobsinvoiceService } from 'app/entities/autojobsinvoice/service/autojobsinvoice.service';
import { IWorkshopworklist } from 'app/entities/workshopworklist/workshopworklist.model';
import { WorkshopworklistService } from 'app/entities/workshopworklist/service/workshopworklist.service';
import { AutojobsinvoiceUpdateComponent } from 'app/entities/autojobsinvoice/update/autojobsinvoice-update.component';
import { AutocarejobUpdateComponent } from './autocarejob-update.component';
import { AutojobsaleinvoicecommonservicechargeUpdateComponent } from 'app/entities/autojobsaleinvoicecommonservicecharge/update/autojobsaleinvoicecommonservicecharge-update.component';
import { AutojobsinvoicelinesUpdateComponent } from 'app/entities/autojobsinvoicelines/update/autojobsinvoicelines-update.component';
import { AutojobsalesinvoiceservicechargelineUpdateComponent } from 'app/entities/autojobsalesinvoiceservicechargeline/update/autojobsalesinvoiceservicechargeline-update.component';
import { IAutojobsaleinvoicecommonservicecharge } from 'app/entities/autojobsaleinvoicecommonservicecharge/autojobsaleinvoicecommonservicecharge.model';
import { WorkshopvehicleworkUpdateComponent } from 'app/entities/workshopvehiclework/update/workshopvehiclework-update.component';
import { AutojobsalesinvoiceservicechargelineService } from 'app/entities/autojobsalesinvoiceservicechargeline/service/autojobsalesinvoiceservicechargeline.service';

import { AutojobsaleinvoicecommonservicechargeService } from 'app/entities/autojobsaleinvoicecommonservicecharge/service/autojobsaleinvoicecommonservicecharge.service';
import { IWorkshopvehiclework } from 'app/entities/workshopvehiclework/workshopvehiclework.model';
import { WorkshopvehicleworkService } from 'app/entities/workshopvehiclework/service/workshopvehiclework.service';
import { WorkshopVehicleWorkListService } from 'app/entities/workshop-vehicle-work-list/service/workshop-vehicle-work-list.service';
import { IWorkshopVehicleWorkList } from 'app/entities/workshop-vehicle-work-list/workshop-vehicle-work-list.model';
import { AutocareJobServiceOptionService } from '../service/autocare-job-service-option.service';
import { IAutocareJobServiceOption } from '../autocare-job-service-option.model';
import { AlertService } from 'app/core/util/alert.service';
import { AlertMuteService } from 'app/core/util/alert-mute.service';
import { AccountService } from 'app/core/auth/account.service';
import { DecimalInputDirective } from 'app/shared/decimal-input.directive';

@Component({
  standalone: true,
  selector: 'jhi-autocarejob-instruction',
  templateUrl: './autocarejob-instruction.component.html',
  imports: [
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    AutojobsinvoiceUpdateComponent,
    AutojobsaleinvoicecommonservicechargeUpdateComponent,
    AutojobsinvoicelinesUpdateComponent,
    AutojobsalesinvoiceservicechargelineUpdateComponent,
    WorkshopvehicleworkUpdateComponent,

    AutocarejobUpdateComponent,
    DecimalInputDirective,
  ],
})
export class AutocarejobInstructionComponent implements OnInit {
  @Input() invid: number = 0;
  @ViewChild(AutojobsinvoiceUpdateComponent) autojobsinvoiceComponent!: AutojobsinvoiceUpdateComponent;
  @ViewChild(WorkshopvehicleworkUpdateComponent) workshopvehicleworkComponent!: WorkshopvehicleworkUpdateComponent;
  @ViewChild(AutojobsaleinvoicecommonservicechargeUpdateComponent)
  autojobsaleinvoicecommonservicechargeComponent!: AutojobsaleinvoicecommonservicechargeUpdateComponent;
  @ViewChild(AutojobsinvoicelinesUpdateComponent) autojobsinvoicelinesComponent!: AutojobsinvoicelinesUpdateComponent;
  jobinvoicelines = inject(AutojobsinvoicelinesService);
  jobinvoicebatches = inject(AutojobsinvoicelinebatchesService);
  autojobinvoice = inject(AutojobsinvoiceService);
  jobcommon = inject(AutojobsaleinvoicecommonservicechargeService);
  jobservice = inject(AutojobsalesinvoiceservicechargelineService);
  @ViewChild(AutocarejobUpdateComponent) autocarejobComponent!: AutocarejobUpdateComponent;
  @ViewChild(AutojobsalesinvoiceservicechargelineUpdateComponent)
  autojobsalesinvoiceservicechargelineComponent!: AutojobsalesinvoiceservicechargelineUpdateComponent;
  constructor(private cdr: ChangeDetectorRef) {} // Inject ChangeDetectorRef
  invoiceId: number | null = null;
  invoiceCode: string | null = null;
  isSaving = false;
  autocarejob: IAutocarejob | null = null;
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;
  vehiclebrandmodel: IVehiclebrandmodel[] = [];
  vehiclebrandname: IVehiclebrandname[] = [];
  servicecategory: IServicecategory[] = [];
  servicesubcategory: IServicesubcategory[] = [];
  inventory: IInventory[] = [];
  commonserviceoption: ICommonserviceoption[] = [];
  billingserviceoption: IBillingserviceoption[] = [];
  // selectedcommonServices: ICommonserviceoption[] = [];
  totalcommonServiceCharge = 0;
  billingserviceoptionvalues: IBillingserviceoptionvalues[] = [];
  vehicletypes: IVehicletype[] = [];
  selectedVehicleTypeId: number | null = null;
  filteredBillingServiceOptionValues: IBillingserviceoptionvalues[] = [];
  private lastRequestedVehicleTypeId: number | null = null;
  private loadedItemsInvoiceKey: string | null = null;
  private persistedItemBatchKeys = new Set<string>();
  private savedServiceOptionIds = new Set<number>();
  private savedCommonServiceOptionIds = new Set<number>();
  private savedServiceNames = new Set<string>();
  private savedCommonServiceNames = new Set<string>();
  private savedCommonServiceCodes = new Set<string>();
  private savedSubcategoryIds = new Set<number>();
  private savedSubcategoryNames = new Set<string>();
  private savedServiceValues = new Map<number, number>();
  workshopworklist: IWorkshopworklist[] = [];
  selectedworkItems: IWorkshopworklist[] = [];
  selectedSubcategoryItems: IServicesubcategory[] = [];

  // Variables for service selection and total calculation
  selectedServices: IBillingserviceoptionvalues[] = [];
  totalServiceCharge: number = 0;

  protected autocarejobService = inject(AutocarejobService);
  protected autocarejobFormService = inject(AutocarejobFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected vehiclebrandmodelService = inject(VehiclebrandmodelService);
  protected vehiclebrandnameService = inject(VehiclebrandnameService);
  protected servicecategoryService = inject(ServicecategoryService);
  protected servicesubcategoryService = inject(ServicesubcategoryService);
  protected inventoryService = inject(InventoryService);
  protected commonserviceoptionService = inject(CommonserviceoptionService);
  protected billingserviceoptionService = inject(BillingserviceoptionService);
  protected billingserviceoptionvaluesService = inject(BillingserviceoptionvaluesService);
  protected vehicletypesService = inject(VehicletypeService);
  protected workshopworklistService = inject(WorkshopworklistService);
  protected workshopvehicleworkService = inject(WorkshopvehicleworkService);
  protected workshopVehicleWorkListService = inject(WorkshopVehicleWorkListService);
  protected autocareJobServiceOptionService = inject(AutocareJobServiceOptionService);
  protected alertService = inject(AlertService);
  protected alertMuteService = inject(AlertMuteService);
  protected accountService = inject(AccountService);
  protected router = inject(Router);
  currentUserId: number = 0;

  subcategoriesVisible = true; // Show service options by default
  showPrintSummary = false; // Controls whether the print summary is shown on screen
  itemsOnlyMode = false;
  private shouldPrintOnLoad = false;
  private hasPrintedOnLoad = false;
  toggleSubcategories() {
    this.subcategoriesVisible = !this.subcategoriesVisible;
  }

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocarejobFormGroup = this.autocarejobFormService.createAutocarejobFormGroup();

  /** Returns current local time offset so it serializes as local time instead of UTC */
  private localNow(): dayjs.Dayjs {
    return dayjs().add(-new Date().getTimezoneOffset(), 'minute');
  }

  /** Offsets a parsed date so it serializes as local time instead of UTC */
  private localDate(val: any): dayjs.Dayjs {
    return val ? dayjs(val).add(-new Date().getTimezoneOffset(), 'minute') : this.localNow();
  }

  ngOnInit(): void {
    this.requireNextMillageForInstructions();

    this.activatedRoute.queryParamMap.subscribe(params => {
      this.itemsOnlyMode = params.get('itemsOnly') === 'true';
      this.shouldPrintOnLoad = params.get('print') === 'true';
      if (this.itemsOnlyMode) {
        this.accountService.identity().subscribe(() => {
          if (!this.accountService.canUpdateAdvisorInstructionItems()) {
            this.router.navigate(['/autocarejob/autocareopenjob']);
          }
        });
      }
      this.cdr.detectChanges();
    });

    this.activatedRoute.data.subscribe(({ autocarejob }) => {
      this.autocarejob = autocarejob;
      if (autocarejob) {
        this.updateForm(autocarejob);
      }

      this.loadDataFromOtherEntities();
      this.loadDataFromBrandEntities();
      this.loadDataFromServicesEntities();
      this.loadDataFromServicessubEntities();
      this.loadDataFromCommonServiceOptionEntities();
      this.loadVehicleTypes();
      this.loadBillingServiceOptions();
      this.setAutoNextServiceDate();
      this.loadExistingItemsForCurrentJob();
    });

    this.accountService.identity().subscribe(account => {
      if (account) {
        if ((account as any).id) {
          this.currentUserId = (account as any).id;
        } else {
          const storedUserId = localStorage.getItem('userId');
          if (storedUserId) {
            this.currentUserId = parseInt(storedUserId, 10);
          }
        }
      }
    });
  }

  setAutoNextServiceDate(): void {
    const futureDate = dayjs().add(-new Date().getTimezoneOffset(), 'minute').add(6, 'month').format('YYYY-MM-DD'); // Add 6 months to today
    this.editForm.patchValue({ nextservicedate: dayjs(futureDate) }); // Update form
    this.cdr.detectChanges(); // Ensure UI updates
  }
  previousState(): void {
    window.history.back();
  }

  loadVehicleTypes(): void {
    this.vehicletypesService.query({ size: 1000 }).subscribe((res: HttpResponse<IVehicletype[]>) => {
      this.vehicletypes = res.body || [];
      this.syncVehicleTypeSelectionFromForm();
    });
  }

  loadBillingServiceOptions(): void {
    let page = 0;
    const pageSize = 20;
    this.billingserviceoption = [];

    const fetchPage = () => {
      this.billingserviceoptionService.query({ page, size: pageSize }).subscribe(
        (res: HttpResponse<IBillingserviceoption[]>) => {
          this.billingserviceoption = [...this.billingserviceoption, ...(res.body || [])];

          const totalItems = res.headers.get('X-Total-Count');
          const totalRecords = totalItems ? parseInt(totalItems, 10) : 0;

          if (this.billingserviceoption.length < totalRecords) {
            page++;
            fetchPage();
          } else {
          }
        },
        error => {},
      );
    };

    fetchPage();
  }

  filterBillingServiceOptionValues(): void {
    const vehicleTypeId = this.selectedVehicleTypeId != null ? Number(this.selectedVehicleTypeId) : null;

    if (vehicleTypeId == null || Number.isNaN(vehicleTypeId)) {
      this.filteredBillingServiceOptionValues = [];
      this.lastRequestedVehicleTypeId = null;
      console.log('Filtered Billing Service Option Values:', this.filteredBillingServiceOptionValues);
      return;
    }

    if (this.lastRequestedVehicleTypeId === vehicleTypeId) {
      return;
    }

    this.lastRequestedVehicleTypeId = vehicleTypeId;
    this.billingserviceoptionvaluesService.findByVehicleTypeId(vehicleTypeId).subscribe({
      next: (res: HttpResponse<IBillingserviceoptionvalues[]>) => {
        this.filteredBillingServiceOptionValues = res.body || [];
        this.syncSelectedServicesFromSaved();
        this.cdr.detectChanges();
        console.log('Filtered Billing Service Option Values:', this.filteredBillingServiceOptionValues);
      },
      error: error => {
        console.error('Failed to load billing service option values:', error);
        this.filteredBillingServiceOptionValues = [];
        this.lastRequestedVehicleTypeId = null;
      },
    });
  }

  getBillingServiceOptionName(billingserviceoptionId: number | null | undefined): string {
    if (billingserviceoptionId == null) {
      return 'Unknown';
    }

    const option = this.billingserviceoption.find(opt => opt.id === billingserviceoptionId);
    // console.log('Billing Service Optionssssssssss:', option); // Debugging
    return option && option.servicename ? option.servicename : 'Unknown';
  }

  onVehicleTypeChange(): void {
    this.editForm.get('vehicletypeid')?.setValue(this.selectedVehicleTypeId != null ? Number(this.selectedVehicleTypeId) : null);
    this.filterBillingServiceOptionValues();
    this.calculateTotalCharge();
  }

  private syncVehicleTypeSelectionFromForm(): void {
    const formVehicleTypeId = this.editForm.get('vehicletypeid')?.value;
    this.selectedVehicleTypeId = formVehicleTypeId != null ? Number(formVehicleTypeId) : null;

    if (this.selectedVehicleTypeId != null && !Number.isNaN(this.selectedVehicleTypeId)) {
      this.filterBillingServiceOptionValues();
    } else {
      this.filteredBillingServiceOptionValues = [];
    }
  }

  isServiceSelected(item: IBillingserviceoptionvalues): boolean {
    const optionId = Number(item.billingserviceoptionid ?? 0);
    const serviceName = this.getBillingServiceOptionName(item.billingserviceoptionid).trim().toLowerCase();
    return (
      this.savedServiceOptionIds.has(optionId) ||
      this.savedServiceNames.has(serviceName) ||
      this.selectedServices.some(service => Number(service.billingserviceoptionid ?? 0) === optionId)
    );
  }

  // Define the serviceArray to hold the selected service details
  serviceArray: Array<{
    id?: number;
    invoiceid: number;
    lineid: number;
    optionid: number;
    servicename: string;
    servicediscription: string;
    value: number;
    addedbyid: number;
    iscustomersrvice: boolean;
    discount: number;
    serviceprice: number;
  }> = [];

  onServiceSelectionChanges(item: IBillingserviceoptionvalues, event: any): void {
    const serviceName = this.getBillingServiceOptionName(item.billingserviceoptionid);

    if (event.target.checked) {
      // Check if the service already exists in the selectedServices array
      const nextLineId = this.serviceArray.length > 0 ? Math.max(...this.serviceArray.map(item => item.lineid), 0) + 1 : 1;
      const exists = this.selectedServices.some(service => service.id === item.id);

      if (!exists) {
        // Add the service to selectedServices with the service name and value
        this.selectedServices.push({
          ...item,
          servicename: serviceName,
        });

        // Add the service to serviceArray with the required structure
        this.serviceArray.push({
          invoiceid: 0,
          lineid: nextLineId,
          optionid: item.billingserviceoptionid ?? 0,
          servicename: serviceName,
          servicediscription: '',
          value: item.value ?? 0,
          addedbyid: 0,
          iscustomersrvice: true,
          discount: 0,
          serviceprice: 0,
        });
      }
    } else {
      // Remove the service by filtering it out
      this.selectedServices = this.selectedServices.filter(service => service.id !== item.id);

      // Remove the service from serviceArray by filtering it out
      this.serviceArray = this.serviceArray.filter(service => service.optionid !== (item.billingserviceoptionid ?? 0));
    }

    this.calculateTotalCharges(); // Ensure this is called

    // console.log('Selected Services:', this.selectedServices);
    // console.log('serviceArrayyyyyyyyyyyyyyyy:', this.serviceArray);

    this.selectedServices.forEach(service => {
      // console.log('Selected Service billingserviceoptionid:', service.billingserviceoptionid);
      // console.log('Service Name:', service.servicename);
    });

    // const selectedServiceNames = this.selectedServices.map(s => s.servicename);
    // console.log('Selected Service Names:', selectedServiceNames);
  }

  onAmountChange(item: IBillingserviceoptionvalues, value: string | number): void {
    const normalizedValue = Number(value) || 0;
    item.value = normalizedValue;

    const selectedService = this.selectedServices.find(service => service.id === item.id);
    if (selectedService) {
      selectedService.value = normalizedValue;
    }

    const serviceEntry = this.serviceArray.find(service => service.optionid === (item.billingserviceoptionid ?? 0));
    if (serviceEntry) {
      serviceEntry.value = normalizedValue;
    }

    console.log(`Updated Amount for Service ID ${item.id}:`, item.value);
    this.calculateTotalCharges(); // Recalculate the total when the amount is changed
  }

  calculateTotalCharges(): void {
    this.totalServiceCharge = this.selectedServices.reduce((sum, service) => sum + (service.value || 0), 0);
    console.log('Total Service Charge:', this.totalServiceCharge);
  }

  loadDataFromOtherEntities() {
    this.vehiclebrandmodelService.query().subscribe((res: any) => {
      this.vehiclebrandmodel = res.body;
    });
  }

  loadDataFromBrandEntities() {
    this.vehiclebrandnameService.query().subscribe((res: any) => {
      this.vehiclebrandname = res.body;
    });
  }

  loadDataFromCommonServiceOptionEntities() {
    this.commonserviceoptionService.query().subscribe((res: any) => {
      this.commonserviceoption = res.body;
      this.syncSelectedCommonServicesFromSaved();
      this.cdr.detectChanges();
    });
  }
  // Define the new array with the given structure
  commonServiceArray: Array<{
    id?: number;
    invoiceid: number;
    lineid: number;
    optionid: number;
    mainid: number;
    code: string;
    name: string;
    description: string;
    value: number;
    addedbyid: number;
    discount: number;
    serviceprice: number;
  }> = [];

  selectedcommonServices: Array<ICommonserviceoption> = []; // Assuming this is defined elsewhere

  isCommonServiceSelected(service: ICommonserviceoption): boolean {
    const optionId = Number(service.id ?? 0);
    const name = (service.name ?? '').trim().toLowerCase();
    const code = (service.code ?? '').trim().toLowerCase();
    return (
      this.savedCommonServiceOptionIds.has(optionId) ||
      this.savedCommonServiceNames.has(name) ||
      this.savedCommonServiceCodes.has(code) ||
      this.selectedcommonServices.some(selected => Number(selected.id ?? 0) === optionId)
    );
  }

  onServiceSelectionChange(service: ICommonserviceoption, event: any) {
    if (event.target.checked) {
      // Add the selected item to the list with the required fields and default values
      const nextLineId = this.itemsArray.length > 0 ? Math.max(...this.itemsArray.map(item => item.lineid), 0) + 1 : 1;
      // Add service to selectedcommonServices if not already added
      this.selectedcommonServices.push(service);

      this.commonServiceArray.push({
        invoiceid: 0,
        lineid: nextLineId,
        optionid: service.id,
        mainid: 0,
        code: service.code || '',
        name: service.name || '',
        description: service.description || 'No description',
        value: service.value ?? 0,
        addedbyid: 0,
        discount: 0,
        serviceprice: 0,
      });
    } else {
      // Remove service from selectedcommonServices if unchecked
      this.selectedcommonServices = this.selectedcommonServices.filter(s => s.id !== service.id);

      // Remove service from commonServiceArray
      this.commonServiceArray = this.commonServiceArray.filter(s => s.optionid !== service.id);
    }

    console.log('Selected Common Services:', this.selectedcommonServices);
    console.log('Common Service Arrayyyy:', this.commonServiceArray);

    // Ensure total charge is recalculated
    this.calculateTotalCharge();
  }

  calculateTotalCharge() {
    this.totalcommonServiceCharge = this.selectedcommonServices.reduce((sum, service) => sum + (service.value || 0), 0);
    this.cdr.detectChanges(); // Ensure UI updates
  }

  loadDataFromServicesEntities() {
    this.servicecategoryService.query({ size: 1000, 'showsecurity.equals': true }).subscribe((res: any) => {
      this.servicecategory = res.body || [];
    });
  }

  // Get unique main categories from servicesubcategory
  getUniqueMainCategories(): string[] {
    if (!this.servicesubcategory) return [];
    const mainCategories = this.servicesubcategory.map(item => item.mainname).filter((name): name is string => name != null);
    return [...new Set(mainCategories)]; // Remove duplicates
  }

  // Get subcategories by main category
  getSubCategoriesByMainCategory(mainCategory: string): IServicesubcategory[] {
    if (!this.servicesubcategory) return [];
    return this.servicesubcategory.filter(item => item.mainname === mainCategory);
  }

  // Load servicesubcategory data
  loadDataFromServicessubEntities(): void {
    this.servicesubcategoryService.query({ size: 1000, 'isactive.equals': true }).subscribe((res: HttpResponse<IServicesubcategory[]>) => {
      this.servicesubcategory = res.body || [];
      this.syncSelectedSubcategoriesFromSaved();
      this.cdr.detectChanges();
    });
  }

  isSubcategorySelected(subcategory: IServicesubcategory): boolean {
    const subcategoryId = Number(subcategory.id ?? 0);
    const subcategoryName = (subcategory.name ?? '').trim().toLowerCase();
    return (
      this.savedSubcategoryIds.has(subcategoryId) ||
      this.savedSubcategoryNames.has(subcategoryName) ||
      this.selectedSubcategoryItems.some(item => Number(item.id ?? 0) === subcategoryId)
    );
  }

  onSubcategorySelectionChange(subcategory: IServicesubcategory, event: any): void {
    if (event.target.checked) {
      // Add the subcategory to the selected list
      if (!this.selectedSubcategoryItems.some(item => Number(item.id ?? 0) === Number(subcategory.id ?? 0))) {
        this.selectedSubcategoryItems.push(subcategory);
      }
    } else {
      // Remove the subcategory from the selected list
      this.selectedSubcategoryItems = this.selectedSubcategoryItems.filter(item => item.id !== subcategory.id);
    }
    console.log('Selected Subcategories:', this.selectedSubcategoryItems);
  }

  loadDataFromWorkshopWorklistEntities() {
    this.workshopworklistService.query({ size: 1000 }).subscribe((res: any) => {
      this.workshopworklist = res.body;
    });
  }

  onworkServiceSelectionChange(item: any, event: any): void {
    if (event.target.checked) {
      this.selectedworkItems.push(item);
    } else {
      this.selectedworkItems = this.selectedworkItems.filter(selectedworkItem => selectedworkItem !== item);
    }
  }

  filteredVehicles: ICustomervehicle[] = [];

  onVehicleSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const searchTerm = input.value;

    if (searchTerm.length > 2) {
      // Use the new service method to fetch matching results
      this.customervehicleService.findByVehicleNumber(searchTerm).subscribe(response => {
        this.filteredVehicles = this.getFirstCreatedVehicles(response.body || []);
      });
    } else {
      // Clear the suggestions if input is too short
      this.filteredVehicles = [];
    }
  }

  filtereditems: IInventory[] = [];
  searchItemsByCode = false;
  selectedItems: Array<IInventory & { discountPercentage: number; requestedQuantity: number }> = [];
  itemAddErrorMessage: string | null = null;

  onItemSearchModeChange(byCode: boolean): void {
    this.searchItemsByCode = byCode;
    this.filtereditems = [];
  }

  onItemSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const searchTerm = input.value;

    if (searchTerm.length > 1) {
      const search$ = this.searchItemsByCode ? this.inventoryService.findByCode(searchTerm) : this.inventoryService.findByItem(searchTerm);
      search$.subscribe(response => {
        this.filtereditems = response.body || [];
      });
    } else {
      this.filtereditems = [];
    }
  }
  itemsArray: Array<{
    id?: number;
    invocieid: number;
    lineid: number;
    itemid: number;
    itemcode: string;
    itemname: string;
    description: string;
    unitofmeasurement: string;
    quantity: number;
    itemcost: number;
    itemprice: number;
    discount: number;
    tax: number;
    sellingprice: number;
    linetotal: number;
    lmu: number;
    lmd: string;
    nbt: boolean;
    vat: boolean;
  }> = [];

  private loadExistingItemsForCurrentJob(): void {
    const jobId = this.editForm.controls.id.value;

    if (jobId == null || jobId <= 0) {
      this.loadedItemsInvoiceKey = null;
      this.persistedItemBatchKeys.clear();
      this.savedServiceOptionIds.clear();
      this.savedCommonServiceOptionIds.clear();
      this.savedSubcategoryIds.clear();
      this.savedSubcategoryNames.clear();
      return;
    }

    this.loadExistingServiceOptionSelections(jobId);

    this.autojobinvoice.query({ 'jobid.equals': jobId, page: 0, size: 1000 }).subscribe({
      next: (invoiceResponse: HttpResponse<IAutojobsinvoice[]>) => {
        const invoices = (invoiceResponse.body || []).filter(invoice => invoice.id != null);
        const invoiceIds = invoices.map(invoice => invoice.id!);
        const invoiceKey = invoiceIds
          .slice()
          .sort((left, right) => left - right)
          .join(',');

        if (invoiceIds.length === 0) {
          this.invoiceId = null;
          this.loadedItemsInvoiceKey = null;
          this.persistedItemBatchKeys.clear();
          this.savedServiceOptionIds.clear();
          this.savedCommonServiceOptionIds.clear();
          this.savedSubcategoryIds.clear();
          this.savedSubcategoryNames.clear();
          this.itemsArray = [];
          this.selectedItems = [];
          this.printSummaryFromRouteOnce();
          return;
        }

        const latestInvoice = invoices[invoices.length - 1];
        this.invoiceId = latestInvoice.id!;
        this.invoiceCode = latestInvoice.code || null;

        if (this.loadedItemsInvoiceKey === invoiceKey) {
          this.printSummaryFromRouteOnce();
          return;
        }

        this.loadedItemsInvoiceKey = invoiceKey;
        forkJoin(invoiceIds.map(invoiceId => this.jobinvoicelines.queryByInvoiceId(invoiceId))).subscribe({
          next: (lineResponses: HttpResponse<any[]>[]) => {
            const invoiceLines = lineResponses.flatMap(response => response.body || []);
            this.itemsArray = invoiceLines.map(line => this.mapInvoiceLineToItemsArray(line));
            this.selectedItems = invoiceLines.map(line => this.mapInvoiceLineToSelectedItem(line));
            this.updateItemTotal();

            this.jobinvoicebatches.queryByParentLineIds(invoiceIds).subscribe({
              next: batchResponse => {
                const batches = batchResponse.body || [];
                this.mergeSavedBatchCodesIntoItems(batches);
                this.persistedItemBatchKeys = new Set(batches.map(batch => this.buildItemBatchKey(batch)));
                this.loadExistingChargeSelections(invoiceIds);
              },
              error: (batchError: unknown) => {
                console.error('Failed to load existing job item batches:', batchError);
                this.persistedItemBatchKeys.clear();
                this.loadExistingChargeSelections(invoiceIds);
              },
            });
          },
          error: (error: unknown) => {
            console.error('Failed to load existing job items:', error);
            this.printSummaryFromRouteOnce();
          },
        });
      },
      error: (error: unknown) => {
        console.error('Failed to load auto job invoice for current job:', error);
      },
    });
  }

  private loadExistingChargeSelections(invoiceIds: number[]): void {
    if (invoiceIds.length === 0) {
      this.savedServiceOptionIds.clear();
      this.savedCommonServiceOptionIds.clear();
      this.savedServiceNames.clear();
      this.savedCommonServiceNames.clear();
      this.savedCommonServiceCodes.clear();
      return;
    }

    forkJoin({
      services: forkJoin(invoiceIds.map(invoiceId => this.jobservice.queryByInvoiceId(invoiceId))),
      commonServices: forkJoin(invoiceIds.map(invoiceId => this.jobcommon.queryByInvoiceId(invoiceId))),
    }).subscribe({
      next: ({ services, commonServices }) => {
        const savedServices = services.flatMap(response => response.body || []);
        const savedCommonServices = commonServices.flatMap(response => response.body || []);

        this.savedServiceValues.clear();
        savedServices.forEach(s => {
          const optId = Number(s.optionid ?? 0);
          if (optId > 0) {
            this.savedServiceValues.set(optId, s.value ?? 0);

            // Also ensure it's in serviceArray so edits can be saved
            if (!this.serviceArray.some(sa => sa.optionid === optId)) {
              this.serviceArray.push({
                id: s.id,
                invoiceid: s.invoiceid ?? 0,
                lineid: s.lineid ?? 0,
                optionid: optId,
                servicename: s.servicename ?? '',
                servicediscription: s.servicediscription ?? '',
                value: s.value ?? 0,
                addedbyid: s.addedbyid ?? 0,
                iscustomersrvice: s.iscustomersrvice ?? true,
                discount: s.discount ?? 0,
                serviceprice: s.serviceprice ?? 0,
              });
            }
          }
        });

        // Track saved common services
        savedCommonServices.forEach(s => {
          const optId = Number(s.optionid ?? 0);
          if (optId > 0) {
            if (!this.commonServiceArray.some(sa => sa.optionid === optId)) {
              this.commonServiceArray.push({
                id: s.id,
                invoiceid: s.invoiceid ?? 0,
                lineid: s.lineid ?? 0,
                optionid: optId,
                mainid: s.mainid ?? 0,
                code: s.code ?? '',
                name: s.name ?? '',
                description: s.description ?? '',
                value: s.value ?? 0,
                addedbyid: s.addedbyid ?? 0,
                discount: s.discount ?? 0,
                serviceprice: s.serviceprice ?? 0,
              });
            }
          }
        });

        this.savedServiceOptionIds = new Set(savedServices.map(service => Number(service.optionid ?? 0)).filter(optionId => optionId > 0));
        this.savedServiceNames = new Set(
          savedServices
            .map(service =>
              String(service.servicename ?? '')
                .trim()
                .toLowerCase(),
            )
            .filter(name => name.length > 0),
        );
        this.savedCommonServiceOptionIds = new Set(
          savedCommonServices.map(service => Number(service.optionid ?? 0)).filter(optionId => optionId > 0),
        );
        this.savedCommonServiceNames = new Set(
          savedCommonServices
            .map(service =>
              String(service.name ?? '')
                .trim()
                .toLowerCase(),
            )
            .filter(name => name.length > 0),
        );
        this.savedCommonServiceCodes = new Set(
          savedCommonServices
            .map(service =>
              String(service.code ?? '')
                .trim()
                .toLowerCase(),
            )
            .filter(code => code.length > 0),
        );

        this.syncSelectedServicesFromSaved();
        this.syncSelectedCommonServicesFromSaved();

        this.calculateTotalCharges();
        this.calculateTotalCharge();
        this.cdr.detectChanges();
        this.printSummaryFromRouteOnce();
      },
      error: (error: unknown) => {
        console.error('Failed to load existing charge selections:', error);
        this.printSummaryFromRouteOnce();
      },
    });
  }

  private printSummaryFromRouteOnce(): void {
    if (!this.shouldPrintOnLoad || this.hasPrintedOnLoad) {
      return;
    }

    this.hasPrintedOnLoad = true;
    this.cdr.detectChanges();
    setTimeout(() => this.printSummary(), 300);
  }

  private syncSelectedServicesFromSaved(): void {
    this.selectedServices = this.filteredBillingServiceOptionValues.filter(service => {
      const optionId = Number(service.billingserviceoptionid ?? 0);

      // Apply saved value if it exists
      if (this.savedServiceValues.has(optionId)) {
        service.value = this.savedServiceValues.get(optionId);
      }

      const serviceName = this.getBillingServiceOptionName(service.billingserviceoptionid).trim().toLowerCase();
      return this.savedServiceOptionIds.has(optionId) || this.savedServiceNames.has(serviceName);
    });
  }

  private syncSelectedCommonServicesFromSaved(): void {
    this.selectedcommonServices = this.commonserviceoption.filter(service => {
      const optionId = Number(service.id ?? 0);
      const name = (service.name ?? '').trim().toLowerCase();
      const code = (service.code ?? '').trim().toLowerCase();
      return (
        this.savedCommonServiceOptionIds.has(optionId) || this.savedCommonServiceNames.has(name) || this.savedCommonServiceCodes.has(code)
      );
    });
  }

  private loadExistingServiceOptionSelections(jobId: number): void {
    this.autocareJobServiceOptionService.queryByJobId(jobId).subscribe({
      next: (response: HttpResponse<IAutocareJobServiceOption[]>) => {
        const savedRows = response.body || [];

        this.savedSubcategoryIds = new Set(savedRows.map(row => Number(row.servicesubcategoryid ?? 0)).filter(id => id > 0));
        this.savedSubcategoryNames.clear();

        this.syncSelectedSubcategoriesFromSaved();
        this.cdr.detectChanges();
      },
      error: (error: unknown) => {
        console.error('Failed to load saved service option selections:', error);
        this.savedSubcategoryIds.clear();
        this.savedSubcategoryNames.clear();
      },
    });
  }

  private syncSelectedSubcategoriesFromSaved(): void {
    const savedSubcategories = this.servicesubcategory.filter(subcategory => {
      const subcategoryId = Number(subcategory.id ?? 0);
      return this.savedSubcategoryIds.has(subcategoryId);
    });

    // Merge: keep any manually-checked items that aren't in the saved set, plus all saved ones
    const manualSelections = this.selectedSubcategoryItems.filter(
      item => !savedSubcategories.some(subcategory => Number(subcategory.id ?? 0) === Number(item.id ?? 0)),
    );

    this.selectedSubcategoryItems = [...manualSelections, ...savedSubcategories];
  }

  private mapInvoiceLineToItemsArray(line: any): (typeof this.itemsArray)[number] {
    return {
      id: line.id ?? undefined,
      invocieid: line.invocieid ?? this.invoiceId ?? 0,
      lineid: line.lineid ?? 0,
      itemid: line.itemid ?? 0,
      itemcode: line.itemcode ?? '',
      itemname: line.itemname ?? '',
      description: line.description ?? '',
      unitofmeasurement: line.unitofmeasurement ?? '',
      quantity: line.quantity ?? 1,
      itemcost: line.itemcost ?? 0,
      itemprice: line.itemprice ?? 0,
      discount: line.discount ?? 0,
      tax: line.tax ?? 0,
      sellingprice: line.sellingprice ?? 0,
      linetotal: line.linetotal ?? 0,
      lmu: line.lmu ?? 0,
      lmd: line.lmd ? dayjs(line.lmd).toString() : '',
      nbt: line.nbt ?? false,
      vat: line.vat ?? false,
    };
  }

  private mapInvoiceLineToSelectedItem(line: any): IInventory & { discountPercentage: number; requestedQuantity: number } {
    return {
      id: line.itemid ?? 0,
      code: line.itemcode ?? '',
      name: line.itemname ?? '',
      description: line.description ?? '',
      unitofmeasurement: line.unitofmeasurement ?? '',
      availablequantity: null,
      lastsellingprice: line.sellingprice ?? 0,
      discountPercentage:
        line.sellingprice && line.quantity
          ? (Number(line.discount) * 100) / (Number(line.sellingprice) * Number(line.quantity))
          : line.discount ?? 0,
      requestedQuantity: line.quantity ?? 1,
    };
  }

  private mergeSavedBatchCodesIntoItems(batches: IAutojobsinvoicelinebatches[]): void {
    const batchCodeByLine = new Map<string, string>();

    batches.forEach(batch => {
      const code = (batch.code ?? '').trim();
      if (code.length === 0) {
        return;
      }

      batchCodeByLine.set(this.buildSavedBatchLineKey(batch), code);
    });

    this.itemsArray.forEach((itemLine, index) => {
      const savedCode = batchCodeByLine.get(this.buildSavedBatchLineKey(itemLine));
      if (!savedCode) {
        return;
      }

      itemLine.itemcode = savedCode;
      if (this.selectedItems[index]) {
        this.selectedItems[index].code = savedCode;
      }
    });

    this.cdr.detectChanges();
  }

  private buildSavedBatchLineKey(item: {
    id?: number | null;
    invocieid?: number | null;
    lineid?: number | null;
    itemid?: number | null;
  }): string {
    return `${item.invocieid ?? item.id ?? ''}|${item.lineid ?? ''}|${item.itemid ?? ''}`;
  }

  private syncItemsArrayFromSelection(): void {
    this.selectedItems.forEach((selectedItem, index) => {
      const itemLine = this.itemsArray[index];

      if (!itemLine) {
        return;
      }

      itemLine.quantity = selectedItem.requestedQuantity ?? 1;
      const price = selectedItem.lastsellingprice ?? 0;
      const discountPercentage = selectedItem.discountPercentage ?? 0;
      const quantity = selectedItem.requestedQuantity ?? 1;
      itemLine.discount = ((price * discountPercentage) / 100) * quantity;
      itemLine.itemprice = selectedItem.lastsellingprice ?? 0;
      itemLine.sellingprice = selectedItem.lastsellingprice ?? 0;
      itemLine.linetotal = this.calculateItemTotal(selectedItem);
    });
  }

  private buildItemBatchKey(item: any): string {
    return `${item.invocieid ?? item.id ?? ''}|${item.lineid ?? ''}|${item.itemid ?? ''}|${item.itemcode ?? item.code ?? ''}`;
  }

  private createInvoiceLineBatch(item: (typeof this.itemsArray)[number]): void {
    const parentInvoiceId = item.invocieid || item.id;

    if (parentInvoiceId == null || item.lineid == null || item.itemid == null) {
      console.error('Skipping invoice line batch creation because parent key is incomplete:', item);
      return;
    }

    const batchPayload: any = {
      id: parentInvoiceId,
      lineid: item.lineid,
      batchlineid: 0,
      itemid: item.itemid,
      code: item.itemcode,
      batchid: 0,
      batchcode: '',
      txdate: this.localNow(),
      manufacturedate: this.localNow(),
      expireddate: this.localNow(),
      qty: item.quantity ?? 1,
      cost: item.itemcost ?? 0,
      price: item.itemprice ?? item.sellingprice ?? 0,
      notes: item.description ?? '',
      lmu: item.lmu ?? 0,
      lmd: item.lmd ? this.localDate(item.lmd) : this.localNow(),
      nbt: item.nbt ?? false,
      vat: item.vat ?? false,
      discount: item.discount ?? 0,
      total: item.linetotal ?? 0,
      issued: false,
      issuedby: 0,
      issueddatetime: this.localNow(),
      addedbyid: 0,
      canceloptid: 0,
      cancelopt: '',
      cancelby: 0,
    };

    this.jobinvoicebatches.create(batchPayload).subscribe({
      next: () => {
        this.persistedItemBatchKeys.add(this.buildItemBatchKey(item));
      },
      error: createError => {
        console.error('Error creating invoice line batch:', createError);
      },
    });
  }

  onAddItem(): void {
    const inputValue = (document.getElementById('field_item') as HTMLInputElement).value;
    const selectedItem = this.filtereditems.find(item => (this.searchItemsByCode ? item.code === inputValue : item.name === inputValue));

    if (selectedItem) {
      if ((selectedItem.availablequantity ?? 0) <= 0) {
        this.itemAddErrorMessage = `Cannot add "${selectedItem.name}". Available quantity is zero.`;
        return;
      }

      const alreadyAdded = this.itemsArray.some(
        item =>
          (selectedItem.id != null && item.itemid === selectedItem.id) ||
          (!!selectedItem.code && item.itemcode?.toLowerCase() === selectedItem.code.toLowerCase()),
      );
      if (alreadyAdded) {
        this.itemAddErrorMessage = `"${selectedItem.name}" is already added to the list.`;
        return;
      }

      this.itemAddErrorMessage = null;
      // Add the selected item to the list with the required fields and default values
      const nextLineId = this.itemsArray.length > 0 ? Math.max(...this.itemsArray.map(item => item.lineid), 0) + 1 : 1;
      this.itemsArray.push({
        //id: 0,
        itemid: selectedItem.id,
        invocieid: 0,
        lineid: nextLineId,
        itemcode: selectedItem.code ?? '',
        itemname: selectedItem.name ?? '',
        description: selectedItem.description ?? '',
        unitofmeasurement: selectedItem.unitofmeasurement ?? '',
        quantity: 1,
        itemcost: 0,
        itemprice: 0,
        discount: 0,
        tax: 0,
        sellingprice: selectedItem.lastsellingprice ?? 0,
        linetotal: selectedItem.lastsellingprice ?? 0,
        lmu: selectedItem.lmu ?? 0,
        lmd: selectedItem.lmd ? selectedItem.lmd.toString() : '',
        nbt: false,
        vat: false,
      });
      this.selectedItems.push({
        ...selectedItem,
        discountPercentage: 0,
        requestedQuantity: 1,
      });
      // Clear the search input and suggestions
      (document.getElementById('field_item') as HTMLInputElement).value = '';
      this.filtereditems = [];
    } else {
      this.itemAddErrorMessage = null;
    }

    console.log('Selected Items Arrayyyyuuu:', this.itemsArray);
  }

  onDeleteItem(index: number): void {
    // Remove the item from the list
    this.selectedItems.splice(index, 1);
    this.itemsArray.splice(index, 1);
    this.updateItemTotal(); // Update totals after deletion
  }

  calculateItemTotal(item: IInventory & { discountPercentage: number; requestedQuantity: number }): number {
    const price = item.lastsellingprice || 0;
    const discountAmount = (price * item.discountPercentage) / 100;
    const discountedPrice = price - discountAmount;

    console.log(
      `Item Total: ${discountedPrice * item.requestedQuantity} (Price: ${price}, Discount: ${discountAmount}, Qty: ${item.requestedQuantity})`,
    );

    return discountedPrice * item.requestedQuantity;
  }

  calculateTotalWithoutDiscount(): number {
    return this.selectedItems.reduce((total, item) => total + (item.lastsellingprice || 0) * item.requestedQuantity, 0);
  }

  calculateTotalDiscount(): number {
    return this.selectedItems.reduce(
      (total, item) => total + ((item.lastsellingprice || 0) * item.discountPercentage * item.requestedQuantity) / 100,
      0,
    );
  }

  calculateTotalWithDiscount(): number {
    return this.calculateTotalWithoutDiscount() - this.calculateTotalDiscount();
  }

  // Update the item total when discount or requested quantity changes
  updateItemTotal(): void {
    this.syncItemsArrayFromSelection();

    // Recalculate totals
    this.calculateTotalWithoutDiscount();
    this.calculateTotalDiscount();
    this.calculateTotalWithDiscount();

    // Ensure Angular updates UI
    this.cdr.detectChanges();
  }

  searchedCustomer: ICustomer | null = null;

  onVehicleSelect(event: Event): void {
    const input = event.target as HTMLInputElement;
    const selectedVehicleNumber = input.value;

    const selectedVehicle = this.filteredVehicles.find(vehicle => vehicle.vehiclenumber === selectedVehicleNumber);

    if (selectedVehicle && selectedVehicle.customerid != null) {
      this.selectedVehicleTypeId = selectedVehicle.typeid ?? null;
      this.editForm.patchValue({
        vehicleid: selectedVehicle.id ?? null,
        vehicletypeid: selectedVehicle.typeid ?? null,
        customerid: selectedVehicle.customerid ?? null,
        vehiclenumber: selectedVehicle.vehiclenumber ?? '',
        nextmillage: selectedVehicle.nextmilage ? Number(selectedVehicle.nextmilage) : null,
        nextgearoilmilage: selectedVehicle.nextgearoilmilage ?? null,
      });
      this.filterBillingServiceOptionValues();

      this.customerService.find(selectedVehicle.customerid).subscribe(res => {
        this.searchedCustomer = res.body;
        console.log(this.searchedCustomer?.fullname);
        this.editForm.patchValue({
          customername: this.searchedCustomer?.fullname ?? '',
          customertel: this.searchedCustomer?.residencephone ?? '',
        });
      });
    } else {
      console.error('Invalid customer ID:', selectedVehicle);
      this.selectedVehicleTypeId = null;
      this.editForm.patchValue({
        vehicletypeid: null,
        customerid: null,
      });
      this.filterBillingServiceOptionValues();
    }
  }

  nextmillage: number | null = null;
  selectedRadioValue: number | null = null;
  nextMilageSelectionAttempted = false;
  saveAttempted = false;

  isRequiredInvalid(controlName: string): boolean {
    const control = this.editForm.get(controlName);
    return !!control && control.hasError('required') && (control.touched || this.saveAttempted);
  }

  isNextMilageInvalid(): boolean {
    const nextMillageControl = this.editForm.get('nextmillage');
    return (
      !!nextMillageControl &&
      nextMillageControl.hasError('required') &&
      (nextMillageControl.touched || this.nextMilageSelectionAttempted || this.saveAttempted)
    );
  }

  canNavigateToOtherTabs(): boolean {
    if (this.itemsOnlyMode) {
      return true;
    }
    return !this.editForm.get('nextmillage')?.invalid;
  }

  private markNextMilageAsRequired(): void {
    const nextMillageControl = this.editForm.get('nextmillage');
    this.nextMilageSelectionAttempted = true;
    nextMillageControl?.markAsTouched();
    nextMillageControl?.markAsDirty();
    nextMillageControl?.updateValueAndValidity();
    this.cdr.detectChanges();
  }

  private requireNextMillageForInstructions(): void {
    const nextMillageControl = this.editForm.get('nextmillage');
    nextMillageControl?.addValidators(Validators.required);
    nextMillageControl?.updateValueAndValidity();
  }

  private markRequiredFieldsForSave(): void {
    this.saveAttempted = true;
    this.nextMilageSelectionAttempted = true;
    this.editForm.markAllAsTouched();
    this.editForm.updateValueAndValidity();
    this.cdr.detectChanges();
  }

  private canSaveRequiredFields(): boolean {
    if (this.editForm.invalid) {
      this.markRequiredFieldsForSave();
      return false;
    }

    return true;
  }

  onTabNavigationAttempt(event: Event): void {
    if (this.itemsOnlyMode) {
      return;
    }
    if (this.editForm.get('nextmillage')?.invalid) {
      event.preventDefault();
      event.stopPropagation();
      this.markNextMilageAsRequired();
    }
  }

  // Handle changes in the millage input field
  onMillageChange(event: Event): void {
    const millage = +(event.target as HTMLInputElement).value;
    this.calculateNextMillage(millage ?? 0, this.selectedRadioValue);
  }

  // Handle changes in the radio button selection
  onRadioButtonChange(event: Event): void {
    const selectedValue = +(event.target as HTMLInputElement).value;
    this.selectedRadioValue = selectedValue;
    const millage = this.editForm.get('millage')?.value;
    this.calculateNextMillage(millage ?? 0, selectedValue);
    this.nextMilageSelectionAttempted = false;
    this.editForm.get('nextmillage')?.markAsTouched();
    this.editForm.get('nextmillage')?.updateValueAndValidity();
  }

  // Calculate the next millage value
  calculateNextMillage(millage: number, selectedValue: number | null): void {
    if (millage && selectedValue) {
      this.nextmillage = millage + selectedValue;
      this.editForm.get('nextmillage')?.setValue(this.nextmillage);
    } else {
      this.nextmillage = null;
      this.editForm.get('nextmillage')?.setValue(null);
    }
  }

  mapFormToAutojobsinvoice(formValue: any): IAutojobsinvoice {
    return {
      id: this.invoiceId || formValue.id || null,
      jobid: this.editForm.controls.id.value ?? 0,
      code: this.invoiceCode || formValue.code || '',
      quoteid: formValue.quoteid || null,
      orderid: formValue.orderid || 0,
      autojobsrepid: formValue.autojobsrepid || null,
      autojobsrepname: formValue.autojobsrepname || '',
      delieverfrom: formValue.delieverfrom || '',
      customerid: formValue.customerid,
      customername: formValue.customername || '',
      customeraddress: formValue.customeraddress || '',
      deliveryaddress: formValue.deliveryaddress || '',
      subtotal: (this.totalServiceCharge || 0) + (this.calculateTotalWithDiscount() || 0) + (this.totalcommonServiceCharge || 0),
      totaltax: formValue.totaltax || 0,
      totaldiscount: formValue.totaldiscount || 0,
      nettotal: (this.totalServiceCharge || 0) + (this.calculateTotalWithDiscount() || 0) + (this.totalcommonServiceCharge || 0),
      message: formValue.message || '',
      lmu: formValue.lmu || null,
      paidamount: formValue.paidamount || null,
      amountowing: (this.totalServiceCharge || 0) + (this.calculateTotalWithDiscount() || 0) + (this.totalcommonServiceCharge || 0),
      isactive: formValue.isactive || true,
      locationid: formValue.locationid || 0,
      locationcode: formValue.locationcode || '',
      referencecode: formValue.referencecode || '',
      createdbyid: formValue.createdbyid || null,
      createdbyname: formValue.createdbyname || '',
      autocarecompanyid: formValue.autocarecompanyid || null,
    };
  }

  mapFormTowork(formValue: any, vehicleBrand?: string, vehicleModel?: string): IWorkshopvehiclework {
    // jobid must always be Autocarejob.id — never from WorkshopWorkList
    const jobId = this.editForm.controls.id.value ?? formValue.id ?? null;
    const formRaw = this.editForm.getRawValue();

    return {
      id: formValue.id || null,
      jobid: jobId,
      vehicleid: formRaw.vehicleid ?? null,
      customerid: formRaw.customerid ?? null,
      customername: formRaw.customername ?? '',
      contactno: formRaw.customertel ?? '',
      vehicleno: formRaw.vehiclenumber ?? '',
      vehiclebrand: vehicleBrand ?? '',
      vehiclemodel: vehicleModel ?? '',
      mileage: String(formRaw.millage ?? ''),
      addeddate: this.localNow(),
      iscalltocustomer: false,
      remarks: '',
      calldate: null,
      lmu: this.currentUserId,
      lmd: this.localNow(),
    };
  }

  printSummary() {
    console.log('printSummary called, invoiceId:', this.invoiceId);
    if (!this.invoiceId) {
      alert('Please save the job first');
      return;
    }

    const customerId = this.editForm.controls.customerid.value;
    if (!this.searchedCustomer && customerId != null) {
      this.customerService.find(customerId).subscribe({
        next: res => {
          this.searchedCustomer = res.body;
          this.cdr.detectChanges();
          setTimeout(() => this.printSummary(), 0);
        },
        error: error => {
          console.error('Failed to load customer details for print summary:', error);
          this.openPrintSummaryWindow();
        },
      });
      return;
    }

    this.openPrintSummaryWindow();
  }

  private openPrintSummaryWindow(): void {
    const element = document.getElementById('printSummary');
    console.log('printSummary element found:', !!element);
    const printContents = element?.innerHTML;
    console.log('printSummary contents length:', printContents?.length ?? 0);
    if (!printContents || printContents.trim() === '') {
      console.error('Print section not found or empty!');
      return;
    }

    const printWindow = window.open('', '', 'width=800,height=900');
    if (printWindow) {
      printWindow.document.write(`
        <html>
        <head>
          <title>Print Summary</title>
          <style>
            body { font-family: Arial, sans-serif; padding: 20px; font-size: 10px; }
            .table { width: 100%; border-collapse: collapse; font-size: 10px;}
            .table th, .table td { border: 1px solid black; padding: 8px; text-align: left; }
            .text-center { text-align: center; }
            .text-right { text-align: right; }
            .d-flex { display: flex; justify-content: space-between; }
            .mt-3 { margin-top: 20px; }
            .border { border: 1px solid black; padding: 10px; }
            .print-button { margin-top: 20px; padding: 10px 20px; background-color: #007bff; color: white; border: none; cursor: pointer; }
            .close-button { margin-top: 20px; padding: 10px 20px; background-color: #dc3545; color: white; border: none; cursor: pointer; }
            .info-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  row-gap: 0rem;
  column-gap: 1.5rem;
}
            .print-item-description-grid {
              display: grid;
              grid-template-columns: 105px minmax(0, 1fr);
              column-gap: 6px;
              align-items: start;
            }

          </style>
        </head>
        <body onload="window.print();">
          ${printContents}
          <div style="text-align: center; margin-top: 20px;">
            <button class="print-button" onclick="window.print();">Print</button>
            <button class="close-button" onclick="window.close();">Close</button>
          </div>
        </body>
        </html>
      `);
      printWindow.document.close();
    } else {
      console.error('Failed to open print window. It might be blocked by the browser.');
    }
  }

  save(): void {
    if (!this.canSaveRequiredFields()) {
      return;
    }

    this.alertMuteService.mute();
    this.isSaving = true;
    let autocarejob = this.autocarejobFormService.getAutocarejob(this.editForm);

    autocarejob = { ...autocarejob, isadvisorchecked: true };

    if (autocarejob.id !== null) {
      this.subscribeToSaveResponse(this.autocarejobService.update(autocarejob));
    } else {
      this.subscribeToSaveResponse(this.autocarejobService.create(autocarejob));
    }
  }
  invoid: number = 0;

  /**
   * Called from the "Save Workshop Work Services" button in the Workshop Work
   * Service tab.  Saves only the workshop tab selections without touching the
   * advisor invoice or other tabs.
   * jobId = Autocarejob.id (must already be saved).
   */
  saveWorkshopTab(): void {
    const jobId = Number(this.editForm.controls.id.value ?? 0);
    if (jobId > 0 && this.workshopvehicleworkComponent) {
      this.workshopvehicleworkComponent.saveWorkshopWorkListDetails(jobId);
    }
  }

  saveAll(): void {
    if (!this.canSaveRequiredFields()) {
      return;
    }

    this.alertMuteService.mute();
    this.syncItemsArrayFromSelection();

    // Persist Workshop Work Service tab selections directly — keyed by jobId = Autocarejob.id
    const jobId = Number(this.editForm.controls.id.value ?? 0);
    if (jobId > 0 && this.workshopvehicleworkComponent) {
      this.workshopvehicleworkComponent.saveWorkshopWorkListDetails(jobId);
    }

    if (this.autojobsinvoiceComponent) {
      this.autojobsinvoiceComponent.save();
    }
    // Job and lines will be saved after invoice via onInvoiceSaved
  }

  goBackToOpenJobs(): void {
    this.router.navigate(['/autocarejob/autocareopenjob']);
  }

  saveItemsOnly(): void {
    this.syncItemsArrayFromSelection();

    if (this.invoiceId == null) {
      alert('No advisor invoice found for this job. Save instructions first.');
      return;
    }

    const effectiveInvoiceId = this.invoiceId;
    const itemsToSave = this.itemsArray.map(item => ({
      ...item,
      invocieid: effectiveInvoiceId,
    }));

    const itemsToCreate = itemsToSave.filter(item => item.id == null && item.itemid != null && typeof item.itemid === 'number');

    if (itemsToCreate.length === 0) {
      alert('Advisor instructions item list is updated.');
      return;
    }

    let completed = 0;
    let hasError = false;
    const totalOperations = itemsToCreate.length;

    const finishIfDone = (): void => {
      completed++;
      if (completed >= totalOperations && !hasError) {
        alert('Advisor instructions item list is updated.');
      }
    };

    itemsToCreate.forEach((item, index) => {
      setTimeout(() => {
        const itemWithDayjsLmd = {
          ...item,
          id: null,
          lmd: this.localDate(item.lmd),
        };

        this.jobinvoicelines.create(itemWithDayjsLmd).subscribe({
          next: createResponse => {
            const savedItem = createResponse.body;
            item.id = savedItem?.id ?? effectiveInvoiceId;
            item.invocieid = savedItem?.invocieid ?? effectiveInvoiceId;
            item.lineid = savedItem?.lineid ?? item.lineid;
            this.createInvoiceLineBatch(item);
            finishIfDone();
          },
          error: () => {
            hasError = true;
            alert('Failed to update advisor care instruction items.');
            finishIfDone();
          },
        });
      }, index * 500);
    });
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutocarejob>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        if (response.body) {
          const savedJobId = response.body.id;
          this.invoid = savedJobId;
          console.log('Saved job ID:', savedJobId);

          const effectiveInvoiceId = this.invoiceId;
          if (effectiveInvoiceId == null) {
            console.error('Invoice ID is missing. Skipping dependent line saves.');
            if (savedJobId != null) {
              this.persistWorkshopSelections(savedJobId);
            }
            this.onSaveSuccess();
            return;
          }

          this.commonServiceArray.forEach(service => {
            service.invoiceid = effectiveInvoiceId;

            if (service.id) {
              this.jobcommon.update(service as any).subscribe({
                next: updateResponse => {
                  console.log('Common Service updated successfully:', updateResponse);
                },
                error: updateError => {
                  console.error('Error updating common service:', updateError);
                },
              });
            } else {
              this.jobcommon.create({ ...service, id: null }).subscribe({
                next: createResponse => {
                  console.log('Serviceee created successfully:', createResponse);
                },
                error: createError => {
                  console.error('Error creating service:', createError.body);
                },
              });
            }
          });

          this.serviceArray.forEach(service => {
            service.invoiceid = effectiveInvoiceId;

            if (service.id) {
              this.jobservice.update(service as any).subscribe({
                next: updateResponse => {
                  console.log('Service updated successfully:', updateResponse);
                },
                error: updateError => {
                  console.error('Error updating service:', updateError);
                },
              });
            } else {
              this.jobservice.create({ ...service, id: null }).subscribe({
                next: createResponse => {
                  console.log('Serviceeeeesssssssssssw created successfully:', createResponse);
                },
                error: createError => {
                  console.error('Error creating service:', createError.body);
                },
              });
            }
          });

          // Loop through the itemsArray and update the invoiceId field for each item
          this.itemsArray.forEach((item, index) => {
            setTimeout(() => {
              if (item.id != null) {
                console.log(`Skipping existing item ${index + 1}:`, item);
                if (!this.persistedItemBatchKeys.has(this.buildItemBatchKey(item))) {
                  this.createInvoiceLineBatch(item);
                }
                return;
              }

              item.invocieid = effectiveInvoiceId;

              console.log(`POSTING ITEM ${index + 1}`);
              console.log('ITEMID VALUE:', item.itemid);
              console.log('ITEMID TYPE:', typeof item.itemid);
              console.log('FULL ITEM:', item);

              // 🚨 SAFETY CHECK (prevents FK crash)
              if (!item.itemid || typeof item.itemid !== 'number') {
                console.error('❌ INVALID ITEMID — SKIPPING ITEM', item);
                return;
              }

              const itemWithDayjsLmd = {
                ...item,
                id: null,
                lmd: this.localDate(item.lmd),
              };

              this.jobinvoicelines.create(itemWithDayjsLmd).subscribe({
                next: createResponse => {
                  const savedItem = createResponse.body;

                  item.id = savedItem?.id ?? effectiveInvoiceId;
                  item.invocieid = savedItem?.invocieid ?? effectiveInvoiceId;
                  item.lineid = savedItem?.lineid ?? item.lineid;

                  console.log(`Item ${index + 1} created:`, createResponse);
                  this.createInvoiceLineBatch(item);
                },
                error: createError => console.error(`Error for item ${index + 1}:`, createError),
              });
            }, index * 500); // 500ms delay per request
          });

          if (savedJobId != null) {
            this.persistServiceOptionSelections(savedJobId);
            this.persistWorkshopSelections(savedJobId);
          }

          // console.log('Updated itemsArray:', this.itemsArray);
          // console.log('arryyyyyyyyyyyyyyyyyyyyay service', this.serviceArray);
          this.onSaveSuccess();
        } else {
          console.error('Save response body is null');
        }
      },
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.alertService.addAlert({
      type: 'success',
      message: 'Job Saved Successfully',
      timeout: 5000,
    });

    this.showPrintSummary = true;
    this.cdr.detectChanges();

    // Scroll to the summary and trigger print with a delay to ensure DOM is ready and bypass popup blockers
    setTimeout(() => {
      const element = document.getElementById('printSummary');
      if (element) {
        element.scrollIntoView({ behavior: 'smooth', block: 'start' });
        this.printSummary(); // Trigger automatic print after save
      } else {
        console.warn('Print summary element not found in onSaveSuccess timeout, trying direct call');
        this.printSummary();
      }

      // Navigate to the open jobs list after a short delay to prevent double-saving/conflicts
      setTimeout(() => {
        this.router.navigate(['/autocarejob/autocareopenjob']);
      }, 1000);
    }, 800);

    // Unmute after some time to allow background saves to finish without alerts
    setTimeout(() => {
      this.alertMuteService.unmute();
    }, 10000); // 10 seconds should cover the background item saves
  }

  protected onSaveError(): void {
    this.alertMuteService.unmute();
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(autocarejob: IAutocarejob): void {
    this.autocarejob = autocarejob;
    this.autocarejobFormService.resetForm(this.editForm, autocarejob);
    this.syncVehicleTypeSelectionFromForm();
    this.loadCustomerDetailsForCurrentJob();
  }

  private loadCustomerDetailsForCurrentJob(): void {
    const customerId = this.editForm.controls.customerid.value;

    if (customerId == null) {
      this.searchedCustomer = null;
      return;
    }

    this.customerService.find(customerId).subscribe({
      next: res => {
        this.searchedCustomer = res.body;
        this.cdr.detectChanges();
      },
      error: error => {
        console.error('Failed to load customer details for current job:', error);
        this.searchedCustomer = null;
      },
    });
  }

  private getFirstCreatedVehicles(vehicles: ICustomervehicle[]): ICustomervehicle[] {
    const uniqueVehicles = new Map<string, ICustomervehicle>();

    [...vehicles]
      .sort((left, right) => (left.id ?? Number.MAX_SAFE_INTEGER) - (right.id ?? Number.MAX_SAFE_INTEGER))
      .forEach(vehicle => {
        const vehicleNumber = vehicle.vehiclenumber?.trim();

        if (vehicleNumber && !uniqueVehicles.has(vehicleNumber)) {
          uniqueVehicles.set(vehicleNumber, vehicle);
        }
      });

    return [...uniqueVehicles.values()];
  }

  private persistServiceOptionSelections(jobId: number): void {
    // Diff-based save: only create newly added, only delete removed — leave existing rows untouched
    this.autocareJobServiceOptionService.queryByJobId(jobId).subscribe({
      next: (response: HttpResponse<IAutocareJobServiceOption[]>) => {
        const existingRows = response.body || [];
        const existingSubcategoryIds = new Set(existingRows.map(row => Number(row.servicesubcategoryid ?? 0)).filter(id => id > 0));
        const desiredSubcategoryIds = new Set(this.selectedSubcategoryItems.map(item => Number(item.id ?? 0)).filter(id => id > 0));

        // Rows to DELETE: exist in DB but not in current selection
        const rowsToDelete = existingRows.filter(
          row => row.id != null && !desiredSubcategoryIds.has(Number(row.servicesubcategoryid ?? 0)),
        );

        // IDs to CREATE: in current selection but not in DB
        const idsToCreate = [...desiredSubcategoryIds].filter(id => !existingSubcategoryIds.has(id));

        // Delete removed rows
        rowsToDelete.forEach(row => {
          this.autocareJobServiceOptionService.delete(row.id).subscribe({
            next: () => {},
            error: err => console.error('Failed to delete service option row:', err),
          });
        });

        // Create new rows
        idsToCreate.forEach(subcategoryId => {
          const payload = {
            id: null,
            jobid: jobId,
            servicesubcategoryid: subcategoryId,
            pendding: true,
            ongoing: false,
            finished: false,
            lmu: 0,
            lmd: new Date().toISOString(),
            starttime: null,
            endtime: null,
          };
          this.autocareJobServiceOptionService.create(payload).subscribe({
            next: () => {},
            error: err => console.error('Failed to create service option row:', err),
          });
        });
      },
      error: err => {
        console.error('Failed to load existing service options for diff:', err);
      },
    });
  }

  private persistWorkshopSelections(jobId: number): void {
    const selectedWorkshopWorks = this.workshopvehicleworkComponent?.selectedworkItems || [];

    const desiredRows = [
      ...selectedWorkshopWorks.map(item => ({
        workid: Number(item.id ?? 0),
        workshopwork: String(item.workshopwork ?? '').trim(),
      })),
    ]
      .filter(item => item.workid > 0 || item.workshopwork.length > 0)
      .filter(
        (item, index, array) =>
          array.findIndex(
            candidate => candidate.workid === item.workid && candidate.workshopwork.toLowerCase() === item.workshopwork.toLowerCase(),
          ) === index,
      );

    if (desiredRows.length === 0) {
      return;
    }

    this.workshopvehicleworkService.queryByJobId(jobId).subscribe({
      next: (headerResponse: HttpResponse<IWorkshopvehiclework[]>) => {
        const existingHeader = (headerResponse.body || []).find(header => Number(header.id ?? 0) > 0);
        const vehicleId = this.editForm.controls.vehicleid?.value;

        // Helper: fetch vehicle then call callback with brand/model
        const withVehicleDetails = (callback: (brand: string, model: string) => void) => {
          if (vehicleId) {
            this.customervehicleService.find(vehicleId).subscribe({
              next: vRes => callback(vRes.body?.makename ?? '', vRes.body?.model ?? ''),
              error: () => callback('', ''),
            });
          } else {
            callback('', '');
          }
        };

        if (existingHeader?.id) {
          // Always update the existing header with current form values
          withVehicleDetails((brand, model) => {
            const formRaw = this.editForm.getRawValue();
            const updatedHeader: IWorkshopvehiclework = {
              ...existingHeader,
              vehicleid: formRaw.vehicleid ?? existingHeader.vehicleid,
              customerid: formRaw.customerid ?? existingHeader.customerid,
              customername: formRaw.customername ?? existingHeader.customername ?? '',
              contactno: formRaw.customertel ?? existingHeader.contactno ?? '',
              vehicleno: formRaw.vehiclenumber ?? existingHeader.vehicleno ?? '',
              vehiclebrand: brand || existingHeader.vehiclebrand || '',
              vehiclemodel: model || existingHeader.vehiclemodel || '',
              mileage: String(formRaw.millage ?? existingHeader.mileage ?? ''),
              addeddate: existingHeader.addeddate ?? this.localNow(),
              iscalltocustomer: false,
              lmu: this.currentUserId,
              lmd: this.localNow(),
            };
            this.workshopvehicleworkService.update(updatedHeader).subscribe({
              next: () => this.persistWorkshopDetailRows(existingHeader.id, desiredRows),
              error: () => this.persistWorkshopDetailRows(existingHeader.id, desiredRows),
            });
          });
          return;
        }

        // Create new header
        withVehicleDetails((brand, model) => {
          const workshopHeaderPayload = {
            ...this.mapFormTowork(this.editForm.getRawValue(), brand, model),
            id: null,
            jobid: jobId,
          };

          this.workshopvehicleworkService.create(workshopHeaderPayload).subscribe({
            next: createResponse => {
              const vehicleWorkId = Number(createResponse.body?.id ?? 0);
              if (vehicleWorkId > 0) {
                this.persistWorkshopDetailRows(vehicleWorkId, desiredRows);
              }
            },
            error: createError => {
              console.error('Failed to create workshop vehicle work header:', createError);
            },
          });
        });
      },
      error: headerError => {
        console.error('Failed to load workshop vehicle work header for save:', headerError);
      },
    });
  }

  private persistWorkshopDetailRows(vehicleWorkId: number, desiredRows: Array<{ workid: number; workshopwork: string }>): void {
    this.workshopVehicleWorkListService.queryByVehicleWorkIds([vehicleWorkId]).subscribe({
      next: (detailResponse: HttpResponse<IWorkshopVehicleWorkList[]>) => {
        const existingRows = detailResponse.body || [];

        // Delete all stale existing rows first, then recreate from desiredRows.
        // Filter out rows with a falsy id to avoid DELETE /undefined (400 Bad Request)
        // which can happen when the API fallback path returns unfiltered items.
        const validExistingRows = existingRows.filter(
          row => row.id != null && String(row.id).trim() !== '' && String(row.id) !== 'undefined',
        );
        const deleteOps = validExistingRows.map(row => this.workshopVehicleWorkListService.delete(row.id));

        const doCreate = () => {
          let lineId = 0;
          desiredRows.forEach(row => {
            lineId += 1;
            const detailPayload = {
              id: null,
              vehicleworkid: vehicleWorkId,
              lineid: lineId,
              workid: row.workid || null,
              workshopwork: row.workshopwork || '',
              isjobdone: false,
              jobdonedate: null,
              jobnumber: '',
              jobvalue: 0,
              estimatevalue: 0,
            };
            this.workshopVehicleWorkListService.create(detailPayload).subscribe({
              next: () => {},
              error: detailError => {
                console.error('Failed to create workshop vehicle work detail row:', detailError);
              },
            });
          });
        };

        if (deleteOps.length === 0) {
          doCreate();
        } else {
          forkJoin(deleteOps).subscribe({
            next: () => doCreate(),
            error: deleteError => {
              console.error('Failed to delete stale workshop detail rows:', deleteError);
              // Proceed with creation even if some deletes failed
              doCreate();
            },
          });
        }
      },
      error: detailError => {
        console.error('Failed to load existing workshop vehicle work detail rows:', detailError);
      },
    });
  }

  onInvoiceSaved(invoice: IAutojobsinvoice): void {
    this.invoiceId = invoice.id ?? null;
    this.invoiceCode = invoice.code ?? null;
    console.log('Invoice saved with ID:', this.invoiceId, 'and Code:', this.invoiceCode);
    // Save the job now that invoice is saved
    this.save();
  }
}
