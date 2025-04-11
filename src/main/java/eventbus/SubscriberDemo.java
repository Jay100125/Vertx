package eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriberDemo extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(SubscriberDemo.class);
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new SubscriberDemo());

  }

  public void start(Promise<Void> startPromise){
    startPromise.complete();
    vertx.eventBus().localConsumer(PublisherAndSubscriber.Publisher.class.getName(), msg -> {
      LOG.info("Received message: sub 1 " + msg.body().toString() + " On " + Thread.currentThread().getName());
    });

    vertx.eventBus().consumer("hello", msg -> {
      LOG.info("Received message: sub 1 " + msg.body().toString() + " On " + Thread.currentThread().getName());
    });
  }
}
