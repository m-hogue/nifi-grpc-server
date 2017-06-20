package org.mikeyo;

import java.util.logging.Logger;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

public class NifiFlowFileServerInterceptor implements ServerInterceptor {
    private static final Logger LOG = Logger.getLogger(NifiFlowFileServerInterceptor.class.getName());

    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        LOG.info("Metadata: " + headers);

//        LOG.info("Server call: " + call);
//        LOG.info("next: " + next);
        return next.startCall(call, headers);
    }
}
