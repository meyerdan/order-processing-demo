'use strict';

angular.module('fruit-shop.controllers', []);

function BasketController($scope) {
  
  $scope.checkoutDialog = new Dialog();
  
  $scope.$emit("navigation-changed", { name: "Basket" });
  
  $scope.basketEmpty = function() {
    return !$scope.basket.items || !$scope.basket.items.length;
  }
  
  $scope.basketPrice = function() {
    var sum = 0;
    
    for (var key in $scope.basket.items) {
      var item = $scope.basket.items[key];
      sum += item.article.price * item.amount;
    }
    
    return sum;
  };
  
  $scope.checkout = function() {
    if ($scope.basketEmpty()) {
      return;
    }
    
    $scope.checkoutDialog.open();
  };
  
  $scope.clear = function() {
    $scope.basket.items = [];
  };
}

function BasketItemController($scope) {
  
  $scope.article = $scope.item.article;
  
}

function BasketCheckoutController($scope, $http, App) {
  
  $scope.isValid = function(form) {
    return form && form.$valid;
  };
  
  $scope.buy = function() {
    
    var items = [];
    
    angular.forEach($scope.basket.items, function(e) {
      if (e.amount) {
        items.push({ articleId: e.article.id, amount: e.amount });
      }
    });
    
    $scope.order = { customer: $scope.customerName, orderItems: items };
    
    $http.post("/fruit-shop/app/resources/order", $scope.order).success(function(data) {
      $scope.order.orderId = data.orderId;
      
      $scope.state = "SUCCESS";
    }).error(function() {
      $scope.state = "FAILURE";
    });
    
    $scope.clear();
  };
}