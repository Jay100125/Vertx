package practice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventBusPauseResumeDemo
{
  public static void main(String[] args)
  {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ProducerVerticle());
    vertx.deployVerticle(new ConsumerVerticle());
  }
}

class ProducerVerticle extends AbstractVerticle
{
  @Override
  public void start() {
    // Send messages every 200ms
    vertx.setPeriodic(200, id -> {
      vertx.eventBus().send("load.address", "Message at " + System.currentTimeMillis());
    });
  }
}

class ConsumerVerticle extends AbstractVerticle
{
  private static final Logger LOG = LoggerFactory.getLogger(ConsumerVerticle.class);
  private int messageCount = 0;
  private static final int MAX_MESSAGES_BEFORE_PAUSE = 5;

  @Override
  public void start() {
    MessageConsumer<String> consumer = vertx.eventBus().consumer("load.address");

    consumer.handler(message -> {
      messageCount++;
      LOG.info("Received: {}", message.body());

      if (messageCount >= MAX_MESSAGES_BEFORE_PAUSE) {
        LOG.warn("Pausing consumer after {} messages", messageCount);
        consumer.pause();

        // Simulate processing delay before resuming
        vertx.setTimer(5000, timerId -> {
          messageCount = 0;
          LOG.info("Resuming consumer...");
          consumer.resume();
        });
      }
    });
  }
}
