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
        width: 245px;
        flex-shrink: 0;
      }

      .content-container {
        flex-grow: 1;
        min-width: 0;
        max-width: 100%;
        overflow-x: hidden;
        background: #f5f5f5;
        min-height: 100vh;
      }

      .rac-topbar {
        min-height: 40px;
        background: #1f1f1f;
        border-bottom: 0;
        box-shadow: none;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 5px 16px;
        position: sticky;
        top: 0;
        z-index: 20;
      }

      .rac-topbar-title {
        color: #ffffff;
        font-size: 16px;
        font-weight: 600;
        line-height: 1.1;
      }

      .rac-topbar-subtitle {
        color: #b9bec5;
        font-size: 11px;
        line-height: 1.2;
        text-transform: uppercase;
      }

      .rac-topbar-chip {
        background: #27ae60;
        color: #ffffff;
        display: inline-flex;
        align-items: center;
        min-height: 24px;
        padding: 0 9px;
        font-size: 11px;
        font-weight: 600;
      }

      .rac-page-body {
        padding: 14px 14px 0;
      }

      .rac-login-body-shell {
        background: #ffffff;
        min-height: 100vh;
        padding: 0;
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

  isLoginRoute(): boolean {
    return this.router.url === '/login';
  }
}
