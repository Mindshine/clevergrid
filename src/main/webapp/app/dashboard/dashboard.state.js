(function() {
    'use strict';

    angular.module('clevergridApp').config(stateConfig);

    stateConfig.$inject = [ '$stateProvider' ];

    function stateConfig($stateProvider) {
        $stateProvider.state('dashboard', {
            parent : 'app',
            url : '/dashboard',
            data : {
                authorities : [ 'ROLE_USER' ],
                pageTitle : 'clevergridApp.dashboard.home.title'
            },
            views : {
                'content@' : {
                    templateUrl : 'app/dashboard/dashboard.html',
                    controller : 'DashboardController',
                    controllerAs : 'vm'
                }
            },
            resolve : {
                mainTranslatePartialLoader : [ '$translate', '$translatePartialLoader',
                        function($translate, $translatePartialLoader) {
                            $translatePartialLoader.addPart('dashboard');
                            $translatePartialLoader.addPart('global');
                            return $translate.refresh();
                        } ]
            }
        });
    }
})();