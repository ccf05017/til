package com.poppo.grpc.client.greet;

import com.proto.greet.GreetEveryoneRequest;
import com.proto.greet.GreetEveryoneResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GreetingClient {

    public static void main(String[] args) {

        System.out.println("Hello I'm gRPC client");

        // gRPC에서 사용할 채널 설정
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()     // 개발 중에만 이 설정 써라
                .build();

        // 양방향 통신
        GreetServiceGrpc.GreetServiceStub asyncClient = GreetServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<GreetEveryoneRequest> requestObserver = asyncClient.greetEveryone(new StreamObserver<GreetEveryoneResponse>() {
            @Override
            public void onNext(GreetEveryoneResponse value) {
                System.out.println("Response from server: " + value.getResult());
            }

            @Override
            public void onError(Throwable t) {
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                System.out.println("Server is done sending data");
                latch.countDown();
            }
        });

        Arrays.asList("poppo", "saul", "ita", "tom").forEach(
                name -> {
                    System.out.println("Sending: " + name);
                    requestObserver.onNext(GreetEveryoneRequest.newBuilder()
                            .setGreeting(Greeting.newBuilder().setFirstName(name).build())
                            .build());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        );

        requestObserver.onCompleted();

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        System.out.println("Creating stub");
//        // Dummies
//        // DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel); <- blocking
//        // DummyServiceGrpc.DummyServiceFutureStub asyncClient = DummyServiceGrpc.newFutureStub(channel); <- non-blocking
//
//        // 원하는 클라이언트 동작 수행
//        // blocking
//        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);
//
//        // message & request
//        Greeting greeting = Greeting.newBuilder().setFirstName("poppo").setLastName("jo").build();
//        GreetRequest greetRequest = GreetRequest.newBuilder().setGreeting(greeting).build();
//
//        // response
//        GreetResponse greetResponse = greetClient.greet(greetRequest);
//
//        System.out.println(greetResponse.getResult());

        //종료
        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
