package eventloop;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EventLoopThreads extends AbstractVerticle
{
  public static final Logger LOG = LoggerFactory.getLogger(EventLoopThreads.class);
  public static void main(String[] args)
  {
    var vertx = Vertx.vertx(
      new VertxOptions().
        setEventLoopPoolSize(4).
        setMaxEventLoopExecuteTime(100).
        setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS).
        setBlockedThreadCheckInterval(1).
        setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
    );
    vertx.deployVerticle(EventLoopThreads.class , new DeploymentOptions().setInstances(5));

  }
  @Override
  public void start() throws Exception
  {
    LOG.info("Event Loop Thread Will block ");
    Thread.sleep(15000);
  }

}
