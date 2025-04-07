package com.example.starter;

import eventbus.RequestExample;
import eventbus.ResponseExample;
import io.vertx.core.*;
import io.vertx.core.file.FileProps;
import io.vertx.core.file.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import practice.MyVerticle;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;



public class MainVerticle {

  private static final Logger LOG  = LoggerFactory.getLogger(MainVerticle.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
//    vertx.deployVerticle(new MainVerticle());
//    vertx.deployVerticle(new RequestExample());
//    vertx.deployVerticle(new ResponseExample());
//    vertx.deployVerticle(new MyVerticle()).onComplete(res->{
//      LOG.info("compl");
//    });
    DeploymentOptions options = new DeploymentOptions().setThreadingModel(ThreadingModel.WORKER);
    vertx.deployVerticle(new ContextDemo(),options);
    LOG.info("MainVerticle startedhhvhvhv");
  }
//  @Override
//  public void start(Promise<Void> startPromise) throws Exception {
//    LOG.debug("MainVerticle start {}", getClass().getName());
//    vertx.deployVerticle(VerticleN.class.getName(),
//      new DeploymentOptions().setInstances(4));
//
//
//    FileSystem fs = vertx.fileSystem();
//
//    Future<FileProps> future = fs.props("./Jay.txt");
//
//    future.onComplete((AsyncResult<FileProps> ar) -> {
//      if (ar.succeeded()) {
//        FileProps props = ar.result();
//        LOG.info("File size = " + props.size());
//      } else {
//        LOG.info("Failure: " + ar.cause().getMessage());
//      }
//    });
//    startPromise.complete();
//  }
}
