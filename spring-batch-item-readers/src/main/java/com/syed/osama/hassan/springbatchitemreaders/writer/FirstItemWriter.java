package com.syed.osama.hassan.springbatchitemreaders.writer;

import com.syed.osama.hassan.springbatchitemreaders.model.*;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<StudentResponse> {

    @Override
    public void write(List<? extends StudentResponse> list) throws Exception {
        System.out.println("Inside Item Writer");
        list.forEach(System.out::println);
    }

}
