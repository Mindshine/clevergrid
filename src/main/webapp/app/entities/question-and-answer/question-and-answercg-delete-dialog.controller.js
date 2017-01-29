(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('QuestionAndAnswerCgDeleteController',QuestionAndAnswerCgDeleteController);

    QuestionAndAnswerCgDeleteController.$inject = ['$uibModalInstance', 'entity', 'QuestionAndAnswer'];

    function QuestionAndAnswerCgDeleteController($uibModalInstance, entity, QuestionAndAnswer) {
        var vm = this;

        vm.questionAndAnswer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            QuestionAndAnswer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
