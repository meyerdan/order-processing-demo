(function() {

  function ControlManager() {
    var self = this;

    this.pushReceiveBuffer = [];
    this.callback = function(response) {
      self.processPushReceive(response);
    };
  }
  
  ControlManager.prototype = {
    loadLayout: function() {
      var self = this;

      $.ajax({
        url: "/fruit-shop/app/resources/dashboard/process-definition/layout",
        type: 'GET',
        contentType:"application/json; charset=utf-8"
      }).success(function(data) {
        self.diagramLayout = data;
      });
    }, 

    refresh: function() {

      var self = this;
      
      setTimeout(function() {
        
        $.ajax({
          url: "/fruit-shop/app/resources/dashboard/monitor",
          type: 'GET',
          contentType:"application/json; charset=utf-8"
        }).success(function(data) {
          if (data) {
            self.pushReceiveBuffer.push(data);
            self.processBuffer();
          }
        });
      }, 2000);
    }, 
    
    controlConnect: function() {
      this.controlUnsubscribe();
      this.controlSubscribe();
    }, 

    controlSubscribe: function () {
      if (this.callbackAdded) {
        return;
      }
      
      var location = '/fruit-shop/monitor/control';
      
      this.connectedEndpoint = $.atmosphere.subscribe(location,
        !this.callbackAdded ? this.callback : null,
        $.atmosphere.request = { 
          transport: 'websocket' 
        });

      this.callbackAdded = true;
    }, 

    controlUnsubscribe: function () {
      this.callbackAdded = false;
      $.atmosphere.unsubscribe();
    }, 

    // jquery.atmosphere.response
    processPushReceive: function(response) {
      if (response.transport != 'polling' && response.state == 'messageReceived') {
        $.atmosphere.log('info', ["response.responseBody: " + response.responseBody]);
        if (response.status == 200) {
          var dataString = response.responseBody;
          if (dataString) {
            var data = jQuery.parseJSON(dataString);
            this.pushReceiveBuffer.push(data);
            this.processBuffer();
          }
        }
      }
    }, 
    
    processBuffer: function() {
      var buffer = this.pushReceiveBuffer; 
      if (buffer.length) {
        var data = buffer.shift();
        this.processData(data);
      }
    }, 

    processData: function(data) {
      var processDiagram = $("#processDiagram");
      
      // do only when process is displayed
      if (!processDiagram.length) {
        return;
      }
      
      $.each(this.diagramLayout['nodes'], function(index, value) {
        if (data[value['id']] > 0) {
          
          var styleClass = value['id'].indexOf('Service') != -1 ? "label-warning" : "label-info";
          $("#pdnode-" + value['id']).remove();

          processDiagram.append(
            '<div id=\"pdnode-' + value['id'] +'\"' +
            'style=\"position: absolute;' +
            'left: '+(value['x']+(value['width']/2)-5) + 'px; ' + 
            'top: '+ (value['y']+value['height']-5) + 'px; '+
            'width: '+ (value['width']-2) + 'px; '+
            'height: '+ (value['height']-2) + 'px; '+
            '\"> <span class="label '+styleClass+'\" style=\"font-size:20pt\">'+ data[value['id']] +'</span> </div>');
        } else {
          $("#pdnode-" + value['id']).remove();
        }
      });
    }
  };

  // init control manager when document is loaded
  $(document).ready(function() {
    
    var controlManager = window.controlManager = new ControlManager();
    controlManager.loadLayout();
    
  // setInterval(function() {
  //   cm.processBuffer();
  // }, 100);
  });
})();