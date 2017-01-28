define(['app'], function (app) {
	require(['service/search'], function () { 
		app.controller('searchBarController', function ($scope,$location,$route,searchService) {
			$scope.search = "";
			
			$scope.paste = function (event) {
				var item = event.clipboardData.items[0];
				item.getAsString(function (data) {
					$scope.search = data;
				    $scope.$apply();
				});
			};
				
			$scope.$watch('search', function() {
				if($scope.search.length < 3){
					return;
				}
				else{
					searchService.setQ($scope.search);
					if($location.path() != "/search"){
						$location.path( "/search" );
					}
					else{
						$route.reload();
					}
				}
			});
		});
	});
})