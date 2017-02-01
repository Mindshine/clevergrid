(function() {
    'use strict';

    angular.module('clevergridApp').filter('reverse', function() {
        return function(items) {
            return items ? items.slice().reverse() : items;
        };
    });
})();
