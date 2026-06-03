import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from 'app/config/font-awesome-icons';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';

/**
 * Application wide Module
 */
@NgModule({
  imports: [AlertComponent, AlertErrorComponent],
  exports: [CommonModule, NgbModule, FontAwesomeModule, AlertComponent, AlertErrorComponent],
})
export default class SharedModule {
  constructor(library: FaIconLibrary) {
    fontAwesomeIcons.forEach(icon => library.addIcons(icon));
  }
}
