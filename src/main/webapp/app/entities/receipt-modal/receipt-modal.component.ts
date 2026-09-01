import { Component, Input, OnChanges, SimpleChanges, inject, input } from '@angular/core';
import { Router } from '@angular/router';
import { ReceiptpaymentsdetailsService } from '../receiptpaymentsdetails/service/receiptpaymentsdetails.service';
import {
  ReceiptpaymentsdetailsFormService,
  ReceiptpaymentsdetailsFormGroup,
} from 'app/entities/receiptpaymentsdetails/update/receiptpaymentsdetails-form.service';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { finalize } from 'rxjs/operators';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import SharedModule from 'app/shared/shared.module';
import { toWords } from 'number-to-words';
import { ReceiptService } from '../receipt/service/receipt.service';
import dayjs from 'dayjs/esm';
import { SalesinvoiceUpdateComponent } from '../salesinvoice/update/salesinvoice-update.component';
import { AutocarejobService } from '../autocarejob/service/autocarejob.service';
import { ReceiptLinesService } from '../receipt-lines/service/receipt-lines.service';
import { TransactionsService } from '../transactions/service/transactions.service';
import { SalesInvoiceLinesService } from '../sales-invoice-lines/service/sales-invoice-lines.service';
import { AccountsService } from '../accounts/service/accounts.service';
import { SalesinvoiceService } from '../salesinvoice/service/salesinvoice.service';
import { CustomerService } from 'app/entities/customer/service/customer.service';
import { ICompanybankaccount } from '../companybankaccount/companybankaccount.model';
import { CompanybankaccountService } from '../companybankaccount/service/companybankaccount.service';

declare const bootstrap: any;

@Component({
  selector: 'app-receipt-modal',
  standalone: true,
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  templateUrl: './receipt-modal.component.html',
  styleUrl: './receipt-modal.component.scss',
})
export class ReceiptModalComponent implements OnChanges {
  @Input() receiptpaymentsdetails: String | null = null;
  @Input() newcode: string | null = null;
  @Input() receiptdate: Date | null = null;
  @Input() totalamountinword: string | null = null;
  @Input() customername: string | null = null;
  @Input() totalamount: number = 0;
  @Input() customeraddress: string | null = null;
  @Input() comments: string | null = null;
  @Input() term: string | null = null;
  @Input() date: Date | null = null;
  @Input() amount: number = 0;
  @Input() vehicleno: string | null = null;
  @Input() checkdate: Date | null = null;
  @Input() checkno: string | null = null;
  @Input() bank: string | null = null;
  @Input() customerid: number = 0;
  @Input() isactive: boolean = true;
  @Input() deposited: boolean = true;
  @Input() createdby: number = 0;
  @Input() accountId: number = 0;
  @Input() accountCode: string = '';
  @Input() invoicecode: string | null = null;
  @Input() sharedSubId: string | null = null;
  subid: string = '';

  isSaving = false;
  field_input1: string = 'field_input1'; // Define this property here00
  selectedOption: number = 0;
  cashAmountStr: string = '0.00';
  chequeAmountStr: string = '0.00';
  cardAmountStr: string = '0.00';
  bankAmountStr: string = '0.00';
  chequeAmount: number = 0;
  cardAmount: number = 0;
  bankAmount: number = 0;
  banks: ICompanybankaccount[] = [];
  companyBankAccounts: ICompanybankaccount[] = [];
  bankbranch: ICompanybankaccount[] = [];
  selectedCompanyBankAccount: ICompanybankaccount | null = null;
  selectedCompanyBankAccountId: number | null = null;

  salesinvoiceupdate = inject(SalesinvoiceUpdateComponent);

  protected receiptpaymentsdetailsService = inject(ReceiptpaymentsdetailsService);
  protected receiptpaymentsdetailsFormService = inject(ReceiptpaymentsdetailsFormService);
  protected companybankaccountService = inject(CompanybankaccountService);
  reciptService = inject(ReceiptService);
  autocarejobService = inject(AutocarejobService);
  reciptlines = inject(ReceiptLinesService);
  paymentdetails = inject(ReceiptpaymentsdetailsService);
  customeraccid = inject(CustomerService);
  transtactions = inject(TransactionsService);
  acc = inject(AccountsService);
  salesInvoiceService = inject(SalesinvoiceService);
  invoicelines = inject(SalesInvoiceLinesService);
  router = inject(Router);

