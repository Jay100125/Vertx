package practice;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class MyVerticle extends AbstractVerticle
{
  private static final Logger LOG = LoggerFactory.getLogger(MyVerticle.class);

//  public static void main(String[] args) {
////    Vertx vertx = Vertx.vertx(new VertxOptions().setMaxWorkerExecuteTime(5000).setMaxWorkerExecuteTimeUnit(TimeUnit.MILLISECONDS));
////    DeploymentOptions options = new DeploymentOptions().setThreadingModel(ThreadingModel.VIRTUAL_THREAD);
////    vertx.deployVerticle(new MyVerticle(),options);
//
//    Vertx vertx = Vertx.vertx();
//    vertx.deployVerticle(new MyVerticle()).onComplete(res->{
//      System.out.println("helloooooo");
//    });
//
//
//    VertxOptions option = new VertxOptions();
//    LOG.info("Vertx Event Loop Pool Size: " + option.getEventLoopPoolSize());
//
//  }

  @Override
  public void start(Promise<Void> startPromise) throws InterruptedException {
    LOG.info("Verticle deployed");

    Thread.sleep(2000);

    startPromise.complete();
  }

}
