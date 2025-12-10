package vn.microservice.service.impl;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import vn.microservice.service.AuthenticationService;
import vn.microservice.grpc.VerifyTokenGrpcRequest;
import vn.microservice.grpc.VerifyTokenGrpcResponse;

@GrpcService
@RequiredArgsConstructor
@Slf4j
public class VerifyTokenServiceImpl extends VerifyTokenServiceGrpc.VerifyTokenServiceImplBase {

    private final AuthenticationService authenticationService;

    @Override
    public void verify(VerifyTokenGrpcRequest request, StreamObserver<VerifyTokenGrpcResponse> responseStreamObserver) {
        log.info("Token {}", request.getToken().substring(0, 15));


    }
}
