'use strict';

/* Misc Services */

angular
  .module('fruit-shop.services.misc', ['ng'])
  /**
   * Lists all available events used in cycle for emit and on
   */
  .factory('Event', function() {
    return {
      userChanged : "user-changed",
      navigationChanged : "navigation-changed",
      roundtripAdded : "roundtrip-added",
      roundtripChanged : "roundtrip-changed",
      modelImageClicked : "model-image-clicked",
      componentError : "component-error",
      selectedConnectorChanged : "selected-connector-changed" ,
      destroy : "$destroy", // angular own event which is fired when current scope is destroyed
      ngChange : "change", // jquery + angularjs event
      imageAvailable :  "image-available"
    };
  });