package verticletree;

import com.example.starter.MainVerticle;
import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleMain extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(VerticleMain.class);
  private String deploymentIdOfA;


  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new VerticleMain());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    LOG.info("VerticleMain started " + this.getClass().getName() );

    Context context = vertx.getOrCreateContext();
    if (context.isEventLoopContext()) {
      LOG.info("Context attached to Event Loop");
    } else if (context.isWorkerContext()) {
      LOG.info("Context attached to Worker Thread");
    } else if (! Context.isOnVertxThread()) {
      LOG.info("Context not attached to a thread managed by vert.x");
    }
    Thread currentThread = Thread.currentThread();

    context.runOnContext(v -> {
      if (Thread.currentThread() == currentThread) {
       LOG.info("Same thread");
      } else {
        LOG.info("Different thread");
      }
    });

    vertx.deployVerticle(new VerticleA(), res -> {
      if (res.succeeded()) {
        deploymentIdOfA = res.result(); // capture deployment ID
        LOG.info("VerticleA deployed with ID: {}", deploymentIdOfA);

        // Undeploy after 5 seconds
        vertx.setTimer(5000, id -> {
          vertx.undeploy(deploymentIdOfA, undeployRes -> {
            if (undeployRes.succeeded()) {
              LOG.info("Successfully undeployed VerticleA");
              startPromise.complete();
            } else {
              LOG.error("Failed to undeploy VerticleA", undeployRes.cause());
              startPromise.fail(res.cause());
            }
          });
        });

      } else {
        LOG.error("Failed to deploy VerticleA", res.cause());
      }
    });



  }
}
