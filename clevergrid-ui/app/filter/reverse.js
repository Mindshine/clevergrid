define(['app'], function (app) {
	app.filter('reverse', function() {
		return function(items) {
			return items ? items.slice().reverse() : items;
		};
	});
})