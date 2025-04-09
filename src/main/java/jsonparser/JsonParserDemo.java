package jsonparser;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.JsonParser;

public class JsonParserDemo {
  public static void main(String[] args) {
    // Create a new JSON parser
    JsonParser parser = JsonParser.newParser();

    // Register handlers for different JSON events
    parser.objectValueMode()
      .handler(event -> {
        // This will be called for each complete object found
        JsonObject person = (JsonObject) event.value();
        System.out.println("Found person: " +
          person.getString("firstName") + " " +
          person.getString("lastName"));
      })
      .exceptionHandler(err -> {
        System.err.println("Error parsing JSON: " + err.getMessage());
      })
      .endHandler(v -> {
        System.out.println("Parsing completed successfully");
      });

    // Feed data in chunks, simulating how network data might arrive
    System.out.println("Starting to parse...");
    parser.handle(Buffer.buffer("[{\"firstName\":\"Bob\","));
    parser.handle(Buffer.buffer("\"lastName\":\"Morane\"},"));
    parser.handle(Buffer.buffer("{\"firstName\":\"Luke\",\"lastName\":\"Lucky\"}"));
    parser.handle(Buffer.buffer("]"));

    // Signal that we're done feeding data
    parser.end();
  }
}
