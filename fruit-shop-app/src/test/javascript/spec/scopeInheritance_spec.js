function OuterController($scope) {
  $scope.vars = [];
}

function InnerController($scope) {
  
  $scope.replaceVars = function() {
    $scope.vars = [ "foo", "bar" ];
  }
  
  $scope.populateVars = function() {
    $scope.vars.push("foo");
    $scope.vars.push("bar");
  }
}

describe("Scope Inheritance", function() {
  var $rootScope, $controller;

  beforeEach(inject(function($injector) {
    
    $rootScope = $injector.get('$rootScope');
    $controller = $injector.get('$controller');
  }));

  it('Should publish vars via outer controller', function() {
    var outerScope = $rootScope.$new(false);
    
    $controller(OuterController, {
      $scope: outerScope
    });
  
    expect(outerScope.vars).toEqual([]);
  });
  
  it('Should inherit vars in inner scope', function() {
    var outerScope = $rootScope.$new(false);
    
    var outerController = $controller(OuterController, {
      $scope: outerScope
    });
  
    var innerScope = outerScope.$new(false);
    
    var innerController = $controller(InnerController, {
      $scope: innerScope
    });
    
    expect(innerScope.vars).toEqual([]);
  });

  it('Should override vars in inner and outer scope', function() {
    var outerScope = $rootScope.$new(false);
    
    var outerController = $controller(OuterController, {
      $scope: outerScope
    });
  
    var innerScope = outerScope.$new(false);
    
    var innerController = $controller(InnerController, {
      $scope: innerScope
    });
    
    // when
    innerScope.replaceVars();
    
    // then
    expect(innerScope.vars).toEqual([ "foo", "bar" ]);
    expect(outerScope.vars).toEqual([ "foo", "bar" ]);
  });
  
  it('Should fill vars in inner AND outer scope', function() {
    var outerScope = $rootScope.$new(false);
    
    var outerController = $controller(OuterController, {
      $scope: outerScope
    });
  
    var innerScope = outerScope.$new(false);
    
    var innerController = $controller(InnerController, {
      $scope: innerScope
    });
    
    // when
    innerScope.populateVars();
    
    // then
    expect(innerScope.vars).toEqual([ "foo", "bar" ]);
    expect(outerScope.vars).toEqual([ "foo", "bar" ]);
  });
});