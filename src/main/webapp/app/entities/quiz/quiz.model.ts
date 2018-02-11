import { BaseEntity } from './../../shared';

export class Quiz implements BaseEntity {
    constructor(
        public id?: string,
        public title?: string,
        public createdDate?: any,
        public createdBy?: string,
        public lastModifiedDate?: any,
        public lastModifiedBy?: string,
    ) {
    }
}
