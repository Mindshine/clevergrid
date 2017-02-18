(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('CategoryCgController', CategoryCgController);

    CategoryCgController.$inject = ['$scope', '$state', 'Category'];

    function CategoryCgController ($scope, $state, Category) {
        var vm = this;

        vm.categories = [];

        loadAll();

        function loadAll() {
            Category.query(function(result) {
                vm.categories = result;
                vm.searchQuery = null;
            });
        }
    }
})();
