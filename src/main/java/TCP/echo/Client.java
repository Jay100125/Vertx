package TCP.echo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Launcher;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client extends AbstractVerticle {

  private static final Logger LOG = LoggerFactory.getLogger(Client.class);
  public static void main(String[] args) {
    Launcher.executeCommand("run", Client.class.getName());
  }

  public void start() throws Exception
  {
    vertx.createNetClient().connect(1234, "localhost", res -> {
      if (res.succeeded()) {
        NetSocket socket = res.result();
        socket.handler(buff -> {
          LOG.info("Net client receiving " + buff.toString());
        });

        for(int i = 0; i < 10; i++)
        {
          String str = "Hello " + i + "\n";
          LOG.info("Sending " + str);
          socket.write(str);
        }
      }
      else
      {
        LOG.info("Net client failed to connect" + res.cause().getMessage());
      }
    });
  }
}
