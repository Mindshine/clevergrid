(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('TagCgDeleteController',TagCgDeleteController);

    TagCgDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tag'];

    function TagCgDeleteController($uibModalInstance, entity, Tag) {
        var vm = this;

        vm.tag = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tag.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
