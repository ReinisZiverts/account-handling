package com.bah.engine.entity.mapper;

import com.bah.engine.entity.Account;
import com.bah.engine.model.AccountDto;
import com.bah.engine.model.BalanceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto toDto(Account account);

    Account toEntity(AccountDto accountDto);

    BalanceResponseDto toBalanceResponseDto(Account account);

}
