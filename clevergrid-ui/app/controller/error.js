define(['app'], function (app) {
	app.controller('ErrorController', function($rootScope, $scope) {
		$scope.close = function(index) {
			$rootScope.errors.splice(index, 1);
		};
	});
})