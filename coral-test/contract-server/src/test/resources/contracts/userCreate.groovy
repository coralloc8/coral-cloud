package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "添加用户"

    request {
        method POST()
        url "/users"
        headers {
            contentType applicationJsonUtf8()
        }
        body([
                age : value(
                        // 消费方想创建任何年龄的用户，都会得到下面的response.body的返回 "success: true"
                        consumer(regex(number())),
                        // 提供方生成的测试会调用接口创建一个年龄20岁的用户
                        producer(20)
                ),
                name: value(
                        consumer(regex(onlyAlphaUnicode())),
                        producer("小三")
                )

        ])
    }

    response {
        status OK()
        headers {
            contentType applicationJsonUtf8()
        }
        // 提供给消费者的默认返回
        body([
                success: true,
                message: "成功"
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