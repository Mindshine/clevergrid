import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TopicComponent } from './topic.component';
import { TopicDetailComponent } from './topic-detail.component';
import { TopicPopupComponent } from './topic-dialog.component';
import { TopicDeletePopupComponent } from './topic-delete-dialog.component';

export const topicRoute: Routes = [
    {
        path: 'topic',
        component: TopicComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clevergridApp.topic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'topic/:id',
        component: TopicDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clevergridApp.topic.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const topicPopupRoute: Routes = [
    {
        path: 'topic-new',
        component: TopicPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clevergridApp.topic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'topic/:id/edit',
        component: TopicPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clevergridApp.topic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'topic/:id/delete',
        component: TopicDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'clevergridApp.topic.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
