(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('GameCgDeleteController',GameCgDeleteController);

    GameCgDeleteController.$inject = ['$uibModalInstance', 'entity', 'Game'];

    function GameCgDeleteController($uibModalInstance, entity, Game) {
        var vm = this;

        vm.game = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Game.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
