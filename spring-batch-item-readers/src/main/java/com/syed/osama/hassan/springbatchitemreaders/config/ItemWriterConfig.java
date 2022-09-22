package com.syed.osama.hassan.springbatchitemreaders.config;

import com.syed.osama.hassan.springbatchitemreaders.model.StudentJdbc;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

@Configuration
public class ItemWriterConfig {

    @Bean
    @StepScope
    public FlatFileItemWriter<StudentJdbc> flatFileItemWriter(
            @Value("#{jobParameters['outputFile']}") FileSystemResource fileSystemResource
    ) {
        FlatFileItemWriter<StudentJdbc> flatFileItemWriter = new FlatFileItemWriter<>();
        flatFileItemWriter.setResource(fileSystemResource);
        flatFileItemWriter.setHeaderCallback(new FlatFileHeaderCallback() {
            @Override
            public void writeHeader(Writer writer) throws IOException {
                writer.write("ID,First Name,Last Name,Email");
            }
        });
        flatFileItemWriter.setLineAggregator(new DelimitedLineAggregator<StudentJdbc>(){
            {
                setFieldExtractor(new BeanWrapperFieldExtractor<StudentJdbc>(){
                    {
                        setNames(new String[]{"id", "firstName", "lastName", "email"});
                    }
                });
            }
        });
        flatFileItemWriter.setFooterCallback(new FlatFileFooterCallback() {
            @Override
            public void writeFooter(Writer writer) throws IOException {
                writer.write("Created At: " + new Date());
            }
        });

        return flatFileItemWriter;
    }

    @Bean
    @StepScope
    public JsonFileItemWriter<StudentJdbc> jsonFileItemWriter(
            @Value("#{jobParameters['outputFile']}") FileSystemResource fileSystemResource
    ) {
        JsonFileItemWriter<StudentJdbc> jsonFileItemWriter = new JsonFileItemWriter<>(
                fileSystemResource, new JacksonJsonObjectMarshaller<StudentJdbc>());

        return jsonFileItemWriter;
    }

}
