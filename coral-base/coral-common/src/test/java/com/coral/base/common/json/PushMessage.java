package com.coral.base.common.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author huss
 * @version 1.0
 * @className PushMessage
 * @description todo
 * @date 2022/2/8 13:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class PushMessage {

    private PushKey pushKey;


    private LocalDateTime sendTime;


}
