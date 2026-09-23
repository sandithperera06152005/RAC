import { Component, inject, NgModule, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { forkJoin, Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IAutocareappointment } from '../autocareappointment.model';
import { AutocareappointmentService } from '../service/autocareappointment.service';
import { AutocareappointmentFormService, AutocareappointmentFormGroup } from './autocareappointment-form.service';
import { AutocareappointmenttypeService } from 'app/entities/autocareappointmenttype/service/autocareappointmenttype.service';
import { IAutocareappointmenttype } from 'app/entities/autocareappointmenttype/autocareappointmenttype.model';
import dayjs from 'dayjs/esm';
import { ICustomervehicle } from 'app/entities/customervehicle/customervehicle.model';
import { CustomervehicleService } from 'app/entities/customervehicle/service/customervehicle.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICustomer } from 'app/entities/customer/customer.model';
import { AutocarehoistService } from 'app/entities/autocarehoist/service/autocarehoist.service';
import { HoisttypeService } from 'app/entities/hoisttype/service/hoisttype.service';
import { AutocaretimetableService } from 'app/entities/autocaretimetable/service/autocaretimetable.service';
import { NgbAccordionCollapse, NgbAccordionHeader, NgbAccordionModule } from '@ng-bootstrap/ng-bootstrap';
import utc from 'dayjs/esm/plugin/utc';

import timezone from 'dayjs/esm/plugin/timezone';
dayjs.extend(utc);
dayjs.extend(timezone);
dayjs.extend(utc);

@Component({
  standalone: true,
  selector: 'jhi-autocareappointment-update',
  templateUrl: './autocareappointment-update.component.html',
  encapsulation: ViewEncapsulation.None,
  imports: [SharedModule, FormsModule, ReactiveFormsModule, NgbAccordionHeader, NgbAccordionCollapse],
  styles: [
    `
      .form-group {
        display: flex;
        align-items: center;
        gap: 1rem; /* Adjust spacing between label and input */
      }

      label {
        margin-bottom: 0; /* Remove bottom margin to align labels vertically */
      }

      input[type='checkbox'] {
        margin: 0; /* Remove default margin from checkboxes */
      }

      .mb-3 {
        margin-bottom: 1rem; /* Adjust spacing between groups */
      }

      table {
        border-collapse: collapse;
        width: 100%;
      }

      th,
      td {
        padding: 10px;
        border: 1px solid #ddd;
        text-align: center;
      }

      .autocare-slot-button {
        width: 100px;
        min-height: 42px;
        position: relative;
      }

      .autocare-slot-button:hover {
        background-color: #e1ae2e !important;
        color: white !important;
      }

      .autocare-slot-selected {
        background-color: #198754 !important;
        border: 2px solid #0f5132 !important;
        box-shadow: 0 0 0 0.2rem rgba(25, 135, 84, 0.25);
        color: white !important;
        font-weight: 700;
      }

      .autocare-slot-selected::after {
        content: 'Selected';
        display: block;
        font-size: 0.65rem;
        line-height: 1;
        margin-top: 2px;
        text-transform: uppercase;
      }
    `,
  ],
})
export class AutocareappointmentUpdateComponent implements OnInit {
  hideIsNoAnswer: boolean = true;
  isSaving = false;
  selectedTab: number = 0;
  autocareappointment: IAutocareappointment | null = null;
  autocareappointmenttypes: IAutocareappointmenttype[] = [];
  customervehicles: ICustomervehicle[] = [];
  customerDetails: any | null = null;
  selectedTime: string | null = null;
  selectedHoist: number | null = null;
  hoistData: any[] = [];
  hoistTypeData: any[] = [];
  timetableData: any[] = [];

  hoists: { id: number; name: string; times: string[] }[] = [];
  constructor(private fb: FormBuilder) {
    this.editForm = this.autocareappointmentFormService.createAutocareappointmentFormGroup();
  }
  protected autocareappointmentService = inject(AutocareappointmentService);
  protected autocareappointmentFormService = inject(AutocareappointmentFormService);
  protected activatedRoute = inject(ActivatedRoute);
  protected autocareappointmenttypeService = inject(AutocareappointmenttypeService);
  protected customervehicleService = inject(CustomervehicleService);
  protected customerService = inject(CustomerService);
  protected autocarehoistService = inject(AutocarehoistService);
  protected autocarehoisttypeService = inject(HoisttypeService);
  protected autocaretimetableService = inject(AutocaretimetableService);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: AutocareappointmentFormGroup = this.autocareappointmentFormService.createAutocareappointmentFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ autocareappointment }) => {
      this.autocareappointment = autocareappointment;
      if (autocareappointment) {
        this.updateForm(autocareappointment);
      }
      this.loadDataFromOtherEntities();
      // this.loadCustomerDetails();
      this.loadHoistAppointmentTime();
    });
  }
  openIndex: number | null = 0;

  openAccordion(index: number) {
    this.openIndex = index;
  }
  closeAccordion(index: number) {
    setTimeout(() => {
      if (this.openIndex === index) {
        this.openIndex = null;
      }
    }, 500);
  }
  getHoistsByType(hoistTypeId: number): any[] {
    return this.hoistData.filter(hoist => hoist.hoisttypeid === hoistTypeId);
  }

  getHoistTypeName(hoisttypeid: number): string | undefined {
    const hoistType = this.hoistTypeData.find(ht => ht.id === hoisttypeid);
    return hoistType ? hoistType.hoisttype : undefined;
  }

  // selectTime(timetable: any): void {
  //   const appointmentDate = this.editForm.get('appointmentdate')?.value;
  //   console.log('new appointment date : ', appointmentDate);
  //   const dd = dayjs('1900-01-01 08:00:00', 'YYYY-MM-DD HH:mm:ss');
  //   console.log('check static value', dd);
  //   if (appointmentDate && timetable.hoisttime) {
  //     const appointmentDateTime = dayjs(appointmentDate)
  //       .hour(dayjs(timetable.hoisttime).hour())
  //       .minute(dayjs(timetable.hoisttime).minute())
  //       .second(0)
  //       .format('YYYY-MM-DDTHH:mm');

  //     // const appointmentDateTime = dayjs(appointmentDate)

  //     console.log('new appointment time : ', appointmentDateTime);

  //     this.editForm.get('appointmenttime')?.patchValue(appointmentDateTime);

  //     this.selectedTime = timetable.hoisttime;
  //     this.selectedHoist = timetable.hoistid;
  //   }
  // }

  selectTime(timetable: any, hoistId: number): void {
    console.log('send timetable : ', timetable);
    const appointmentDate = this.editForm.get('appointmentdate')?.value;
    console.log('new appointment date : ', appointmentDate);

    if (appointmentDate && timetable.hoisttime) {
      // Convert hoisttime to UTC and combine with the appointment date
      const hoistTimeInUTC = dayjs(timetable.hoisttime, 'HH:mm:ss').utc();
      console.log('timatable.hoisttime before attachment : ', hoistTimeInUTC);

      // Convert the appointment date to Asia/Colombo timezone
      const appointmentDateTime = dayjs(appointmentDate)
        .hour(hoistTimeInUTC.hour())
        .minute(hoistTimeInUTC.minute())
        .second(0)
        .tz('Asia/Colombo', true) // Convert the time to the Asia/Colombo timezone
        .format('YYYY-MM-DDTHH:mm');

      console.log('new appointment time (Asia/Colombo) : ', appointmentDateTime);

      // Update the appointmenttime in the form
      this.editForm.get('appointmenttime')?.patchValue(appointmentDateTime);

      console.log('hoistid :', hoistId);
      this.editForm.get('hoistid')?.patchValue(hoistId);

      // Store the selected time and hoist for the selected-slot indicator.
      this.selectedTime = this.normalizeTimetableSlotTime(timetable.hoisttime);
      this.selectedHoist = hoistId;
    } else {
      alert('Please select an Appointment Date');
    }
  }

  loadDataFromOtherEntities() {
    this.autocareappointmenttypeService.query().subscribe((res: any) => {
      this.autocareappointmenttypes = res.body;
    });

    // for (let i = 0; i < 31; i++) {
    //   this.customervehicleService.query({ size: 2000, page: i }).subscribe((res: any) => {
    //     if (res.body) {
    //       this.customervehicles = this.customervehicles.concat(res.body); // Append results to the array
    //     }
    //   });
    // }
  }

  filteredVehicles: ICustomervehicle[] = [];

  onVehicleSearch(event: Event): void {
    const input = event.target as HTMLInputElement;
    const uppercasedValue = input.value.toUpperCase();
    if (input.value !== uppercasedValue) {
      input.value = uppercasedValue;
      this.editForm.get('vehiclenumber')?.setValue(uppercasedValue, { emitEvent: false });
    }
    const searchTerm = uppercasedValue;

    if (searchTerm.length > 2) {
      // Use the new service method to fetch matching results
      this.customervehicleService.findByVehicleNumber(searchTerm).subscribe(response => {
        this.filteredVehicles = response.body || [];
        console.log('Filtered vehicles:', this.filteredVehicles);
        // Update the vehicle ID field if there's any vehicle found
        if (this.filteredVehicles.length > 0) {
          this.editForm.get('vehicleid')?.patchValue(this.filteredVehicles[0].id); // Patching vehicle ID
        }
      });
    } else {
      // Clear the suggestions if input is too short
      this.filteredVehicles = [];
    }
  }

  searchedCustomer: ICustomer | null = null;

  onVehicleSelect(event: Event): void {
    const input = event.target as HTMLInputElement;
    const selectedVehicleNumber = input.value.toUpperCase();
    if (input.value !== selectedVehicleNumber) {
      input.value = selectedVehicleNumber;
      this.editForm.get('vehiclenumber')?.setValue(selectedVehicleNumber, { emitEvent: false });
    }

    // Find the selected vehicle from the filtered list
    const selectedVehicle = this.filteredVehicles.find(vehicle => vehicle.vehiclenumber === selectedVehicleNumber);

    if (selectedVehicle && selectedVehicle.customerid != null) {
      // Fetch the customer details if the vehicle has a valid customer ID
      this.customerService.find(selectedVehicle.customerid).subscribe(res => {
        this.searchedCustomer = res.body;
        console.log(this.searchedCustomer?.fullname);
        const storedUserId = localStorage.getItem('userId');
        const userIdNumber = parseInt(storedUserId!, 10); // Parse userId to number
        this.editForm.get('lmu')?.patchValue(userIdNumber);
        // Patch the form with the customer's details
        this.editForm.get('customername')?.patchValue(this.searchedCustomer?.fullname);
        this.editForm.get('contactnumber')?.patchValue(this.searchedCustomer?.residencephone);
        this.editForm.get('customerid')?.patchValue(this.searchedCustomer?.id);
      });
    } else {
      console.error('Invalid customer ID or vehicle not found:', selectedVehicle);
    }
  }

  onclickconfirmed(): void {
    console.log('onclickconfirmed method called'); // Log function call
    const isConfirmed = this.editForm.get('isconformed')?.value; // Access checkbox value
    console.log('isConfirmed value:', isConfirmed); // Log current value of isconformed

    if (isConfirmed) {
      const storedUserId = localStorage.getItem('userId'); // Retrieve userId from localStorage
      console.log('storedUserId value:', storedUserId); // Log userId value

      if (storedUserId) {
        const userIdNumber = parseInt(storedUserId, 10); // Parse userId to number
        this.editForm.get('conformedby')?.patchValue(userIdNumber); // Update form control
        console.log('Confirmed by user ID:', userIdNumber); // Log confirmation
      } else {
        console.error('No user ID found in local storage');
      }
    } else {
      console.log('isconformed is not ticked');
      this.editForm.get('conformedby')?.patchValue(null); // Clear the conformedby field if unchecked
    }
  }

  loadHoistAppointmentTime(): void {
    forkJoin({
      hoistTypes: this.autocarehoisttypeService.query(),
      hoists: this.autocarehoistService.query(),
      timetables: this.autocaretimetableService.query({ size: 2000, page: 0 }),
    }).subscribe(({ hoistTypes, hoists, timetables }) => {
      this.hoistTypeData = hoistTypes.body || [];
      this.hoistData = hoists.body || [];
      this.timetableData = timetables.body || [];

      console.log('Hoist Type : ', this.hoistTypeData);
      console.log('Hoists : ', this.hoistData);
      console.log('Time slots : ', this.timetableData);

      this.applySelectedSlotFromCurrentForm();
      this.openSelectedHoistType();
    });
  }

  isSelectedTimeSlot(timetable: any, hoistId: number): boolean {
    return this.selectedHoist === hoistId && this.selectedTime === this.normalizeTimetableSlotTime(timetable?.hoisttime);
  }

  private applySelectedSlotFromCurrentForm(): void {
    const savedAppointmentTime = this.editForm.get('appointmenttime')?.value;
    const savedHoistId = this.editForm.get('hoistid')?.value;

    if (!savedAppointmentTime || savedHoistId == null) {
      return;
    }

    this.selectedTime = this.normalizeAppointmentSlotTime(savedAppointmentTime);
    this.selectedHoist = Number(savedHoistId);
  }

  private openSelectedHoistType(): void {
    if (this.selectedHoist == null) {
      return;
    }

    const selectedHoist = this.hoistData.find(hoist => Number(hoist.id) === Number(this.selectedHoist));
    if (!selectedHoist) {
      return;
    }

    const selectedHoistTypeIndex = this.hoistTypeData.findIndex(hoistType => Number(hoistType.id) === Number(selectedHoist.hoisttypeid));
    if (selectedHoistTypeIndex >= 0) {
      this.openIndex = selectedHoistTypeIndex;
    }
  }

  private normalizeAppointmentSlotTime(value: any): string | null {
    if (!value) {
      return null;
    }

    if (typeof value === 'string') {
      const simpleTimeMatch = value.match(/(\d{2}):(\d{2})/);
      if (simpleTimeMatch) {
        return `${simpleTimeMatch[1]}:${simpleTimeMatch[2]}`;
      }
    }

    const parsed = dayjs(value);
    return parsed.isValid() ? parsed.format('HH:mm') : null;
  }

  private normalizeTimetableSlotTime(value: any): string | null {
    if (!value) {
      return null;
    }

    const parsed = dayjs(value);
    if (parsed.isValid()) {
      return parsed.utc().format('HH:mm');
    }

    if (typeof value === 'string') {
      const simpleTimeMatch = value.match(/(\d{2}):(\d{2})/);
      if (simpleTimeMatch) {
        return `${simpleTimeMatch[1]}:${simpleTimeMatch[2]}`;
      }
    }

    return null;
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const autocareappointment = this.autocareappointmentFormService.getAutocareappointment(this.editForm);
    const now = dayjs();
    const selectedAppointmentDate = autocareappointment.appointmentdate;
    autocareappointment.appointmentdate = selectedAppointmentDate
      ? selectedAppointmentDate.hour(now.hour()).minute(now.minute()).second(now.second()).millisecond(now.millisecond())
      : now;
    autocareappointment.conformdate = now;
    if (autocareappointment.id !== null) {
      autocareappointment.lmd = now;
      this.subscribeToSaveResponse(this.autocareappointmentService.update(autocareappointment));
    } else {
      autocareappointment.addeddate = now;
      autocareappointment.lmd = now;
      this.subscribeToSaveResponse(this.autocareappointmentService.create(autocareappointment));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAutocareappointment>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(autocareappointment: IAutocareappointment): void {
    this.autocareappointment = autocareappointment;
    this.autocareappointmentFormService.resetForm(this.editForm, autocareappointment);
    this.applySelectedSlotFromCurrentForm();
  }
}
