/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ClevergridTestModule } from '../../../test.module';
import { QuizComponent } from '../../../../../../main/webapp/app/entities/quiz/quiz.component';
import { QuizService } from '../../../../../../main/webapp/app/entities/quiz/quiz.service';
import { Quiz } from '../../../../../../main/webapp/app/entities/quiz/quiz.model';

describe('Component Tests', () => {

    describe('Quiz Management Component', () => {
        let comp: QuizComponent;
        let fixture: ComponentFixture<QuizComponent>;
        let service: QuizService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClevergridTestModule],
                declarations: [QuizComponent],
                providers: [
                    QuizService
                ]
            })
            .overrideTemplate(QuizComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuizComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuizService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Quiz('123')],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.quizzes[0]).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
