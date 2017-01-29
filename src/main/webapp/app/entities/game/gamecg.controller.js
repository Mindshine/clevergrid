(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('GameCgController', GameCgController);

    GameCgController.$inject = ['$scope', '$state', 'Game'];

    function GameCgController ($scope, $state, Game) {
        var vm = this;

        vm.games = [];

        loadAll();

        function loadAll() {
            Game.query(function(result) {
                vm.games = result;
                vm.searchQuery = null;
            });
        }
    }
})();
