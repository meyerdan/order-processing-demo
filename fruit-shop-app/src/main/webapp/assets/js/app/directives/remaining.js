'use strict';

/* Directives */

angular
.module('fruit-shop.directives.remaining', [])
.directive("help", function(App) {
  return {
    restrict: 'A',    
    scope : {
		helpText: "@",
		helpTitle: "@", 
		helpTextVar: "&",
		helpTitleVar: "&", 
		colorInvert: "@"
    },
    template: '<span ng-transclude></span><span class="help-toggle"><i class="icon-question-sign" ng-class="colorInvertCls()"></i></span>',
    transclude: true, 		
    link: function(scope, element, attrs) {
      var help = attrs.helpText || scope.helpTextVar, 
          helpTitle = attrs.helpTitle || scope.helpTitleVar, 
          colorInvert = !!attrs.colorInvert;
      
      scope.colorInvertCls = function() {
    	  return (colorInvert ? 'icon-white' : '');
      };
      
      $(element).find(".help-toggle").popover({content: help, title: helpTitle, delay: { show: 0, hide: 0 }});
    }
  };
})
.directive("diagramImage", function(App, Commons) {
  return {
    restrict: 'E',
    replace : true,
    scope : {
      diagram: "=",
      status: "=", 
      click: "&"
    },
    templateUrl: App.uri("secured/view/partials/diagram-image.html"),
    link: function(scope, element, attrs) {

      function changeImageStatus(newStatus) {
          scope.status = newStatus;
          
          // FIXME workaround for a angular bug!
          scope.$digest();
          scope.$apply();
      }
      
      function performImageClick() {
        scope.$apply(function() {
          scope.click();
        });
      }
      
      function fixDiagramImageHeight(element) {
        // fix image height if it is higher than the diagram container
        var e = $(element);
        var imgHeight = parseInt(e.css("height"), 10);
        var containerHeight = parseInt(e.parents(".diagram").css("height"), 10);

        if (imgHeight > containerHeight) {
          e.css("height", containerHeight + "px");
        }
      }
      
      // register image load interceptor
      $(element)
        .find("img")
        .css({ width: "auto", height: "auto" })
        .bind({
          load: function() {
            fixDiagramImageHeight(this);
            changeImageStatus("LOADED");
          },
          error: function(){
            changeImageStatus("UNAVAILABLE");
          }, 
          click: performImageClick
        });

      // $scope.checkImageAvailable();
//      scope.checkImageAvailable = function () {
//        if (scope.diagram) {
//          Commons.isImageAvailable(scope.diagram.connectorNode).then(function (data) {
//            scope.imageAvailable = data.available && ((data.lastModified + 5000) >= scope.diagram.lastModified);
//            scope.$emit(Event.imageAvailable, scope.imageAvailable, scope.identifier);
//          });
//        }
//      };

      function updateImage(diagram, update) {
        scope.status = "LOADING";
        $(element).find("img").attr("src", Commons.getImageUrl(diagram, update));
      };

      scope.$watch("diagram", function (newDiagramValue) {
        if (newDiagramValue && newDiagramValue.id) {
          updateImage(newDiagramValue);
        }
      });

      /**
       * Update image status when it is set back to unknown
       */
      scope.$watch("status", function (newStatus, oldStatus) {
        if (scope.diagram && newStatus == "UNKNOWN" && oldStatus) {
          updateImage(scope.diagram, true);
        }
      });
    }
  };
});