### NiFi FlowFile gRPC Server
Simple server to receive FlowFiles sent from a NiFi processor

Reference: https://issues.apache.org/jira/browse/NIFI-4037


##  Instructions

1. Clone this repo.
2. Build a NiFi flow of `GenerateFlowFile` and `InvokeGRPC` as described in the ticket above, but wait to enable the flow.
3. Configure the `InvokeGRPC` as follows:
  3a. remote host: localhost
  3b. remote port: Ensure that the port is the same as set here: https://github.com/m-hogue/nifi-grpc-server/blob/master/src/main/java/org/mikeyo/NifiFlowFileServer.java#L18
4. Configure the `GenerateFlowFile` processor to generate an abitrarily sized file at an arbitrary interval. I chose 30B @ every 5 seconds.
4. Run the gRPC server main method in your IDE: https://github.com/m-hogue/nifi-grpc-server/blob/master/src/main/java/org/mikeyo/NifiFlowFileServer.java
5. Enable both the `GenerateFlowFile` and `InvokeGRPC` processors. You should see the server log a message roughly every 5 seconds once the generated flow file has made it through the flow.
