package com.konasl.batchjob1.step.txnlog;

import com.konasl.batchjob1.domain.TxnLog;
import com.konasl.batchjob1.dto.RechargeTxn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/***************************
 * {@link Name: } Jobayed Ullah
 */

public class TxnItemProcessor implements ItemProcessor<RechargeTxn, TxnLog> {

    private static final Logger log = LoggerFactory.getLogger(TxnItemProcessor.class);
    //private static Long inc = 0L;

    @Override
    public TxnLog process(RechargeTxn rechargeTxn) throws Exception {
        final String trId = "TXN_" + rechargeTxn.getTransactionId() + System.currentTimeMillis();

        final TxnLog transformedTxnLog = new TxnLog(trId, rechargeTxn.getAmount());

        log.info("Converting (" + rechargeTxn + ") into (" + transformedTxnLog + ")");

        return transformedTxnLog;
    }
}
