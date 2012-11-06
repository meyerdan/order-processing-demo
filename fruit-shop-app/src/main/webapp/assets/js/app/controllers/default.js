'use strict';

window.credentials = null;

function DefaultController($scope, $location) {

  $scope.basket = { items: [] };

  $scope.basketSize = function() {
    var size = 0;
    
    for (var i in $scope.basket.items) {
      var item = $scope.basket.items[i];
      size += item.amount;
    }
    
    return size;
  };
  
  // Bread Crumb 
  var breadCrumbs = $scope.breadCrumbs = [];

  $scope.$on("navigation-changed", function(event, navigationItem) {
    if (!navigationItem) {
      breadCrumbs.splice(0, breadCrumbs.length);
    } else {
      var contains = false;
      var remove = 0;
      angular.forEach(breadCrumbs, function(item) {
        if (item.name == navigationItem.name) {
          contains = true;
        }
        if (item.href.indexOf($location.path())) {
          remove++;
        }
      });

      for (var i = 0; i < remove; i++) {
        breadCrumbs.pop();
      }

      if (!contains) {
        breadCrumbs.push({name:navigationItem.name, href: $location.path()});
      }
    }
  });
  // end Bread Crumb
}

/**
 * Deals with navigation between different guys
 */
function NavigationController($scope, $location) {

  $scope.activeClass = function(link) {
    var path = $location.path();
    
    if (!path || link == "/") {
      if (path == "/") {
        return "active";
      }
      
      return "";
    }
  
    return path.indexOf(link) == 0 ? "active" : "";
  };
}