(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('topiccg', {
            parent: 'entity',
            url: '/topiccg',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'clevergridApp.topic.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/topic/topicscg.html',
                    controller: 'TopicCgController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('topic');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('topiccg-detail', {
            parent: 'entity',
            url: '/topiccg/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'clevergridApp.topic.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/topic/topiccg-detail.html',
                    controller: 'TopicCgDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('topic');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Topic', function($stateParams, Topic) {
                    return Topic.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'topiccg',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('topiccg-detail.edit', {
            parent: 'topiccg-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/topic/topiccg-dialog.html',
                    controller: 'TopicCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Topic', function(Topic) {
                            return Topic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('topiccg.new', {
            parent: 'topiccg',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/topic/topiccg-dialog.html',
                    controller: 'TopicCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('topiccg', null, { reload: 'topiccg' });
                }, function() {
                    $state.go('topiccg');
                });
            }]
        })
        .state('topiccg.edit', {
            parent: 'topiccg',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/topic/topiccg-dialog.html',
                    controller: 'TopicCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Topic', function(Topic) {
                            return Topic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('topiccg', null, { reload: 'topiccg' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('topiccg.delete', {
            parent: 'topiccg',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/topic/topiccg-delete-dialog.html',
                    controller: 'TopicCgDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Topic', function(Topic) {
                            return Topic.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('topiccg', null, { reload: 'topiccg' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
