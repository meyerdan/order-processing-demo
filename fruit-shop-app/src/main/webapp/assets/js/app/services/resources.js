'use strict';

/* Resource Services */

angular
  .module('fruit-shop.services.resources', ['ngResource', 'fruit-shop.services.base' ])
  .factory('User', function($resource, App) {
    return $resource(App.uri('secured/resource/user/:id'), {id: "@id"}, {});
  });