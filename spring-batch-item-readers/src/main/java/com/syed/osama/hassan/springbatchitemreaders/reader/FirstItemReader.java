package com.syed.osama.hassan.springbatchitemreaders.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FirstItemReader implements ItemReader<Integer> {
    List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
    int index = 0;

    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        System.out.println("Inside item reader");
        if(index < list.size()) {
            return list.get(index++);
        }
        index = 0;
        return null;
    }

}
