package e_shop.e_shop.service.impl;

import e_shop.e_shop.dto.TransactionDto;
import e_shop.e_shop.entity.Transaction;
import e_shop.e_shop.mapper.TransactionMapper;
import e_shop.e_shop.repository.TransactionRepository;
import e_shop.e_shop.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(TransactionMapper::toDto)
                .collect(Collectors.toList());
    }

}

