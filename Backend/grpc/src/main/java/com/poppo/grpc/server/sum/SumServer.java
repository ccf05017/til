package com.poppo.grpc.server.sum;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class SumServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Sum Server On: " + 50080);

        Server server = ServerBuilder.forPort(50080).addService(new SumServiceImpl()).build();

        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully Shutdown");
        }));

        server.awaitTermination();
    }
}
