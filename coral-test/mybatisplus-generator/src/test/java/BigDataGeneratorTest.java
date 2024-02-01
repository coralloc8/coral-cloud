import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huss
 * @version 1.0
 * @className BigDataGeneratorTest
 * @description 大数据自动生成器
 * @date 2023/4/23 13:47
 */
public class BigDataGeneratorTest extends GeneratorTest {

    private static final String BIGDATA_KEY = "bigdata";
    private static final DataSourceConfig.Builder DB_BIGDATA = new DataSourceConfig
            .Builder("jdbc:postgresql://192.168.29.112:5432/bigdata", "app", "App.1202p")
            .schema("dwd");

    @Test
    @DisplayName("大数据代码生成")
    public void execute() {
        FastAutoGenerator.create(DB_BIGDATA)
                .globalConfig(builder -> {
                    builder.author("zhyx") // 设置作者
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                            .outputDir(findOutputDir()); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.zhyx.event") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, findOutputXmlDir())); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder
//                            .addTablePrefix("ods_v_", "dwd_v") // 设置过滤表前缀
                            //实体
                            .entityBuilder()
                            .enableLombok()
                            //controller
                            .controllerBuilder()
                            .enableRestStyle();
                })
                .templateEngine(new GenFreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .injectionConfig(consumer -> {
                    /**自定义生成模板参数**/
                    Map<String, Object> paramMap = new HashMap<>(8);
                    paramMap.put("dto.swagger", false);

                    CustomFile customFile = new CustomFile.Builder()
                            .fileName("DTO")
                            .templatePath(findDtoTemplatePath(BIGDATA_KEY))
                            .packageName("dto")
                            .build();
                    consumer.customFile(customFile)
                            .customMap(paramMap);


                    CustomFile customFile2 = new CustomFile.Builder()
                            .fileName("DTO2")
                            .templatePath(findDtoTemplatePath(BIGDATA_KEY))
                            .packageName("dto2")
                            .build();
                    consumer.customFile(customFile2)
                            .customMap(paramMap);
                })
                .execute();
    }
}
