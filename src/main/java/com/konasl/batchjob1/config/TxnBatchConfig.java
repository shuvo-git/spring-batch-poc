package com.konasl.batchjob1.config;

import com.konasl.batchjob1.domain.TxnLog;
import com.konasl.batchjob1.dto.RechargeTxn;
import com.konasl.batchjob1.listener.JobCompletionListener;
import com.konasl.batchjob1.step.txnlog.TxnItemProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/***************************
 * {@link Name: } Jobayed Ullah
 */

@Configuration
public class TxnBatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean(name = "processTxnJob")
    public Job processTxnJob(JobRepository jobRepository,
                             JobExecutionListener listener,
                             @Qualifier("importTxn") Step importTxn) {
        return //jobBuilderFactory
                new JobBuilder("processTxnJob")
                        .incrementer(new RunIdIncrementer())
                        .listener(listener)
                        .repository(jobRepository)
                        .flow(importTxn)
                        .end()
                        .build();
    }

    @Bean(name = "importTxn")
    public Step importTxn(JobRepository jobRepository,
                          PlatformTransactionManager transactionManager,
                          JdbcBatchItemWriter<TxnLog> writer) {
        return new StepBuilder("importTxn")
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .<RechargeTxn, TxnLog>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public FlatFileItemReader<RechargeTxn> reader() {
        return new FlatFileItemReaderBuilder<RechargeTxn>()
                .name("personItemReader")
                .resource(new ClassPathResource("txn.csv"))
                .delimited()
                .delimiter(",")
                .names(new String[]{"transactionId", "amount"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<RechargeTxn>() {{
                    setTargetType(RechargeTxn.class);
                }})
                .build();
    }

    @Bean
    public TxnItemProcessor processor() {
        return new TxnItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<TxnLog> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<TxnLog>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO txn_log (txn_id, amt) VALUES (:transactionId, :amount)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public JobCompletionListener listener(){
        return new JobCompletionListener();
    }
}
