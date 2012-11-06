'use strict';

angular.module('fruit-shop.controllers', []);

function ArticlesController($scope, Article) {
  $scope.$emit("navigation-changed", { name: "Articles" });
  
  $scope.articles = Article.query();
  
  $scope.addToBasket = function(article) {
    
  };
  
  $scope.showDetails = function(article) {
    
  };
  
  $scope.inBasketAmount = function(article) {
    
  };
}
