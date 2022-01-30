package com.cydrag.service;

import com.cydrag.dto.SpendingsDto;
import com.cydrag.dto.TransactionsDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalculationService {

    public List<SpendingsDto> averageSpendingsByCode(TransactionsDto transactionsDto) {
        Map<String, Pair> spendingsMap = new HashMap<>();

        transactionsDto.getData().forEach(transactionDataDto -> {
            String code = transactionDataDto.getSubClass().getCode();

            if (!spendingsMap.containsKey(code)) {
                spendingsMap.put(code, new Pair());
            }

            Pair pair = spendingsMap.get(code);
            pair.total += transactionDataDto.getAmount();
            pair.count++;
        });

        List<SpendingsDto> spendingsDtoList = new ArrayList<>();

        for (Map.Entry<String, Pair> spendingsEntry : spendingsMap.entrySet()) {
            SpendingsDto spendingsDto = new SpendingsDto();

            spendingsDto.setCode(spendingsEntry.getKey());

            Pair pair = spendingsEntry.getValue();
            spendingsDto.setAverage(Math.abs(pair.total) / pair.count);

            spendingsDtoList.add(spendingsDto);
        }

        return spendingsDtoList;
    }

    private static class Pair {
        private int count;
        private float total;

        public Pair() {
            this.count = 0;
            this.total = 0.0F;
        }
    }
}
