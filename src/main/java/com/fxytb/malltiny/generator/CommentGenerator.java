package com.fxytb.malltiny.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.HashSet;
import java.util.Properties;

public class CommentGenerator extends DefaultCommentGenerator {

    private boolean addRemarkComments = false;
    private static final String EXAMPLE_SUFFIX = "Example";
    private static final String MAPPER_SUFFIX = "Mapper";
    private static final String SWAGGER_API_MODEL_PROPERTY_FULL_CLASS_NAME = "io.swagger.annotations.ApiModelProperty";

    @Override
    public void addConfigurationProperties(Properties props) {
        super.addConfigurationProperties(props);
        this.addRemarkComments = StringUtility.isTrue(props.getProperty("addRemarkComments"));
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        super.addFieldComment(field, introspectedTable, introspectedColumn);
        String remarks = introspectedColumn.getRemarks();
        //根据参数和备注信息判断是否添加备注信息
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            if (remarks.contains("\"")) {
                remarks = remarks.replace("\"", "");
            }
            //给model的字段添加swagger注解
            field.addJavaDocLine("@ApiModelProperty(value=\"" + remarks + "\")");
        }
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        super.addJavaFileComment(compilationUnit);
        //只在model中添加swagger、lombok注解类的导入
        String fullyQualifiedName = compilationUnit.getType().getFullyQualifiedName();
        if (!fullyQualifiedName.contains(EXAMPLE_SUFFIX) && !fullyQualifiedName.contains(MAPPER_SUFFIX)) {
            HashSet<FullyQualifiedJavaType> importedTypes = new HashSet<>();
            importedTypes.add(new FullyQualifiedJavaType(SWAGGER_API_MODEL_PROPERTY_FULL_CLASS_NAME));
            compilationUnit.addImportedTypes(importedTypes);
        }

    }


}
