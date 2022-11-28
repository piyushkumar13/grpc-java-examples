/*
 *  Copyright (c) 2022 GoTo
 *  All Rights Reserved Worldwide.
 *
 *  THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO GOTO
 *  AND CONSTITUTES A VALUABLE TRADE SECRET.
 */
package com.example.client;

import static com.example.greetservice.GreetServiceGrpc.GreetServiceBlockingStub;

import com.example.Channel;
import com.example.greetservice.GreetRequest;
import com.example.greetservice.GreetResponse;
import com.example.greetservice.GreetServiceGrpc;
import java.util.concurrent.TimeUnit;
import io.grpc.ManagedChannel;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

/**
 * @author Piyush Kumar.
 * @since 28/11/22.
 */
public class GreetServiceClient {

    public static void main(String[] args) {
        Channel channel = new Channel("localhost", 8080);

        ManagedChannel managedChannel = channel.getManagedChannel();

        GreetServiceBlockingStub clientStub = GreetServiceGrpc.newBlockingStub(managedChannel);

        GreetRequest request1 = GreetRequest.newBuilder().setFirstName("Piyush").setLastName("Kumar").build();

        GreetResponse greetResponse1 = clientStub.greet(request1);
//        GreetResponse greetResponse1 = clientStub.withDeadlineAfter(5, TimeUnit.SECONDS).greet(request1); // We can also set the deadline for the client call.
        System.out.println("Response from first request ::: " + greetResponse1);


        GreetRequest request2 = GreetRequest.newBuilder().setFirstName("Kumar").setLastName("Piyush").build();

        try {
            GreetResponse greetResponse2 = clientStub.greet(request2);
            System.out.println("Response from second request ::: " + greetResponse2); // this should not get executed since in case of invalid request, api will throw exception.
        }catch (StatusRuntimeException e){
            System.out.println("Exception while calling greet api for request 2 " + "message : " + e.getMessage() + " status code : " + e.getStatus());

            boolean isInvalidCode = e.getStatus().getCode() == Status.Code.INVALID_ARGUMENT;
            if (isInvalidCode){
                System.out.println("Request was INVALID.");
            }
            e.printStackTrace();
        }
    }

}
