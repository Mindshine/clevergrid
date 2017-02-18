(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('category-cg', {
            parent: 'entity',
            url: '/category-cg',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'clevergridApp.category.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/category/categories-cg.html',
                    controller: 'CategoryCgController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('category');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('category-cg-detail', {
            parent: 'entity',
            url: '/category-cg/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'clevergridApp.category.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/category/category-cg-detail.html',
                    controller: 'CategoryCgDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('category');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Category', function($stateParams, Category) {
                    return Category.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'category-cg',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('category-cg-detail.edit', {
            parent: 'category-cg-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category/category-cg-dialog.html',
                    controller: 'CategoryCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Category', function(Category) {
                            return Category.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('category-cg.new', {
            parent: 'category-cg',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category/category-cg-dialog.html',
                    controller: 'CategoryCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                parent: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('category-cg', null, { reload: 'category-cg' });
                }, function() {
                    $state.go('category-cg');
                });
            }]
        })
        .state('category-cg.edit', {
            parent: 'category-cg',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category/category-cg-dialog.html',
                    controller: 'CategoryCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Category', function(Category) {
                            return Category.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('category-cg', null, { reload: 'category-cg' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('category-cg.delete', {
            parent: 'category-cg',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category/category-cg-delete-dialog.html',
                    controller: 'CategoryCgDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Category', function(Category) {
                            return Category.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('category-cg', null, { reload: 'category-cg' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
