import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'Authorities' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'customer',
    data: { pageTitle: 'Customers' },
    loadChildren: () => import('./customer/customer.routes'),
  },
  {
    path: 'customervehicle',
    data: { pageTitle: 'Customervehicles' },
    loadChildren: () => import('./customervehicle/customervehicle.routes'),
  },
  {
    path: 'servicecategory',
    data: { pageTitle: 'Servicecategories' },
    loadChildren: () => import('./servicecategory/servicecategory.routes'),
  },
  {
    path: 'autocarejob',
    data: { pageTitle: 'Autocarejobs' },
    loadChildren: () => import('./autocarejob/autocarejob.routes'),
  },
  {
    path: 'printinvoice',
    loadChildren: () => import('./printinvoice/printinvoice.module').then(m => m.PrintInvoiceRoutingModule),
  },
  {
    path: 'vehicletype',
    data: { pageTitle: 'Vehicletypes' },
    loadChildren: () => import('./vehicletype/vehicletype.routes'),
  },
  {
    path: 'autocareappointment',
    data: { pageTitle: 'Autocareappointments' },
    loadChildren: () => import('./autocareappointment/autocareappointment.routes'),
  },
  {
    path: 'autocarejobinimages',
    data: { pageTitle: 'Autocarejobinimages' },
    loadChildren: () => import('./autocarejobinimages/autocarejobinimages.routes'),
  },
  {
    path: 'autocareappointmenttype',
    data: { pageTitle: 'Autocareappointmenttypes' },
    loadChildren: () => import('./autocareappointmenttype/autocareappointmenttype.routes'),
  },
  {
    path: 'vehiclecategory',
    data: { pageTitle: 'Vehiclecategories' },
    loadChildren: () => import('./vehiclecategory/vehiclecategory.routes'),
  },
  {
    path: 'inventory',
    data: { pageTitle: 'Inventories' },
    loadChildren: () => import('./inventory/inventory.routes'),
  },
  {
    path: 'servicesubcategory',
    data: { pageTitle: 'Servicesubcategories' },
    loadChildren: () => import('./servicesubcategory/servicesubcategory.routes'),
  },
  {
    path: 'stocklocations',
    data: { pageTitle: 'Stocklocations' },
    loadChildren: () => import('./stocklocations/stocklocations.routes'),
  },

  {
    path: 'inventorybatches',
    data: { pageTitle: 'Inventorybatches' },
    loadChildren: () => import('./inventorybatches/inventorybatches.routes'),
  },
  {
    path: 'vehiclebrandname',
    data: { pageTitle: 'Vehiclebrandnames' },
    loadChildren: () => import('./vehiclebrandname/vehiclebrandname.routes'),
  },
  {
    path: 'vehiclebrandmodel',
    data: { pageTitle: 'Vehiclebrandmodels' },
    loadChildren: () => import('./vehiclebrandmodel/vehiclebrandmodel.routes'),
  },
  {
    path: 'workshopworklist',
    data: { pageTitle: 'Workshopworklists' },
    loadChildren: () => import('./workshopworklist/workshopworklist.routes'),
  },
  {
    path: 'workshopvehiclework',
    data: { pageTitle: 'Workshopvehicleworks' },
    loadChildren: () => import('./workshopvehiclework/workshopvehiclework.routes'),
  },
  {
    path: 'autojobempallocation',
    data: { pageTitle: 'Autojobempallocations' },
    loadChildren: () => import('./autojobempallocation/autojobempallocation.routes'),
  },
  {
    path: 'autocarejobcategory',
    data: { pageTitle: 'Autocarejobcategories' },
    loadChildren: () => import('./autocarejobcategory/autocarejobcategory.routes'),
  },
  {
    path: 'emp-sectiontbl',
    data: { pageTitle: 'EmpSectiontbls' },
    loadChildren: () => import('./emp-sectiontbl/emp-sectiontbl.routes'),
  },
  {
    path: 'emp-jobcommission',
    data: { pageTitle: 'EmpJobcommissions' },
    loadChildren: () => import('./emp-jobcommission/emp-jobcommission.routes'),
  },
  {
    path: 'autocarecancelitemopt',
    data: { pageTitle: 'Autocarecancelitemopts' },
    loadChildren: () => import('./autocarecancelitemopt/autocarecancelitemopt.routes'),
  },
  {
    path: 'paymentterm',
    data: { pageTitle: 'Paymentterms' },
    loadChildren: () => import('./paymentterm/paymentterm.routes'),
  },
  {
    path: 'salesinvoice',
    data: { pageTitle: 'Salesinvoices' },
    loadChildren: () => import('./salesinvoice/salesinvoice.routes'),
  },
  {
    path: 'salesorder',
    data: { pageTitle: 'Salesorders' },
    loadChildren: () => import('./salesorder/salesorder.routes'),
  },
  {
    path: 'taxes',
    data: { pageTitle: 'Taxes' },
    loadChildren: () => import('./taxes/taxes.routes'),
  },
  {
    path: 'banks',
    data: { pageTitle: 'Banks' },
    loadChildren: () => import('./banks/banks.routes'),
  },
  {
    path: 'bankbranch',
    data: { pageTitle: 'Bankbranches' },
    loadChildren: () => import('./bankbranch/bankbranch.routes'),
  },
  {
    path: 'accounts',
    data: { pageTitle: 'Accounts' },
    loadChildren: () => import('./accounts/accounts.routes'),
  },
  {
    path: 'companybankaccount',
    data: { pageTitle: 'Companybankaccounts' },
    loadChildren: () => import('./companybankaccount/companybankaccount.routes'),
  },
  {
    path: 'receipt',
    data: { pageTitle: 'Receipts' },
    loadChildren: () => import('./receipt/receipt.routes'),
  },
  {
    path: 'locationbasedstock',
    data: { pageTitle: 'Locationbasedstocks' },
    loadChildren: () => import('./locationbasedstock/locationbasedstock.routes'),
  },
  {
    path: 'autojobsinvoice',
    data: { pageTitle: 'Autojobsinvoices' },
    loadChildren: () => import('./autojobsinvoice/autojobsinvoice.routes'),
  },
  {
    path: 'billingserviceoption',
    data: { pageTitle: 'Billingserviceoptions' },
    loadChildren: () => import('./billingserviceoption/billingserviceoption.routes'),
  },
  {
    path: 'billingserviceoptionvalues',
    data: { pageTitle: 'Billingserviceoptionvalues' },
    loadChildren: () => import('./billingserviceoptionvalues/billingserviceoptionvalues.routes'),
  },
  {
    path: 'commonserviceoption',
    data: { pageTitle: 'Commonserviceoptions' },
    loadChildren: () => import('./commonserviceoption/commonserviceoption.routes'),
  },
  {
    path: 'nextserviceinstructions',
    data: { pageTitle: 'Nextserviceinstructions' },
    loadChildren: () => import('./nextserviceinstructions/nextserviceinstructions.routes'),
  },
  {
    path: 'lastserviceinstructions',
    data: { pageTitle: 'Lastserviceinstructions' },
    loadChildren: () => import('./lastserviceinstructions/lastserviceinstructions.routes'),
  },
  {
    path: 'autocareservicemillages',
    data: { pageTitle: 'Autocareservicemillages' },
    loadChildren: () => import('./autocareservicemillages/autocareservicemillages.routes'),
  },
  {
    path: 'autocarecompany',
    data: { pageTitle: 'Autocarecompanies' },
    loadChildren: () => import('./autocarecompany/autocarecompany.routes'),
  },
  {
    path: 'autocarehoist',
    data: { pageTitle: 'Autocarehoists' },
    loadChildren: () => import('./autocarehoist/autocarehoist.routes'),
  },
  {
    path: 'hoisttype',
    data: { pageTitle: 'Hoisttypes' },
    loadChildren: () => import('./hoisttype/hoisttype.routes'),
  },
  {
    path: 'autocaretimetable',
    data: { pageTitle: 'Autocaretimetables' },
    loadChildren: () => import('./autocaretimetable/autocaretimetable.routes'),
  },
  {
    path: 'sales-invoice-dummy',
    data: { pageTitle: 'SalesInvoiceDummies' },
    loadChildren: () => import('./sales-invoice-dummy/sales-invoice-dummy.routes'),
  },
  {
    path: 'sales-invoice-lines-dummy',
    data: { pageTitle: 'SalesInvoiceLinesDummies' },
    loadChildren: () => import('./sales-invoice-lines-dummy/sales-invoice-lines-dummy.routes'),
  },
  {
    path: 'sales-invoice-service-charge-line-dummy',
    data: { pageTitle: 'SalesInvoiceServiceChargeLineDummies' },
    loadChildren: () => import('./sales-invoice-service-charge-line-dummy/sales-invoice-service-charge-line-dummy.routes'),
  },
  {
    path: 'sale-invoice-common-service-charge-dummy',
    data: { pageTitle: 'SaleInvoiceCommonServiceChargeDummies' },
    loadChildren: () => import('./sale-invoice-common-service-charge-dummy/sale-invoice-common-service-charge-dummy.routes'),
  },
  {
    path: 'voucher',
    data: { pageTitle: 'Vouchers' },
    loadChildren: () => import('./voucher/voucher.routes'),
  },
  {
    path: 'voucher-lines',
    data: { pageTitle: 'VoucherLines' },
    loadChildren: () => import('./voucher-lines/voucher-lines.routes'),
  },
  {
    path: 'voucher-payments-details',
    data: { pageTitle: 'VoucherPaymentsDetails' },
    loadChildren: () => import('./voucher-payments-details/voucher-payments-details.routes'),
  },
  {
    path: 'sales-invoice-lines',
    data: { pageTitle: 'SalesInvoiceLines' },
    loadChildren: () => import('./sales-invoice-lines/sales-invoice-lines.routes'),
  },
  {
    path: 'invoice-service-charge',
    data: { pageTitle: 'InvoiceServiceCharges' },
    loadChildren: () => import('./invoice-service-charge/invoice-service-charge.routes'),
  },
  {
    path: 'invoice-service-common',
    data: { pageTitle: 'InvoiceServiceCommons' },
    loadChildren: () => import('./invoice-service-common/invoice-service-common.routes'),
  },
  {
    path: 'sale-invoice-common-service-charge',
    data: { pageTitle: 'SaleInvoiceCommonServiceCharges' },
    loadChildren: () => import('./sale-invoice-common-service-charge/sale-invoice-common-service-charge.routes'),
  },
  {
    path: 'sales-invoice-service-charge-line',
    data: { pageTitle: 'SalesInvoiceServiceChargeLines' },
    loadChildren: () => import('./sales-invoice-service-charge-line/sales-invoice-service-charge-line.routes'),
  },
  {
    path: 'autojobsalesinvoiceservicechargeline',
    data: { pageTitle: 'Autojobsalesinvoiceservicechargelines' },
    loadChildren: () => import('./autojobsalesinvoiceservicechargeline/autojobsalesinvoiceservicechargeline.routes'),
  },
  {
    path: 'autojobsaleinvoicecommonservicecharge',
    data: { pageTitle: 'Autojobsaleinvoicecommonservicecharges' },
    loadChildren: () => import('./autojobsaleinvoicecommonservicecharge/autojobsaleinvoicecommonservicecharge.routes'),
  },
  {
    path: 'autojobsinvoicelines',
    data: { pageTitle: 'Autojobsinvoicelines' },
    loadChildren: () => import('./autojobsinvoicelines/autojobsinvoicelines.routes'),
  },
  {
    path: 'workshop-vehicle-work-list',
    data: { pageTitle: 'WorkshopVehicleWorkLists' },
    loadChildren: () => import('./workshop-vehicle-work-list/workshop-vehicle-work-list.routes'),
  },
  {
    path: 'receiptpaymentsdetails',
    data: { pageTitle: 'Receiptpaymentsdetails' },
    loadChildren: () => import('./receiptpaymentsdetails/receiptpaymentsdetails.routes'),
  },
  {
    path: 'receipt-lines',
    data: { pageTitle: 'ReceiptLines' },
    loadChildren: () => import('./receipt-lines/receipt-lines.routes'),
  },
  {
    path: 'autojobsinvoicelinebatches',
    data: { pageTitle: 'Autojobsinvoicelinebatches' },
    loadChildren: () => import('./autojobsinvoicelinebatches/autojobsinvoicelinebatches.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
