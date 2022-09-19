package com.syed.osama.hassan.springbatchitemreaders.writer;

import com.syed.osama.hassan.springbatchitemreaders.model.StudentCsv;
import com.syed.osama.hassan.springbatchitemreaders.model.StudentJson;
import com.syed.osama.hassan.springbatchitemreaders.model.StudentXml;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FirstItemWriter implements ItemWriter<StudentXml> {

    @Override
    public void write(List<? extends StudentXml> list) throws Exception {
        System.out.println("Inside Item Writer");
        list.forEach(System.out::println);
    }

}
