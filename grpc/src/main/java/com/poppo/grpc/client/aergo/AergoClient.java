package com.poppo.grpc.client.aergo;

import com.proto.aergo.AergoRPCServiceGrpc;
import com.proto.aergo.BlockchainStatus;
import com.proto.aergo.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class AergoClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("10.6.119.22", 7845)
                .usePlaintext()
                .build();

        AergoRPCServiceGrpc.AergoRPCServiceBlockingStub aergoClient = AergoRPCServiceGrpc.newBlockingStub(channel);

        BlockchainStatus blockchain = aergoClient.blockchain(Empty.newBuilder().build());

        // 할일
        System.out.println(blockchain.toString());

        channel.shutdown();
    }
}
