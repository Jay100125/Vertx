package cluster;//package cluster;
//
//import io.vertx.core.AbstractVerticle;
//import io.vertx.core.Vertx;
//import io.vertx.core.VertxOptions;
//import io.vertx.core.impl.logging.Logger;
//import io.vertx.core.impl.logging.LoggerFactory;
//import io.vertx.core.spi.cluster.ClusterManager;
//import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
//
//import java.util.UUID;
//
//public class SenderVerticle {
//  private static final Logger LOGGER = LoggerFactory.getLogger(SenderVerticle.class);
//
//  public static void main(String[] args) {
//    // Create the cluster manager
//    ClusterManager clusterManager = new HazelcastClusterManager();
//
//    // Configure the Vert.x instance to use clustering
//    VertxOptions options = new VertxOptions()
//      .setClusterManager(clusterManager);
//
//    // Create a clustered Vert.x instance
//    Vertx.clusteredVertx(options, res -> {
//      if (res.succeeded()) {
//        Vertx vertx = res.result();
//        LOGGER.info("Sender started as part of the cluster");
//
//        // Deploy our sender verticle
//        vertx.deployVerticle(new MessageSender());
//      } else {
//        LOGGER.error("Failed to start sender node", res.cause());
//      }
//    });
//  }
//
//  // This verticle will send messages to the event bus
//  static class MessageSender extends AbstractVerticle {
//    @Override
//    public void start() {
//      LOGGER.info("Message sender verticle deployed");
//
//      // Set up a periodic task to send messages
//      vertx.setPeriodic(5000, id -> {
//        String message = "Hello from sender! " + UUID.randomUUID().toString();
//        LOGGER.info("Sending message: " + message);
//
//        // Send message to address "cluster.messages"
//        vertx.eventBus().request("cluster.messages", message, reply -> {
//          if (reply.succeeded()) {
//            LOGGER.info("Received reply: " + reply.result().body());
//          } else {
//            LOGGER.warn("No reply received: " + reply.cause().getMessage());
//          }
//        });
//      });
//    }
//  }
//}



import cluster.Person;
import cluster.PersonMessageCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderVerticle extends AbstractVerticle {

  public static final Logger LOG = LoggerFactory.getLogger(SenderVerticle.class);
  @Override
  public void start() {
    EventBus eventBus = vertx.eventBus();

    // Register the same codec on the sender side
    eventBus.registerDefaultCodec(Person.class, new PersonMessageCodec());

    // Create a sample Person object
    Person person = new Person("John Doe", 35);

    JsonObject personJson = new JsonObject();
    personJson.put("name", "John Doe");
    personJson.put("age", 35);

    // Send a request to the designated address; Vert.x will route it to the consumer on the cluster
      eventBus.request("person.request", person, reply -> {
        if (reply.succeeded()) {

          Person replyPerson = (Person) reply.result();
          LOG.info("Person received: {}", replyPerson);
        }
        else
        {
          LOG.error("Person received failed", reply.cause());
        }
      });
  }
}
