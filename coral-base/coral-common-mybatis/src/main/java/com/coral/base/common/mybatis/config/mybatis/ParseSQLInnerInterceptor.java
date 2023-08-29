package com.coral.base.common.mybatis.config.mybatis;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.EncryptUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParser;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author huss
 * @date 2023-08-22
 */
@Slf4j
public class ParseSQLInnerInterceptor implements InnerInterceptor {


    @Getter
    private final String replaceStr;
    /**
     * 缓存验证结果，提高性能
     */
    private static final Set<String> cacheValidResult = new HashSet<>();

    public ParseSQLInnerInterceptor(String replaceStr) {
        this.replaceStr = replaceStr;
    }

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        try {
            PluginUtils.MPStatementHandler mpStatementHandler = PluginUtils.mpStatementHandler(sh);
            MappedStatement ms = mpStatementHandler.mappedStatement();
            if (InterceptorIgnoreHelper.willIgnoreIllegalSql(ms.getId())) {
                return;
            }
            BoundSql boundSql = mpStatementHandler.boundSql();
            String originalSql = boundSql.getSql();
            log.debug("检查SQL是否合规,SQL:" + originalSql);
            String md5Base64 = EncryptUtils.md5Base64(originalSql);
            if (cacheValidResult.contains(md5Base64)) {
                log.debug("该SQL已验证,无需再次验证.SQL:" + originalSql);
                return;
            }
            LogVisitor logVisitor = new LogVisitor(getReplaceStr());
            originalSql = logVisitor.rebuildSql(originalSql);
            CCJSqlParser parser = CCJSqlParserUtil.newParser(originalSql);
            Statement stmt = parser.Statement();
            parser.getASTRoot().jjtAccept(logVisitor, null);
            String newSql = stmt.toString();
            //修改sql
            try {
                newSql = DbConfig.replaceAll(newSql, getReplaceStr());
                Field field = boundSql.getClass().getDeclaredField("sql");
                field.setAccessible(true);
                field.set(boundSql, newSql);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            //缓存验证结果
            cacheValidResult.add(md5Base64);
        } catch (Exception e) {
            log.error("### beforePrepare error.", e);
        }

    }


}