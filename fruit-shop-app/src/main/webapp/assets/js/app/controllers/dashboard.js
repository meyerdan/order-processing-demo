'use strict';

angular.module('fruit-shop.controllers', []);

function DashboardController($scope, $routeParams) {
  
  var controlManager = window.controlManager;
  
  $scope.$emit("navigation-changed", { name: "Dashboard" });
  
  $scope.$on("$destroy", function() {
    // unsubscribe from socket
    controlManager.controlUnsubscribe();
  });
  
  // subscribe to socket
  controlManager.controlConnect();
  controlManager.refresh();
}