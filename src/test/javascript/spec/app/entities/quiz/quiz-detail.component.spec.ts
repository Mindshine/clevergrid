/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ClevergridTestModule } from '../../../test.module';
import { QuizDetailComponent } from '../../../../../../main/webapp/app/entities/quiz/quiz-detail.component';
import { QuizService } from '../../../../../../main/webapp/app/entities/quiz/quiz.service';
import { Quiz } from '../../../../../../main/webapp/app/entities/quiz/quiz.model';

describe('Component Tests', () => {

    describe('Quiz Management Detail Component', () => {
        let comp: QuizDetailComponent;
        let fixture: ComponentFixture<QuizDetailComponent>;
        let service: QuizService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClevergridTestModule],
                declarations: [QuizDetailComponent],
                providers: [
                    QuizService
                ]
            })
            .overrideTemplate(QuizDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuizDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuizService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Quiz('123')
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith('123');
                expect(comp.quiz).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
