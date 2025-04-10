package timer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class PeriodicTimer extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(PeriodicTimer.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx(new VertxOptions().setMaxEventLoopExecuteTime(6000).setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS));
    vertx.deployVerticle(new PeriodicTimer());
  }

  public void start() throws InterruptedException {

    LOG.info("PeriodicTimer started");
//
//    vertx.setPeriodic(1000, l -> {
//      vertx.executeBlocking(future -> {
//        try {
//          Thread.sleep(10000);
//        } catch (InterruptedException e) {
//          throw new RuntimeException(e);
//        }
//        LOG.info("THis is how it works");
//      }, false);
//
//    });

    Thread.sleep(5000);
    vertx.executeBlocking(blockingFuture -> {
      vertx.setPeriodic(1000, l->{
        LOG.info("PeriodicTimer triggered");
      });
    });

    Thread.sleep(5000);

//    LOG.info("PeriodicTimer started let's go");
//    vertx.setPeriodic(1000, l -> {
//      try
//      {
//        Thread.sleep(5000);
//        LOG.info("IM HERE");
//      }
//      catch(InterruptedException e)
//      {
//        LOG.info("Something went wrong");
//      }
//    });
  }
}
