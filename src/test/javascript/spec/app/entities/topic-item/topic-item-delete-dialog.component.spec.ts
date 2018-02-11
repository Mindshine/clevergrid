/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ClevergridTestModule } from '../../../test.module';
import { TopicItemDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/topic-item/topic-item-delete-dialog.component';
import { TopicItemService } from '../../../../../../main/webapp/app/entities/topic-item/topic-item.service';

describe('Component Tests', () => {

    describe('TopicItem Management Delete Component', () => {
        let comp: TopicItemDeleteDialogComponent;
        let fixture: ComponentFixture<TopicItemDeleteDialogComponent>;
        let service: TopicItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ClevergridTestModule],
                declarations: [TopicItemDeleteDialogComponent],
                providers: [
                    TopicItemService
                ]
            })
            .overrideTemplate(TopicItemDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TopicItemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TopicItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete('123');
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith('123');
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
