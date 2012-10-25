'use strict';

/* Directives */

angular
.module('fruit-shop.directives.remaining', [])
.directive('ngCombobox', function(Event) {
  return {
    restrict: 'A',
    require: 'ngModel',
    link: function (scope, elm, attrs, model) {
      if (model) {
        elm.on(Event.ngChange, function() {
          scope.$apply(function() {
            var input = getInputText();
            if (input) {
              // catch user typed values (not selected ones)
              model.$setViewValue(input.value);
            }
          });
        });
      }

      // value list changed, update combobox
      scope.$watch(attrs.values, function() {
        var comboboxContainer = getComboboxContainer();
        if (comboboxContainer) {
          // combobox already presents, remove old one
          var select = elm.detach();
          $(comboboxContainer).parent().prepend(select);
          $(comboboxContainer).remove();
        }
        // init new combobox
        elm.combobox({
          template: '<div class="combobox-container"><input type="text" autocomplete="off" class="dropdown-toggle" /><span class="" data-dropdown="dropdown"></span></div>'
        });
      });

      // update input with model when default value is set
      scope.$watch(model, function() {
        var input = getInputText();
        if (input) {
          var oldValue = input.value;
          if (oldValue !== model.$modelValue) {
            $(input).val(model.$modelValue);
          }
        }
      });

      // do some cleanup
      scope.$on(Event.destroy, function () {
        $('ul.typeahead.dropdown-menu').each(function(){
          $(this).remove();
        });
        elm.unbind($().combobox());
      });

      // get container which holds the combobox elements
      function getComboboxContainer() {
        var comboboxContainer = elm.parent('.combobox-container');
        if (comboboxContainer.length == 1) {
          return comboboxContainer[0];
        } else {
          return;
        }
      }

      // get combobox's input text
      function getInputText() {
        var comboboxContainer = getComboboxContainer();
        if (comboboxContainer) {
          var input = $(comboboxContainer).children('input')[0];
          if (input) {
            return input;
          }
        }

        return;
      }
    }
  };
})
/**
 * Realizes a bpmn diagram ui component in the roundtrip details dialog.
 * 
 * @param roundtrip reference to the roundtrip the diagram belongs to
 * @param diagram or null the diagram which is managed / displayed
 * @param identifier the identifier of the diagram (eighter leftHandSide or rightHandSide)
 * 
 * Usage:
 * 
 * <bpmn-diagram handle="leftDiagram" roundtrip="myRoundtrip" diagram="myRoundtrip.leftHandSide" identifier="leftHandSide" />
 */
.directive("bpmnDiagram", function(App) {
  return {
    restrict: 'E',
    scope: {
      roundtrip: '=', 
      diagram: '=',
      handle : '@',
      identifier: '@'
    }, 
    templateUrl: App.uri("secured/view/partials/bpmn-diagram.html"),
    controller: 'BpmnDiagramController', 
    link: function(scope, element, attrs) {
      scope.identifier = attrs.identifier;
      if (attrs.handle) {
    	  scope.$parent[attrs.handle] = scope;
   	  }
    }
  };
})
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
})

.directive('ifAdmin', function(Credentials) {
  return {
    restrict: 'A',
    scope: { }, 
    transclude: true, 
    template: '<span ngm-if="isAdmin" ng-transclude></span>', 
    link: function(scope, element, attrs) {
      scope.$watch(Credentials.watchCurrent, function(newValue) {
        scope.isAdmin = Credentials.isAdmin();
      });
    }
  };
})
/**
 * A directive which conditionally displays a dialog 
 * and allows it to control it via a explicitly specified model.
 * 
 * <dialog model="aModel">
 *   <div class="model" ngm-if="aModel.renderHtml()">
 *     <!-- dialog contents ... -->
 *   </div>
 * </dialog>
 * 
 * <script>
 *   // outside the dialog
 *   aModel.open(); // openes the dialog (asynchronously)
 *   aModel.close(); // closes the dialog (immediately)
 *   
 *   // Or inside the dialog: 
 *   $model.close();
 * </script>
 */
.directive('modalDialog', function($http, $timeout) {
  return {
    restrict: 'E',
    scope: {
      $model: '=model'
    }, 
    transclude: true, 
    template: '<div ng-transclude />', 
    link: function(scope, element, attrs) {
      /**
       * Obtain the dialog
       * @returns the dialog instance as a jQuery object
       */
      function dialog() {
        return angular.element(element.find(".modal"));
      }
      
      /**
       * Obtain the dialogs model
       * @returns the dialogs model
       */
      function model() {
        return scope.$model;
      }
      
      /**
       * Init (ie. register events / dialog functionality) and show the dialog.
       * @returns nothing
       */
      function initAndShow() {
        
        var options = model().autoClosable ? {} : {
          backdrop: 'static', 
          keyboard: false
        };
        
        dialog()
          .hide()
          // register events to make sure the model is updated 
          // when things happen to the dialog. We establish a two-directional mapping
          // between the dialog model and the bootstrap modal. 
          .on('hidden', function() {
            // Model is still opened; refresh it asynchronously
            if (model().status != "closed") {
              $timeout(function() {
                model().setStatus("closed");
              });
            }
          })
          .on('shown', function() {
            model().setStatus("open");
          })
          // and show modal
          .modal(options);
      }

      /**
       * Hide (and destroys) the dialog
       * @returns nothing
       */
      function hide() {
        dialog().modal("hide");
      }
      
      /**
       * Watch the $model.status property in order to map it to the 
       * bootstrap modal dialog live cycle. The HTML has to be rendered first, 
       * for the dialog to appear and actual stuff can be done with the dialog.
       */
      scope.$watch("$model.status", function(newValue , oldValue) {
        
        // dialog lifecycle
        // closed -> opening -> open -> closing -> closed
        //            ^ html is about to exist       ^ dialog closed (no html)
        //                       ^ dialog operational and displayed
        //  ^ dialog closed (no html)    ^ dialog closing
        switch (newValue) {
          case "opening": 
            // dialog about to show and markup will be ready, soon
            // asynchronously initialize dialog and register events            
            $timeout(initAndShow);
            break;
          case "closing": 
            hide();
            break;
        }
      });
    }
  };
})

/** 
 * Dialog model to be used along with the 
 * dialog directive and attaches it to the given scope
 */
function Dialog() {
  this.status = "closed";
  this.autoClosable = true;
}

Dialog.prototype = {
  
  open: function() {
    this.status = "opening";
  }, 

  close: function() {
    this.status = "closing";
  },

  setStatus: function(status) {
    console.log("new status: " + status);
    this.status = status;
  }, 
  
  setAutoClosable: function(closable) {
    this.autoClosable = closable;
  }, 
  
  renderHtml: function() {
    return this.status != "closed";
  }
};
