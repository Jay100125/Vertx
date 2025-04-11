package filesystem;

import io.vertx.core.AbstractVerticle;

public class FileDemo extends AbstractVerticle {
  @Override
  public void start() {
    vertx.fileSystem().readFile("./src/Jay.txt", result -> {
      if (result.succeeded()) {
        System.out.println("File content: " + result.result().toString());
      } else {
        System.out.println("Failed to read file: " + result.cause());
      }
    });

//    vertx.fileSystem().copy("./src/Jay.txt", "./src/Jay_copy.txt", result -> {
//      if (result.succeeded()) {
//        System.out.println("File copied successfully");
//      } else {
//        System.out.println("Failed to copy file: " + result.cause());
//      }
//    });

  }
}
