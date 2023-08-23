package com.coral.database.test.mybatis.config.mybatis;

import com.coral.base.common.StringUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserDefaultVisitor;
import net.sf.jsqlparser.parser.CCJSqlParserTreeConstants;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

import java.text.BreakIterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author huss
 * @date 2023-08-22
 */
@Slf4j
public class LogVisitor extends CCJSqlParserDefaultVisitor {
    @Getter
    private final String replaceStr;

    public LogVisitor(String replaceStr) {
        this.replaceStr = replaceStr;
    }

//    public static void main(String[] args) throws ParseException {
//        String sql = "SELECT person.id,person.name,group.name,synonym as \"症状\", synonym_name as \"症状名称\"  FROM person JOIN group ON person.group_id = group.id WHERE person.birthdat > '1980-01-01'";
//        sql = "INSERT INTO ebm_emr_nsyy.emr_resolve_switch_cfg (res_sw_id, classify_name, data_type,synonym, switch_flag, type_desc, create_time, update_time, frequency) VALUES (96, '病案首页', 10015, 1, '（提醒）诊断合并', '2021-05-31 17:07:08', '2021-05-31 17:07:08', 101);";
////         sql = "测试，咳嗽，咳嗽伴哮喘";
//        System.out.println("原sql：" + sql);
//        String replaceStr = DbConfig.getReplaceStr(DbType.DM);
//
//        LogVisitor logVisitor = new LogVisitor(replaceStr);
//
//        BreakIterator boundary = BreakIterator.getWordInstance();
//        boundary.setText(sql);
//        sql = logVisitor.rebuildSql(sql);
//        System.out.println("新sql：" + sql);
//
//        CCJSqlParser parser = CCJSqlParserUtil.newParser(sql);
//        Statement stmt = parser.Statement();
//        parser.getASTRoot().jjtAccept(logVisitor, null);
//        System.out.printf("sql--> %s", stmt.toString());
//    }

    /**
     * 重新构建sql
     *
     * @param sql
     * @return
     */
    public String rebuildSql(String sql) {
        BreakIterator boundary = BreakIterator.getWordInstance();
        boundary.setText(sql);
        List<String> words = new LinkedList<>();
        int end = boundary.first();
        while (end != BreakIterator.DONE) {
            int start = end;
            end = boundary.next();
            if (end == BreakIterator.DONE) {
                break;
            }
            String word = sql.substring(start, end);
            String finalWord = word;
            if (DbConfig.DB_KEYWORDS.stream().anyMatch(e -> e.equalsIgnoreCase(finalWord))) {
                word = DbConfig.appendAll(word, replaceStr);
            }
            words.add(word);
        }
        String newSql = String.join("", words);

        log.info("#####重新构建后的sql--> {}", newSql);
        return newSql;
    }


    @Override
    public Object visit(SimpleNode node, Object data) {
        Object value = node.jjtGetValue();
        log.debug("id:{} class:{} value:{}", node.getId(), Objects.nonNull(value) ? value.getClass().getSimpleName() : null, value);
        if (node.getId() == CCJSqlParserTreeConstants.JJTCOLUMN) {
            Column column = (Column) value;
            column.setColumnName(DbConfig.appendAll(column.getColumnName(), getReplaceStr()));
            Table table = column.getTable();
            if (Objects.nonNull(table)) {
                table.setName(DbConfig.appendAll(table.getName(), getReplaceStr()));
            }
        } else if (node.getId() == CCJSqlParserTreeConstants.JJTTABLE) {
            Table table = (Table) value;
            if (StringUtils.isNotBlank(table.getSchemaName())) {
                table.setSchemaName(DbConfig.appendAll(table.getSchemaName(), getReplaceStr()));
            }
            if (StringUtils.isNotBlank(table.getName())) {
                table.setName(DbConfig.appendAll(table.getName(), getReplaceStr()));
            }
        }
        return super.visit(node, data);
    }

}
