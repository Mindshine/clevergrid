import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Quiz } from './quiz.model';
import { QuizPopupService } from './quiz-popup.service';
import { QuizService } from './quiz.service';

@Component({
    selector: 'jhi-quiz-dialog',
    templateUrl: './quiz-dialog.component.html'
})
export class QuizDialogComponent implements OnInit {

    quiz: Quiz;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private quizService: QuizService,
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
        if (this.quiz.id !== undefined) {
            this.subscribeToSaveResponse(
                this.quizService.update(this.quiz));
        } else {
            this.subscribeToSaveResponse(
                this.quizService.create(this.quiz));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Quiz>>) {
        result.subscribe((res: HttpResponse<Quiz>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Quiz) {
        this.eventManager.broadcast({ name: 'quizListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-quiz-popup',
    template: ''
})
export class QuizPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private quizPopupService: QuizPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.quizPopupService
                    .open(QuizDialogComponent as Component, params['id']);
            } else {
                this.quizPopupService
                    .open(QuizDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
