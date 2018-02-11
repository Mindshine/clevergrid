import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ClevergridSharedModule } from '../../shared';
import {
    TopicItemService,
    TopicItemPopupService,
    TopicItemComponent,
    TopicItemDetailComponent,
    TopicItemDialogComponent,
    TopicItemPopupComponent,
    TopicItemDeletePopupComponent,
    TopicItemDeleteDialogComponent,
    topicItemRoute,
    topicItemPopupRoute,
} from './';

const ENTITY_STATES = [
    ...topicItemRoute,
    ...topicItemPopupRoute,
];

@NgModule({
    imports: [
        ClevergridSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TopicItemComponent,
        TopicItemDetailComponent,
        TopicItemDialogComponent,
        TopicItemDeleteDialogComponent,
        TopicItemPopupComponent,
        TopicItemDeletePopupComponent,
    ],
    entryComponents: [
        TopicItemComponent,
        TopicItemDialogComponent,
        TopicItemPopupComponent,
        TopicItemDeleteDialogComponent,
        TopicItemDeletePopupComponent,
    ],
    providers: [
        TopicItemService,
        TopicItemPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClevergridTopicItemModule {}
