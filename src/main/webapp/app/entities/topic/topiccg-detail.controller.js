(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('TopicCgDetailController', TopicCgDetailController);

    TopicCgDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Topic'];

    function TopicCgDetailController($scope, $rootScope, $stateParams, previousState, entity, Topic) {
        var vm = this;

        vm.topic = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('clevergridApp:topicUpdate', function(event, result) {
            vm.topic = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
