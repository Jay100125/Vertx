package TCP;

import cluster.ReceiverVerticle;
import cluster.SenderVerticle;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  private static Logger LOG = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Sender()).onFailure(result -> {
      LOG.info("Result " + result.getMessage());
    });
//      .compose(res->{
//      return vertx.deployVerticle(new Receiver());
//    });


  }
}
