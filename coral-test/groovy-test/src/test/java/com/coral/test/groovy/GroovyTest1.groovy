package com.coral.test.groovy


def test1() {
    def str = "test1"
    str = str + " test2"
    println str

    // 闭包
    def cols = { param -> println "Hello $param" }
    cols.call("World")


    def list = [1, 2, 3, 5, 4]
    list.each { println(it) }

    list.each { it -> if (it % 2 == 0) println it }

    list2 = list.find { el -> el > 2 }
    println "list: $list2"

}


test1()
