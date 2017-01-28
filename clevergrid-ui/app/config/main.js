define(['app'], function (app) {
	require(['factory/connection-interceptor', 'controller/game-manager', 'controller/search', 'controller/search-bar'], function () { 
		app.config(function($routeProvider, $httpProvider){
		    $httpProvider.interceptors.push('connectionInterceptor');
		    
		    $routeProvider
		        .when("/dashboard", {
		        	templateUrl: "dashboard",
		        	controller: "gameManagerController"
		        })
		        .when("/search", {
		        	templateUrl: "search",
		        	controller: "searchController"
		        })
		        .when("/settings", {
		        	templateUrl: "settings",
		        	controller: "gameManagerController"
		        })
		        .when("/profile", {
		        	templateUrl: "userprofile",
		        	controller: "gameManagerController"
		        })
		        .when("/help", {
		        	templateUrl: "help",
		        	controller: "gameManagerController"
		        })
		        .when("/createnewgame", {
		        	templateUrl: "createnewgame",
		        	controller: "gameManagerController"
		        })
		        .when("/editgame", {
		        	templateUrl: "editgame",
		        	controller: "gameManagerController"
		        })
		        .when("/playgame", {
		        	templateUrl: "playgame",
		        	controller: "gameManagerController"
		        })
		        .when("/taglist", {
		        	templateUrl: "taglist",
		        	controller: "gameManagerController"
		        })
		        .otherwise({redirectTo: "/dashboard"});
		});
		
		app.run(function(editableOptions) {
			editableOptions.theme = 'bs3';
		});
	});
})