(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gamecg', {
            parent: 'entity',
            url: '/gamecg',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'clevergridApp.game.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/game/gamescg.html',
                    controller: 'GameCgController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('game');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('gamecg-detail', {
            parent: 'entity',
            url: '/gamecg/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'clevergridApp.game.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/game/gamecg-detail.html',
                    controller: 'GameCgDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('game');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Game', function($stateParams, Game) {
                    return Game.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gamecg',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gamecg-detail.edit', {
            parent: 'gamecg-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/game/gamecg-dialog.html',
                    controller: 'GameCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Game', function(Game) {
                            return Game.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gamecg.new', {
            parent: 'gamecg',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/game/gamecg-dialog.html',
                    controller: 'GameCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                createdDate: null,
                                createdBy: null,
                                lastModifiedDate: null,
                                lastModifiedBy: null,
                                isPublic: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gamecg', null, { reload: 'gamecg' });
                }, function() {
                    $state.go('gamecg');
                });
            }]
        })
        .state('gamecg.edit', {
            parent: 'gamecg',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/game/gamecg-dialog.html',
                    controller: 'GameCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Game', function(Game) {
                            return Game.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gamecg', null, { reload: 'gamecg' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gamecg.delete', {
            parent: 'gamecg',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/game/gamecg-delete-dialog.html',
                    controller: 'GameCgDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Game', function(Game) {
                            return Game.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gamecg', null, { reload: 'gamecg' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
