define(['app'], function (app) {
	app.directive('logoutLink', function() {
		return {
			restrict : 'A',
			link : function link(scope, element) {
				element.bind('click', function(event) {
					event.preventDefault();
					document.getElementById('logout').submit();
				});
			}
		};
	});
})