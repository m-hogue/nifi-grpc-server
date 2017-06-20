package org.mikeyo;

import java.io.IOException;
import java.util.logging.Logger;

import io.grpc.CompressorRegistry;
import io.grpc.DecompressorRegistry;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerInterceptors;

public class NifiFlowFileServer {
    private static final Logger LOG = Logger.getLogger(NifiFlowFileServer.class.getName());

    private Server server;

    private void start() throws IOException {
    /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(ServerInterceptors.intercept(new FlowFileService(), new NifiFlowFileServerInterceptor()))
                // supports both gzip and plaintext
                .compressorRegistry(CompressorRegistry.getDefaultInstance())
                .decompressorRegistry(DecompressorRegistry.getDefaultInstance())
                .build()
                .start();
        LOG.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                LOG.info("*** shutting down gRPC server since JVM is shutting down");
                NifiFlowFileServer.this.stop();
                LOG.info("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final NifiFlowFileServer server = new NifiFlowFileServer();
        server.start();
        server.blockUntilShutdown();
    }
}
