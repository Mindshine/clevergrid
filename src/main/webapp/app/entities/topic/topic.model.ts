import { BaseEntity } from './../../shared';

export class Topic implements BaseEntity {
    constructor(
        public id?: string,
        public title?: string,
    ) {
    }
}
