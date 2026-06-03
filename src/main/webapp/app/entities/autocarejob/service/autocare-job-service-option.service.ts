import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { IAutocareJobServiceOption, NewAutocareJobServiceOption } from '../autocare-job-service-option.model';

export type EntityResponseType = HttpResponse<IAutocareJobServiceOption>;
export type EntityArrayResponseType = HttpResponse<IAutocareJobServiceOption[]>;

@Injectable({ providedIn: 'root' })
export class AutocareJobServiceOptionService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/autocare-job-service-options');

  create(option: NewAutocareJobServiceOption | any): Observable<EntityResponseType> {
    return this.http.post<IAutocareJobServiceOption>(this.resourceUrl, option, { observe: 'response' });
  }

  update(option: IAutocareJobServiceOption): Observable<EntityResponseType> {
    return this.http.put<IAutocareJobServiceOption>(`${this.resourceUrl}/${option.id}`, option, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAutocareJobServiceOption>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  queryByJobId(jobId: number): Observable<HttpResponse<IAutocareJobServiceOption[]>> {
    return this.http.get<IAutocareJobServiceOption[]>(`${this.resourceUrl}/by-job/${jobId}`, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  deleteByJobId(jobId: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/by-job/${jobId}`, { observe: 'response' });
  }
}
