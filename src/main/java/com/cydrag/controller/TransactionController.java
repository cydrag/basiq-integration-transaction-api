package com.cydrag.controller;

import com.cydrag.client.basiq.BasiqClient;
import com.cydrag.client.basiq.response.TransactionsResponse;
import com.cydrag.dto.SpendingsDto;
import com.cydrag.dto.TransactionsDto;
import com.cydrag.mapper.SpendingsMapper;
import com.cydrag.mapper.TransactionsMapper;
import com.cydrag.response.SpendingsResponse;
import com.cydrag.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}")
public class TransactionController {

    private final BasiqClient basiqClient;
    private final CalculationService calculationService;
    private final TransactionsMapper transactionsMapper;
    private final SpendingsMapper spendingsMapper;

    @Autowired
    public TransactionController(
            BasiqClient basiqClient,
            CalculationService calculationService,
            TransactionsMapper transactionsMapper,
            SpendingsMapper spendingsMapper) {
        this.basiqClient = basiqClient;
        this.calculationService = calculationService;
        this.transactionsMapper = transactionsMapper;
        this.spendingsMapper = spendingsMapper;
    }

    @GetMapping("/average-spendings")
    public SpendingsResponse averageSpendings(@PathVariable String userId) {
        TransactionsResponse transactionsResponse = basiqClient.getTransactionsByUserId(userId);

        TransactionsDto transactionsDto = transactionsMapper.convertTransactions(transactionsResponse);

        List<SpendingsDto> spendingsDtoList = calculationService.averageSpendingsByCode(transactionsDto);

        SpendingsResponse spendingsResponse = new SpendingsResponse();
        spendingsResponse.setSpendings(spendingsDtoList.stream().map(spendingsMapper::convertSpendings).collect(Collectors.toList()));

        return spendingsResponse;
    }
}
