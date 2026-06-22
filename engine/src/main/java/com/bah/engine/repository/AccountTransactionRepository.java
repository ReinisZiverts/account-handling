package com.bah.engine.repository;

import com.bah.engine.entity.AccountTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Integer> {

    Page<AccountTransaction> findByAccountIdOrderByCreatedOnDesc(Integer accountId, Pageable pageable);

    AccountTransaction findByAccountIdAndId(Integer accountId, Integer id);

}
