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
 * @className InsuranceGeneratorTest
 * @description 医保结算清单自动生成器
 * @date 2023/4/23 13:47
 */
public class InsuranceGeneratorTest extends GeneratorTest {

    private static final String INSURANCE_KEY = "insurance";
    private static final DataSourceConfig.Builder DB_INSURANCE = new DataSourceConfig
            .Builder("jdbc:postgresql://192.168.29.92:5432/zhyx_insurance", "zhyx", "Meimima1202")
            .schema("public");

    @Test
    @DisplayName("医保结算清单代码生成")
    public void execute() {
        FastAutoGenerator.create(DB_INSURANCE)
                .globalConfig(builder -> {
                    builder.author("zhyx") // 设置作者
                            .dateType(DateType.TIME_PACK)
                            .commentDate("yyyy-MM-dd HH:mm:ss")
                            .outputDir(findOutputDir()); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.zhyx.insurance") // 设置父包名
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
