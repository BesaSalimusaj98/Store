package e_shop.e_shop.mapper;

import e_shop.e_shop.dto.TransactionDto;
import e_shop.e_shop.entity.Transaction;

public class TransactionMapper {
    public static TransactionDto toDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionId(transaction.getTransactionId());
        transactionDto.setBarcode(transaction.getBarcode());
        transactionDto.setSoldPrice(transaction.getSoldPrice());
        transactionDto.setUser(UserMapper.toDto(transaction.getUser()));
        transactionDto.setTransactionDate(transaction.getTransactionDate()); // Ensure this is formatted properly
        return transactionDto;
    }

    public static Transaction toEntity(TransactionDto transactionDto) {
        if (transactionDto == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionDto.getTransactionId());
        transaction.setBarcode(transactionDto.getBarcode());
        transaction.setSoldPrice(transactionDto.getSoldPrice());
        transaction.setUser(UserMapper.toEntity(transactionDto.getUser()));
        transaction.setTransactionDate(transactionDto.getTransactionDate()); // Ensure this is parsed properly
        return transaction;
    }
}
