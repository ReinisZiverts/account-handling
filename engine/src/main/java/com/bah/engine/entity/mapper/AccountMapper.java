package com.bah.engine.entity.mapper;

import com.bah.engine.entity.Account;
import com.bah.engine.model.AccountDto;
import com.bah.engine.model.BalanceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto toDto(Account account);

    List<AccountDto> toDtoList(List<Account> accountList);

    Account toEntity(AccountDto accountDto);

    BalanceResponseDto toBalanceResponseDto(Account account);

}
