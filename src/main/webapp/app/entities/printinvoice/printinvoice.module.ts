import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrintinvoiceComponent } from './printinvoice.component';

const routes: Routes = [
  { path: '', component: PrintinvoiceComponent }, // This makes `/printinvoice` load this component
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PrintInvoiceRoutingModule {}
