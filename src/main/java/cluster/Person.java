package cluster;
//import io.vertx.core.json.JsonObject;
//
//public class Person {
//  private String name;
//  private int age;
//
//  // Required: No-arg constructor
//  public Person() {}
//
//  public Person(String name, int age) {
//    this.name = name;
//    this.age = age;
//  }
//
//  // Getters
//  public String getName() {
//    return name;
//  }
//
//  public int getAge() {
//    return age;
//  }
//
//  // Setters (needed for deserialization)
//  public void setName(String name) {
//    this.name = name;
//  }
//
//  public void setAge(int age) {
//    this.age = age;
//  }
//
//  // Convert to JSON
//  public JsonObject toJson() {
//    return new JsonObject()
//      .put("name", this.name)
//      .put("age", this.age);
//  }
//
//  // Convert from JSON
//  public static Person fromJson(String jsonString) {
//    JsonObject json = new JsonObject(jsonString);
//    return new Person(json.getString("name"), json.getInteger("age"));
//  }
//
//  @Override
//  public String toString() {
//    return "Person{name='" + name + "', age=" + age + '}';
//  }
//}

import io.vertx.core.json.JsonObject;

public class Person {
  private String name;
  private int age;

  public Person() {} // Needed for codec

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public JsonObject toJson() {
    return new JsonObject()
      .put("name", name)
      .put("age", age);
  }

  public static Person fromJson(String jsonStr) {
    JsonObject json = new JsonObject(jsonStr);
    return new Person(json.getString("name"), json.getInteger("age"));
  }

  @Override
  public String toString() {
    return "Person{name='" + name + "', age=" + age + '}';
  }

  // Getters and setters
  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }
}

