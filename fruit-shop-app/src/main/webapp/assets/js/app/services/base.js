'use strict';

/* Base Services */

angular
  .module('fruit-shop.services.base', ['ng'])
  /**
   * Credentials (áka user management)
   * 
   * A generic object which offers the current user credentials and allows to re-load them.
   * 
   * Credentials#reload();
   * Credentials#isAdmin();
   * Credentals#current() -> user;
   */
  .factory('Credentials', function($http, App) {
    
    function Credentials() {
      this.currentCredentials = null;
      
      var self = this;
      
      // bind watchCurrent to credentials to make it directly accessible
      // for scope.$watch(Credentials.watchCurrent)
      self.watchCurrent = function() {
        return self.current();
      };
    }

    Credentials.prototype = {
      reload: function() {
        var self = this;
        
        $http.get(App.uri('currentUser')).success(function(data) {
          self.currentCredentials = data;
        });
      },
      isAdmin: function() {
        return this.currentCredentials && this.currentCredentials.adminRole;
      },
      current: function() {
        return this.currentCredentials;
      }
    };

    return new Credentials();
  })

  /**
   * Provides the app service with the functions 
   * 
   *  root() -> returns the application root
   *  uri(str) -> returns a application root relative uri from the given argument
   */
  .factory('App', function() {
    function root() {
      return $("base").attr("app-base");
    }
    
    return {
      root: root, 
      uri: function(str) {
        return root() + (str.indexOf("/") == 0 ? str.substring(1, str.length) : str);
      }
    };
  });