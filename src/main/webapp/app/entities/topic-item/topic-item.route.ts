import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TopicItemComponent } from './topic-item.component';
import { TopicItemDetailComponent } from './topic-item-detail.component';
import { TopicItemPopupComponent } from './topic-item-dialog.component';
import { TopicItemDeletePopupComponent } from './topic-item-delete-dialog.component';

export const topicItemRoute: Routes = [
    {
        path: 'topic-item',
        component: TopicItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clevergridApp.topicItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'topic-item/:id',
        component: TopicItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clevergridApp.topicItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const topicItemPopupRoute: Routes = [
    {
        path: 'topic-item-new',
        component: TopicItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clevergridApp.topicItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'topic-item/:id/edit',
        component: TopicItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clevergridApp.topicItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'topic-item/:id/delete',
        component: TopicItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clevergridApp.topicItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
