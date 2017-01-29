(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('TagCgDetailController', TagCgDetailController);

    TagCgDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tag'];

    function TagCgDetailController($scope, $rootScope, $stateParams, previousState, entity, Tag) {
        var vm = this;

        vm.tag = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('clevergridApp:tagUpdate', function(event, result) {
            vm.tag = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
