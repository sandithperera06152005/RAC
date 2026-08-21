import { Component, inject, OnInit } from '@angular/core';
import { RouterOutlet, Router } from '@angular/router';

import { AccountService } from 'app/core/auth/account.service';
import { AppPageTitleStrategy } from 'app/app-page-title-strategy';
import FooterComponent from '../footer/footer.component';
import PageRibbonComponent from '../profiles/page-ribbon.component';
// import SidebarComponent from '../sidebar/sidebar.component'; // Import the Sidebar component
import SidenavbarComponent from '../sidenavbar/sidenavbar.component';

@Component({
  standalone: true,
  selector: 'jhi-main',
  templateUrl: './main.component.html',
  providers: [AppPageTitleStrategy],
  imports: [RouterOutlet, FooterComponent, PageRibbonComponent, SidenavbarComponent], // Include SidebarComponent

  styles: [
    `
      .sidebar {
        width: 250px; /* Adjust width as needed */
        flex-shrink: 0; /* Prevent shrinking */
      }

      .content-container {
        flex-grow: 1;
        // overflow: hidden; /* Prevent unwanted scrolling */
      }
    `,
  ],
})
export default class MainComponent implements OnInit {
  private router = inject(Router);
  private appPageTitleStrategy = inject(AppPageTitleStrategy);
  private accountService = inject(AccountService);

  constructor() {}

  ngOnInit(): void {
    // try to log in automatically
    this.accountService.identity().subscribe();
  }

  isSidebarHidden(): boolean {
    const url = this.router.url;
    if (url === '/login' || this.isPrintRoute()) {
      return true;
    }
    const isHome = url === '/' || url === '' || url === '/#';
    return isHome && !this.accountService.isAuthenticated();
  }

  isPrintRoute(): boolean {
    return this.router.url.startsWith('/printinvoice');
  }
}
