package eventbus;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class PointToPoint {

  private static final Logger LOG = LoggerFactory.getLogger(PointToPoint.class);


  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(Receiver.class.getName(), new DeploymentOptions().setInstances(5));
  }

 public static class Sender extends AbstractVerticle {
   static int counter = 0;
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
//      vertx.setPeriodic(1000, l -> {
//        System.out.println("Blocking...");
//        try {
//          Thread.sleep(700); // Blocks event loop for 200 ms
//        } catch (InterruptedException e) {
//          e.printStackTrace();
//        }
//      });

      vertx.setPeriodic(1000, l -> {
        vertx.eventBus().send(Sender.class.getName(), counter++);
      });

      vertx.eventBus().consumer(Receiver.class.getName(), msg -> {
        LOG.info("received message: " + msg.body());
      });
    }
  }

 public static class Receiver extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().consumer(Sender.class.getName(), message -> {
        LOG.info("Received: " + message.body().toString() + " On " + Thread.currentThread().getName());
      });
      vertx.eventBus().send(Receiver.class.getName(), "Hello");
    }
  }
}


// message
//package eventbus;
//
//import io.vertx.core.*;
//import io.vertx.core.eventbus.DeliveryOptions;
//import io.vertx.core.eventbus.Message;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class PointToPoint {
//
//  private static final Logger LOG = LoggerFactory.getLogger(PointToPoint.class);
//  public static final String ADDRESS = "my.message.address";
//
//  public static void main(String[] args) {
//    Vertx vertx = Vertx.vertx();
//    vertx.deployVerticle(new Sender());
//    vertx.deployVerticle(Receiver.class.getName(), new DeploymentOptions().setInstances(5));
//  }
//
//  public static class Sender extends AbstractVerticle {
//    static int counter = 0;
//
//    @Override
//    public void start(Promise<Void> startPromise) {
//      startPromise.complete();
//
//      vertx.setPeriodic(1000, id -> {
//        DeliveryOptions options = new DeliveryOptions()
//          .addHeader("some-header", "some-value")
//          .addHeader("message-number", String.valueOf(counter));
//
//        vertx.eventBus().send(ADDRESS, "Message " + counter++, options);
//      });
//    }
//  }
//
//  public static class Receiver extends AbstractVerticle {
//    @Override
//    public void start(Promise<Void> startPromise) {
//      startPromise.complete();
//
//      vertx.eventBus().consumer(ADDRESS, (Message<Object> message) -> {
//        String headerValue = message.headers().get("some-header");
//        String messageNum = message.headers().get("message-number");
//        LOG.info("Received: {} with header: {} (message-number: {}) on thread: {}",
//          message.body(), headerValue, messageNum, Thread.currentThread().getName());
//      });
//    }
//  }
//}
