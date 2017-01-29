(function() {
    'use strict';
    angular.module('clevergridApp').factory('Dashboard', Dashboard);

    Dashboard.$inject = [ '$resource', 'DateUtils' ];

    function Dashboard($resource, DateUtils) {
        var resourceUrl = 'api/games';

        return $resource(resourceUrl, {}, {
            'query' : {
                method : 'GET',
                isArray : true
            }
        });
    }
})();
