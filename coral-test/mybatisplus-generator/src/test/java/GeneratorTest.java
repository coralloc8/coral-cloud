import com.coral.base.common.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.Objects;

/**
 * @author huss
 * @version 1.0
 * @className GeneratorTest
 * @description 生成测试
 * @date 2023/4/26 10:38
 */
@Slf4j
public class GeneratorTest {
    private static final String TEMPLATE = "templates";
    private static final String ENTITY_DTO_FTL = "entityDTO.java.ftl";

    private static final String OUTPUT_DIR = "C:\\Users\\Administrator\\Desktop";

    private static final String DEF_OUT_XML_PATH = "xml";

    private static final String DEF_OUT_FILE_PATH = "generator_data";


    protected String findOutputXmlDir() {
        return findOutputDir("") + File.separator + DEF_OUT_XML_PATH;
    }

    protected String findOutputXmlDir(String path) {
        return findOutputDir(path) + File.separator + DEF_OUT_XML_PATH;
    }

    protected String findOutputDir() {
        return findOutputDir("");
    }

    protected String findOutputDir(String path) {
        path = StringUtils.isBlank(path) ? DEF_OUT_FILE_PATH : path;
        return OUTPUT_DIR + File.separator + path;
    }

    protected String findDtoTemplatePath(String path) {
        String fullPath = String.join("/", TEMPLATE, path, ENTITY_DTO_FTL);
        URL url = this.getClass().getClassLoader().getResource(fullPath);
        if (Objects.nonNull(url)) {
            log.info(">>>>fullPath:{}", fullPath);
            return fullPath;
        }
        fullPath = String.join("/", TEMPLATE, ENTITY_DTO_FTL);
        log.info(">>>>fullPath:{}", fullPath);
        return fullPath;
    }

}
