package com.poppo.grpc.server;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {

    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {

        // 요청
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();
        String lastName = greeting.getLastName();

        // 원하는 동작 수행
        String result = "Hello " + firstName + " " + lastName;

        // 응답 생성
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(result)
                .build();

        // 응답 전송(비동기)
        responseObserver.onNext(response);

        // rpc 종료
        responseObserver.onCompleted();
    }
}
