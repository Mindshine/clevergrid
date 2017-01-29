(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('GameCgDetailController', GameCgDetailController);

    GameCgDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Game'];

    function GameCgDetailController($scope, $rootScope, $stateParams, previousState, entity, Game) {
        var vm = this;

        vm.game = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('clevergridApp:gameUpdate', function(event, result) {
            vm.game = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
