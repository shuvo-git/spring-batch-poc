package com.konasl.batchjob1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/***************************
 * {@link Name: } Jobayed Ullah
 */

@Entity
@Table(name = "txn_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxnLog {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "txn_id")
    private String transactionId;

    @Column(name = "amt")
    private double amount;

    public TxnLog(String transactionId, double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
    }
}
