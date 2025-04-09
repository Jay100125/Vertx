package cluster;//package cluster;
//
//import io.vertx.core.AbstractVerticle;
//import io.vertx.core.eventbus.EventBus;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//
//public class ReceiverVerticle extends AbstractVerticle {
//  private static final Logger LOG = LoggerFactory.getLogger(ReceiverVerticle.class);
//  @Override
//  public void start() {
//    EventBus eventBus = vertx.eventBus();
//
//    eventBus.registerDefaultCodec(Person.class, new PersonMessageCodec());
//
//    eventBus.consumer("person.request", message -> {
////            Person person = (Person) message.body();
//      LOG.info("Received person: " + message.body());
//
//      // Send reply
////            Person reply = new Person("Hello " + person.getName(), person.getAge() + 1);
////            message.reply(message.);
//    });
//  }
//}

import cluster.Person;
import cluster.PersonMessageCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class ReceiverVerticle extends AbstractVerticle {

  @Override
  public void start() {
    EventBus eventBus = vertx.eventBus();
    System.out.println("ReceiverVerticle started");
    // Register the codec once for Person class
    eventBus.registerDefaultCodec(Person.class, new PersonMessageCodec());

    eventBus.consumer("person.request", message -> {
      // Safely cast the incoming message to Person
      Person person = (Person) message.body();

//            JsonObject data=(JsonObject) message.body();

      System.out.println("Received person: " + person.toString());
//      System.out.println("Shut up");
//      System.out.println(message.body());
      // Optionally send a reply
//      Person reply = new Person("Hello " + person.getName(), person.getAge() + 1);
//      message.reply(reply);  // This will also go through PersonMessageCodec
//            System.out.println("send "+ reply);
    });
  }
}
