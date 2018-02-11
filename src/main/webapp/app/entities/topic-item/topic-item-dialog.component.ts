import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TopicItem } from './topic-item.model';
import { TopicItemPopupService } from './topic-item-popup.service';
import { TopicItemService } from './topic-item.service';

@Component({
    selector: 'jhi-topic-item-dialog',
    templateUrl: './topic-item-dialog.component.html'
})
export class TopicItemDialogComponent implements OnInit {

    topicItem: TopicItem;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private topicItemService: TopicItemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.topicItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.topicItemService.update(this.topicItem));
        } else {
            this.subscribeToSaveResponse(
                this.topicItemService.create(this.topicItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TopicItem>>) {
        result.subscribe((res: HttpResponse<TopicItem>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TopicItem) {
        this.eventManager.broadcast({ name: 'topicItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-topic-item-popup',
    template: ''
})
export class TopicItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private topicItemPopupService: TopicItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.topicItemPopupService
                    .open(TopicItemDialogComponent as Component, params['id']);
            } else {
                this.topicItemPopupService
                    .open(TopicItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
