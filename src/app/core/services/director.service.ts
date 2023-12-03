import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
import { Director } from '../interfaces/movie.interface';
import { Page } from '../interfaces/page.interface';
import { ENDPOINTS } from '../constants/Endpoints.const';
import { ServiceResponse } from '../interfaces/service-response.interface';

@Injectable({
  providedIn: 'root',
})
export class DirectorService {
  constructor(private http: HttpClient) {}

  public getPage(
    name = '',
    page = 0,
    size = 5
  ): Observable<ServiceResponse<Page<Director>>> {
    const params = new HttpParams()
      .append('name', name)
      .append('page', page)
      .append('size', size);

    return this.http.get<ServiceResponse<Page<Director>>>(
      `${environment.apiBaseUrl}${ENDPOINTS.DIRECTORS}`,
      { params }
    );
  }

  public getDirectors(): Observable<ServiceResponse<Director[]>> {
    return this.http.get<ServiceResponse<Director[]>>(
      environment.apiBaseUrl + ENDPOINTS.DIRECTORS_GET
    );
  }

  public addDirector(director: Director): Observable<Director> {
    return this.http.post<Director>(
      environment.apiBaseUrl + ENDPOINTS.DIRECTORS_ADD,
      director
    );
  }

  public updateDirector(director: Director): Observable<Director> {
    return this.http.put<Director>(
      environment.apiBaseUrl + ENDPOINTS.DIRECTORS_UPDATE,
      director
    );
  }

  public deleteDirector(directorId: number): Observable<void> {
    return this.http.delete<void>(
      environment.apiBaseUrl + ENDPOINTS.DIRECTORS_DELETE + `${directorId}`
    );
  }
}
