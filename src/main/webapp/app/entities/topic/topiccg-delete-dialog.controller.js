(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('TopicCgDeleteController',TopicCgDeleteController);

    TopicCgDeleteController.$inject = ['$uibModalInstance', 'entity', 'Topic'];

    function TopicCgDeleteController($uibModalInstance, entity, Topic) {
        var vm = this;

        vm.topic = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Topic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
