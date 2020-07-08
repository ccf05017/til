package com.poppo.grpc.server.sum;

import com.proto.sum.Params;
import com.proto.sum.SumRequest;
import com.proto.sum.SumResponse;
import com.proto.sum.SumServiceGrpc;
import io.grpc.stub.StreamObserver;

public class SumServiceImpl extends SumServiceGrpc.SumServiceImplBase {

    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {

        Params params = request.getParams();

        int result = params.getFirstParam() + params.getSecondParam();
        SumResponse sumResponse = SumResponse.newBuilder().setResult(result).build();

        responseObserver.onNext(sumResponse);

        responseObserver.onCompleted();
    }
}
