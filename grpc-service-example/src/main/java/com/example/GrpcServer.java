/*
 *  Copyright (c) 2022 GoTo
 *  All Rights Reserved Worldwide.
 *
 *  THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO GOTO
 *  AND CONSTITUTES A VALUABLE TRADE SECRET.
 */
package com.example;

import com.example.service.GreetServiceImpl;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * This is the better way of writing Grpc server which gracefully shutdown the server when any
 * jvm interrupts happen. Sometimes jvm might terminate externally due to some interrupts or unexpected errors, in such
 * scenario we should gracefully shutdown the server which can be done by registering shutdown hooks as shown below.
 *
 * @author Piyush Kumar.
 * @since 28/11/22.
 */
public class GrpcServer {

    private Server server;
    private int portNumber;

    public GrpcServer(int portNumber, BindableService service) {
        this.portNumber = portNumber;
        this.server = ServerBuilder.forPort(portNumber).addService(service).build();
    }

    public void startServer() throws IOException {

        System.out.println(":::: Starting server at port :::: " + portNumber);
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            System.err.println("Some unexpected exception occurred. Shutting down gRPC server");

            try {
                server.shutdown().awaitTermination(30, TimeUnit.SECONDS); /* Shutdown server after waiting for 30 seconds. Check shutdown method java doc. */
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
        }));
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        GrpcServer grpcServer = new GrpcServer(8080, new GreetServiceImpl());
        grpcServer.startServer();
        grpcServer.server.awaitTermination();

    }
}
