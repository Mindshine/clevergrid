define(['app'], function (app) {
	require(['service/game', 'service/topic', 'service/qna', 'service/play', 'service/tag'], function () { 
	    app.controller('gameManagerController', function ($scope,$sce,$http,$location,$route,$timeout,$interval,gameService,topicsService,qnasService,playService,tagService) {
			
			var urlBase="";
			$http.defaults.headers.post["Content-Type"] = "application/json";
			
			function findAllGames(gamesUri){
		        $http.get(gamesUri).
		        success(function (data) {
		        	if(typeof $scope.page == 'undefined' && data.page && data.page.number < (data.page.totalPages - 1)){
		        		findAllGames(data._links.last.href);
		        		return;
		        	}
		            if (data._embedded != undefined) {
		                $scope.games = data._embedded.games;
		                $scope.games._links = data._links;
		                $scope.page = data.page;
			            findAllTags();
			            gameToTagRelations();
		            } else {
		                $scope.games = [];
		                $scope.games._links = {};
		                $scope.page = {};
		            }
		            $scope.title="";
		            $scope.public=true;
		            $scope.gametags = new Array($scope.games.length);
		            $scope.tagsByGameUri = {};
		        });
			}
			findAllGames(urlBase + '/games');
		
			function findAllTags() {
				$http.get(urlBase + '/tags').
		        success(function (data) {
		            if (data._embedded != undefined) {
		            	$scope.tags = data._embedded.tags;
		            	if(tagService.getSelectedTagName() != undefined){
							selectTag1(tagService.getSelectedTagName(), false);
						}
		            } else {
		            	$scope.tags = [];
		            }
		        });
			}
			
			function initGameTags(game) {
				$http.get(game._links.tags.href).success(function(data,status,headers,config) {
					if (data._embedded != undefined) {
						$scope.tagsByGames[game._links.self.href] = data._embedded.tags;
						for (var j = 0; j < data._embedded.tags.length; j++){
							var tag = data._embedded.tags[j];
						}
					}
				});
			}
			
			function gameToTagRelations(){
				$scope.gamesByTags = {};
				$scope.tagsByGames = {};
	            for (var i = 0; i < $scope.games.length; i++) {
					var game = $scope.games[i];
		            initGameTags(game);
				}
			}
			
			if(gameService.getActiveGame() != undefined){
				$scope.activeGame = gameService.getActiveGame();
				$scope.newTagName = "";
				console.log("Active game selected:" + $scope.activeGame.title);
				console.log("Attempt to find topics and questions:" + $scope.activeGame.title);
				findGameTopics($scope.activeGame._links.topics.href);
				findGameTags($scope.activeGame._links.tags.href);
			}
			if($scope.activeGame == undefined && ($location.path() == "/editgame" || $location.path() == "/playgame")) {
				$location.path( "/dashboard" );
			}
			if($location.path() == "/playgame"){
				if(playService.getDimension() != undefined){
					$scope.dimension = playService.getDimension();
				}
				if(playService.getQIndexes() != undefined){
					$scope.qindexes = playService.getQIndexes();
				}
			}
			
			$scope.showPrev = function showPrev(){
				return $scope.page ? $scope.page.number > 0 : false;
			}
			
			$scope.showNext = function showNext(){
				return $scope.page ? $scope.page.totalNumbers > 1 && ($scope.page.number < $scope.page.totalNumbers - 1) : false;
			}
			
			$scope.reloadGames = function reloadGames(linkNext) {
				if (linkNext && linkNext != "") {
					findAllGames(linkNext);
				}
			}
			
			// create a new game
			$scope.createNewGame = function createNewGame() {
				if($scope.title==""){
					alert("Insufficient Data! Please provide value for game title");
				}
				else{
					$http.post(urlBase + '/games', {
						title: $scope.title,
						public: $scope.public
					}).
					success(function(data, status, headers) {
						var newGameUri = headers()["location"];
						$http.get(newGameUri).success(function(data) {
							console.log("Game added with title:" + data.title);
							$scope.games.push(data);
							$scope.activeGame = data;
							gameService.setActiveGame(data);
							findGameTopics($scope.activeGame._links.topics.href);
							$location.path( "/editgame" );
						})
					});
				}
			};
			
			$scope.editGame = function editGame(gameUri) {
				if (gameUri != "") {
					$http.get(gameUri).success(function(data) {
						$scope.activeGame = data;
						gameService.setActiveGame(data);
						findGameTopics($scope.activeGame._links.topics.href);
						findGameTags($scope.activeGame._links.tags.href);
						if($location.path() != "/editgame"){
							$location.path( "/editgame" );
						}
						else{
							$route.reload();
						}
					})
				}
			}
			
			$scope.saveGame = function saveGame(gameUri) {
				$http.patch(gameUri,{
					title : $scope.activeGame.title,
					public : $scope.activeGame.public
				}).success(function(data, status, headers) {
					findAllGames(urlBase + '/games');
				});
			}
			
			$scope.removeGame = function removeGame(gameUri) {
				if (gameUri && gameUri != "") {
					if(confirm('Are you sure to delete this item?'))
					$http.delete(gameUri).success(function() {
						if($location.path() != "/dashboard"){
							$location.path( "/dashboard" );
						}
						else{
							$route.reload();
						}
					})
				}
			}
			
			$scope.playGame = function playGame(gameUri){
				$http.get(gameUri).success(function(data) {
					$scope.activeGame = data;
					gameService.setActiveGame(data);
					playService.setDimension(0);
					playService.setQIndexes([]);
					findGameTopics($scope.activeGame._links.topics.href);
					if($location.path() != "/playgame"){
						$location.path( "/playgame" );
					}
					else{
						$route.reload();
					}
				})
			}
			
			$scope.colorByTopicIdx = function colorByTopicIdx(topicIdx) {
				for(var i = 0; i < $scope.players.length; i++){
					if($scope.players[i].topic == topicIdx) return $scope.colors[i];
				}
				return "default";
			}
			
			$scope.renderHtml = function(html_code)
			{
			    return $sce.trustAsHtml(html_code);
			};
			
			var stop;
			var checkTimeout = false;
		
			$scope.timeToAnswer = 0;// turn it off
		
			$scope.selectQuestion = function selectQuestion(qIdx){
				var topicIdx = $scope.qindexes[qIdx].t;
				var qnaIdx = $scope.qindexes[qIdx].q;
				$scope.topicIdx = topicIdx;
				$scope.qnaIdx = qnaIdx;
				$scope.qColor = $scope.colorByTopicIdx(topicIdx);
				$scope.question = $scope.qnas[topicIdx][qnaIdx].question;
				$scope.answer = $scope.qnas[topicIdx][qnaIdx].answer;
				$scope.showAnswer = false;
				$scope.selectedQuestions.push(qIdx);
				
				if(checkTimeout){
					$scope.timeToAnswer = 60;// seconds
					stop = $interval(function() {
			            if ($scope.timeToAnswer > 0) {
			            	$scope.timeToAnswer--;
			            } else {
			            	$scope.stopTime();
			            	$scope.takeAnswer(false);
			            	$("#qModal").modal('hide');
			            }
			        }, 1000);
				}
			}
		
			$scope.stopTime = function() {
		        if (angular.isDefined(stop)) {
		        	$interval.cancel(stop);
		        	stop = undefined;
		        }
			};
		      
			$scope.$on('$destroy', function() {
				// Make sure that the interval is destroyed too
				$scope.stopTime();
			});
		
			$scope.takeAnswer =  function takeAnswer(isCorrect) {
				if(isCorrect){
					$scope.players[$scope.playerIdx].score += ($scope.players[$scope.playerIdx].topic == $scope.topicIdx ? 1 : 2);			
				}
				
				$scope.playerIdx++;
				if($scope.playerIdx == $scope.players.length)$scope.playerIdx = 0;
			}
		
			$scope.addTopic = function addTopic(){
				if($scope.topics == undefined){
					$scope.topics = [];
				}
				$scope.topics.push({"title":""});
				topicsService.setActiveTopics($scope.topics);
				if($scope.topics.length >= 4){
					$(".row.topics>div.col-md-6").last().hide();
				}
			}
			
			$scope.selectTopic = function selectTopic(topicIdx){
				$scope.topicIdx = topicIdx;
			}
			
			$scope.saveTopic = function saveTopic(idx){
				if (idx > -1) {
					if ($scope.topics[idx]._links != undefined){
						$http.patch($scope.topics[idx]._links.self.href,{
							title : $scope.topics[idx].title
						}).success(function(data, status, headers) {
							$http.get($scope.topics[idx]._links.self.href).success(function(data) {
								console.log("Topic updated with title:" + data.title);
								$scope.topics[idx] = data;
								topicsService.setActiveTopics($scope.topics);
							})
						});
					}
					else{
						$http.post(urlBase + "/topics",{
							game : $scope.activeGame._links.self.href, 
							title : $scope.topics[idx].title
						}).success(function(data, status, headers) {
							var newTopicUri = headers()["location"];
							$http.get(newTopicUri).success(function(data) {
								console.log("Topic added with title:" + data.title);
								$scope.topics[idx] = data;
								topicsService.setActiveTopics($scope.topics);
							})
						});
					}
				}
			}
			
			$scope.removeTopic = function removeTopic(idx){
				if (idx > -1) {
					if ($scope.topics[idx]._links != undefined){
						$http.delete($scope.topics[idx]._links.self.href).success(function(data, status, headers) {
							console.log("Topic deleted:" + $scope.topics[idx].title);
							$scope.topics.splice(idx, 1);
							topicsService.setActiveTopics($scope.topics);
							if($scope.topics.length < 4){
								$(".row.topics>div.col-md-6").last().show();
							}
						});
					}
					else {
						$scope.topics.splice(idx, 1);
						topicsService.setActiveTopics($scope.topics);
						if($scope.topics.length < 4){
							$(".row.topics>div.col-md-6").last().show();
						}
					}
				}
			}
			
			$scope.addQuestion = function addQuestion(topicIdx) {
				if($scope.qnas == undefined){
					$scope.qnas = [];
				}
				if($scope.qnas[topicIdx] == undefined){
					$scope.qnas[topicIdx] = [];
				}
				if($scope.qnas[topicIdx].length >= 15){
					return;
				}
				$scope.topicIdx = topicIdx;
				$scope.qnaIdx = $scope.qnas[topicIdx].length;
				$scope.qnas[topicIdx].push({"question":"","answer":""});
				$scope.question = "";
				$scope.answer = "";
				qnasService.setActiveQnAs($scope.qnas);
			}
		
			$scope.editQuestion = function editQuestion(topicIdx, qnaIdx){
				$scope.topicIdx = topicIdx;
				$scope.qnaIdx = qnaIdx;
				$scope.question = $scope.qnas[topicIdx][qnaIdx].question;
				$scope.answer = $scope.qnas[topicIdx][qnaIdx].answer;
			}
		
		
			$scope.saveQuestion = function saveQuestion() {
				$scope.qnas[$scope.topicIdx][$scope.qnaIdx].question = $scope.question;
				$scope.qnas[$scope.topicIdx][$scope.qnaIdx].answer = $scope.answer;
				qnasService.setActiveQnAs($scope.qnas);
				if ($scope.qnas[$scope.topicIdx][$scope.qnaIdx]._links != undefined){
					$http.patch($scope.qnas[$scope.topicIdx][$scope.qnaIdx]._links.self.href,{
						question : $scope.qnas[$scope.topicIdx][$scope.qnaIdx].question,
						answer : $scope.qnas[$scope.topicIdx][$scope.qnaIdx].answer
					}).success(function(data, status, headers) {
						$http.get($scope.qnas[$scope.topicIdx][$scope.qnaIdx]._links.self.href).success(function(data) {
							console.log("Question and Answer updated. Link:" + $scope.qnas[$scope.topicIdx][$scope.qnaIdx]._links.self.href);
							$scope.qnas[$scope.topicIdx][$scope.qnaIdx] = data;
							qnasService.setActiveQnAs($scope.qnas);
						})
					});
				}
				else{
					if($scope.topics[$scope.topicIdx]._links == undefined){
						saveTopic($scope.topicIdx);
					}
					$http.post(urlBase + "/questionAndAnswers",{
						topic : $scope.topics[$scope.topicIdx]._links.self.href,
						question : $scope.qnas[$scope.topicIdx][$scope.qnaIdx].question,
						answer : $scope.qnas[$scope.topicIdx][$scope.qnaIdx].answer
					}).success(function(data, status, headers) {
						var newTopicUri = headers()["location"];
						$http.get(newTopicUri).success(function(data) {
							console.log("Question and Answer added. Link:" + data._links.self.href);
							$scope.qnas[$scope.topicIdx][$scope.qnaIdx] = data;
							qnasService.setActiveQnAs($scope.qnas);
						})
					});
				}
			}
		
			$scope.removeQuestion = function removeQuestion(topicIdx, qnaIdx) {
				if (topicIdx > -1 && qnaIdx > -1) {
					if ($scope.qnas[topicIdx][qnaIdx]._links != undefined){
						$http.delete($scope.qnas[topicIdx][qnaIdx]._links.self.href).success(function(data, status, headers) {
							console.log("Question and Answer deleted:" + $scope.qnas[topicIdx][qnaIdx].title);
							$scope.qnas[topicIdx].splice(qnaIdx, 1);
							qnasService.setActiveQnAs($scope.qnas);
						});
					}
					else {
						$scope.qnas[topicIdx].splice(qnaIdx, 1);
						qnasService.setActiveQnAs($scope.qnas);
					}
				}
			}
			
			$scope.importQuestions = function importQuestions() {
				if($scope.topics[$scope.topicIdx]._links == undefined){
					saveTopic($scope.topicIdx);
				}
				var topicUri = $scope.topics[$scope.topicIdx]._links.self.href;
				var toImport = $scope.imported.split("Q:");
				for(var i = 0; i < toImport.length; i++){
					if(toImport[i].trim() == ""){
						toImport.splice(i, 1);
					}
				}
				var maxNumber = 15;
				if($scope.qnas[$scope.topicIdx] != undefined){
					maxNumber -= $scope.qnas[$scope.topicIdx].length;
				}
				for(var i = 0; i < toImport.length && i < maxNumber; i++){
					var qna = toImport[i].split("A:");
					if(qna.length == 2){
						var q = qna[0];
						var a = qna[1];
						$http.post(urlBase + "/questionAndAnswers",{
							topic : topicUri,
							question : q,
							answer : a
						}).success(function(data, status, headers) {
							console.log("QnA imported");
							var qnasUri = $scope.topics[$scope.topicIdx]._links.qnas.href;
							$http.get(qnasUri).success(function(data,status,headers,config) {
								for (var j = 0; j < $scope.topics.length; j++){
									if($scope.topics[j]._links.qnas.href == config.url){
										if (data._embedded != undefined) {
											$scope.qnas[j] = data._embedded.questionAndAnswers;
						                } else {
						                	$scope.qnas[j] = [];
						                }
										qnasService.setActiveQnAs($scope.qnas);
										break;
									}
								}
							})
						});
					}
				}
			}
			
			$scope.addTag = function addTag() {
				if($scope.newTagName != ""){
					$scope.newTagName = $scope.newTagName.replace(/[^\w+$]/g, "").toLowerCase();
					var tag;
					for (var i = 0; i < $scope.tags.length; i++) {
						if($scope.newTagName == $scope.tags[i].tagName){
							tag = $scope.tags[i];
							break;
						}
					}
					var newTagUri = $scope.tags[$scope.newTagName];
					var tagUris = [];
					if(tag){
						$scope.newTagName = "";
						for (var i = 0; i < $scope.activeGameTags.length; i++){
							tagUris.push($scope.activeGameTags[i]._links.self.href);
						}
						tagUris.push(tag._links.self.href);
						$http.patch($scope.activeGame._links.self.href,{
							tags : tagUris
						}).success(function(data, status, headers) {
							findGameTags($scope.activeGame._links.tags.href);
						});
					}
					else{
						$http.post(urlBase + '/tags', {
							tagName: $scope.newTagName
						}).
						success(function(data, status, headers) {
							$scope.newTagName = "";
							newTagUri = headers()["location"];
							for (var i = 0; i < $scope.activeGameTags.length; i++){
								tagUris.push($scope.activeGameTags[i]._links.self.href);
							}
							tagUris.push(newTagUri);
							$http.patch($scope.activeGame._links.self.href,{
								tags : tagUris
							}).success(function(data, status, headers) {
								findGameTags($scope.activeGame._links.tags.href);
							});
						});
					}
				}
			}
			
			$scope.removeTag = function removeTag(tagIdx) {
				var tagUris = [];
				if($scope.activeGameTags.length > 1){
					$scope.activeGameTags.splice(tagIdx, 1);
					for (var i = 0; i < $scope.activeGameTags.length; i++){
						tagUris.push($scope.activeGameTags[i]._links.self.href);
					}
				}
				$http.patch($scope.activeGame._links.self.href,{
					tags : tagUris
				}).success(function(data, status, headers) {
					findGameTags($scope.activeGame._links.tags.href);
				});
			}
			
			$scope.selectTag = function selectTag(tagName) {
				selectTag1(tagName, true);
			}
			function selectTag1(tagName, forward) {
				if(tagName != "") {
					$scope.selectedTagName = tagName;
					
					var tag;
					for (var i = 0; i < $scope.tags.length; i++) {
						if($scope.tags[i].tagName == tagName){
							tag = $scope.tags[i];
							break;
						}
					}
					if(tag){
						tagService.setSelectedTagName($scope.selectedTagName);
						$http.get(tag._links.games.href).success(function(data) {
							if (data._embedded != undefined) {
			                    $scope.tagGames = data._embedded.games;
			                } else {
			                    $scope.tagGames = [];
			                }
						})
						if(forward && $location.path() != "/taglist"){
							$location.path( "/taglist" );
						}
					}
				}
			}
			
			function findGameTopics(gameTopicsUri) {
				if (gameTopicsUri != "") {
					$http.get(gameTopicsUri).success(function(data) {
						if (data._embedded != undefined) {
		                    $scope.topics = data._embedded.topics;
		                } else {
		                    $scope.topics = [];
		                }
						topicsService.setActiveTopics($scope.topics);
						if($scope.qnas == undefined){
							$scope.qnas = [];
						}
						for (var i = 0; i < $scope.topics.length; i++){
							var qnasUri = $scope.topics[i]._links.qnas.href;
							$http.get(qnasUri).success(function(data,status,headers,config) {
								for (var j = 0; j < $scope.topics.length; j++){
									if($scope.topics[j]._links.qnas.href == config.url){
										if (data._embedded != undefined) {
											$scope.qnas[j] = data._embedded.questionAndAnswers;
						                } else {
						                	$scope.qnas[j] = [];
						                }
										qnasService.setActiveQnAs($scope.qnas);
										initDataToPlay();
										break;
									}
								}
							})
						}
					})
				}
			}
			
			function findGameTags(gameTagsUri) {
				if (gameTagsUri != "") {
					$http.get(gameTagsUri).success(function(data) {
						if (data._embedded != undefined) {
		                    $scope.activeGameTags = data._embedded.tags;
		                } else {
		                    $scope.activeGameTags = [];
		                }
					})
				}
			}
			
			$scope.$watch('players',function(newPlayers){
				if(newPlayers == undefined)return;
				var topicsSelected = [];
				for(var i = 0; i < newPlayers.length; i++){
					if(newPlayers[i].topic != "?"){
						if(topicsSelected.indexOf(newPlayers[i].topic) > -1){
							newPlayers[i].topic = "?";
						}
						else{
							topicsSelected.push(newPlayers[i].topic);
						}
					}
				}
				$scope.topicsSelected = topicsSelected;
			}, true);
			
			$scope.showQuestions = function showQuestions(){
				$timeout(function(){
			    	if($scope.selectedQuestions.length < $scope.qindexes.length){
			    		console.log("selected:" + $scope.selectedQuestions);
			    		$scope.selectedQuestions.push($scope.selectedQuestions.length);
			    		showQuestions();
			    	}
			    	else{
			    		$timeout(function(){
				    		console.log("clear:" + $scope.selectedQuestions);
			    			$scope.selectedQuestions = [];
			    		},3000);
			    	}
			    },200);
			};
		
			function initDataToPlay(){
				var qindexes = [];
				if(topicsService.getActiveTopics() != undefined){
					$scope.topics = topicsService.getActiveTopics();
				}
				else{
					$scope.topics = [];
				}
				if(qnasService.getActiveQnAs() != undefined){
					$scope.qnas = qnasService.getActiveQnAs();
				}
				else {
					$scope.qnas = [];
				}
				for(var i = 0; i < $scope.topics.length; i++){
					if($scope.qnas[i] != undefined){
						for(var j = 0; j < $scope.qnas[i].length; j++){
							qindexes.push({"t":i,"q":j});
						}
					}
				}
				shuffle(qindexes);
				var dimension = 6;
				while(((qindexes.length % dimension) > 0) && (dimension > 1)){
					dimension--;
					if(dimension == 5)dimension--;// skip 5 columns, look for
													// 6,4,3,2
													// or 1 because there is 12
													// columns
													// in Bootstrap Grid
				}
				playService.setQIndexes(qindexes);
				$scope.qindexes = qindexes;
				playService.setDimension(dimension);
				$scope.dimension = dimension;
				$scope.colors = ["danger","warning","primary","success"];
				$scope.players = [];
				for(var i = 0; i < $scope.topics.length; i++){
					$scope.players.push({"name":"","topic":"?","score":0});
				}
				$scope.playerIdx = 0;
				$scope.selectedQuestions = [];
				$scope.topicsSelected = [];
				$scope.gameStarted = false;
			}
			
			function shuffle(array) {
				var currentIndex = array.length, temporaryValue, randomIndex ;
				// While there remain elements to shuffle...
				while (0 !== currentIndex) {
					// Pick a remaining element...
					randomIndex = Math.floor(Math.random() * currentIndex);
					currentIndex -= 1;
				    // And swap it with the current element.
					temporaryValue = array[currentIndex];
					array[currentIndex] = array[randomIndex];
					array[randomIndex] = temporaryValue;
				}
				return array;
			}
		
		});
	});
})