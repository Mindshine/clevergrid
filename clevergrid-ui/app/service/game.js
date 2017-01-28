define(['app'], function (app) {
    app.service('gameService', function() {
		
		this.gameData;
	
		this.getActiveGame = function() {
			return this.gameData;
		};
	
		this.setActiveGame = function(data) {
			this.gameData = data;
		};
	});
})