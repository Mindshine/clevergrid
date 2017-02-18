(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('CategoryCgDetailController', CategoryCgDetailController);

    CategoryCgDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Category'];

    function CategoryCgDetailController($scope, $rootScope, $stateParams, previousState, entity, Category) {
        var vm = this;

        vm.category = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('clevergridApp:categoryUpdate', function(event, result) {
            vm.category = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
