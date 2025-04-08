package practice;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileBufferDemo {

  public static final Logger LOG = LoggerFactory.getLogger(FileBufferDemo.class);

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new FileVerticle());
  }
}

class FileVerticle extends AbstractVerticle {

  public static final Logger LOG = LoggerFactory.getLogger(FileVerticle.class);

  @Override
  public void start() {
    String fileName = "demo.txt";
    Buffer data = Buffer.buffer("This is some content.");

    // Write file
    vertx.fileSystem().writeFile(fileName, data, writeResult -> {
      if (writeResult.succeeded()) {
        LOG.info("✅ File written successfully!");

        // Read file after successful write
        vertx.fileSystem().readFile(fileName, readResult -> {
          if (readResult.succeeded()) {
            Buffer fileData = readResult.result();
            LOG.info("📄 File content: " + fileData.toString());
          } else {
            LOG.info("❌ Failed to read file: " + readResult.cause());
          }
        });
      } else {
        LOG.info("❌ Failed to write file: " + writeResult.cause());
      }
    });
  }
}
