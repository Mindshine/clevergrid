(function() {
    'use strict';

    angular
        .module('clevergridApp')
        .controller('EditorCreateController', EditorCreateController);

    EditorCreateController.$inject = ['$timeout', '$scope', '$stateParams', 'entity', 'Editor'];

    function EditorCreateController ($timeout, $scope, $stateParams, entity, Editor) {
        var vm = this;

        vm.game = entity;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;
            if (vm.game.id !== null) {
                Editor.update(vm.game, onSaveSuccess, onSaveError);
            } else {
            	Editor.save(vm.game, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('clevergridApp:gameUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
