define(['app'], function (app) {
	require(['service/search', 'service/game'], function () { 

		app.controller('searchController', function ($scope,$sce,$http,$location,$route,searchService,gameService) {
	
			var urlBase="";
			$http.defaults.headers.post["Content-Type"] = "application/json";
	
			$scope.$watch(function(){
			    return searchService.getQ;
			}, function (newValue) {
				searchService.searchGames().then(function(searchData) {
					$scope.games = searchData;
				});
				searchService.searchTopics().then(function(searchData) {
					$scope.topics = searchData;
				});
				searchService.searchQuestions().then(function(searchData) {
					$scope.questions = searchData;
				});
				searchService.searchAnswers().then(function(searchData) {
					$scope.answers = searchData;
				});
			});
			
			$scope.renderHtml = function(html_code) {
			    return $sce.trustAsHtml(html_code);
			};
			
	
			$scope.editGame = function editGame(gameUri) {
				if (gameUri != "") {
					$http.get(gameUri).success(function(data) {
						gameService.setActiveGame(data);
						$location.path( "/editgame" );
					})
				}
			}
	
			$scope.editGameByTopic = function editGameByTopic(topicUri) {
				if (topicUri != "") {
					$http.get(topicUri).success(function(data) {
						$http.get(data._links.game.href).success(function(data) {
							gameService.setActiveGame(data);
							$location.path( "/editgame" );
						})
					})
				}
			}
	
		});
	});
})