package eventbus;

import io.vertx.core.*;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class EventBusTimeOut
{
  public static void main(String[] args) throws InterruptedException {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new SenderVerticle());

    Thread.sleep(10000);
    vertx.deployVerticle(new ReceiverVerticle());
  }
}


class SenderVerticle extends AbstractVerticle {
  AtomicInteger counter = new AtomicInteger(0);
  private static final Logger LOG = LoggerFactory.getLogger(SenderVerticle.class);

  @Override
  public void start() {
    DeliveryOptions options = new DeliveryOptions().setSendTimeout(4000); // 2 seconds timeout
    vertx.setPeriodic(1000, id -> {

      String str = counter.incrementAndGet() + " Hello World!";
    vertx.eventBus().request("delayed.address", str, options
//      reply -> {
//      if (reply.succeeded()) {
//        LOG.info("Received reply: {}", reply.result().body());
//      }
//      else {
//        LOG.error("Failed to get reply: {}", reply.cause().getMessage());
//      }
//    }
    );


    });
  }
}

class ReceiverVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(ReceiverVerticle.class);

  @Override
  public void start() {
    vertx.eventBus().consumer("delayed.address", this::handleMessage);
  }

  private void handleMessage(Message<Object> message) {
    LOG.info("Received message: {}", message.body());

    // Simulate a delay (more than 2 seconds, so it will timeout)
    vertx.setTimer(3000, id -> {
      message.reply("World!"); // This will be too late
    });
  }
}
