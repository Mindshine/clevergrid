define(['app'], function (app) {
	app.service('playService', function() {
		
		var qindexes;
		var dimension;
	
		this.getQIndexes = function() {
			return this.qindexes;
		};
	
		this.setQIndexes = function(data) {
			this.qindexes = data;
		};
	
		this.getDimension = function() {
			return this.dimension;
		}
	
		this.setDimension = function(dimension) {
			this.dimension = dimension;
		}
		
	});
})