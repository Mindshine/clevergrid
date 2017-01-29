(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('QuestionAndAnswerCgDetailController', QuestionAndAnswerCgDetailController);

    QuestionAndAnswerCgDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'QuestionAndAnswer'];

    function QuestionAndAnswerCgDetailController($scope, $rootScope, $stateParams, previousState, entity, QuestionAndAnswer) {
        var vm = this;

        vm.questionAndAnswer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('clevergridApp:questionAndAnswerUpdate', function(event, result) {
            vm.questionAndAnswer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
