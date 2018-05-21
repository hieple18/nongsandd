package com.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Account;

/**
*@author HiepLe
*@version 1.0 Nov 8, 2017
*/

@Transactional
public class UserDAO {
    @PersistenceContext 
    private EntityManager entityManager;
    public Account getActiveUser(String userName) {
        Account activeUserInfo = new Account();
        short enabled = 1;
        List<?> list = entityManager.createQuery("SELECT u FROM UserInfo u WHERE userName=? and enabled=?")
                .setParameter(1, userName).setParameter(2, enabled).getResultList();
        if(!list.isEmpty()) {
            activeUserInfo = (Account)list.get(0);
        }
        return activeUserInfo;
    }
}
