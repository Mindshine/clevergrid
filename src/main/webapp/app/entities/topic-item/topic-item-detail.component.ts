import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TopicItem } from './topic-item.model';
import { TopicItemService } from './topic-item.service';

@Component({
    selector: 'jhi-topic-item-detail',
    templateUrl: './topic-item-detail.component.html'
})
export class TopicItemDetailComponent implements OnInit, OnDestroy {

    topicItem: TopicItem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private topicItemService: TopicItemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTopicItems();
    }

    load(id) {
        this.topicItemService.find(id)
            .subscribe((topicItemResponse: HttpResponse<TopicItem>) => {
                this.topicItem = topicItemResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTopicItems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'topicItemListModification',
            (response) => this.load(this.topicItem.id)
        );
    }
}
