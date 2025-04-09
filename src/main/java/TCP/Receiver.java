package TCP;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.buffer.Buffer;

public class Receiver extends AbstractVerticle {

  public static final Logger LOG = LoggerFactory.getLogger(Receiver.class);
  public void start() {
    Buffer buffer = Buffer.buffer().appendFloat(1.23f).appendInt(15);

    LOG.info("Starting Receiver");
    NetClient client = vertx.createNetClient();
    client.connect(8080, "localhost").onComplete(result -> {
      if (result.succeeded())
      {
        LOG.info("Client connected");
        NetSocket socket = result.result();

        socket.write(buffer).onComplete(result1->
        {
          if(result1.succeeded())
          {
           LOG.info("Message sent successfully");
          }
        });
        socket.handler(handler -> {
          LOG.info("Buffer received: " + handler.getString(0,10));
        });
      }
      else
      {
        LOG.info("Something wend wrong" + result.cause());
      }
    });
  }
}
