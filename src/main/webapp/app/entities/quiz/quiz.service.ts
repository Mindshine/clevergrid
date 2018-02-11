import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Quiz } from './quiz.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Quiz>;

@Injectable()
export class QuizService {

    private resourceUrl =  SERVER_API_URL + 'api/quizzes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(quiz: Quiz): Observable<EntityResponseType> {
        const copy = this.convert(quiz);
        return this.http.post<Quiz>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(quiz: Quiz): Observable<EntityResponseType> {
        const copy = this.convert(quiz);
        return this.http.put<Quiz>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<Quiz>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Quiz[]>> {
        const options = createRequestOption(req);
        return this.http.get<Quiz[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Quiz[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Quiz = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Quiz[]>): HttpResponse<Quiz[]> {
        const jsonResponse: Quiz[] = res.body;
        const body: Quiz[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Quiz.
     */
    private convertItemFromServer(quiz: Quiz): Quiz {
        const copy: Quiz = Object.assign({}, quiz);
        copy.createdDate = this.dateUtils
            .convertDateTimeFromServer(quiz.createdDate);
        copy.lastModifiedDate = this.dateUtils
            .convertDateTimeFromServer(quiz.lastModifiedDate);
        return copy;
    }

    /**
     * Convert a Quiz to a JSON which can be sent to the server.
     */
    private convert(quiz: Quiz): Quiz {
        const copy: Quiz = Object.assign({}, quiz);

        copy.createdDate = this.dateUtils.toDate(quiz.createdDate);

        copy.lastModifiedDate = this.dateUtils.toDate(quiz.lastModifiedDate);
        return copy;
    }
}
