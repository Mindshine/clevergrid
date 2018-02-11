import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ClevergridQuizModule } from './quiz/quiz.module';
import { ClevergridTopicModule } from './topic/topic.module';
import { ClevergridTopicItemModule } from './topic-item/topic-item.module';
import { ClevergridTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ClevergridQuizModule,
        ClevergridTopicModule,
        ClevergridTopicItemModule,
        ClevergridTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ClevergridEntityModule {}
