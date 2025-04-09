package cluster;

//import com.hazelcast.config.Config;
//import com.hazelcast.config.JoinConfig;
//import com.hazelcast.config.TcpIpConfig;
//import io.vertx.core.Vertx;
//import io.vertx.core.VertxOptions;
//import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
//
//public class ClusteredAppStarter {
//
//  public static void main(String[] args) {
//    HazelcastClusterManager clusterManager = new HazelcastClusterManager();
//    VertxOptions options = new VertxOptions().setClusterManager(clusterManager);
//
//    // Optionally, configure the host for this node
//    // For the machine that should have IP 10.20.40.123:
//    options.getEventBusOptions().setHost("10.20.41.66");
//
//    Config hazelcastConfig = new Config();
//    JoinConfig join = hazelcastConfig.getNetworkConfig().getJoin();
//
//    join.getMulticastConfig().setEnabled(false);
//    TcpIpConfig tcpIpConfig = join.getTcpIpConfig()
//      .setEnabled(true)
//      .addMember("10.20.41.66") // your machine IP (consumer)
//      .addMember("10.20.40.123"); // sender machine IP (this machine)
//
//
//    Vertx.clusteredVertx(options, res -> {
//      if (res.succeeded()) {
//        Vertx vertx = res.result();
//
//        // Deploy the consumer or sender depending on the purpose of this node
//        // For example, on the consumer node (10.20.40.123):
////        vertx.deployVerticle(new PersonConsumerVerticle());
//
//        // On the sender node, deploy the sender verticle:
//                 vertx.deployVerticle(new SenderVerticle());
//      } else {
//        System.err.println("Cluster up failed: " + res.cause().getMessage());
//      }
//    });
//  }
//}
//
//import com.hazelcast.config.Config;
//import com.hazelcast.config.JoinConfig;
//import com.hazelcast.config.NetworkConfig;
//import io.vertx.core.Vertx;
//import io.vertx.core.VertxOptions;
//import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
//
//public class ClusteredAppStarter {
//  public static void main(String[] args) {
//    // Programmatic Hazelcast config
//    Config hazelcastConfig = new Config();
//    NetworkConfig network = hazelcastConfig.getNetworkConfig();
//
//    network.setPort(5701);
//    network.setPortAutoIncrement(true);
//
//    JoinConfig join = network.getJoin();
//    join.getMulticastConfig().setEnabled(false);
//    join.getTcpIpConfig()
//      .setEnabled(true)
//      .addMember("10.20.41.66")  // Add your friend's IP
//      .addMember("10.20.40.123"); // Add your own IP as well
//
//    HazelcastClusterManager clusterManager = new HazelcastClusterManager(hazelcastConfig);
//
//    VertxOptions options = new VertxOptions()
//      .setClusterManager(clusterManager);
//
//    options.getEventBusOptions().setHost("10.20.40.123");
//
//    Vertx.clusteredVertx(options, res -> {
//      if (res.succeeded()) {
//        Vertx vertx = res.result();
//
//        // Deploy publisher on your PC only
//        vertx.deployVerticle(new PublisherVerticle());
//
//        // Deploy consumer on your friend's PC only
////        vertx.deployVerticle(new ConsumerVerticle());
//      } else {
//        System.err.println("Failed to join cluster: " + res.cause());
//      }
//    });
//  }
//}


import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.TcpIpConfig;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class ClusteredAppStarter {

  public static void main(String[] args) {
    Config hazelcastConfig = new Config();
    JoinConfig join = hazelcastConfig.getNetworkConfig().getJoin();
    join.getMulticastConfig().setEnabled(false);
    TcpIpConfig tcp = join.getTcpIpConfig().setEnabled(true);
    tcp.addMember("10.20.41.66"); // Friend's IP

    HazelcastClusterManager mgr = new HazelcastClusterManager(hazelcastConfig);
    VertxOptions options = new VertxOptions().setClusterManager(mgr);
    options.getEventBusOptions().setHost("10.20.40.123"); // Your IP

    Vertx.clusteredVertx(options, res -> {
      if (res.succeeded()) {
        Vertx vertx = res.result();
        vertx.deployVerticle(new ReceiverVerticle());
//        vertx.deployVerticle(new SenderVerticle());
      } else {
        System.err.println("Cluster failed: " + res.cause());
      }
    });
  }
}


