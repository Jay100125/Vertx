package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextDemo extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(ContextDemo.class);

  public void start(Promise<Void> startPromise) throws Exception {
    LOG.info("Start");

    Context context = vertx.getOrCreateContext();
    if (context.isEventLoopContext()) {
      LOG.info("Context attached to Event Loop");
    } else if (context.isWorkerContext()) {
      LOG.info("Context attached to Worker Thread");
    } else if (! Context.isOnVertxThread()) {
      LOG.info("Context not attached to a thread managed by vert.x");
    }

    Promise<String> promise = Promise.promise(); // producer
    Future<String> future = promise.future();    // consumer

// Consumer reacts
    future.onSuccess(result -> {
      LOG.info("Success: " + result);
    }).onFailure(err -> {
      LOG.info("Failed: " + err.getMessage());
    });

// Producer completes
    promise.complete("Hello from promise!");

  }

}
