package com.konasl.batchjob1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/***************************
 * {@link Name: } Jobayed Ullah
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargeTxn {
    private String transactionId;
    private double amount;
}
