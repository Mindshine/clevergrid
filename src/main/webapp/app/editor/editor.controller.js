(function() {
    'use strict';

    angular.module('clevergridApp').controller('EditorController', EditorController);

    EditorController.$inject = [ '$scope', '$state', 'Editor' ];

    function EditorController($scope, $state, entity, Editor) {
        var vm = this;

        vm.game = entity;

    }
})();
