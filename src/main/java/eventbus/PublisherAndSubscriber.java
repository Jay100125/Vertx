package eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublisherAndSubscriber{

  private static final Logger LOG = LoggerFactory.getLogger(PublisherAndSubscriber.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Subscriber1());
    vertx.deployVerticle(new Subscriber2());
    vertx.deployVerticle(Subscriber3.class.getName(), new DeploymentOptions().setInstances(3));
    vertx.deployVerticle(new Publisher());

  }

   static class Publisher extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().publish(Publisher.class.getName(), " A message to everyone");

    }
  }

   public static class Subscriber1 extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().consumer(Publisher.class.getName(), msg -> {
        LOG.info("Received message: sub 1 " + msg.body().toString() + " On " + Thread.currentThread().getName());
      });
    }
  }

  public static class Subscriber2 extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().consumer(Publisher.class.getName(), msg -> {
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
      vertx.eventBus().consumer(Publisher.class.getName(), msg -> {
        LOG.info("Received message: sub 3 " + msg.body().toString() + " On " + Thread.currentThread().getName());
      });
    }
  }
}
