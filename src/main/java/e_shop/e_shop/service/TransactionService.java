package e_shop.e_shop.service;

import e_shop.e_shop.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getAllTransactions();
}

