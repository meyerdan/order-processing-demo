'use strict';

angular
  .module('fruit-shop', ['ng', 'fruit-shop.filters', 'fruit-shop.services', 'fruit-shop.directives'])
    .config(['$routeProvider', function($routeProvider) {
      
      $routeProvider.when('/', {
        controller: HomeController,
        templateUrl: '../partials/homepage.html'
      });
      
      $routeProvider.when('/filter', { 
        controller: FilterProcessInstancesController, 
        templateUrl: '../partials/filter-process-instances.html'
      });
      
      $routeProvider.when('/instance-details', { 
        controller: InstanceDetailsController, 
        templateUrl: '../partials/instance-details.html'
      });
    
      $routeProvider.when('/users', { 
        controller: UserController, 
        templateUrl: '../partials/users.html'
      });
}]);