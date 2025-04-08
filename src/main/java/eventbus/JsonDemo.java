package eventbus;

import com.example.starter.ContextDemo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonDemo {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(new JsonSender());
    vertx.deployVerticle(new JsonReceiver());
    vertx.deployVerticle(new JsonReceiver2());

  }
}

class JsonSender extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(JsonSender.class);

  public void start()
  {
    JsonObject data = new JsonObject().put("id", 101).put("Name", "Jay patel").put("Role", "Trainee software engineer");

    vertx.eventBus().request("json.address", data, reply -> {
      if(reply.succeeded())
      {
        LOG.info("Reply : {}", reply.result().body());
      }
      else
      {
        LOG.error("Failed : {}", reply.cause().getMessage());
      }
    });

    // In JsonSender:
    vertx.eventBus().publish("json.address22", data);

  }
}

class JsonReceiver extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(JsonReceiver.class);

  public void start(){
    vertx.eventBus().consumer("json.address", this::handleJsonMessage);
  }

  private void handleJsonMessage(Message<JsonObject> message)
  {
    JsonObject data = message.body();
    LOG.info("Message : {}", data.encodePrettily());

    String name = data.getString("Name");
    int id = data.getInteger("id");
    String role = data.getString("Role");

    LOG.info("Hello {}, ID: {}, Role: {}", name, id, role);

    // Send reply back
    message.reply("Received JSON for " + name);
  }
}


class JsonReceiver2 extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(JsonReceiver2.class);

  public void start(){
    vertx.eventBus().consumer("json.address22", this::handleJsonMessage);
  }

  private void handleJsonMessage(Message<JsonObject> message)
  {
    JsonObject data = message.body();
    LOG.info("[Receiver2] Got: {}", data.encodePrettily());
  }
}
