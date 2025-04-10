package scalableTCP;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScalableTCPServer {

  private static final Logger LOG = LoggerFactory.getLogger(ScalableTCPServer.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    // Deploy multiple instances of the server verticle
    // The number of instances is equal to the number of available processors
    int instances = Runtime.getRuntime().availableProcessors();

    DeploymentOptions options = new DeploymentOptions()
      .setInstances(instances);

    vertx.deployVerticle(TcpServerVerticle.class.getName(), options, res -> {
      if (res.succeeded()) {
        LOG.info("Deployed " + instances + " instances of the TCP server verticle");
      } else {
        LOG.info("Failed to deploy: " + res.cause());
      }
    });
  }

  public static class TcpServerVerticle extends AbstractVerticle {

    @Override
    public void start() {
      // Create the TCP server with NetServerOptions
      NetServerOptions options = new NetServerOptions()
        .setPort(8888)
        .setHost("localhost");

      NetServer server = vertx.createNetServer(options);

      // Set up connection handler
      server.connectHandler(this::handleConnection);

      // Start listening
      server.listen(ar -> {
        if (ar.succeeded()) {
          String instanceID = context.deploymentID().substring(0, 8);
          LOG.info("[Instance " + instanceID + "] Server is listening on " + ar.result().actualPort());
        } else {
          LOG.info("Failed to start server: " + ar.cause());
        }
      });
    }

    private void handleConnection(NetSocket socket) {
      String instanceID = context.deploymentID().substring(0, 8);
      LOG.info("[Instance " + instanceID + "] New connection from " + socket.remoteAddress());

      // Set up data handler
      socket.handler(buffer -> {
        String message = buffer.toString().trim();
        LOG.info("[Instance " + instanceID + "] Received: " + message);

        // Echo the message back with instance ID
        socket.write("Echo from instance " + instanceID + ": " + message + "\n");
      });

      // Handle connection closed
      socket.closeHandler(v -> {
        LOG.info("[Instance " + instanceID + "] Connection closed");
      });

      // Handle connection errors
      socket.exceptionHandler(e -> {
        LOG.info("[Instance " + instanceID + "] Connection error: " + e.getMessage());
      });
    }
  }
}
