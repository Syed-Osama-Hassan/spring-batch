package com.syed.osama.hassan.springbatchitemreaders.config;

import com.syed.osama.hassan.springbatchitemreaders.model.StudentCsv;
import com.syed.osama.hassan.springbatchitemreaders.processor.FirstItemProcessor;
import com.syed.osama.hassan.springbatchitemreaders.reader.FirstItemReader;
import com.syed.osama.hassan.springbatchitemreaders.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

@Configuration
public class JobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

   @Autowired
    private FirstItemReader firstItemReader;

    @Autowired
    private FirstItemProcessor firstItemProcessor;

    @Autowired
    private FirstItemWriter firstItemWriter;

    @Bean
    public Job firstJobWithChunkOrientedStep() {
        return jobBuilderFactory.get("Job with chunk oriented step")
                .incrementer(new RunIdIncrementer())
                .start(firstChunkStep())
                .build();

    }

    private Step firstChunkStep() {
        return stepBuilderFactory.get("First chunk step")
                .<StudentCsv, StudentCsv>chunk(3)
                .reader(flatFileItemReader(null))
//                .processor(firstItemProcessor)
                .writer(firstItemWriter)
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader<StudentCsv> flatFileItemReader(
            @Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource
    ) {
        FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(fileSystemResource);

        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>(){
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("ID", "First Name", "Last Name", "Email");
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>(){
                    {
                        setTargetType(StudentCsv.class);
                    }
                });
            }
        });

        flatFileItemReader.setLinesToSkip(1);

        return flatFileItemReader;
    }

}
