package practice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

import java.util.ArrayList;
import java.util.List;

public class CMEExample {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new CMEVerticle());
  }
}

class CMEVerticle extends AbstractVerticle {

  List<String> items = new ArrayList<>();

  @Override
  public void start() {
    // Populate the list
    items.add("A");
    items.add("B");
    items.add("C");

    // Run the iteration in the event loop
    vertx.setTimer(1000, id -> {
      for (String item : items) {
        System.out.println("Reading item: " + item);
        try {
          Thread.sleep(1000); // simulate delay
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    // Run this modification in a different thread (simulate parallel)
    vertx.executeBlocking(promise -> {
      try {
        Thread.sleep(1500); // wait a bit until for-loop starts
        System.out.println("Modifying list from another thread...");
        items.add("D");  // 💥 This will cause ConcurrentModificationException
        promise.complete();
      } catch (Exception e) {
        promise.fail(e);
      }
    }, res -> {
      if (res.failed()) {
        System.out.println("Modification failed: " + res.cause());
      }
    });
  }
}
