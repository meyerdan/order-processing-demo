'use strict';

angular.module('fruit-shop.controllers', []);

function ArticlesController($scope, Article) {
  $scope.$emit("navigation-changed", { name: "Articles" });
  
  $scope.articles = Article.query();
  
  function findItemByArticle(items, article) {
    for (var key in items) {
      var item = items[key];
      
      if (item.article.id == article.id) {
        return item;
      }
    }
    
    return null;
  }
  
  $scope.articleImgSrc = function(article) {
    if (article.imageUrl) {
      return article.imageUrl;
    } else {
      return "http://placehold.it/300x150";
    }
  };
  
  $scope.addToBasket = function(article) {
    var basket = $scope.basket;
    
    var item = findItemByArticle(basket.items, article);
    if (item) {
      item.amount++;
    } else {
      basket.items.push({ article: article, amount: 1 });
    }
  };
  
  $scope.showDetails = function(article) {
    
  };
  
  $scope.inBasketAmount = function(article) {
    return (findItemByArticle($scope.basket.items, article) || {}).amount;
  };
  
  $scope.inBasket = function(article) {
    
    console.log(article, findItemByArticle($scope.basket.items, article));
    return !!findItemByArticle($scope.basket.items, article);
  };
}
