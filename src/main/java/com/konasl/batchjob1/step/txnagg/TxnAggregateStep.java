package com.konasl.batchjob1.step.txnagg;

import com.konasl.batchjob1.dto.RechargeTxn;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/***************************
 * {@link Name: } Jobayed Ullah
 */

@Configuration
public class TxnAggregateStep {
    @Bean("txnAggregateReader")
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
}
