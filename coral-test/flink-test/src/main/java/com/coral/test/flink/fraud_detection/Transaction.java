package com.coral.test.flink.fraud_detection;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Transaction
 *
 * @author huss
 * @date 2024/3/5 13:42
 * @packageName com.coral.test.flink.fraud_detection
 * @className Transaction
 */

@NoArgsConstructor
@Data
public class Transaction {

    private String accountId;

    private Double amount;

    private LocalDateTime createTime;

    public Transaction(String accountId, Double amount, LocalDateTime createTime) {
        this.accountId = accountId;
        this.amount = amount;
        this.createTime = createTime;
    }
}
