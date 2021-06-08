package com.coral.test.r.util;

import com.coral.base.common.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author huss
 * @version 1.0
 * @className RUtil
 * @description r调用
 * @date 2021/6/7 11:18
 */
@Slf4j
public class RserveUtil {


    public static String callRserve(String host, int port, String rPath, String funcName, Map<String, Object> args) {
        log.info(">>>>>rPath:{},funcName:{},args:{}", rPath, funcName, args);
        RConnection rConnection = null;
        try {
            rConnection = new RConnection(host, port);
            //执行test.R脚本，执行这一步才能调用里面的自定义函数myFunc，如果不行，就在R工具上也执行一下test.R脚本
            rConnection.eval("source('" + rPath + "')");
            //调用函数
            String func = parseFunc(funcName, args);
            log.info(">>>>>func:{}", func);

            REXP rexp = rConnection.eval(func);
            if (rexp.isNull()) {
                return "";
            }
            return rexp.asString();
        } catch (RserveException | REXPMismatchException e) {
            log.error("Error:", e);
            return "";
        } finally {
            if (Objects.nonNull(rConnection)) {
                rConnection.close();
            }
        }


    }


    private static String parseFunc(String funcName, Map<String, Object> args) {
        if (StringUtils.isBlank(funcName)) {
            log.error(">>>>>funcName为空...");
            return "";
        }
        StringBuilder paramBuilder = new StringBuilder();
        if (Objects.nonNull(args)) {
            args.forEach((k, v) -> {
                paramBuilder.append(k).append("='");
                if (v instanceof List) {
                    paramBuilder.append(String.join(",", ((List) v)));
                } else {
                    paramBuilder.append(v);
                }
                paramBuilder.append("',");
            });
        }
        return new StringBuilder(funcName).append("(")
                .append(paramBuilder.length() > 0 ? paramBuilder.substring(0, paramBuilder.length() - 1) : "")
                .append(")")
                .toString();
    }


}
