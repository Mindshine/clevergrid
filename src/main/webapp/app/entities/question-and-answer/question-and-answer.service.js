(function() {
    'use strict';
    angular
        .module('clevergridApp')
        .factory('QuestionAndAnswer', QuestionAndAnswer);

    QuestionAndAnswer.$inject = ['$resource'];

    function QuestionAndAnswer ($resource) {
        var resourceUrl =  'api/question-and-answers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
