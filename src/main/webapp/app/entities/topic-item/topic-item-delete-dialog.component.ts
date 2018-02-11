import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TopicItem } from './topic-item.model';
import { TopicItemPopupService } from './topic-item-popup.service';
import { TopicItemService } from './topic-item.service';

@Component({
    selector: 'jhi-topic-item-delete-dialog',
    templateUrl: './topic-item-delete-dialog.component.html'
})
export class TopicItemDeleteDialogComponent {

    topicItem: TopicItem;

    constructor(
        private topicItemService: TopicItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.topicItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'topicItemListModification',
                content: 'Deleted an topicItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-topic-item-delete-popup',
    template: ''
})
export class TopicItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private topicItemPopupService: TopicItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.topicItemPopupService
                .open(TopicItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
