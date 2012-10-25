'use strict';

angular.module('fruit-shop.controllers', []);

function BasketController($scope) {
  $scope.$emit("navigation-changed", { name: "Basket" });
}
