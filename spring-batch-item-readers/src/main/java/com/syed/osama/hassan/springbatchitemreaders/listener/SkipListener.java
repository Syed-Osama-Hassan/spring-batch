package com.syed.osama.hassan.springbatchitemreaders.listener;

import org.springframework.batch.core.annotation.OnSkipInRead;
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

}
