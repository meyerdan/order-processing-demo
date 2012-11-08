'use strict';

angular.module('fruit-shop.controllers', []);

function OrderController($scope, $routeParams, Order, OrderUpdate) {
  
  $scope.$emit("navigation-changed", { name: "Order" });
  
  $scope.order = Order.get({ id: $routeParams.id });
  
  $scope.updates = OrderUpdate.query({ orderId: $routeParams.id });
  
}