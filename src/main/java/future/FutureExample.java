package future;
import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FutureExample {
  private static final Logger LOG = LoggerFactory.getLogger(FutureExample.class);
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MyVerticle());
  }

  static class MyVerticle extends AbstractVerticle {
    @Override
    public void start() {
      getUserFromDB("jay")
        .onSuccess(user -> {
          LOG.info("User fetched: " + user);
        })
        .onFailure(err -> {
          LOG.info("Failed to fetch user: " + err.getMessage());
        });
    }

    private Future<String> getUserFromDB(String username) {
      Promise<String> promise = Promise.promise();

      // Simulate DB delay
      vertx.setTimer(1000, id -> {
        if ("jay".equals(username)) {
          promise.complete("Jay Patel, Software Engineer");
        } else {
          promise.fail("User not found");
        }
      });

      return promise.future();
    }
  }
}
