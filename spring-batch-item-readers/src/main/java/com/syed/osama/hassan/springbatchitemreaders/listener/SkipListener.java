package com.syed.osama.hassan.springbatchitemreaders.listener;

import com.syed.osama.hassan.springbatchitemreaders.model.StudentCsv;
import com.syed.osama.hassan.springbatchitemreaders.model.StudentJson;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

@Component
public class SkipListener {

    @OnSkipInRead
    public void skipInRead(Throwable t) {
        if(t instanceof FlatFileParseException) {
            FlatFileParseException ex = (FlatFileParseException) t;
            System.out.println("RECORD: " + ex.getInput());
        }
    }

    @OnSkipInProcess
    public void skipInProcess(StudentCsv studentCsv, Throwable t) {
        System.out.println(studentCsv.toString());
    }

    @OnSkipInWrite
    public void skipInWrite(StudentJson studentJson, Throwable t) {
        System.out.println(studentJson.toString());
    }

}
