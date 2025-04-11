package TCP.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server extends AbstractVerticle {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
  public static void main(String[] args) {
    Launcher.executeCommand("run", Server.class.getName());
  }

  public void start()
  {
    vertx.createNetServer().connectHandler(socket -> {
      socket.pipeTo(socket);
    }).listen(1234);

    LOG.info("Server started at port 1234");
  }
}
