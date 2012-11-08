package com.camunda.fox.showcase.fruitshop.application.dashboard.boundary.rest;

import java.util.Date;

import org.atmosphere.config.service.WebSocketHandlerService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketHandler;

@WebSocketHandlerService
public class MonitoringWebSocketHandler extends WebSocketHandler {

  @Override
  public void onOpen(WebSocket webSocket) {
    webSocket.resource().setBroadcaster(BroadcasterFactory.getDefault().lookup("/tpd", true)).suspend(-1);
  }

  public void onTextMessage(WebSocket webSocket, String message) {

    AtmosphereResource r = webSocket.resource();
    Broadcaster b = r.getBroadcaster();

    // Simple JSON -- Use Jackson for more complex structure
    // Message looks like { "author" : "foo", "message" : "bar" }
    String author = message.substring(message.indexOf(":") + 2, message.indexOf(",") - 1);
    String chat = message.substring(message.lastIndexOf(":") + 2, message.length() - 2);

    b.broadcast(new Data(author, chat).toString());
  }

  private final static class Data {

    private final String text;
    private final String author;

    public Data(String author, String text) {
      this.author = author;
      this.text = text;
    }

    public String toString() {
      return "{ \"text\" : \"" + text + "\", \"author\" : \"" + author + "\" , \"time\" : " + new Date().getTime() + "}";
    }
  }
}
