(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('TagCgController', TagCgController);

    TagCgController.$inject = ['$scope', '$state', 'Tag'];

    function TagCgController ($scope, $state, Tag) {
        var vm = this;

        vm.tags = [];

        loadAll();

        function loadAll() {
            Tag.query(function(result) {
                vm.tags = result;
                vm.searchQuery = null;
            });
        }
    }
})();
