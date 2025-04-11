package TCP;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.vertx.core.eventbus.impl.EventBusImpl;

import java.util.concurrent.atomic.AtomicInteger;

public class Sender extends AbstractVerticle
{
  private static final Logger LOG = LoggerFactory.getLogger(Sender.class);
  private static AtomicInteger counter = new AtomicInteger(0);
  public void start()
  {
    NetServer server = vertx.createNetServer();
//    server.listen(1234, "localhost");

    server.connectHandler(socket -> {
      LOG.info("Server connected");
      LOG.info("Local address: " + socket.localAddress());
      LOG.info("remote address: " + socket.remoteAddress());

      socket.write("Hello surf up").onComplete(result -> {
        if(result.succeeded())
        {
          LOG.info("Server connected result succeeded ");
        }
        else
        {
          LOG.info("Some error occurred "+ result.cause().getMessage());
        }
      });

      socket.sendFile("/home/jay/Downloads/goland-2024.3.5.tar.gz").onComplete(result -> {
        if(result.succeeded())
        {
          LOG.info("File send ");
        }
        else
        {
          LOG.info("Some error occurred "+ result.cause().getMessage());
        }
      });

//      vertx.setPeriodic(1000,  res -> {
//        socket.write("Hello world " + counter.incrementAndGet());
//        LOG.info("Sent " + counter + " messages");
//      });
    }).listen(8080, "localhost").onComplete(res->{
      LOG.info("Server started on port 8080 "+res.result());
    }).onFailure(result -> {
      LOG.info("Server failed to listen" + result.getCause().getMessage());
    });
    LOG.info("Hello world");
  }
}
