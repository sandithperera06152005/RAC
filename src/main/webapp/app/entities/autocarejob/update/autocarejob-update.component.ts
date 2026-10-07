import { Component, inject, OnInit, ViewChild } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';
import dayjs from 'dayjs/esm'; // Import Dayjs
import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FormatMediumDatetimePipe } from 'app/shared/date';
import { AutocarejobInstructionComponent } from './autocarejob-instruction.component';
import { IAutocarejob, NewAutocarejob } from '../autocarejob.model';
import { ICustomervehicle } from 'app/entities/customervehicle/customervehicle.model';
import { CustomervehicleService } from 'app/entities/customervehicle/service/customervehicle.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { AutocareappointmentService } from 'app/entities/autocareappointment/service/autocareappointment.service';
import { IAutocareappointment } from 'app/entities/autocareappointment/autocareappointment.model';
import { IServicecategory } from 'app/entities/servicecategory/servicecategory.model';
import { ServicecategoryService } from 'app/entities/servicecategory/service/servicecategory.service';
import { AutocarejobService } from '../service/autocarejob.service';
import { AutocarejobFormService, AutocarejobFormGroup } from './autocarejob-form.service';

@Component({
  standalone: true,
  selector: 'jhi-autocarejob-update',
  templateUrl: './autocarejob-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule, FormatMediumDatetimePipe, AutocarejobInstructionComponent],
})
export class AutocarejobUpdateComponent implements OnInit {
  isSaving = false;
  autocarejob: IAutocarejob | null = null;
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;
  autocareappointments: IAutocareappointment[] = [];
  serviceCategories: IServicecategory[] = [];
  selectedAppointmentForJob: IAutocareappointment | null = null;
  @ViewChild(AutocarejobInstructionComponent) autocarejobinstructionComponent!: AutocarejobInstructionComponent;
  protected autocarejobService = inject(AutocarejobService);
  protected autocarejobFormService = inject(AutocarejobFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected autocareappointmentService = inject(AutocareappointmentService);
  protected servicecategoryService = inject(ServicecategoryService);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocarejobFormGroup = this.autocarejobFormService.createAutocarejobFormGroup();
  saveAttempted = false;

  todayAppointments: IAutocareappointment[] = []; // New property for today's appointments
  isLoadingAppointments = false;

  isRequiredInvalid(controlName: string): boolean {
    const control = this.editForm.get(controlName);
    return !!control && control.hasError('required') && (control.touched || this.saveAttempted);
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autocarejob }) => {
      this.autocarejob = autocarejob;
      if (autocarejob) {
        this.updateForm(autocarejob);
      }
      this.loadAllAppointments();
      this.loadJobTypes();
    });
  }

  loadJobTypes(): void {
    this.servicecategoryService
      .query({
        size: 1000,
        sort: ['sortorder,asc', 'name,asc'],
        'showsecurity.equals': true,
      })
      .subscribe({
        next: (res: HttpResponse<IServicecategory[]>) => {
          this.serviceCategories = res.body || [];
          this.syncJobTypeNameFromSelectedId();
        },
        error: error => {
          console.error('Failed to load job types', error);
          this.serviceCategories = [];
        },
      });
  }

  loadAllAppointments(): void {
    this.isLoadingAppointments = true;
    const todayStart = dayjs().startOf('day');
    const tomorrowStart = todayStart.add(1, 'day');

    this.autocareappointmentService
      .query({
        page: 0,
        size: 100,
        sort: ['appointmentdate,asc', 'id,asc'],
        'appointmentdate.greaterThanOrEqual': todayStart.toJSON(),
        'appointmentdate.lessThan': tomorrowStart.toJSON(),
      })
      .pipe(finalize(() => (this.isLoadingAppointments = false)))
      .subscribe({
        next: (res: HttpResponse<IAutocareappointment[]>) => {
          this.todayAppointments = this.filterTodayAppointments(res.body || []);
        },
        error: error => {
          console.error('Failed to load appointments', error);
          this.todayAppointments = [];
        },
      });
  }
  jobType: string | null = null;
  customername: string | null = null;
  appointmentnum: number | null = null;
  vehicleNo: string | null = null;
  customerTel: string | null = null;
  loadAppointment(appointment: any): void {
    console.log('Loading appointment:', appointment);

    this.selectedAppointmentForJob = appointment;
    this.customerTel = appointment.contactnumber;
    this.vehicleNo = appointment.vehiclenumber;
    this.customername = appointment.customername;
    this.appointmentnum = appointment.appointmenttype;

    this.editForm.patchValue({
      vehiclenumber: appointment.vehiclenumber || '',
      customername: appointment.customername || '',
      customertel: appointment.contactnumber || '',
      customerid: appointment.customerid ?? null,
      vehicleid: appointment.vehicleid ?? null,
    });
    this.patchJobTypeFromId(appointment.appointmenttype ?? null);
  }

  jobTypeMap: { [key: number]: string } = {
    1: 'Full Service and Other Services',
    2: 'Detailing services',
    3: 'Performance Care',
    4: 'Other',
  };
  filterTodayAppointments(appointments: IAutocareappointment[]): IAutocareappointment[] {
    const today = dayjs().startOf('day'); // Get today's date at midnight
    console.log('Today:', today);

    return appointments.filter(appointment => {
      if (!appointment.appointmentdate || appointment.isarrived === true || appointment.iscancel === true || appointment.jobid != null) {
        return false; // Exclude unavailable appointments
      }
      const appointmentDate = dayjs(appointment.appointmentdate).startOf('day'); // Get appointment date at midnight
      return appointmentDate.isSame(today);
    });
  }

  previousState(): void {
    window.history.back();
  }

  filteredVehicles: IAutocareappointment[] = [];
  filteredCustomerVehicles: ICustomervehicle[] = [];
  filteredCustomers: ICustomer[] = [];

  onVehicleSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const uppercasedValue = input.value.toUpperCase();
    this.selectedAppointmentForJob = null;
    if (input.value !== uppercasedValue) {
      input.value = uppercasedValue;
      this.editForm.get('vehiclenumber')?.setValue(uppercasedValue, { emitEvent: false });
    }
    const searchTerm = uppercasedValue;

    if (searchTerm.length > 2) {
      this.autocareappointmentService.findByVehicleNumber(searchTerm).subscribe(response => {
        this.filteredVehicles = this.getFirstCreatedAppointments(response.body || []);
      });
      this.customervehicleService.findByVehicleNumber(searchTerm).subscribe(response => {
        this.filteredCustomerVehicles = this.getFirstCreatedCustomerVehicles(response.body || []);

        const exactVehicle = this.findCustomerVehicleByNumber(searchTerm);
        if (exactVehicle) {
          this.patchCustomerVehicleDetails(exactVehicle);
        }
      });
    } else {
      // Clear the suggestions if input is too short
      this.filteredVehicles = [];
      this.filteredCustomerVehicles = [];
    }
  }

  searchedCustomer: ICustomer | null = null;

  onCustomerSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const searchTerm = input.value.trim();

    this.editForm.patchValue({ customerid: null });
    this.customername = input.value;

    if (searchTerm.length > 2) {
      this.customerService.query({ 'fullname.contains': searchTerm, size: 20 }).subscribe(response => {
        this.filteredCustomers = response.body || [];
      });
    } else {
      this.filteredCustomers = [];
    }
  }

  onCustomerSelect(event: Event): void {
    const input = event.target as HTMLInputElement;
    const selectedCustomerName = input.value;
    const selectedCustomer = this.filteredCustomers.find(customer =>
      this.isSameCustomerName(this.getCustomerDisplayName(customer), selectedCustomerName),
    );

    if (!selectedCustomer) {
      return;
    }

    const customerName = this.getCustomerDisplayName(selectedCustomer);
    const customerTel = this.getCustomerTel(selectedCustomer);

    this.customername = customerName;
    this.customerTel = customerTel;
    this.searchedCustomer = selectedCustomer;
    this.editForm.patchValue({
      customerid: selectedCustomer.id,
      customername: customerName,
      customertel: customerTel,
    });
  }

  onVehicleSelect(event: Event): void {
    const input = event.target as HTMLInputElement;
    const selectedVehicleNumber = input.value;

    const selectedAppointment = this.filteredVehicles.find(vehicle => vehicle.vehiclenumber === selectedVehicleNumber);
    const selectedCustomerVehicle = this.findCustomerVehicleByNumber(selectedVehicleNumber);

    if (selectedAppointment) {
      console.log('Selected Vehicle:', selectedAppointment);
      this.selectedAppointmentForJob = selectedAppointment;

      this.editForm.patchValue({
        vehiclenumber: selectedAppointment.vehiclenumber || '',
        customername: selectedAppointment.customername || '',
        customertel: selectedAppointment.contactnumber || '',
        customerid: selectedAppointment.customerid ?? null,
        vehicleid: selectedAppointment.vehicleid ?? null,
      });
      this.patchJobTypeFromId(selectedAppointment.appointmenttype ?? null);

      this.customervehicleService.findByVehicleNumber(selectedVehicleNumber).subscribe(response => {
        const customerVehicleFromResponse =
          (response.body || []).find(vehicle => this.isSameVehicleNumber(vehicle.vehiclenumber, selectedVehicleNumber)) ??
          selectedCustomerVehicle;

        if (customerVehicleFromResponse) {
          this.patchCustomerVehicleDetails(customerVehicleFromResponse, selectedAppointment);
        } else {
          this.editForm.patchValue({
            vehicletypeid: null,
          });
          console.error('No matching customer vehicle found for:', selectedVehicleNumber);
        }
      });
    } else if (selectedCustomerVehicle) {
      this.selectedAppointmentForJob = null;
      this.patchCustomerVehicleDetails(selectedCustomerVehicle);
    } else {
      this.selectedAppointmentForJob = null;
      this.customervehicleService.findByVehicleNumber(selectedVehicleNumber).subscribe(response => {
        const customerVehicleFromResponse = (response.body || []).find(vehicle =>
          this.isSameVehicleNumber(vehicle.vehiclenumber, selectedVehicleNumber),
        );

        if (customerVehicleFromResponse) {
          this.patchCustomerVehicleDetails(customerVehicleFromResponse);
        } else {
          console.error('No matching vehicle found for:', selectedVehicleNumber);
        }
      });
    }
  }

  private patchCustomerVehicleDetails(customerVehicle: ICustomervehicle, selectedAppointment?: IAutocareappointment): void {
    this.editForm.patchValue({
      vehiclenumber: customerVehicle.vehiclenumber ?? selectedAppointment?.vehiclenumber ?? '',
      vehicleid: customerVehicle.id ?? selectedAppointment?.vehicleid ?? null,
      customerid: customerVehicle.customerid ?? selectedAppointment?.customerid ?? null,
      vehicletypeid: customerVehicle.typeid ?? null,
      nextgearoilmilage: customerVehicle.nextgearoilmilage ?? this.editForm.get('nextgearoilmilage')?.value,
    });

    if (customerVehicle.customerid) {
      this.customerService.find(customerVehicle.customerid).subscribe(response => {
        const customer = response.body;
        if (customer) {
          this.editForm.patchValue({
            customername: customer.fullname || customer.businessname || selectedAppointment?.customername || '',
            customertel:
              customer.residencephone ||
              customer.businessphone1 ||
              customer.businessphone2 ||
              customer.businessmobile ||
              selectedAppointment?.contactnumber ||
              '',
          });
        }
      });
    }
  }

  private findCustomerVehicleByNumber(vehicleNumber: string): ICustomervehicle | undefined {
    return this.filteredCustomerVehicles.find(vehicle => this.isSameVehicleNumber(vehicle.vehiclenumber, vehicleNumber));
  }

  get customerVehicleOptionsForDatalist(): ICustomervehicle[] {
    return this.filteredCustomerVehicles.filter(
      customerVehicle =>
        !!customerVehicle.vehiclenumber &&
        !this.filteredVehicles.some(appointment => this.isSameVehicleNumber(appointment.vehiclenumber, customerVehicle.vehiclenumber)),
    );
  }

  private isSameVehicleNumber(left: string | null | undefined, right: string | null | undefined): boolean {
    return (
      String(left ?? '')
        .trim()
        .toUpperCase() ===
      String(right ?? '')
        .trim()
        .toUpperCase()
    );
  }

  private getCustomerDisplayName(customer: ICustomer): string {
    return customer.fullname || customer.businessname || '';
  }

  private getCustomerTel(customer: ICustomer): string {
    return customer.residencephone || customer.businessphone1 || customer.businessphone2 || customer.businessmobile || '';
  }

  private isSameCustomerName(left: string | null | undefined, right: string | null | undefined): boolean {
    return (
      String(left ?? '')
        .trim()
        .toUpperCase() ===
      String(right ?? '')
        .trim()
        .toUpperCase()
    );
  }

  onJobTypeSelect(): void {
    this.syncJobTypeNameFromSelectedId();
  }

  getJobTypeDisplayName(jobTypeId: number | null | undefined): string {
    return this.findServiceCategoryById(jobTypeId)?.name || this.jobTypeMap[jobTypeId ?? 0] || '';
  }

  private patchJobTypeFromId(jobTypeId: number | null): void {
    const serviceCategory = this.findServiceCategoryById(jobTypeId);

    if (!serviceCategory) {
      this.editForm.patchValue({
        jobtypeid: null,
        jobtypename: null,
      });
      return;
    }

    this.editForm.patchValue({
      jobtypeid: serviceCategory.id,
      jobtypename: serviceCategory.name ?? '',
    });
  }

  private syncJobTypeNameFromSelectedId(): void {
    const jobTypeId = this.normalizeJobTypeId(this.editForm.get('jobtypeid')?.value);
    const serviceCategory = this.findServiceCategoryById(jobTypeId);

    this.editForm.patchValue({
      jobtypeid: serviceCategory?.id ?? jobTypeId,
      jobtypename: serviceCategory?.name ?? null,
    });
  }

  private findServiceCategoryById(jobTypeId: number | string | null | undefined): IServicecategory | undefined {
    const normalizedId = this.normalizeJobTypeId(jobTypeId);
    return normalizedId == null ? undefined : this.serviceCategories.find(serviceCategory => serviceCategory.id === normalizedId);
  }

  private normalizeJobTypeId(jobTypeId: number | string | null | undefined): number | null {
    if (jobTypeId === null || jobTypeId === undefined || jobTypeId === '') {
      return null;
    }

    const normalizedId = Number(jobTypeId);
    return Number.isNaN(normalizedId) ? null : normalizedId;
  }

  save(): void {
    if (this.isSaving) {
      return;
    }

    if (this.editForm.invalid) {
      this.saveAttempted = true;
      this.editForm.markAllAsTouched();
      return;
    }

    this.isSaving = true;
    const autocarejob = this.autocarejobFormService.getAutocarejob(this.editForm);

    // Ensure lookup-driven fields are included in the payload
    const selectedJobTypeId = this.normalizeJobTypeId(this.editForm.get('jobtypeid')?.value);
    const selectedJobType = this.findServiceCategoryById(selectedJobTypeId);
    autocarejob.jobtypeid = selectedJobType?.id ?? selectedJobTypeId;
    autocarejob.jobtypename = selectedJobType?.name ?? this.editForm.get('jobtypename')?.value;
    autocarejob.vehicleid = this.editForm.get('vehicleid')?.value;
    autocarejob.customerid = this.editForm.get('customerid')?.value;
    autocarejob.vehicletypeid = this.editForm.get('vehicletypeid')?.value;

    if (autocarejob.id !== null) {
      this.subscribeToSaveResponse(this.autocarejobService.update(autocarejob));
    } else {
      this.checkOngoingJobAndCreate(autocarejob);
    }
  }

  private checkOngoingJobAndCreate(autocarejob: NewAutocarejob): void {
    const vehicleNumber = autocarejob.vehiclenumber?.trim();
    const jobTypeId = autocarejob.jobtypeid;
    const jobDate = autocarejob.jobdate ?? dayjs();

    if (!vehicleNumber || jobTypeId == null) {
      this.subscribeToSaveResponse(this.autocarejobService.create(autocarejob));
      return;
    }

    this.autocarejobService.hasOngoingJob(vehicleNumber, jobTypeId, jobDate).subscribe({
      next: response => {
        if (response.body?.exists) {
          alert('This vehicle has an ongoing job.');
          this.isSaving = false;
          return;
        }

        this.subscribeToSaveResponse(this.autocarejobService.create(autocarejob));
      },
      error: error => {
        console.error('Failed to check ongoing job:', error);
        alert('Failed to check ongoing jobs. Please try again.');
        this.isSaving = false;
      },
    });
  }

  invid: number = 0;
  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutocarejob>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        console.log('Save Successfully:', response.body); // Log the response
        alert('Save Successful! Job ID: ' + response.body?.id); // Display an alert

        this.invid = response.body?.id ?? 0;
        console.log('Saved Job ID:', this.invid); // Log the response

        // Save as a number in local storage (stored as a string but retrieved as a number)
        localStorage.setItem('invid', JSON.stringify(this.invid)); // Use JSON.stringify to store properly

        // Retrieve from local storage and convert back to a number
        const storedInvid = JSON.parse(localStorage.getItem('invid') || '0'); // Use JSON.parse
        console.log('Retrieved from Local Storage (as number):', storedInvid);
        this.markSelectedAppointmentArrived(this.invid, () => this.onSaveSuccess());
      },
      error: error => {
        console.error('Save Failed:', error);
        this.onSaveError(error);
      },
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(error?: any): void {
    if (error?.error?.message === 'error.ongoingjobexists') {
      alert('This vehicle has an ongoing job.');
    }
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  private markSelectedAppointmentArrived(jobId: number, onComplete: () => void): void {
    if (!this.selectedAppointmentForJob?.id || !jobId) {
      onComplete();
      return;
    }

    this.autocareappointmentService
      .partialUpdate({
        id: this.selectedAppointmentForJob.id,
        isarrived: true,
        jobid: jobId,
      })
      .subscribe({
        next: () => onComplete(),
        error: error => {
          console.error('Failed to mark appointment as arrived:', error);
          onComplete();
        },
      });
  }

  protected updateForm(autocarejob: IAutocarejob): void {
    this.autocarejob = autocarejob;
    this.autocarejobFormService.resetForm(this.editForm, autocarejob);
  }

  private getFirstCreatedAppointments(appointments: IAutocareappointment[]): IAutocareappointment[] {
    const uniqueAppointments = new Map<string, IAutocareappointment>();

    [...appointments]
      .sort((left, right) => (left.id ?? Number.MAX_SAFE_INTEGER) - (right.id ?? Number.MAX_SAFE_INTEGER))
      .forEach(appointment => {
        const vehicleNumber = appointment.vehiclenumber?.trim();

        if (vehicleNumber && !uniqueAppointments.has(vehicleNumber)) {
          uniqueAppointments.set(vehicleNumber, appointment);
        }
      });

    return [...uniqueAppointments.values()];
  }

  private getFirstCreatedCustomerVehicles(customerVehicles: ICustomervehicle[]): ICustomervehicle[] {
    const uniqueCustomerVehicles = new Map<string, ICustomervehicle>();

    [...customerVehicles]
      .sort((left, right) => (left.id ?? Number.MAX_SAFE_INTEGER) - (right.id ?? Number.MAX_SAFE_INTEGER))
      .forEach(customerVehicle => {
        const vehicleNumber = customerVehicle.vehiclenumber?.trim();

        if (vehicleNumber && !uniqueCustomerVehicles.has(vehicleNumber)) {
          uniqueCustomerVehicles.set(vehicleNumber, customerVehicle);
        }
      });

    return [...uniqueCustomerVehicles.values()];
  }
}
