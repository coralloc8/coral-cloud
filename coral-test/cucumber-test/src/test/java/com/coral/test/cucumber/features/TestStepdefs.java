package com.coral.test.cucumber.features;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.PendingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className com.coral.test.cucumber.features.TestStepdefs
 * @description 中文测试
 * @date 2023/3/2 18:30
 */
public class TestStepdefs {
    private List<Map<String, String>> userList = new ArrayList<>();
    boolean loginResult;

    @io.cucumber.java.zh_cn.假如("^系统中有以下用户列表。$")
    public boolean give(DataTable users) {
        userList = users.entries();
        System.out.println("当前用户列表:" + userList);
        return true;
    }

    @io.cucumber.java.zh_cn.当("^用户在登录界面输入账号\"([^\"]*)\"和密码\"([^\"]*)\"后\"([^\"]*)\"登录按钮$")
    public void when(String account, String password, String action) {
        System.out.println(String.format("account:%s  password:%s  action:%s", account, password, action));
        System.out.println("userList:" + userList);
        boolean match = userList.stream().anyMatch(e -> e.get("账号").equals(account) && e.get("密码").equals(password));
        System.out.println("最终匹配结果:" + match);
        loginResult = match;
    }

    @io.cucumber.java.zh_cn.那么("^登录系统成功，跳转到\"([^\"]*)\"$")
    public void then(String page) {
        if (loginResult) {
            System.out.println("跳转" + page);
        } else {
            throw new PendingException("账号或密码错误");
        }
    }


}
