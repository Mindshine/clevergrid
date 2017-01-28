define(['app'], function (app) {
	app.service('tagService', function() {
		
		var tagData;
		var selectedTagName;
		
		this.getActiveTags = function() {
			return this.tagsData;
		};
	
		this.setActiveTags = function(data) {
			this.tagsData = data;
		};
		
		this.getSelectedTagName = function() {
			return this.selectedTagName;
		}
		
		this.setSelectedTagName = function(tagName) {
			this.selectedTagName = tagName;
		}
		
	});
})