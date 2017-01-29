(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tagcg', {
            parent: 'entity',
            url: '/tagcg',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'clevergridApp.tag.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tag/tagscg.html',
                    controller: 'TagCgController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tag');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tagcg-detail', {
            parent: 'entity',
            url: '/tagcg/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'clevergridApp.tag.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tag/tagcg-detail.html',
                    controller: 'TagCgDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tag');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tag', function($stateParams, Tag) {
                    return Tag.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tagcg',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tagcg-detail.edit', {
            parent: 'tagcg-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tag/tagcg-dialog.html',
                    controller: 'TagCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tag', function(Tag) {
                            return Tag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tagcg.new', {
            parent: 'tagcg',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tag/tagcg-dialog.html',
                    controller: 'TagCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tagName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tagcg', null, { reload: 'tagcg' });
                }, function() {
                    $state.go('tagcg');
                });
            }]
        })
        .state('tagcg.edit', {
            parent: 'tagcg',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tag/tagcg-dialog.html',
                    controller: 'TagCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tag', function(Tag) {
                            return Tag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tagcg', null, { reload: 'tagcg' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tagcg.delete', {
            parent: 'tagcg',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tag/tagcg-delete-dialog.html',
                    controller: 'TagCgDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tag', function(Tag) {
                            return Tag.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tagcg', null, { reload: 'tagcg' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
