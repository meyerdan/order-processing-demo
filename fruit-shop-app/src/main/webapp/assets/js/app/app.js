'use strict';

angular
  .module('fruit-shop', ['ng', 'fruit-shop.filters', 'fruit-shop.services', 'fruit-shop.directives'])
    .config(['$routeProvider', function($routeProvider) {

      $routeProvider.when('/', {
        controller: HomeController,
        templateUrl: '/fruit-shop/app/partials/homepage.html'
      });

      $routeProvider.when('/articles', { 
        controller: ArticlesController, 
        templateUrl: '/fruit-shop/app/partials/articles.html'
      });

      $routeProvider.when('/basket', { 
        controller: BasketController, 
        templateUrl: '/fruit-shop/app/partials/basket.html'
      });

      $routeProvider.when('/order/:id', { 
        controller: OrderController, 
        templateUrl: '/fruit-shop/app/partials/order.html'
      });
      
      $routeProvider.when('/dashboard', { 
          controller: DashboardController, 
          templateUrl: '/fruit-shop/app/partials/dashboard.html'
      });
}]);