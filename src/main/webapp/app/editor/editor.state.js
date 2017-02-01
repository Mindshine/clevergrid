(function() {
    'use strict';

    angular.module('clevergridApp').config(stateConfig);

    stateConfig.$inject = [ '$stateProvider' ];

    function stateConfig($stateProvider) {
        $stateProvider.state(
                'editor',
                {
                    parent : 'app',
                    url : '/editor/{id}',
                    data : {
                        authorities : [ 'ROLE_USER' ],
                        pageTitle : 'clevergridApp.editor.title'
                    },
                    views : {
                        'content@' : {
                            templateUrl : 'app/editor/editor.html',
                            controller : 'EditorController',
                            controllerAs : 'vm'
                        }
                    },
                    resolve : {
                        translatePartialLoader : [ '$translate', '$translatePartialLoader',
                                function($translate, $translatePartialLoader) {
                                    $translatePartialLoader.addPart('editor');
                                    return $translate.refresh();
                                } ],
                        entity : [ '$stateParams', 'Game', function($stateParams, Game) {
                            return Game.get({
                                id : $stateParams.id
                            }).$promise;
                        } ]
                    }
                }).state(
                'editor.create',
                {
                    parent : 'app',
                    url : '/editor',
                    data : {
                        authorities : [ 'ROLE_USER' ],
                        pageTitle : 'clevergridApp.editor.create.title'
                    },
                    views : {
                        'content@' : {
                            templateUrl : 'app/editor/editor-create.html',
                            controller : 'EditorCreateController',
                            controllerAs : 'vm'
                        }
                    },
                    resolve : {
                        translatePartialLoader : [ '$translate', '$translatePartialLoader',
                                function($translate, $translatePartialLoader) {
                                    $translatePartialLoader.addPart('editor');
                                    return $translate.refresh();
                                } ]
                    }
                });
    }
})();
