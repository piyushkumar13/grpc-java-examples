package com.example;

import com.example.service.GreetServiceImpl;
import java.io.IOException;
import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * This is the trivial way of writing Grpc server which does not handle gracefully shutting down of the server when any
 * jvm interrupts happen. Sometimes jvm might terminate externally due to some interrupts or unexpected errors.
 *
 */
public class TrivialGrpcServer
{
    public static void main( String[] args ) throws IOException, InterruptedException {


        System.out.println( ":::: Starting service :::: " );

        Server server = ServerBuilder
                .forPort(8080)
                .addService(new GreetServiceImpl().bindService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
