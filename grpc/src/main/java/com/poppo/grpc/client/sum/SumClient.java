package com.poppo.grpc.client.sum;

import com.proto.sum.Params;
import com.proto.sum.SumRequest;
import com.proto.sum.SumResponse;
import com.proto.sum.SumServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class SumClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50080)
                .usePlaintext()
                .build();

        SumServiceGrpc.SumServiceBlockingStub sumBlockingClient = SumServiceGrpc.newBlockingStub(channel);

        Params sumParams = Params.newBuilder().setFirstParam(1).setSecondParam(1).build();
        SumRequest sumRequest = SumRequest.newBuilder().setParams(sumParams).build();

        SumResponse sumResponse = sumBlockingClient.sum(sumRequest);

        System.out.println(sumResponse);

        channel.shutdown();
    }
}
