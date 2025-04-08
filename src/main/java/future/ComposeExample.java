package future;
import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComposeExample {
  private static final Logger LOG = LoggerFactory.getLogger(ComposeExample.class);
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new MyVerticle());
  }

  static class MyVerticle extends AbstractVerticle {
    @Override
    public void start() {
      getUser("jay")
        .compose(user -> getUserProfile(user))
        .onSuccess(profile -> {
          LOG.info("Profile received: " + profile);
        })
        .onFailure(err -> {
          LOG.info("Error occurred: " + err.getMessage());
        });
    }

    private Future<String> getUser(String username) {
      Promise<String> promise = Promise.promise();

      vertx.setTimer(1000, id -> {
        if ("jay".equals(username)) {
          promise.complete("Jay Patel");
        } else {
          promise.fail("User not found");
        }
      });

      return promise.future();
    }

    private Future<String> getUserProfile(String user) {
      Promise<String> promise = Promise.promise();

      vertx.setTimer(1000, id -> {
        promise.complete("Profile of " + user + ": software engineer");
      });

      return promise.future();
    }
  }
}
