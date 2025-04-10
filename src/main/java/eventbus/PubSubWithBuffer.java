package eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class PubSubWithBuffer{

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    // Publisher Verticle
    vertx.deployVerticle(new AbstractVerticle() {
      final Logger logger = LoggerFactory.getLogger("Publisher");

      @Override
      public void start() {
        vertx.setPeriodic(500, id -> {
          String message = "Message at " + System.currentTimeMillis();
          vertx.eventBus().publish("news-feed", message);
          logger.info("Published: " + message);
        });
      }
    });

    // Subscriber Verticle
    vertx.deployVerticle(new AbstractVerticle() {
      final Logger logger = LoggerFactory.getLogger("Subscriber");

      @Override
      public void start() {

//        EventBusOptions ebOptions = new EventBusOptions();
        MessageConsumer<String> consumer = vertx.eventBus().consumer("news-feed");

        // Set max buffered messages to 5
        consumer.setMaxBufferedMessages(5);

        // Initially pause the consumer to simulate buffering
        consumer.pause();
        logger.info("Consumer paused. Buffering messages...");

        vertx.setTimer(5000, id -> {
          logger.info("Resuming consumer after 5 seconds...");
          consumer.resume();
        });

        consumer.handler(msg -> {
          logger.info("Received: " + msg.body());
        });
      }
    });
  }
}
