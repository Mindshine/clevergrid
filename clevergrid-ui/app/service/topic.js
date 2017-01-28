define(['app'], function (app) {
    app.service('topicsService', function() {
		
		var topicsData;
	
		this.getActiveTopics = function() {
			return this.topicsData;
		};
	
		this.setActiveTopics = function(data) {
			this.topicsData = data;
		};
		
	});
})