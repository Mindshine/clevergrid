define(['app'], function (app) {
	app.factory('connectionInterceptor', function($q, $rootScope) {
		return {
			'responseError' : function(response) {
				if(response.status === 400 && typeof response.data === 'object') {
					$rootScope.errors = response.data;
				} else {
					$rootScope.errors = [ 'Server error: ' + response.statusText + "(" + response.status + ")" ];
				}
				return $q.reject(response);
			},

			'response' : function(response) {
				$rootScope.errors = null;
				return response;
			}
		};
	});
})