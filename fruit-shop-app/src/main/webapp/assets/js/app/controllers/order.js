'use strict';

angular.module('fruit-shop.controllers', []);

function OrderController($scope, $routeParams) {
  
  $scope.$emit("navigation-changed", { name: "Order" });
  
  $scope.order = { orderId: $routeParams.id };
}