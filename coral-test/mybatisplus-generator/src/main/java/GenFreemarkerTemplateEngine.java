import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.coral.base.common.CollectionUtil;
import com.coral.base.common.json.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huss
 * @version 1.0
 * @className GenFreemarkerTemplateEngine
 * @description 自定义
 * @date 2023/4/23 15:08
 */
@Slf4j
public class GenFreemarkerTemplateEngine extends FreemarkerTemplateEngine {


    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            ConfigBuilder config = this.getConfigBuilder();
            List<TableInfo> tableInfoList = config.getTableInfoList();
            tableInfoList.forEach(tableInfo -> {
                Map<String, Object> objectMap = this.getObjectMap(config, tableInfo);
                Optional.ofNullable(config.getInjectionConfig()).ifPresent(t -> {
                    //objectMap属性复制
                    Map<String, Object> currentObjMap = new HashMap<>(objectMap);
                    // 添加自定义属性
                    t.beforeOutputFile(tableInfo, currentObjMap);
                    // 输出自定义文件
                    this.outputCustomFile(t.getCustomFiles(), tableInfo, config.getPackageConfig(), currentObjMap);
                });
                // entity
                outputEntity(tableInfo, objectMap);
                // mapper and xml
                outputMapper(tableInfo, objectMap);
                // service
                outputService(tableInfo, objectMap);
                // controller
                outputController(tableInfo, objectMap);
            });
        } catch (Exception e) {
            throw new RuntimeException("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }


    protected void outputCustomFile(List<CustomFile> customFiles, TableInfo tableInfo,
                                    PackageConfig packageConfig,
                                    Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String parentPath = getPathInfo(OutputFile.parent);
        customFiles.forEach(file -> {
            String filePath = com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(file.getFilePath()) ? file.getFilePath() : parentPath;
            if (com.baomidou.mybatisplus.core.toolkit.StringUtils.isNotBlank(file.getPackageName())) {
                filePath = filePath + File.separator + file.getPackageName();
                filePath = filePath.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
            }
            // 自定义 start
            String packageName = file.getPackageName();
            objectMap.put(GlobalValue.ENTITY_KEY, entityName + file.getFileName());

            this.updateGlobalConfig(file, objectMap, GlobalValue.SWAGGER_KEY, false);
            this.updateGlobalConfig(file, objectMap, GlobalValue.SPRINGDOC_KEY, false);
            this.updateGlobalConfig(file, objectMap, GlobalValue.CHAIN_MODEL_KEY, false);
            this.updateGlobalConfig(file, objectMap, GlobalValue.FIELD_PREFIX_KEY, "");
            this.updateGlobalConfig(file, objectMap, GlobalValue.FIELD_SUFFIX_KEY, "");

            Map<String, Object> packageMap = new HashMap<>((Map<String, Object>) objectMap.get(GlobalValue.PACKAGE_KEY));
            packageMap.put(StringUtils.capitalize(packageName), packageConfig.joinPackage(packageName));
            objectMap.put(GlobalValue.PACKAGE_KEY, packageMap);

            // 自定义 end
            List<TableField> originalFields = new ArrayList<>(tableInfo.getFields());
            List<TableField> fields = this.filterTableFields(tableInfo, objectMap);
            tableInfo.getFields().clear();
            tableInfo.getFields().addAll(fields);

            String fileName = filePath + File.separator + entityName + file.getFileName() + ConstVal.JAVA_SUFFIX;

            outputFile(new File(fileName), objectMap, file.getTemplatePath(), file.isFileOverride());

            //执行完后还原之前的field
            tableInfo.getFields().clear();
            tableInfo.getFields().addAll(originalFields);
        });
    }

    @Override
    public Map<String, Object> getObjectMap(ConfigBuilder config, TableInfo tableInfo) {
        String comment = this.parseTableComment(tableInfo.getComment());

        if (CollectionUtil.isNotBlank(tableInfo.getFields())) {
            tableInfo.getFields().forEach(e -> e.setComment(this.parseColumnComment(e.getComment())));
        }

        tableInfo.setComment(comment);
        return super.getObjectMap(config, tableInfo);
    }


    private void updateGlobalConfig(CustomFile file, Map<String, Object> objectMap, String key, Object defVal) {
        String packageName = file.getPackageName();
        String packageKey = packageName + "." + key;
        Object newVal = objectMap.getOrDefault(packageKey, defVal);


        objectMap.put(key, newVal);
        objectMap.remove(packageKey);
    }

    private List<TableField> filterTableFields(TableInfo tableInfo, Map<String, Object> objectMap) {
        if (CollectionUtil.isBlank(tableInfo.getFields())) {
            return Collections.emptyList();
        }
        List<TableField> fields = tableInfo.getFields().stream()
                .filter(e -> {
                    boolean filter = true;
                    String prefix = (String) objectMap.getOrDefault(GlobalValue.FIELD_PREFIX_KEY, "");
                    if (StringUtils.isNotBlank(prefix)) {
                        filter &= Arrays.stream(prefix.split(","))
                                .noneMatch(pre -> e.getPropertyName().startsWith(pre));
                    }
                    String suffix = (String) objectMap.getOrDefault(GlobalValue.FIELD_SUFFIX_KEY, "");
                    if (StringUtils.isNotBlank(suffix)) {
                        filter &= Arrays.stream(suffix.split(","))
                                .noneMatch(pre -> e.getPropertyName().endsWith(pre));
                    }
                    return filter;
                }).collect(Collectors.toList());
        return fields;
    }

    private String parseColumnComment(String columnComment) {
        if (StringUtils.isBlank(columnComment)) {
            return "";
        }
        try {
            columnComment = columnComment.trim();
            if (columnComment.startsWith("{") && columnComment.endsWith("}")) {
                Map map = JsonUtil.parse(columnComment, Map.class);
                Object obj = map.get("biz_property");
                if (Objects.isNull(obj)) {
                    return columnComment;
                }
                return obj.toString();
            }
            return columnComment;
        } catch (Exception e) {
            log.error("解析失败", e);
        }
        return columnComment;

    }

    private String parseTableComment(String tableComment) {
        if (StringUtils.isBlank(tableComment)) {
            return "";
        }
        try {
            tableComment = tableComment.trim();
            if (tableComment.startsWith("{") && tableComment.endsWith("}")) {
                Map map = JsonUtil.parse(tableComment, Map.class);
                Object obj = map.get("biz_entity");
                if (Objects.isNull(obj)) {
                    return tableComment;
                }
                return obj.toString();
            }
            return tableComment;
        } catch (Exception e) {
            log.error("解析失败", e);
        }

        return tableComment;
    }
}
