package executeblocking;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

public class ExecuteBlocking
{
  private static final Vertx vertx = Vertx.vertx();

  private static final Logger LOGGER = LoggerFactory.getLogger(ExecuteBlocking.class);

  public static void main (String[] args)
  {
   vertx.deployVerticle(new Demo1());

  }

  public static class Demo1 extends AbstractVerticle
  {
    public void start()
    {
      vertx.executeBlocking(promise ->
      {
        try
        {
          Thread.sleep(3000);
        }
        catch ( Exception exception )
        {
          exception.printStackTrace();
        }

        LOGGER.info("I am after thread.sleep try catch block");

        promise.complete();
      });

      vertx.executeBlocking(promise ->
      {
        LOGGER.info("I am in second executeblocking code");

        promise.complete();
      });

      vertx.<String>executeBlocking(stringPromise ->
      {
        try
        {
          Thread.sleep(5000);
        }
        catch ( InterruptedException e )
        {
          throw new RuntimeException(e);
        }

        LOGGER.info("Main Promise");

        stringPromise.complete("completed!");

      },false,stringAsyncResult -> {
        if(stringAsyncResult.succeeded())
        {
          LOGGER.info(stringAsyncResult.result());
        }
        else
        {
          LOGGER.info(stringAsyncResult.cause().getMessage());
        }
      });

      vertx.executeBlocking(promise ->
      {
        try
        {
          Thread.sleep(5000);
        }
        catch ( InterruptedException e )
        {
          throw new RuntimeException(e);
        }

        LOGGER.info("I am after string execute-blocking code");

        promise.complete();
      });

      vertx.executeBlocking(promise ->
      {
        try
        {
          Thread.sleep(5000);
        }
        catch ( InterruptedException e )
        {
          throw new RuntimeException(e);
        }
        LOGGER.info("I am after execute-blocking code 2");

        promise.complete();
      });
    }
  }

}
