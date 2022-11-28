/*
 *  Copyright (c) 2022 GoTo
 *  All Rights Reserved Worldwide.
 *
 *  THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO GOTO
 *  AND CONSTITUTES A VALUABLE TRADE SECRET.
 */
package com.example.service;

import static com.example.greetservice.GreetServiceGrpc.GreetServiceImplBase;

import com.example.greetservice.GreetRequest;
import com.example.greetservice.GreetResponse;
import com.google.protobuf.ApiOrBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

/**
 * @author Piyush Kumar.
 * @since 28/11/22.
 */
public class GreetServiceImpl extends GreetServiceImplBase {

    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver){

        String name = request.getFirstName() + request.getLastName();

        String message  = "Welcome to the world of grpc ::: " + name;
        System.out.println(message);

        if (name.equalsIgnoreCase("KumarPiyush")){
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Name is not correct").asRuntimeException());
            return;
//            responseObserver.onCompleted(); // Either use return statement or oncompleted here so that flow will not go further in case of error.
        }

        System.out.println("executing");

        GreetResponse greetResponse = GreetResponse.newBuilder().setMessage(message).build();

        responseObserver.onNext(greetResponse);
        responseObserver.onCompleted();
    }
}
