(function() {
	'use strict';

	angular.module('clevergridApp').controller('DashboardController',
			DashboardController);

	DashboardController.$inject = [ '$scope', '$state', 'Dashboard' ];

	function DashboardController($scope, $state, Dashboard) {
		var vm = this;

		vm.games = [];

		loadAll();

		function loadAll() {
			Dashboard.query(function(result) {
				vm.games = result;
				vm.searchQuery = null;
			});
		}
	}
})();
