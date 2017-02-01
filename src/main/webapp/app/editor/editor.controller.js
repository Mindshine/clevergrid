(function() {
    'use strict';

    angular.module('clevergridApp').controller('EditorController', EditorController);

    EditorController.$inject = [ '$scope', '$state', 'Editor' ];

    function EditorController($scope, $state, Editor) {
        var vm = this;

        vm.games = [];

        loadAll();

        function loadAll() {
            Editor.query(function(result) {
                vm.games = result;
                vm.searchQuery = null;
            });
        }
    }
})();
