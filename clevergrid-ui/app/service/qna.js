define(['app'], function (app) {
    app.service('qnasService', function() {
		
		var qnasData;
	
		this.getActiveQnAs = function() {
			return this.qnasData;
		};
	
		this.setActiveQnAs = function(data) {
			this.qnasData = data;
		};
		
	});
})