'use strict';

angular.module('fruit-shop.controllers', []);

function HomeController($scope) {
  $scope.$emit("navigation-changed");
}
