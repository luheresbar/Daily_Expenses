package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Transfer;
import com.luheresbar.daily.domain.repository.ITransferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {

    private final ITransferRepository transferRepository;

    public TransferService(ITransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public List<Transfer> getAll() {
        return transferRepository.getAll();
    }

    public Optional<Transfer> getById(int transferId) {
        return this.transferRepository.getById(transferId);
    }
    public List<Transfer> getUserTransfers(Integer userId) {
        return this.transferRepository.getUserTransfers(userId);
    }

    public List<Transfer> findByDateBetween (LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return this.transferRepository.findByDateBetween(startDate, endDate, userId);
    }

    public Transfer save(Transfer transfer) {
        return this.transferRepository.save(transfer);
    }

    public boolean delete(int transferId, Integer userId) {
        return this.transferRepository.delete(transferId, userId);
    }


}
