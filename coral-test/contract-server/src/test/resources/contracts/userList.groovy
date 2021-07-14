package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "查询用户列表"
    request {
        method GET()
        urlPath("/users") {
            queryParameters {
                parameter "age": $(consumer(regex(number())), producer(20))
                //只匹配汉字和字母
                parameter "name": $(consumer(regex(onlyAlphaUnicode())), producer(""))
            }
        }

    }

    response {
        status OK()
        headers {
            contentType applicationJsonUtf8()
        }
        body([

                success: true,
                message: "成功",
                data   :
                        '''
                    [{
                        "age": 20,
                        "name": "小三"
                    }, {
                        "age": 20,
                        "name": "小四"
                    }]
                '''

        ])
        // 提供方在测试过程中，body需要满足的规则
        bodyMatchers {
            // 自定义的模型中有 success 字段，byEquality 可以验证服务端返回json中的 success 是不是 true
            jsonPath '$.success', byEquality()
            // 当然我们也可以自定义校验, 可以在基类中实现 assertIsTrue 方法
            jsonPath '$.success', byCommand('assertIsTrue($it)')
        }
    }

}


