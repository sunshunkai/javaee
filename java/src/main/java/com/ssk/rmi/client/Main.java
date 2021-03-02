package com.ssk.rmi.client;

import com.ssk.rmi.service.GreetService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author ssk
 * @date 2021/3/1
 */
public class Main {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        GreetService greetService = (GreetService) Naming.lookup("rmi://localhost:8888/GreetService");
        System.out.println(greetService.sayHello("jobs"));
    }
}
