package com.bah.engine.entity.mapper;

import com.bah.engine.entity.AccountTransaction;
import com.bah.engine.model.AccountTransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTransactionsMapper {

    AccountTransactionsMapper INSTANCE = Mappers.getMapper(AccountTransactionsMapper.class);

    AccountTransactionDto toDto(AccountTransaction accountTransaction);

    List<AccountTransactionDto> toDtoList(List<AccountTransaction> accountTransactionsList);

}
