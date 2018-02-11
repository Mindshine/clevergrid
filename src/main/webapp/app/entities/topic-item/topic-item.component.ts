import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TopicItem } from './topic-item.model';
import { TopicItemService } from './topic-item.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-topic-item',
    templateUrl: './topic-item.component.html'
})
export class TopicItemComponent implements OnInit, OnDestroy {
topicItems: TopicItem[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private topicItemService: TopicItemService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.topicItemService.query().subscribe(
            (res: HttpResponse<TopicItem[]>) => {
                this.topicItems = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTopicItems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TopicItem) {
        return item.id;
    }
    registerChangeInTopicItems() {
        this.eventSubscriber = this.eventManager.subscribe('topicItemListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
