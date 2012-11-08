'use strict'

angular.module('fruit-shop.directives')

.directive('windowCenter', function($window) {
  return {
    restrict: 'A',
    link: function(scope, element, attrs) {
      
      var container = $("#" + attrs["windowCenter"]);
      
      function updateContainerPositioning() {
        var windowSize = { width: $($window).width(), height: $($window).height() };
        
        var containerWidth = container.width();
        var containerParentWidth = $(container).parent().width();
        
        var marginLeft = 0;
        
        console.log(windowSize);
        
        if (windowSize.width > containerWidth && containerWidth > containerParentWidth) {
          marginLeft = (containerWidth - containerParentWidth) / 2 * -1;
        }

        container.css({ marginLeft: marginLeft + "px" });
      }
      
      function updateContainerSize(size) {
        container.css({ position: "relative", width: size.width, height: size.height });
        updateContainerPositioning();
      }
      
      var img = new Image();
      img.onload = function() {
        updateContainerSize({ width: img.width, height: img.height });
      };
      
      // load image to get original size
      img.src = element.attr("src");
      
      $($window).on('resize', function(event) {
        updateContainerPositioning();
      });
    }
  };
});