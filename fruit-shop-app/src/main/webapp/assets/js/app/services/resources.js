'use strict';

/* Resource Services */

angular
  .module('fruit-shop.services.resources', ['ngResource', 'fruit-shop.services.base' ])
  .factory('Article', function($resource, App) {
    return $resource(App.uri('resources/article/:id'), {id: "@id"}, {});
  });