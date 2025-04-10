## doubts
- context.runOnContext()
- what is quorum
- setDeamonThread
- executeBlocking - 10 or 60


## what is vertx
- Vert.x is a reactive, non-blocking toolkit for building high-performance, event-driven applications on the Java Virtual Machine (JVM).
- Vert.x implements a reactor pattern similar to Node.js

### what is reactive
- responsive : the system respond in timely manner
- resilient : The system stays responsive in the face of failure.
  - Resilience is achieved by replication, containment, isolation and delegation. Failures are contained within each component, isolating components from each other and thereby ensuring that parts of the system can fail and recover without compromising the system as a whole.

- Elastic : The system stays responsive under varying workload. shard or replicate
- Message Driven : Reactive Systems rely on asynchronous message-passing

### what is non-blocking
- non-blocking means the system never waits (blocks a thread) for something to finish. Instead, it continues doing other work and gets notified when the result is ready.

- vertx instance
- single vertx instance share same event bus
- can set options
-


verticle
- standard verticle
- worker verticle
- verticle communicate using event bus

extend abstractVerticle
