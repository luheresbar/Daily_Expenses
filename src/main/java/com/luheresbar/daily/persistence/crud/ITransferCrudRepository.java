package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.TransferEntity;
import org.springframework.data.repository.ListCrudRepository;

public interface ITransferCrudRepository extends ListCrudRepository<TransferEntity, Integer> {

}
