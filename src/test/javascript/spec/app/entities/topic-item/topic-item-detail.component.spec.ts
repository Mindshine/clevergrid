/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ClevergridTestModule } from '../../../test.module';
import { TopicItemDetailComponent } from '../../../../../../main/webapp/app/entities/topic-item/topic-item-detail.component';
import { TopicItemService } from '../../../../../../main/webapp/app/entities/topic-item/topic-item.service';
import { TopicItem } from '../../../../../../main/webapp/app/entities/topic-item/topic-item.model';

describe('Component Tests', () => {

    describe('TopicItem Management Detail Component', () => {
        let comp: TopicItemDetailComponent;
        let fixture: ComponentFixture<TopicItemDetailComponent>;
        let service: TopicItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClevergridTestModule],
                declarations: [TopicItemDetailComponent],
                providers: [
                    TopicItemService
                ]
            })
            .overrideTemplate(TopicItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TopicItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TopicItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TopicItem('123')
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith('123');
                expect(comp.topicItem).toEqual(jasmine.objectContaining({id: '123'}));
            });
        });
    });

});
