import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TopicItem } from './topic-item.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TopicItem>;

@Injectable()
export class TopicItemService {

    private resourceUrl =  SERVER_API_URL + 'api/topic-items';

    constructor(private http: HttpClient) { }

    create(topicItem: TopicItem): Observable<EntityResponseType> {
        const copy = this.convert(topicItem);
        return this.http.post<TopicItem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(topicItem: TopicItem): Observable<EntityResponseType> {
        const copy = this.convert(topicItem);
        return this.http.put<TopicItem>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<TopicItem>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TopicItem[]>> {
        const options = createRequestOption(req);
        return this.http.get<TopicItem[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TopicItem[]>) => this.convertArrayResponse(res));
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TopicItem = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TopicItem[]>): HttpResponse<TopicItem[]> {
        const jsonResponse: TopicItem[] = res.body;
        const body: TopicItem[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TopicItem.
     */
    private convertItemFromServer(topicItem: TopicItem): TopicItem {
        const copy: TopicItem = Object.assign({}, topicItem);
        return copy;
    }

    /**
     * Convert a TopicItem to a JSON which can be sent to the server.
     */
    private convert(topicItem: TopicItem): TopicItem {
        const copy: TopicItem = Object.assign({}, topicItem);
        return copy;
    }
}
