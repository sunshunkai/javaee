package com.ssk.rmi.service;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author ssk
 * @date 2021/3/1
 */
public class Main {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException, MalformedURLException {
        LocateRegistry.createRegistry(8888);
        Naming.bind("rmi://localhost:8888/GreetService",new GreetServiceImpl());
        System.out.println("对象绑定成功");
    }
}
