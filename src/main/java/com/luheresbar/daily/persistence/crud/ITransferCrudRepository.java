package com.luheresbar.daily.persistence.crud;

import com.luheresbar.daily.persistence.entity.TransferEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface ITransferCrudRepository extends ListCrudRepository<TransferEntity, Integer> {
    List<TransferEntity> findAllByUserIdOrderByTransferDate(Integer userId);

}
