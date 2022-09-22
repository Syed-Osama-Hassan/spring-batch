package com.syed.osama.hassan.springbatchitemreaders.config;

import com.syed.osama.hassan.springbatchitemreaders.model.StudentCsv;
import com.syed.osama.hassan.springbatchitemreaders.model.StudentJdbc;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.Writer;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

@Configuration
public class ItemWriterConfig {
    @Autowired
    @Qualifier("universityDS")
    private DataSource dataSource;

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

    @Bean
    @StepScope
    public StaxEventItemWriter<StudentJdbc> staxEventItemWriter(
            @Value("#{jobParameters['outputFile']}") FileSystemResource fileSystemResource
    ) {
        StaxEventItemWriter<StudentJdbc> staxEventItemWriter = new StaxEventItemWriter<>();
        staxEventItemWriter.setResource(fileSystemResource);
        staxEventItemWriter.setRootTagName("students");
        staxEventItemWriter.setMarshaller(new Jaxb2Marshaller(){
            {
                setClassesToBeBound(StudentJdbc.class);
            }
        });

        return staxEventItemWriter;
    }

    @Bean
    public JdbcBatchItemWriter<StudentCsv> jdbcBatchItemWriter() {
        JdbcBatchItemWriter<StudentCsv> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();
        jdbcBatchItemWriter.setDataSource(dataSource);
        jdbcBatchItemWriter.setSql("INSERT INTO student(id,first_name,last_name,email) " +
                "VALUES (:id, :firstName, :lastName, :email)");
        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<StudentCsv>()
        );

        // With Prepared Statement
        /*jdbcBatchItemWriter.setItemPreparedStatementSetter(new ItemPreparedStatementSetter<StudentCsv>() {
            @Override
            public void setValues(StudentCsv studentCsv, PreparedStatement ps) throws SQLException {
                int i = 1;
                ps.setLong(i++, studentCsv.getId());
                ps.setString(i++, studentCsv.getFirstName());
                ps.setString(i++, studentCsv.getLastName());
                ps.setString(i++, studentCsv.getEmail());
            }
        });*/

        return jdbcBatchItemWriter;
    }

}
