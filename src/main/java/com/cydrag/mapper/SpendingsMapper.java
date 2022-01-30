package com.cydrag.mapper;

import com.cydrag.dto.SpendingsDto;
import com.cydrag.response.Spendings;
import org.springframework.stereotype.Component;

@Component
public class SpendingsMapper {

    public Spendings convertSpendings(SpendingsDto spendingsDto) {
        Spendings spendings = new Spendings();

        spendings.setCode(spendingsDto.getCode());
        spendings.setAverage(spendingsDto.getAverage());

        return spendings;
    }
}
