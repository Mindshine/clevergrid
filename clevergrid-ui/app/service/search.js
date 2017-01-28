define(['app'], function (app) {
	app.service('searchService', function($http) {
		
		var urlBase="";
		var _q;

		this.getQ = function() {
			return this._q === undefined ? "" : this._q;
		}
		
		this.setQ = function(q) {
			this._q = q;
		}
		
		this.searchGames = function() {
			return $http.get(urlBase + "/games/search/findByTitleLike",{
				params: { q:"%" + this.getQ() + "%" }
			}).then(function(response) {
				return response.data._embedded === undefined ? [] : response.data._embedded.games;
			})
		}
		
		this.searchTopics = function() {
			return $http.get(urlBase + "/topics/search/findByTitleLike",{
				params: { q:"%" + this.getQ() + "%" }
			}).then(function(response) {
				return response.data._embedded === undefined ? [] : response.data._embedded.topics;
			})
		}
		
		this.searchQuestions = function() {
			return $http.get(urlBase + "/questionAndAnswers/search/findByQuestionLike",{
				params: { q:"%" + this.getQ() + "%" }
			}).then(function(response) {
				return response.data._embedded === undefined ? [] : response.data._embedded.questionAndAnswers;
			})
		}
		
		this.searchAnswers = function() {
			return $http.get(urlBase + "/questionAndAnswers/search/findByAnswerLike",{
				params: { q:"%" + this.getQ() + "%" }
			}).then(function(response) {
				return response.data._embedded === undefined ? [] : response.data._embedded.questionAndAnswers;
			})
		}
		
	});

})