  nextvalue: string = '';
  newnextvalue: string = '';
  newlastvalue: string = '';
  items: any[] = [];
  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: ReceiptpaymentsdetailsFormGroup = this.receiptpaymentsdetailsFormService.createReceiptpaymentsdetailsFormGroup();

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['newcode'] && changes['newcode'].currentValue) {
      this.receipt.code = changes['newcode'].currentValue;
    }
    if (changes['receiptpaymentsdetails'] && changes['receiptpaymentsdetails'].currentValue) {
      console.log('Updated receiptpaymentsdetails:', changes['receiptpaymentsdetails'].currentValue);
      this.updateForm(this.receiptpaymentsdetails);
      this.loadCompanyBankAccounts();
    }
    if (changes['customername']) {
      const custName = changes['customername'].currentValue;
      if (custName && custName.trim().toUpperCase() === 'CASH') {
        this.onOptionChange(1);
      }
    }
  }

  // Log for debugging
  ngOnInit() {
    console.log('selectedOption:', this.selectedOption);
    this.fetchpaymentmethod();
    this.loadCompanyBankAccounts();
    if (this.customername && this.customername.trim().toUpperCase() === 'CASH') {
      this.onOptionChange(1);
    }
  }
  previousState(): void {
    window.history.back();
  }

  /** Returns current local time as a Dayjs that serializes to local time (not UTC) */
  private localNow(): dayjs.Dayjs {
    return dayjs().add(-new Date().getTimezoneOffset(), 'minute');
  }

  /** Takes a selected date and appends current time offset for local timezone */
  private localDateTime(d: any): dayjs.Dayjs {
    if (!d) return this.localNow();
    const now = dayjs();
    return dayjs(d).hour(now.hour()).minute(now.minute()).second(now.second()).add(-new Date().getTimezoneOffset(), 'minute');
  }

  loadCompanyBankAccounts(): void {
    this.companybankaccountService.query({ size: 1000 }).subscribe((res: HttpResponse<ICompanybankaccount[]>) => {
      this.companyBankAccounts = res.body || [];
      this.banks = this.companyBankAccounts.filter(account => !!account.bankname);
      this.loadBankBranch();
    });
  }

  accountsId: number = 0;

  fetchacc(): void {
    if (this.customername) {
      this.salesInvoiceService.fetchReceiptAccountId(this.customername).subscribe(resAcc => {
        const accounts = resAcc.body;
        if (accounts && accounts.length > 0) {
          const account = accounts[0];
          this.accountCode = account.code;
          this.transaction.accountCode = account.code;
          this.transaction.accountId = account.id;
          this.accountsId = account.id;
        }
      });
    }
  }

  paymentType: string = '';
  finalcommisonamount: number = 0;

  onpaymentOptionChange(option: string): void {
    this.paymentType = option;
    console.log('Payment Option Changed:', this.paymentType);
    console.log('Total Amount:', this.totalamount);

    const calculateCommission = () => {
      let commissionRate = 0;
      const selected = this.items.find(item => {
        const name = (item.paymentMethodName || '').toLowerCase();
        if (this.paymentType === 'visa' && (name.includes('visa') || name.includes('master'))) {
          return true;
        }
        if (this.paymentType === 'amex' && name.includes('amex')) {
          return true;
        }
        if (this.paymentType === 'paypal' && (name.includes('qr') || name.includes('paypal'))) {
          return true;
        }
        return false;
      });

      if (selected) {
        commissionRate = selected.commission || 0;
      }
      this.finalcommisonamount = (this.totalamount * commissionRate) / 100;
      console.log('Commission Rate calculated:', commissionRate);
      console.log('Final Commission Amount:', this.finalcommisonamount);
    };

    if (!this.items || this.items.length === 0) {
      this.fetchpaymentmethod(() => {
        calculateCommission();
      });
    } else {
      calculateCommission();
    }
  }

  fetchpaymentmethod(callback?: () => void): void {
    // Mocked payment methods since PaymentMethodService is missing in this project
    this.items = [
      { id: 1, paymentMethodName: 'Amex', commission: 2.5 },
      { id: 2, paymentMethodName: 'QR/PayPal', commission: 0.0 },
      { id: 3, paymentMethodName: 'Visa/Master', commission: 2.5 },
    ];
    console.log('Mocked payment methods:', this.items);
    if (callback) {
      callback();
    }
  }

  account = {
    id: null,
    code: '',
    date: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
    name: '',
    description: '',
    type: 0,
    parent: 0,
    balance: 0,
    lmu: 0,
    lmd: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
    hasbatches: null as boolean | null,
    accountvalue: 0,
    accountlevel: 0,
    accountsnumberingsystem: 0,
    subparentid: 0,
    canedit: null as boolean | null,
    amount: 0,
    creditamount: 0,
    debitamount: 0,
    debitorcredit: '',
    reporttype: 0,
  };

  transaction = {
    id: null,
    accountId: 0,
    accountCode: '',
    debit: 0,
    credit: 0,
    date: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
    refDoc: '',
    refId: 0,
    subId: '',
    source: 'invoice',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
  };

  reciptnocustransaction = {
    id: null,
    accountId: 0,
    accountCode: '',
    debit: 0,
    credit: 0,
    date: this.localNow(),
    refDoc: '',
    refId: 0,
    subId: '',
    source: 'Receipt',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: this.localNow(),
  };

  reciptnocustransactions(recid: number, reccode: String, subid: string, termid: number, termname: string, paymentAmount: number): void {
    this.reciptnocustransaction.refId = recid;
    this.reciptnocustransaction.subId = subid;
    this.reciptnocustransaction.refDoc = reccode ? reccode.toString() : '';
    this.reciptnocustransaction.debit = 0;
    this.reciptnocustransaction.credit = paymentAmount;
    this.reciptnocustransaction.paymentTermId = termid;
    this.reciptnocustransaction.paymentTermName = termname;

    const custName = this.customername || 'CASH';
    this.salesInvoiceService.fetchReceiptAccountId(custName).subscribe(resAcc => {
      const accounts = resAcc.body;
      if (accounts && accounts.length > 0) {
        const account = accounts[0];
        this.reciptnocustransaction.accountId = account.id;
        this.reciptnocustransaction.accountCode = account.code;
      } else {
        this.reciptnocustransaction.accountId = this.transaction.accountId;
        this.reciptnocustransaction.accountCode = this.transaction.accountCode;
      }
      this.transtactions.create(this.reciptnocustransaction as any).subscribe();
    });
  }

  reciptnocustomerupdate(accountid: number): void {
    this.acc.query({ 'id.equals': accountid }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        const account = accounts[0];
        if (account) {
          const updatedAmount = Number(account.amount || 0) - this.totalamount;
          const updatedCredit = Number(account.debitamount || 0) + this.totalamount;
          this.acc.partialUpdate({ id: this.accountId, debitamount: updatedCredit, amount: updatedAmount }).subscribe();
        }
      },
    });
  }

  receiptmainacctransaction = {
    id: null,
    accountId: 33,
    accountCode: '42',
    debit: 0,
    credit: 0,
    date: this.localNow(),
    refDoc: '',
    refId: 0,
    subId: '',
    source: 'Receipt',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: this.localNow(),
  };

  receiptmainacctransactions(recid: number, reccode: String, subid: string, totalrecived: number): void {
    this.receiptmainacctransaction.refId = recid;
    this.receiptmainacctransaction.subId = subid;
    this.receiptmainacctransaction.refDoc = reccode ? reccode.toString() : '';
    this.receiptmainacctransaction.debit = totalrecived;
    this.receiptmainacctransaction.credit = 0;
    this.receiptmainacctransaction.accountId = this.accountId;
    this.receiptmainacctransaction.accountCode = this.accountCode;
    this.transtactions.create(this.receiptmainacctransaction as any).subscribe();
  }

  receipttransaction = {
    id: null,
    accountId: 33,
    accountCode: '42',
    debit: 0,
    credit: 0,
    date: this.localNow(),
    refDoc: '',
    refId: 0,
    subId: '',
    source: 'Receipt',
    paymentTermId: 0,
    paymentTermName: '',
    lmu: 0,
    lmd: this.localNow(),
  };

  receipttransactions(recid: number, reccode: String, subid: string, termid: number, termname: string, paymentAmount: number): void {
    this.receipttransaction.refId = recid;
    this.receipttransaction.subId = subid;
    this.receipttransaction.refDoc = reccode ? reccode.toString() : '';
    this.receipttransaction.debit = 0;
    this.receipttransaction.credit = paymentAmount;

    if (this.customername) {
      this.salesInvoiceService.fetchReceiptAccountId(this.customername).subscribe(resAcc => {
        const accounts = resAcc.body;
        if (accounts && accounts.length > 0) {
          const account = accounts[0];
          this.receipttransaction.accountId = account.id;
          this.receipttransaction.accountCode = account.code;
        } else {
          this.receipttransaction.accountId = this.transaction.accountId;
          this.receipttransaction.accountCode = this.transaction.accountCode;
        }
        this.transtactions.create(this.receipttransaction as any).subscribe();
      });
    } else {
      this.receipttransaction.accountId = this.transaction.accountId;
      this.receipttransaction.accountCode = this.transaction.accountCode;
      this.transtactions.create(this.receipttransaction as any).subscribe();
    }
  }

  updaterecipttransactionwithcustomer(): void {
    this.acc.query({ 'id.equals': this.accountId }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        const account = accounts[0];
        if (account) {
          const updatedAmount = Number(account.amount || 0) - this.totalamount;
          const updatedCredit = Number(account.creditamount || 0) + this.totalamount;
          this.acc.partialUpdate({ id: this.accountId, creditamount: updatedCredit, amount: updatedAmount }).subscribe();
        }
      },
    });
  }

  accountmethod(name: string): void {
    let queryName = name;
    if (name == 'bankdeposit') queryName = 'Current Assets';
    this.acc.query({ 'name.contains': queryName }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts: any[] = res.body || [];
        if (accounts.length > 0) {
          this.accountId = accounts[0].id;
          this.accountCode = accounts[0].code;
        }
      },
    });
  }

  updatecustomermain(amountrec: number): void {
    this.acc.query({ 'id.equals': 7 }).subscribe({
      next: (res: HttpResponse<any[]>) => {
        const accounts = res.body || [];
        const account = accounts[0];
        if (account) {
          const updatedAmount = Number(account.amount || 0) - amountrec;
          const updatedCredit = Number(account.debitamount || 0) + amountrec;
          this.acc.partialUpdate({ id: 7, debitamount: updatedCredit, amount: updatedAmount }).subscribe();
        }
      },
    });
  }

  incrementId(id: string): string {
    const match = id.match(/^([A-Za-z]+)(\d+)$/);
    if (!match) return id;
    const prefix = match[1];
    const number = parseInt(match[2], 10) + 1;
    return `${prefix}${number}`;
  }

  cash: number = 0;
  balance: number = 0;

  onCashInput(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    let value = inputElement.value;

    // Only allow numbers and one decimal point
    value = value.replace(/[^0-9.]/g, '');
    const parts = value.split('.');
    if (parts.length > 2) {
      value = parts[0] + '.' + parts.slice(1).join('');
    }
    inputElement.value = value;
    this.cashAmountStr = value;

    this.cash = parseFloat(value) || 0;
    this.balance = Number((Number(this.totalamount || 0) - Number(this.cash || 0)).toFixed(2));
  }

  onCashBlur(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    let val = parseFloat(inputElement.value);
    if (isNaN(val)) val = 0;
    this.cashAmountStr = val.toFixed(2);
    inputElement.value = this.cashAmountStr;
    this.cash = val;
    this.balance = Number((Number(this.totalamount || 0) - Number(this.cash || 0)).toFixed(2));
  }

  onCashFocus(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    if (parseFloat(inputElement.value) === 0) {
      this.cashAmountStr = '';
      inputElement.value = '';
    }
  }

  onChequeInput(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    let value = inputElement.value;

    // Only allow numbers and one decimal point
    value = value.replace(/[^0-9.]/g, '');
    const parts = value.split('.');
    if (parts.length > 2) {
      value = parts[0] + '.' + parts.slice(1).join('');
    }
    inputElement.value = value;
    this.chequeAmountStr = value;
    this.chequeAmount = parseFloat(value) || 0;
  }

  onChequeBlur(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    let val = parseFloat(inputElement.value);
    if (isNaN(val)) val = 0;
    this.chequeAmountStr = val.toFixed(2);
    inputElement.value = this.chequeAmountStr;
    this.chequeAmount = val;
  }

  onChequeFocus(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    if (parseFloat(inputElement.value) === 0) {
      this.chequeAmountStr = '';
      inputElement.value = '';
    }
  }

  onCardInput(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    const value = this.normalizeAmountInput(inputElement.value);
    inputElement.value = value;
    this.cardAmountStr = value;
    this.cardAmount = parseFloat(value) || 0;
  }

  onCardBlur(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    let val = parseFloat(inputElement.value);
    if (isNaN(val)) val = 0;
    this.cardAmountStr = val.toFixed(2);
    inputElement.value = this.cardAmountStr;
    this.cardAmount = val;
  }

  onCardFocus(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    if (parseFloat(inputElement.value) === 0) {
      this.cardAmountStr = '';
      inputElement.value = '';
    }
  }

  onBankAmountInput(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    const value = this.normalizeAmountInput(inputElement.value);
    inputElement.value = value;
    this.bankAmountStr = value;
    this.bankAmount = parseFloat(value) || 0;
  }

  onBankAmountBlur(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    let val = parseFloat(inputElement.value);
    if (isNaN(val)) val = 0;
    this.bankAmountStr = val.toFixed(2);
    inputElement.value = this.bankAmountStr;
    this.bankAmount = val;
  }

  onBankAmountFocus(event: Event): void {
    const inputElement = <HTMLInputElement>event.target;
    if (parseFloat(inputElement.value) === 0) {
      this.bankAmountStr = '';
      inputElement.value = '';
    }
  }

  private normalizeAmountInput(value: string): string {
    value = value.replace(/[^0-9.]/g, '');
    const parts = value.split('.');
    if (parts.length > 2) {
      value = parts[0] + '.' + parts.slice(1).join('');
    }
    return value;
  }

  bankid: number = 0;
  bankname: string = '';

  onItemBankInput($event: Event): void {
    const selectedBank = ($event.target as HTMLSelectElement).value;

    if (!selectedBank) {
      console.log('No bank selected');
      this.bankbranch = [];
      this.selectedCompanyBankAccount = null;
      this.selectedCompanyBankAccountId = null;
      return;
    }

    const selectedObject = this.banks.find(bank => String(bank.id) === selectedBank);

    if (selectedObject) {
      this.bankid = Number(selectedObject.bankid ?? 0);
      this.bankname = selectedObject.bankname ? selectedObject.bankname.toString() : '';
      this.bank = this.bankname; // sync with input property
      this.selectedCompanyBankAccount = selectedObject;
      this.selectedCompanyBankAccountId = selectedObject.id;
      this.Branch = selectedObject.branchname ?? '';
      this.branchid = Number(selectedObject.branchid ?? 0);
      this.applySelectedCompanyBankAccount(selectedObject);

      this.bankbranch = [selectedObject];
      console.log('Bank Branches:', this.bankbranch);
    } else {
      console.log('Selected bank not found in the list');
      this.bankbranch = [];
      this.selectedCompanyBankAccount = null;
      this.selectedCompanyBankAccountId = null;
    }
  }

  Branch: string = '';
  branchid: number = 0;

  onItemChequebranchInput(event: Event): void {
    const selectedBranchId = (event.target as HTMLSelectElement).value;
    const selectedBranch = this.bankbranch.find(branch => String(branch.id) === selectedBranchId);
    if (selectedBranch) {
      this.Branch = selectedBranch.branchname ?? '';
      this.branchid = Number(selectedBranch.branchid ?? 0);
      this.selectedCompanyBankAccount = selectedBranch;
      this.applySelectedCompanyBankAccount(selectedBranch);
    } else {
      this.Branch = '';
      this.branchid = 0;
    }
    console.log('Selected Branch:', this.Branch, 'ID:', this.branchid);
  }

  loadBankBranch(): void {
    if (this.selectedCompanyBankAccount) {
      this.bankbranch = [this.selectedCompanyBankAccount];
      return;
    }
    if (!this.bank) {
      this.bankbranch = [];
      return;
    }
    const selectedObject = this.banks.find(bank => bank.bankname === this.bank);
    if (selectedObject) {
      this.bankbranch = [selectedObject];
    } else {
      this.bankbranch = [];
    }
  }

  private applySelectedCompanyBankAccount(companyBankAccount: ICompanybankaccount): void {
    this.accountId = Number(companyBankAccount.accountid ?? 0);
    this.accountCode = companyBankAccount.accountcode ?? '';
  }
  receipt = {
    code: 'string',
    receiptdate: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
    customername: 'string',
    customeraddress: 'string',
    totalamount: 0,
    totalamountinword: 'string',
    comments: 'string',
    lmu: 0,
    lmd: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
    termid: 0,
    term: 'string',
    date: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
    amount: 0,
    checkdate: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
    checkno: 'string',
    bank: 'string',
    customerid: 0,
    isactive: true,
    deposited: true,
    createdby: 0,
    vehicleno: 'string',
    id: null as number | null,
  };

  receiptlines = {
    id: 0,
    lineid: 1,
    invoicecode: 'string',
    invoicetype: 'string',
    originalamount: 0,
    amountowing: 0,
    discountavailable: 0,
    discounttaken: 0,
    amountreceived: 0,
    lmu: 0,
    lmd: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
    accountid: 0,
  };

  receiptPaymentDetail = {
    id: 0,
    lineid: 0,
    paymentamount: 0,
    totalreceiptamount: null,
    checkqueamount: 0,
    checkqueno: '',
    checkquedate: null as dayjs.Dayjs | null,
    checkqueexpiredate: null as dayjs.Dayjs | null,
    bankname: '',
    bankid: 0,
    bankbranchname: '',
    bankbranchid: 0,
    creditcardno: '',
    creditcardamount: 0,
    reference: '',
    otherdetails: '',
    lmu: 0,
    lmd: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
    termid: 0,
    termname: '',
    accountno: '',
    accountnumber: '',
    chequereturndate: null,
    isdeposit: false,
    depositeddate: null,
    chequestatuschangeddate: null,
    returnchequesttledate: null,
    chequestatusid: 0,
    ispdcheque: false,
    depositdate: null,
    accountid: 0,
    accountcode: '',
    bankdepositbankname: '',
    bankdepositbankid: 0,
    bankdepositbankbranchname: '',
    bankdepositbankbranchid: 0,
    returnchequefine: 0,
    companybankid: 0,
    isbankreconciliation: false,
  };

  method: string = '';
  id: number = 0;

  finishBilling(): void {
    this.balance = Number((Number(this.totalamount || 0) - Number(this.cash || 0)).toFixed(2));

    if (this.method === 'Cash' && Math.abs(this.balance) > 0.004) {
      alert(`Please settle the full cash amount. Balance: ${this.balance.toFixed(2)}`);
      return;
    }

    this.save();
  }

  generateNextReceiptCode(lastCode: string | null | undefined): string {
    const defaultCode = 'RCPT1000';
    let originalCode = lastCode?.trim() || defaultCode;

    if (!originalCode.toLowerCase().startsWith('rcpt')) {
      originalCode = defaultCode;
    }

    const match = originalCode.match(/\d+$/);
    if (match) {
      return originalCode.replace(/\d+$/, (numStr: string) => {
        const incremented = Number(numStr) + 1;
        return String(incremented).padStart(numStr.length, '0');
      });
    } else {
      return 'RCPT1001';
    }
  }

  saveReceiptWithCode(nextReceiptCode: string, finalUserId: number): void {
    const paymentAmount = this.getCurrentPaymentAmount();
    const selectedPaymentBankAccount = this.method === 'Cheque' || this.method === 'Bank' ? this.selectedCompanyBankAccount : null;
    const paymentAccountId =
      selectedPaymentBankAccount?.accountid && !isNaN(Number(selectedPaymentBankAccount.accountid))
        ? Number(selectedPaymentBankAccount.accountid)
        : this.accountId && !isNaN(Number(this.accountId))
          ? Number(this.accountId)
          : 0;
    const paymentAccountCode = selectedPaymentBankAccount?.accountcode ?? this.accountCode ?? '';
    const safeAccountId = paymentAccountId;
    const receiptTotalAmount = this.method === 'Credit' ? 0 : this.totalamount || 0;

    this.receipt.code = nextReceiptCode;
    this.receipt.lmu = finalUserId;
    this.receipt.lmd = dayjs().add(-new Date().getTimezoneOffset(), 'minute');
    this.receipt.customername = this.customername ?? '';
    this.receipt.totalamount = receiptTotalAmount;
    this.receipt.deposited = this.method === 'Cheque' ? false : this.deposited ?? true;

    // Calculate amount in words if not already set or to ensure it's current
    const words = toWords(receiptTotalAmount).replace(/,/g, '').replace(/and/g, 'and');
    this.receipt.totalamountinword = words + ' Rupees Only';

    this.subscribeToSaveResponseWithCallback(this.reciptService.create(this.receipt as any), (receiptId: number) => {
      if (this.method === 'Credit') {
        this.salesinvoiceupdate.save();
        return;
      }

      const receiptLinePayload: any = {
        id: receiptId,
        lineid: 1,
        invoicecode: this.invoicecode ?? '',
        invoicetype: 'Sales Invoice',
        originalamount: this.totalamount || 0,
        amountowing: (this.totalamount || 0) - (paymentAmount || 0),
        discountavailable: 0,
        discounttaken: 0,
        amountreceived: paymentAmount || 0,
        lmu: finalUserId,
        lmd: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
        accountid: safeAccountId,
      };
      console.log('ReceiptLines Payload:', receiptLinePayload);
      this.reciptlines.create(receiptLinePayload).subscribe({
        next: () => console.log('ReceiptLines saved OK'),
        error: (err: any) => console.error('ReceiptLines error:', err),
      });

      // ==========================================
      // FIX: PROPERLY MAP CHEQUE DETAILS INTO THE PAYLOAD
      // ==========================================
      const receiptPaymentsPayload: any = {
        id: receiptId,
        lineid: 1,
        paymentamount: this.method === 'Cash' ? this.totalamount || 0 : paymentAmount || 0,
        totalreceiptamount: this.totalamount || 0,
        lmu: finalUserId,
        lmd: dayjs().add(-new Date().getTimezoneOffset(), 'minute'),
        termid: this.receipt.termid || 0,
        termname: this.method || '',
        accountid: paymentAccountId,
        accountcode: paymentAccountCode,
        isdeposit: false,
        ispdcheque: false,

        // These were previously hardcoded to 0/null/'' in the other system's code
        checkqueamount: this.method === 'Cheque' ? this.chequeAmount || 0 : 0,
        checkqueno: this.method === 'Cheque' ? this.checkno || '' : '',
        checkquedate: this.method === 'Cheque' ? (this.checkdate ? this.localDateTime(this.checkdate) : this.localNow()) : null,
        checkqueexpiredate: this.method === 'Cheque' ? (this.checkdate ? this.localDateTime(this.checkdate) : this.localNow()) : null,
        bankname: this.method === 'Cheque' || this.method === 'Bank' ? this.bankname || this.bank || '' : '',
        bankid: this.method === 'Cheque' || this.method === 'Bank' ? this.bankid : 0,
        bankbranchname: this.method === 'Cheque' || this.method === 'Bank' ? this.Branch : '',
        bankbranchid: this.method === 'Cheque' || this.method === 'Bank' ? this.branchid : 0,
        // ==========================================

        creditcardno: '',
        creditcardamount: this.method === 'Card/Other' ? paymentAmount || 0 : 0,
        reference: 'Sales Invoice',
        otherdetails: '',
        accountno: '',
        accountnumber: '',
        chequereturndate: null,
        depositeddate: null,
        chequestatuschangeddate: null,
        returnchequesttledate: null,
        chequestatusid: this.method === 'Cheque' ? 1 : 0,
        depositdate: null,
        bankdepositbankname: this.method === 'Bank' ? this.bankname || this.bank || '' : '',
        bankdepositbankid: this.method === 'Bank' ? this.bankid : 0,
        bankdepositbankbranchname: this.method === 'Bank' ? this.Branch : '',
        bankdepositbankbranchid: this.method === 'Bank' ? this.branchid : 0,
        returnchequefine: 0,
        companybankid: selectedPaymentBankAccount?.id ?? 0,
        isbankreconciliation: false,
      };
      console.log('ReceiptPayments Payload:', receiptPaymentsPayload);
      this.paymentdetails.create(receiptPaymentsPayload).subscribe({
        next: () => console.log('Receiptpaymentsdetails saved OK'),
        error: (err: any) => console.error('Receiptpaymentsdetails error:', err),
      });

      this.salesinvoiceupdate.save();

      this.subid = crypto.randomUUID();

      const transactionAmount = this.method === 'Cash' ? this.totalamount || 0 : paymentAmount || 0;

      if (this.customername != 'CASH') {
        this.updaterecipttransactionwithcustomer();
        this.receipttransactions(receiptId, this.receipt.code, this.subid, this.receipt.termid, this.method, transactionAmount);
      } else {
        this.reciptnocustomerupdate(this.accountId);
        this.reciptnocustransactions(receiptId, this.receipt.code, this.subid, this.receipt.termid, this.method, transactionAmount);
      }

      this.updatecustomermain(transactionAmount);
      this.receiptmainacctransactions(receiptId, this.receipt.code, this.subid, transactionAmount);
    });
  }

  save(): void {
    this.isSaving = true;

    const currentPaidAmount = this.getCurrentPaymentAmount();

    this.salesinvoiceupdate.editForm.patchValue({
      paidamount: currentPaidAmount,
      paymenttype: this.method,
    });

    const storedUserId = localStorage.getItem('empId');
    const userIdNumber = storedUserId ? parseInt(storedUserId, 10) : 0;
    const finalUserId = isNaN(userIdNumber) ? 0 : userIdNumber;

    this.salesinvoiceupdate.checkDuplicateActiveInvoice().subscribe({
      next: response => {
        if (response.body?.exists) {
          alert('This sales invoice already exists.');
          this.onSaveFinalize();
          return;
        }

        this.continueBillingSave(finalUserId);
      },
      error: err => {
        console.error('Error checking duplicate sales invoice:', err);
        this.onSaveError();
        this.onSaveFinalize();
      },
    });
  }

  private continueBillingSave(finalUserId: number): void {
    if (this.receipt) {
      this.reciptService.query({ page: 0, size: 1, sort: ['id,desc'] }).subscribe({
        next: (res: HttpResponse<any[]>) => {
          const lastReceipt = res.body?.[0];
          const lastCode = lastReceipt?.code;
          const nextReceiptCode = this.generateNextReceiptCode(lastCode);

          this.saveReceiptWithCode(nextReceiptCode, finalUserId);
        },
        error: (err: any) => {
          console.error('Error fetching last receipt code:', err);
          const nextReceiptCode = this.generateNextReceiptCode(null);
          this.saveReceiptWithCode(nextReceiptCode, finalUserId);
        },
      });
    } else {
      this.isSaving = true;
      this.salesinvoiceupdate.save();
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<any>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        this.id = response.body.id;
        this.onSaveSuccess();
      },
      error: () => this.onSaveError(),
    });
  }

  protected subscribeToSaveResponseWithCallback(result: Observable<HttpResponse<any>>, callback: (id: number) => void): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: response => {
        this.id = response.body.id;
        callback(response.body.id);
        this.onSaveSuccess();
      },
      error: () => this.onSaveError(),
    });
  }

  onOptionChange(option: number): void {
    this.selectedOption = option;
    if (option !== 4) {
      this.finalcommisonamount = 0;
      this.paymentType = '';
    }
    this.receipt.totalamount = this.totalamount;
    this.receipt.customername = this.customername ?? '';
    this.receipt.customeraddress = this.customeraddress ?? '';
    this.receipt.comments = this.comments ?? '';

    this.receipt.date = this.date ? this.localDateTime(this.date) : this.localNow();

    this.receipt.amount = this.amount ?? 0;
    this.receipt.checkdate = this.checkdate ? this.localDateTime(this.checkdate) : this.localNow();
    this.receipt.checkno = this.checkno ?? '';
    this.receipt.bank = this.bank ?? '';
    this.receipt.customerid = this.customerid ?? 0;
    this.receipt.isactive = this.isactive ?? true;
    this.receipt.deposited = this.deposited ?? true;
    this.receipt.createdby = this.createdby ?? 0;
    this.receipt.totalamountinword = this.totalamountinword ?? '';
    this.receipt.code = this.newcode ?? '';
    this.receipt.receiptdate = this.receiptdate ? this.localDateTime(this.receiptdate) : this.localNow();
    this.receipt.vehicleno = this.vehicleno ?? '';

    // Logging all values

    let paymentMethod = '';
    let termid = 0;
    switch (option) {
      case 1:
        paymentMethod = 'Cash';
        termid = 1;
        break;
      case 2:
        paymentMethod = 'Credit';
        termid = 2;
        break;
      case 3:
        paymentMethod = 'Cheque';
        termid = 3;
        break;
      case 4:
        paymentMethod = 'Card/Other';
        termid = 4;
        break;
      case 5:
        paymentMethod = 'Bank';
        termid = 5;
        break;
      default:
        paymentMethod = 'Unknown';
    }

    console.log('Selected Payment Method:', paymentMethod);
    console.log('Selected Term ID:', termid);
    this.method = paymentMethod;
    this.accountmethod(paymentMethod);
    if (this.method === 'Cash') {
      this.balance = Number((Number(this.totalamount || 0) - Number(this.cash || 0)).toFixed(2));
    }
    if (paymentMethod !== 'Cheque' && paymentMethod !== 'Bank') {
      this.selectedCompanyBankAccount = null;
      this.selectedCompanyBankAccountId = null;
      this.bankbranch = [];
      this.bankid = 0;
      this.bankname = '';
      this.Branch = '';
      this.branchid = 0;
    }
    this.receipt.term = paymentMethod;
    this.receipt.termid = termid;
    this.receipt.deposited = this.method === 'Cheque' ? false : this.deposited ?? true;
    if (this.method === 'Credit') {
      this.receipt.totalamount = 0;
    }

    // Sync with main SalesInvoice form
    this.salesinvoiceupdate.editForm.patchValue({
      paymenttype: paymentMethod,
    });

    console.log('totalamount:', this.totalamount);

    let totalAmountInWords = toWords(this.totalamount).replace(/,/g, '').replace(/and/g, 'and'); // Formatting the words
    console.log(totalAmountInWords + ' Rupees Only');
    this.receipt.totalamountinword = totalAmountInWords + ' Rupees Only';
    console.log('Updated Receipt:', this.receipt);
  }

  private getCurrentPaymentAmount(): number {
    if (this.method === 'Cheque') {
      return this.chequeAmount || 0;
    }
    if (this.method === 'Cash') {
      const currentPaidAmount = this.cash || this.totalamount || 0;
      return currentPaidAmount > this.totalamount ? this.totalamount : currentPaidAmount;
    }
    if (this.method === 'Credit') {
      return 0;
    }
    if (this.method === 'Card/Other') {
      return this.cardAmount || 0;
    }
    if (this.method === 'Bank') {
      return this.bankAmount || 0;
    }
    return this.totalamount || 0;
  }

  protected onSaveSuccess(): void {
    this.cleanupModalBackdrop();

    // No navigation here anymore, letting SalesinvoiceUpdateComponent handle it
  }

  private cleanupModalBackdrop(): void {
    const modalElement = document.getElementById('exampleModal');
    const modalInstance =
      modalElement && typeof bootstrap !== 'undefined' && bootstrap.Modal ? bootstrap.Modal.getInstance(modalElement) : null;

    if (modalInstance) {
      modalInstance.hide();
    }

    if (modalElement) {
      modalElement.classList.remove('show');
      modalElement.setAttribute('aria-hidden', 'true');
      modalElement.removeAttribute('aria-modal');
      modalElement.removeAttribute('role');
      modalElement.style.display = 'none';
    }

    document.querySelectorAll('.modal-backdrop').forEach(backdrop => backdrop.remove());
    document.body.classList.remove('modal-open');
    document.body.style.removeProperty('overflow');
    document.body.style.removeProperty('padding-right');
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(receiptpaymentsdetails: any): void {
    this.receiptpaymentsdetailsFormService.resetForm(this.editForm, receiptpaymentsdetails);
  }
}
