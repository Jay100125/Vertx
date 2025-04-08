package future;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComposeDemo {
  private static final Logger LOG = LoggerFactory.getLogger(ComposeDemo.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    asyncOperation1()
      .compose(result1 -> {
        LOG.info("First result: " + result1);
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        return asyncOperation2(result1);  // passing result to next
      })
      .compose(result2 -> {
        LOG.info("Second result: " + result2);
        return asyncOperation3(result2);  // chaining another
      })
      .onSuccess(finalResult -> {
        LOG.info("Final result: " + finalResult);
      })
      .onFailure(err -> {
        LOG.error("Something went wrong", err);
      });
  }

  static Future<String> asyncOperation1() {
    return Future.future(promise -> {
      // simulate async work
      promise.complete("Hello");
    });
  }

  static Future<String> asyncOperation2(String input) {
    return Future.future(promise -> {
      promise.complete(input + " World");
    });
  }

  static Future<String> asyncOperation3(String input) {
    return Future.future(promise -> {
      promise.complete(input + "!");
    });
  }
}
