import { BaseEntity } from './../../shared';

export class TopicItem implements BaseEntity {
    constructor(
        public id?: string,
        public question?: string,
        public answer?: string,
    ) {
    }
}
