(function() {
    'use strict';
    angular.module('clevergridApp').factory('Editor', Editor);

    Editor.$inject = [ '$resource', 'DateUtils' ];

    function Editor($resource, DateUtils) {
        var resourceUrl = 'api/games/:id';

        return $resource(resourceUrl, {}, {
            'query' : {
                method : 'GET',
                isArray : true
            },
            'get' : {
                method : 'GET',
                transformResponse : function(data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'update' : {
                method : 'PUT'
            }
        });
    }
})();
