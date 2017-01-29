(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('TopicCgController', TopicCgController);

    TopicCgController.$inject = ['$scope', '$state', 'Topic'];

    function TopicCgController ($scope, $state, Topic) {
        var vm = this;

        vm.topics = [];

        loadAll();

        function loadAll() {
            Topic.query(function(result) {
                vm.topics = result;
                vm.searchQuery = null;
            });
        }
    }
})();
