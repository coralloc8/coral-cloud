package com.coral.test.flink.fraud_detection;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Alert
 *
 * @author huss
 * @date 2024/3/5 13:43
 * @packageName com.coral.test.flink.fraud_detection
 * @className Alert
 */

@Data
@NoArgsConstructor
public class Alert {

    private String accountId;

    private String messagge;

    public Alert(String accountId, String messagge) {
        this.accountId = accountId;
        this.messagge = messagge;
    }
}
