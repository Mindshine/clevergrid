(function() {
    'use strict';
    angular.module('clevergridApp').controller('CategoriesController', CategoriesController);

    CategoriesController.$inject = [ '$state', '$scope', 'Category' ];

    function CategoriesController($state, $scope, Category) {
        var vm = this;

        vm.data = [ {
            'id' : 1,
            'title' : 'node1',
            'nodes' : [ {
                'id' : 11,
                'title' : 'node1.1',
                'nodes' : [ {
                    'id' : 111,
                    'title' : 'node1.1.1',
                    'nodes' : []
                } ]
            }, {
                'id' : 12,
                'title' : 'node1.2',
                'nodes' : []
            } ]
        }, {
            'id' : 2,
            'title' : 'node2',
            'nodrop' : true, // An arbitrary property to check in custom
            // template for
            // nodrop-enabled
            'nodes' : [ {
                'id' : 21,
                'title' : 'node2.1',
                'nodes' : []
            }, {
                'id' : 22,
                'title' : 'node2.2',
                'nodes' : []
            } ]
        }, {
            'id' : 3,
            'title' : 'node3',
            'nodes' : [ {
                'id' : 31,
                'title' : 'node3.1',
                'nodes' : []
            } ]
        } ];

        vm.remove = remove;
        vm.toggle = toggle;
        vm.moveLastToTheBeginning = moveLastToTheBeginning;
        vm.newSubItem = newSubItem;
        vm.collapseAll = collapseAll;
        vm.expandAll = expandAll;
        vm.$state = $state;
        $scope.data = vm.data;
        vm.$scope = $scope;

        function remove(scope) {
            scope.remove();
        }

        function toggle(scope) {
            scope.toggle();
        }

        function moveLastToTheBeginning() {
            var a = vm.$scope.data.pop();
            vm.$scope.data.splice(0, 0, a);
        }

        function newSubItem(scope) {
            var nodeData = scope.$modelValue;
            nodeData.nodes.push({
                id : nodeData.id * 10 + nodeData.nodes.length,
                title : nodeData.title + '.' + (nodeData.nodes.length + 1),
                nodes : []
            });
        }

        function collapseAll() {
            vm.$scope.$broadcast('angular-ui-tree:collapse-all');
        }

        function expandAll() {
            vm.$scope.$broadcast('angular-ui-tree:expand-all');
        }

    }
}());