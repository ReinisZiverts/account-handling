package com.bah.engine.repository;

import com.bah.engine.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionsRepository extends JpaRepository<AccountTransaction, Integer> {

    List<AccountTransaction> findByAccountId(Integer accountId);

}
