package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Transfer;
import com.luheresbar.daily.persistence.entity.TransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {IAccountMapper.class})
public interface ITransferMapper {

    Transfer toTransfer(TransferEntity transferEntity);

    List<Transfer> toTransfers(List<TransferEntity> transferEntities);

    @Mapping(target = "sourceAccount", ignore = true)
    @Mapping(target = "destinationAccount", ignore = true)
    TransferEntity toTransferEntity(Transfer transfer);

}
