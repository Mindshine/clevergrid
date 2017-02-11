(function() {
    'use strict';
    angular.module('clevergridApp').factory('Editor', Editor);

    Editor.$inject = [ '$resource' ];

    function Editor($resource) {
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
