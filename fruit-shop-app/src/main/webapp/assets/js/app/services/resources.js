'use strict';

/* Resource Services */

angular
  .module('fruit-shop.services.resources', ['ngResource', 'fruit-shop.services.base' ])
  .factory('Article', function($resource, App) {
    return $resource(App.uri('resources/article/:id'), {id: "@id"}, {});
  })
  .factory('Order', function($resource, App) {
    return $resource(App.uri('resources/order/:id'), {id: "@id"}, {});
  })
  .factory('OrderUpdate', function($resource, App) {
    return $resource(App.uri('resources/order/:orderId/update/:id'), {id: "@id"}, {});
  });