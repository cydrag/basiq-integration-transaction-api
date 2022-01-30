package com.cydrag.mapper;

import com.cydrag.client.basiq.response.SubClass;
import com.cydrag.client.basiq.response.TransactionData;
import com.cydrag.client.basiq.response.TransactionsResponse;
import com.cydrag.dto.SubClassDto;
import com.cydrag.dto.TransactionDataDto;
import com.cydrag.dto.TransactionsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionsMapper {

    private static final Logger log = LoggerFactory.getLogger(TransactionsMapper.class);

    public TransactionsDto convertTransactions(TransactionsResponse response) {
        TransactionsDto transactionsDto = new TransactionsDto();
        List<TransactionDataDto> transactionDataDtoList = new ArrayList<>();

        for (TransactionData transactionData : response.getData()) {
            try {
                TransactionDataDto transactionDataDto = convertTransactionData(transactionData);
                transactionDataDtoList.add(transactionDataDto);
            } catch (Exception e) {
                log.warn("Ignoring transaction due to conversion error.", e);
            }
        }
        transactionsDto.setData(transactionDataDtoList);

        return transactionsDto;
    }

    private TransactionDataDto convertTransactionData(TransactionData transactionData) {
        TransactionDataDto transactionDataDto = new TransactionDataDto();

        transactionDataDto.setAmount(Float.parseFloat(transactionData.getAmount()));
        transactionDataDto.setSubClass(convertSubClass(transactionData.getSubClass()));

        return transactionDataDto;
    }

    private SubClassDto convertSubClass(SubClass subClass) {
        SubClassDto subClassDto = new SubClassDto();

        subClassDto.setCode(subClass.getCode());
        subClassDto.setTitle(subClass.getTitle());

        return subClassDto;
    }
}
