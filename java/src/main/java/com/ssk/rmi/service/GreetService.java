package com.ssk.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author ssk
 * @date 2021/3/1
 */
public interface GreetService extends Remote {
    String sayHello(String name) throws RemoteException;
}
