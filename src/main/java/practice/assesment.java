package practice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class assesment extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(assesment.class);
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(assesment.class.getName());
  }


  public void start() {


    try
    {
      LOG.info("HEllo");

      vertx.setPeriodic(1000, id -> {
          int a = 10/0;
      });
    }
    catch (Exception e)
    {
      LOG.info("Exception: " + e.getMessage());
    }
  }
}
