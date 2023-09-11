package com.konasl.batchjob1.step.txnlog;

import com.konasl.batchjob1.domain.TxnLog;
import com.konasl.batchjob1.dto.RechargeTxn;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/***************************
 * {@link Name: } Jobayed Ullah
 */

@Configuration
public class TxnLogImportStep {
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
        String sql = "INSERT INTO txn_log (txn_id, amt) VALUES (:transactionId, :amount)";
        return new JdbcBatchItemWriterBuilder<TxnLog>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<TxnLog>())
                .sql(sql)
                .dataSource(dataSource)
                .build();
    }
}
