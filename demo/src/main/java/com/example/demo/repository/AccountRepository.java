package com.example.demo.repository;

import com.example.demo.Entity.Account;
import com.example.demo.Entity.Enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    //select

    @Query
    Account findAccountByPhone(String phone);

    Account findAccountById(long id);

    List<Account> findAccountsByStoreId(long storeId);

    List<Account> findAccountsByRole(Role role);
}
