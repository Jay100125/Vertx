package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class VerticleN extends AbstractVerticle {

  private static final Logger log  = LoggerFactory.getLogger(VerticleN.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.setTimer(1000, id -> {
      log.info("Timer fired on thread: {}", Thread.currentThread().getName());
//      vertx.close(); // gracefully shut down after the task
    });

    log.info("Main thread: {}", Thread.currentThread().getName());

    vertx.executeBlocking(promise -> {
      log.info("Inside executeBlocking - Thread: {}", Thread.currentThread().getName());
      // Do some blocking stuff here (simulate with sleep, etc. if you want)
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      promise.complete(); // Important!
    }, res -> {
      log.info("Blocking code done! Result: {}", res.succeeded());
      vertx.close(); // Graceful shutdown
    });
  }
//  @Override
//  public void start(Promise<Void> startPromise) throws Exception {
////    System.out.println("VerticleN start" + " " + Thread.currentThread().getName());
//    log.debug("Verticle {}", Thread.currentThread().getName());
//  }
}
