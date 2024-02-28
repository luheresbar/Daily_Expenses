package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.Transfer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ITransferRepository {

    List<Transfer> getAll();

    Transfer save(Transfer transfer);

    boolean delete(int transferId, Integer userId);

    List<Transfer> getUserTransfers(Integer userId);

    Optional<Transfer> getById(int transferId);

    List<Transfer> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);
}
