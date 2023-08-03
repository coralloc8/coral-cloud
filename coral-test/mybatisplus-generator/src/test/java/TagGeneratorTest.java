
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className InsuranceGeneratorTest
 * @description 医保结算清单自动生成器
 * @date 2023/4/23 13:47
 */
public class TagGeneratorTest extends GeneratorTest {

    private static final String INSURANCE_KEY = "tag";
    private static final DataSourceConfig.Builder DB_INSURANCE = new DataSourceConfig
            .Builder("jdbc:postgresql://192.168.29.112:5432/bigdata", "app", "App.1202p")
            .schema("tool_tag");

    private static Connection connection;


    @BeforeAll
    public static void init() {
        try {
            Class.forName("org.postgresql.Driver");
            // hive 库表 default.t_hive
            connection = DriverManager.getConnection("jdbc:postgresql://192.168.29.112:5432/bigdata?currentSchema=tool_tag", "app", "App.1202p");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("metadata测试")
    public void jdbcTest() throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();

        ResultSet resultSet = databaseMetaData.getTables(connection.getCatalog(), connection.getSchema(), "", new String[]{"TABLE", "VIEW"});

        while (resultSet.next()) {
            String name = resultSet.getString(3);
            String comment = resultSet.getString(5);
            String type = resultSet.getString(4);
            System.out.printf("table:%s\t\t\t\t\tcomment:%s\t\t\t\t\ttype:%s\n", name, comment, type);
        }
        System.out.println("==========================================================================");

        resultSet = databaseMetaData.getColumns(connection.getCatalog(), connection.getSchema(), "test", "");
        while (resultSet.next()) {
            String column = resultSet.getString(4);
            String comment = resultSet.getString(12);
            String type = resultSet.getString(6);
            System.out.printf("column:%s\t\t\t\t\tcomment:%s\t\t\t\t\ttype:%s\n", column, comment, type);
        }

        System.out.println("==========================================================================");
        resultSet = databaseMetaData.getPrimaryKeys(connection.getCatalog(), connection.getSchema(),"test");
        while (resultSet.next()) {
            String column = resultSet.getString(4);
            String comment = resultSet.getString(5);
            String type = resultSet.getString(6);
            System.out.printf("column:%s\t\t\t\t\tcomment:%s\t\t\t\t\ttype:%s\n", column, comment, type);

        }


    }

    @Test
    @DisplayName("标签平台单代码生成")
    public void execute() {
        FastAutoGenerator.create(DB_INSURANCE)
                .globalConfig(builder -> {
                    builder.author("zhyx") // 设置作者
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                            .outputDir(findOutputDir()); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.zhyx.tag") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, findOutputXmlDir())); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder
                            .addTablePrefix("ods_v_", "dwd_v") // 设置过滤表前缀
                            //实体
                            .entityBuilder()
                            .enableLombok()
                            .enableChainModel()
                            //controller
                            .controllerBuilder()
                            .enableRestStyle();
                })
                .templateEngine(new GenFreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .injectionConfig(consumer -> {
                    CustomFile dtoFile = new CustomFile.Builder()
                            .fileName("DTO")
                            .templatePath(findDtoTemplatePath(INSURANCE_KEY))
                            .packageName("dto")
                            .build();


                    /**自定义生成模板参数**/
                    Map<String, Object> paramMap = new HashMap<>(8);
                    paramMap.put(dtoFile.getPackageName() + "." + GlobalValue.SWAGGER_KEY, true);
//                    paramMap.put(dtoFile.getPackageName() + "." + GlobalValue.SPRINGDOC_KEY, true);
                    paramMap.put(dtoFile.getPackageName() + "." + GlobalValue.CHAIN_MODEL_KEY, false);
                    //dto字段过滤
                    paramMap.put(dtoFile.getPackageName() + "." + GlobalValue.FIELD_PREFIX_KEY, "");
                    paramMap.put(dtoFile.getPackageName() + "." + GlobalValue.FIELD_SUFFIX_KEY, "Std,Clean");

                    consumer.customFile(dtoFile)
                            .customMap(paramMap);
                })
                .execute();
    }
}
