package com.syed.osama.hassan.springbatchitemreaders.config;

import com.syed.osama.hassan.springbatchitemreaders.model.*;
import com.syed.osama.hassan.springbatchitemreaders.processor.FirstItemProcessor;
import com.syed.osama.hassan.springbatchitemreaders.reader.FirstItemReader;
import com.syed.osama.hassan.springbatchitemreaders.service.StudentService;
import com.syed.osama.hassan.springbatchitemreaders.writer.FirstItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.skip.AlwaysSkipItemSkipPolicy;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;

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

    @Autowired
    private StudentService studentService;

    @Autowired
    @Qualifier("universityDS")
    private DataSource dataSource;

    @Autowired
    private ItemWriterConfig itemWriterConfig;

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
//                .reader(responseItemReaderAdapter())
//                .reader(jdbcCursorItemReader())
//                .reader(xmlStaxEventItemReader(null))
//                .reader(jsonItemReader(null))
                .reader(flatFileItemReader(null))
//                .processor(firstItemProcessor)
//                .writer(firstItemWriter)
//                .writer(itemWriterConfig.flatFileItemWriter(null))
//                .writer(itemWriterConfig.jsonFileItemWriter(null))
//                .writer(itemWriterConfig.staxEventItemWriter(null))
//                .writer(itemWriterConfig.jdbcBatchItemWriter())
                .writer(itemWriterConfig.itemWriterAdapter())
                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skipPolicy(new AlwaysSkipItemSkipPolicy())
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader<StudentCsv> flatFileItemReader(
            @Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource
    ) {
        FlatFileItemReader<StudentCsv> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(fileSystemResource);

//        flatFileItemReader.setLineMapper(new DefaultLineMapper<StudentCsv>(){
//            {
//                setLineTokenizer(new DelimitedLineTokenizer() {
//                    {
//                        setNames("ID", "First Name", "Last Name", "Email");
//                    }
//                });
//
//                setFieldSetMapper(new BeanWrapperFieldSetMapper<StudentCsv>(){
//                    {
//                        setTargetType(StudentCsv.class);
//                    }
//                });
//            }
//        });

        DefaultLineMapper<StudentCsv> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setNames("ID", "First Name", "Last Name", "Email");

        BeanWrapperFieldSetMapper<StudentCsv> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(StudentCsv.class);

        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        flatFileItemReader.setLineMapper(defaultLineMapper);
        flatFileItemReader.setLinesToSkip(1);

        return flatFileItemReader;
    }

    @Bean
    @StepScope
    public JsonItemReader<StudentJson> jsonItemReader(
            @Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {
        JsonItemReader<StudentJson> jsonItemReader = new JsonItemReader<>();
        jsonItemReader.setResource(fileSystemResource);
        jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(StudentJson.class));


        return jsonItemReader;
    }

    @Bean
    @StepScope
    public StaxEventItemReader<StudentXml> xmlStaxEventItemReader(
            @Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {
        StaxEventItemReader<StudentXml> xmlStaxEventItemReader = new StaxEventItemReader<>();
        xmlStaxEventItemReader.setResource(fileSystemResource);
        xmlStaxEventItemReader.setFragmentRootElementName("student");
        xmlStaxEventItemReader.setUnmarshaller(new Jaxb2Marshaller(){
            {
                setClassesToBeBound(StudentXml.class);
            }
        });

        return xmlStaxEventItemReader;
    }

    public JdbcCursorItemReader<StudentJdbc> jdbcCursorItemReader() {
        JdbcCursorItemReader<StudentJdbc> jdbcCursorItemReader = new JdbcCursorItemReader<>();
        jdbcCursorItemReader.setDataSource(dataSource);
        jdbcCursorItemReader.setSql(
                "SELECT id, first_name as firstName, last_name as lastName, " +
                        "email FROM student");
        jdbcCursorItemReader.setRowMapper(new BeanPropertyRowMapper<>(StudentJdbc.class));

        return jdbcCursorItemReader;
    }

    public ItemReaderAdapter<StudentResponse> responseItemReaderAdapter() {
        ItemReaderAdapter<StudentResponse> responseItemReaderAdapter = new ItemReaderAdapter<>();
        responseItemReaderAdapter.setTargetObject(studentService);
        responseItemReaderAdapter.setTargetMethod("getStudent");

        return responseItemReaderAdapter;
    }
}
