(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('QuestionAndAnswerCgDialogController', QuestionAndAnswerCgDialogController);

    QuestionAndAnswerCgDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'QuestionAndAnswer'];

    function QuestionAndAnswerCgDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, QuestionAndAnswer) {
        var vm = this;

        vm.questionAndAnswer = entity;
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
            if (vm.questionAndAnswer.id !== null) {
                QuestionAndAnswer.update(vm.questionAndAnswer, onSaveSuccess, onSaveError);
            } else {
                QuestionAndAnswer.save(vm.questionAndAnswer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('clevergridApp:questionAndAnswerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
