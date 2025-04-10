package practice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class assesment extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
//    vertx.deployVerticle(assesment.class);
    System.out.println(assesment.class.getName());
    System.out.println(assesment.class.getCanonicalName());
    System.out.println(assesment.class.getSimpleName());
  }
//
//  public void start() {
//
//  }

}
