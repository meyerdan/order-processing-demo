'use strict';

angular.module('fruit-shop.controllers', []);

function ArticlesController($scope) {
  $scope.$emit("navigation-changed", { name: "Articles" });
}
