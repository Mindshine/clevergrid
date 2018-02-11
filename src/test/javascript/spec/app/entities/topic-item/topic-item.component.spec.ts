/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ClevergridTestModule } from '../../../test.module';
import { TopicItemComponent } from '../../../../../../main/webapp/app/entities/topic-item/topic-item.component';
import { TopicItemService } from '../../../../../../main/webapp/app/entities/topic-item/topic-item.service';
import { TopicItem } from '../../../../../../main/webapp/app/entities/topic-item/topic-item.model';

describe('Component Tests', () => {

    describe('TopicItem Management Component', () => {
        let comp: TopicItemComponent;
        let fixture: ComponentFixture<TopicItemComponent>;
        let service: TopicItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClevergridTestModule],
                declarations: [TopicItemComponent],
                providers: [
                    TopicItemService
                ]
            })
            .overrideTemplate(TopicItemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TopicItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TopicItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TopicItem('123')],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.topicItems[0]).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
