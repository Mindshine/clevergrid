import { BaseEntity } from './../../shared';

export class Tag implements BaseEntity {
    constructor(
        public id?: string,
        public tagName?: string,
    ) {
    }
}
