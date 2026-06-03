import { CommonModule } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SalesInvoiceDummyService } from '../sales-invoice-dummy/service/sales-invoice-dummy.service';

@Component({
  selector: 'jhi-printinvoice',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './printinvoice.component.html',
  styleUrl: './printinvoice.component.scss',
})
export class PrintinvoiceComponent implements OnInit {
  salesInvoice: any = null;
  invoiceLines: any[] = [];
  serviceLines: any[] = [];
  commonServiceLines: any[] = [];

  protected salesInvoiceDummyService = inject(SalesInvoiceDummyService);

  private totalRequests = 4;
  private completedRequests = 0;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    // Get 'id' from the query params
    this.route.queryParams.subscribe(params => {
      const id = params['id']; // Extract the ID
      console.log('ID from URL:', id);

      if (id) {
        const numericId = Number(id);
        this.getSalesInvoice(numericId);
        this.getSalesInvoicelines(numericId);
        this.getSalesServicelines(numericId);
        this.getSalesSercolines(numericId);
      }
    });
  }

  // Function to fetch invoice data
  getSalesInvoice(id: number): void {
    this.salesInvoiceDummyService.find(id).subscribe({
      next: response => {
        console.log('Sales Invoice Data:', response.body);
        this.salesInvoice = response.body;
        this.checkAndPrint();
      },
      error: err => {
        console.error('Error fetching Sales Invoice:', err);
        this.checkAndPrint();
      },
    });
  }

  getSalesInvoicelines(id: number): void {
    this.salesInvoiceDummyService.fetchInvoiceLines(id).subscribe({
      next: response => {
        console.log('Sales Invoice lines Data:', response.body);
        this.invoiceLines = response.body || [];
        this.checkAndPrint();
      },
      error: err => {
        console.error('Error fetching Sales Invoice lines:', err);
        this.checkAndPrint();
      },
    });
  }

  getSalesServicelines(id: number): void {
    this.salesInvoiceDummyService.fetchService(id).subscribe({
      next: response => {
        console.log('Sales Service lines Data:', response.body);
        this.serviceLines = response.body || [];
        this.checkAndPrint();
      },
      error: err => {
        console.error('Error fetching Sales Service lines:', err);
        this.checkAndPrint();
      },
    });
  }

  getSalesSercolines(id: number): void {
    this.salesInvoiceDummyService.fetchServiceCommon(id).subscribe({
      next: response => {
        console.log('Sales Service common Data:', response.body);
        this.commonServiceLines = response.body || [];
        this.checkAndPrint();
      },
      error: err => {
        console.error('Error fetching Sales Service common lines:', err);
        this.checkAndPrint();
      },
    });
  }

  private checkAndPrint(): void {
    this.completedRequests++;
    if (this.completedRequests === this.totalRequests) {
      console.log('All data loaded, triggering print...');
      setTimeout(() => {
        window.print();
      }, 1000); // Small delay to ensure rendering is complete
    }
  }
}
