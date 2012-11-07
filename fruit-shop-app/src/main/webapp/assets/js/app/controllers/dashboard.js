'use strict';

angular.module('fruit-shop.controllers', []);

function DashboardController($scope, $routeParams) {
  
  $scope.$emit("navigation-changed", { name: "Dashboard" });
  
}