package com.poppo.grpc.client;

import com.proto.dummy.DummyServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {

    public static void main(String[] args) {

        System.out.println("Hello I'm gRPC client");

        // gRPC에서 사용할 채널 설정
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()     // 개발 중에만 이 설정 써라
                .build();

        // 동기 클라이언트 생성
        System.out.println("Creating stub");
        DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);

        // 비동기 클라이언트 생성
        DummyServiceGrpc.DummyServiceFutureStub asyncClient = DummyServiceGrpc.newFutureStub(channel);

        // 원하는 클라이언트 동작 수행

        //종료
        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
