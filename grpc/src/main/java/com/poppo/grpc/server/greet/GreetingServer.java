package com.poppo.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Hello gRPC! Server on 50051");

        Server server = ServerBuilder.forPort(50051)
                .addService(new GreetServiceImpl())
                .build();

        server.start();

        // 꺼지는 조건 추가
        // 중요
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Received Shutdown Request");
            server.shutdown();
            System.out.println("Successfully Stopped The Server");
        }));

        // 이건 뭐여..?
        // 이걸 안하면 서버 프로세스가 전체 블로킹 건다는 듯..?
        server.awaitTermination();
    }
}
