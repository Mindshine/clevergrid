import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Quiz } from './quiz.model';
import { QuizService } from './quiz.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-quiz',
    templateUrl: './quiz.component.html'
})
export class QuizComponent implements OnInit, OnDestroy {
quizzes: Quiz[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private quizService: QuizService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.quizService.query().subscribe(
            (res: HttpResponse<Quiz[]>) => {
                this.quizzes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInQuizzes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Quiz) {
        return item.id;
    }
    registerChangeInQuizzes() {
        this.eventSubscriber = this.eventManager.subscribe('quizListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
