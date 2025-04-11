package eventbus;

import io.vertx.core.*;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.eventbus.MessageConsumerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class PublisherAndSubscriber {

  private static final Logger LOG = LoggerFactory.getLogger(PublisherAndSubscriber.class);

  public static void main(String[] args) throws InterruptedException {
    Vertx vertx = Vertx.vertx(new VertxOptions().setMaxEventLoopExecuteTime(20000).setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS));
    vertx.deployVerticle(new Publisher());
    Thread.sleep(10000);
    vertx.deployVerticle(new Subscriber1());
//    vertx.deployVerticle(new Subscriber2());
//    vertx.deployVerticle(Subscriber3.class.getName(), new DeploymentOptions().setInstances(1));
  }

  static class Publisher extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();

      // Send messages periodically at a fast rate (every 50ms)
      vertx.setPeriodic(1000, id -> {
        String message = "Message at time: " + System.currentTimeMillis();
        LOG.info("Publishing: " + message);
        vertx.eventBus().publish(Publisher.class.getName(), message);
      });
    }
  }

  public static class Subscriber1 extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().localConsumer(Publisher.class.getName(), msg -> {
        LOG.info("Received message: sub 1 " + msg.body().toString() + " On " + Thread.currentThread().getName());
      });

      vertx.eventBus().consumer("hello", msg -> {
        LOG.info("Received message: sub 1 " + msg.body().toString() + " On " + Thread.currentThread().getName());
      });
    }
  }

  public static class Subscriber2 extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().localConsumer(Publisher.class.getName(), msg -> {
        LOG.info("Received message: sub 2 " + msg.body().toString() + " On " + Thread.currentThread().getName());
      });
      vertx.eventBus().consumer(Publisher.class.getName(), msg -> {
        LOG.info("Received message: sub 22 " + msg.body().toString() + " On " + Thread.currentThread().getName());
      });
    }
  }

  public static class Subscriber3 extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();

      // Set a small queue size of 5 messages
//      MessageConsumerOptions options = new MessageConsumerOptions().setMaxBufferedMessages(5);

      MessageConsumer<Object> consumer = vertx.eventBus().localConsumer(Publisher.class.getName(), msg -> {
        LOG.info("Received message START: sub 3 " + msg.body().toString() + " On " + Thread.currentThread().getName());

        // Simulate slow processing by sleeping
        try {
          // Sleep for 500ms to simulate slow processing
          Thread.sleep(5000);
        } catch (InterruptedException e) {
          LOG.error("Interrupted while processing", e);
        }

        LOG.info("Received message END: sub 3 " + msg.body().toString() + " On " + Thread.currentThread().getName());
      });

      // Set the max queue size to 5 messages
      consumer.setMaxBufferedMessages(5);
    }
  }
}
