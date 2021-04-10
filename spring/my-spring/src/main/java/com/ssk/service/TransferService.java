package com.ssk.service;

/**
 * @author ssk
 */
public interface TransferService {

    void transfer(String fromCardNo,String toCardNo,int money) throws Exception;
}
