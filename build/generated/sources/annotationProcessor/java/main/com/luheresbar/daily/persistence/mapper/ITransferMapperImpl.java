package com.luheresbar.daily.persistence.mapper;

import com.luheresbar.daily.domain.Transfer;
import com.luheresbar.daily.persistence.entity.TransferEntity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-15T22:09:03-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.3.jar, environment: Java 17.0.10 (JetBrains s.r.o.)"
)
@Component
public class ITransferMapperImpl implements ITransferMapper {

    @Override
    public Transfer toTransfer(TransferEntity transferEntity) {
        if ( transferEntity == null ) {
            return null;
        }

        Transfer transfer = new Transfer();

        if ( transferEntity.getTransferId() != null ) {
            transfer.setTransferId( transferEntity.getTransferId() );
        }
        transfer.setTransferValue( transferEntity.getTransferValue() );
        if ( transferEntity.getTransferDate() != null ) {
            transfer.setTransferDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( transferEntity.getTransferDate() ) );
        }
        transfer.setUserId( transferEntity.getUserId() );
        transfer.setSourceAccountName( transferEntity.getSourceAccountName() );
        transfer.setDestinationAccountName( transferEntity.getDestinationAccountName() );
        transfer.setType( transferEntity.getType() );
        transfer.setDescription( transferEntity.getDescription() );

        return transfer;
    }

    @Override
    public List<Transfer> toTransfers(List<TransferEntity> transferEntities) {
        if ( transferEntities == null ) {
            return null;
        }

        List<Transfer> list = new ArrayList<Transfer>( transferEntities.size() );
        for ( TransferEntity transferEntity : transferEntities ) {
            list.add( toTransfer( transferEntity ) );
        }

        return list;
    }

    @Override
    public TransferEntity toTransferEntity(Transfer transfer) {
        if ( transfer == null ) {
            return null;
        }

        TransferEntity transferEntity = new TransferEntity();

        transferEntity.setTransferId( transfer.getTransferId() );
        transferEntity.setTransferValue( transfer.getTransferValue() );
        if ( transfer.getTransferDate() != null ) {
            transferEntity.setTransferDate( LocalDateTime.parse( transfer.getTransferDate() ) );
        }
        transferEntity.setUserId( transfer.getUserId() );
        transferEntity.setSourceAccountName( transfer.getSourceAccountName() );
        transferEntity.setDestinationAccountName( transfer.getDestinationAccountName() );
        transferEntity.setType( transfer.getType() );
        transferEntity.setDescription( transfer.getDescription() );

        return transferEntity;
    }
}
