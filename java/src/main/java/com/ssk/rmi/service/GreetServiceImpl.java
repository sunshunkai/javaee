package com.ssk.rmi.service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author ssk
 * @date 2021/3/1
 */
public class GreetServiceImpl extends UnicastRemoteObject implements GreetService{

    public GreetServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        return "Hello " + name;
    }
}
