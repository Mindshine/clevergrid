(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('TopicCgDialogController', TopicCgDialogController);

    TopicCgDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Topic'];

    function TopicCgDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Topic) {
        var vm = this;

        vm.topic = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.topic.id !== null) {
                Topic.update(vm.topic, onSaveSuccess, onSaveError);
            } else {
                Topic.save(vm.topic, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('clevergridApp:topicUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
