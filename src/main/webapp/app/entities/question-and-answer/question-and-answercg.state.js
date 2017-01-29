(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('question-and-answercg', {
            parent: 'entity',
            url: '/question-and-answercg',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'clevergridApp.questionAndAnswer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-and-answer/question-and-answerscg.html',
                    controller: 'QuestionAndAnswerCgController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questionAndAnswer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('question-and-answercg-detail', {
            parent: 'entity',
            url: '/question-and-answercg/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'clevergridApp.questionAndAnswer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/question-and-answer/question-and-answercg-detail.html',
                    controller: 'QuestionAndAnswerCgDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('questionAndAnswer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'QuestionAndAnswer', function($stateParams, QuestionAndAnswer) {
                    return QuestionAndAnswer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'question-and-answercg',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('question-and-answercg-detail.edit', {
            parent: 'question-and-answercg-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-and-answer/question-and-answercg-dialog.html',
                    controller: 'QuestionAndAnswerCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionAndAnswer', function(QuestionAndAnswer) {
                            return QuestionAndAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-and-answercg.new', {
            parent: 'question-and-answercg',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-and-answer/question-and-answercg-dialog.html',
                    controller: 'QuestionAndAnswerCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                question: null,
                                answer: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('question-and-answercg', null, { reload: 'question-and-answercg' });
                }, function() {
                    $state.go('question-and-answercg');
                });
            }]
        })
        .state('question-and-answercg.edit', {
            parent: 'question-and-answercg',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-and-answer/question-and-answercg-dialog.html',
                    controller: 'QuestionAndAnswerCgDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['QuestionAndAnswer', function(QuestionAndAnswer) {
                            return QuestionAndAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-and-answercg', null, { reload: 'question-and-answercg' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('question-and-answercg.delete', {
            parent: 'question-and-answercg',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/question-and-answer/question-and-answercg-delete-dialog.html',
                    controller: 'QuestionAndAnswerCgDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['QuestionAndAnswer', function(QuestionAndAnswer) {
                            return QuestionAndAnswer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('question-and-answercg', null, { reload: 'question-and-answercg' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
