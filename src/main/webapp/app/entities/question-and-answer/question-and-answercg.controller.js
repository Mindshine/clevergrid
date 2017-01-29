(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('QuestionAndAnswerCgController', QuestionAndAnswerCgController);

    QuestionAndAnswerCgController.$inject = ['$scope', '$state', 'QuestionAndAnswer'];

    function QuestionAndAnswerCgController ($scope, $state, QuestionAndAnswer) {
        var vm = this;

        vm.questionAndAnswers = [];

        loadAll();

        function loadAll() {
            QuestionAndAnswer.query(function(result) {
                vm.questionAndAnswers = result;
                vm.searchQuery = null;
            });
        }
    }
})();
