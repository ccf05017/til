package com.poppo.grpc.client;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {

    public static void main(String[] args) {

        System.out.println("Hello I'm gRPC client");

        // gRPC에서 사용할 채널 설정
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()     // 개발 중에만 이 설정 써라
                .build();

        System.out.println("Creating stub");
        // Dummies
        // DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel); <- blocking
        // DummyServiceGrpc.DummyServiceFutureStub asyncClient = DummyServiceGrpc.newFutureStub(channel); <- non-blocking

        // 원하는 클라이언트 동작 수행
        // blocking
        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        // message & request
        Greeting greeting = Greeting.newBuilder().setFirstName("poppo").setLastName("jo").build();
        GreetRequest greetRequest = GreetRequest.newBuilder().setGreeting(greeting).build();

        // response
        GreetResponse greetResponse = greetClient.greet(greetRequest);

        System.out.println(greetResponse.getResult());

        //종료
        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}