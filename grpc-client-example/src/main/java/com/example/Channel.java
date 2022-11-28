/*
 *  Copyright (c) 2022 GoTo
 *  All Rights Reserved Worldwide.
 *
 *  THIS PROGRAM IS CONFIDENTIAL AND PROPRIETARY TO GOTO
 *  AND CONSTITUTES A VALUABLE TRADE SECRET.
 */
package com.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author Piyush Kumar.
 * @since 28/11/22.
 */
public class Channel {

    private ManagedChannel managedChannel;

    public Channel(String hostName, int portNumber){
        this.managedChannel = ManagedChannelBuilder.forAddress(hostName, portNumber).usePlaintext().build();
    }

    public ManagedChannel getManagedChannel(){
        return this.managedChannel;
    }
}
