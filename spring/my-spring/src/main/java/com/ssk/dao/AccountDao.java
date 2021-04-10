package com.ssk.dao;

import com.ssk.pojo.Account;

/**
 * @author ssk
 */
public interface AccountDao {

    Account queryAccountByCardNo(String cardNo) throws Exception;

    int updateAccountByCardNo(Account account) throws Exception;
}
