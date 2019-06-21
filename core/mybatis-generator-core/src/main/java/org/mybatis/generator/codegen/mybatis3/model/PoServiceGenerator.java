/**
 *    Copyright 2006-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.model;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.GeneratedMethod;
import org.mybatis.generator.config.GeneratedMethod.GeneratedMethodBean;
import org.mybatis.generator.config.TableConfiguration;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @author chengjz
 * @version 1.0
 * @date 2019-06-20 16:10
 */
public class PoServiceGenerator extends AbstractJavaGenerator {

    private FullyQualifiedJavaType service = new FullyQualifiedJavaType("org.springframework.stereotype.Service");

    private FullyQualifiedJavaType resource = new FullyQualifiedJavaType("javax.annotation.Resource");

    private FullyQualifiedJavaType autowired = new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired");

    private FullyQualifiedJavaType list = new FullyQualifiedJavaType("java.util.List");

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private TableConfiguration tableConfiguration;

    public PoServiceGenerator() {
        super();
    }

    public void setTableConfiguration(TableConfiguration tableConfiguration) {
        this.tableConfiguration = tableConfiguration;
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString("Progress.11", table.toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();



        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getPoService());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addImportedType(this.service);
        topLevelClass.addImportedType(this.autowired);
        topLevelClass.addImportedType(this.list);
        topLevelClass.addImportedType(this.resource);

        FullyQualifiedJavaType mapper = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());

        FullyQualifiedJavaType example = new FullyQualifiedJavaType(
                introspectedTable.getExampleType());

        FullyQualifiedJavaType dto = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());

        topLevelClass.addImportedType(mapper);
        topLevelClass.addImportedType(example);
        topLevelClass.addImportedType(dto);


        commentGenerator.addJavaFileComment(topLevelClass);
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine("* Add by Mybatis3 generator cx.");
        topLevelClass.addJavaDocLine("* " + sdf.format(new Date()));
        topLevelClass.addJavaDocLine("*/");
        commentGenerator.addJavaFileComment(topLevelClass);
        topLevelClass.addAnnotation("@Service");


        Field field = new Field();
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setType(mapper);
        StringBuilder sb = new StringBuilder();
        sb.append(mapper.getShortName());
        sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));

        String mapperFiledName = sb.toString();

        field.setName(mapperFiledName);
        field.addAnnotation("@Resource");
        topLevelClass.addField(field);
        // 生成主键查询
        generatedPkQueryMethod(topLevelClass, mapperFiledName);
        // 生成主键更新
        generatedPkUpdateMethod(topLevelClass, mapperFiledName);
        // 生成要联合查询的方法
        generatedQueryMethod(topLevelClass, mapperFiledName);
        // 生成要联合更新的方法
        generatedUpdateMethod(topLevelClass, mapperFiledName);
        // 调用所有插件的modelExampleClassGenerated 方法
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelPoServiceGenerated(
                topLevelClass, introspectedTable)) {

        }
        answer.add(topLevelClass);
        return answer;
    }


    private void generatedPkQueryMethod(TopLevelClass topLevelClass, String mapperFiledName) {
        if (!introspectedTable.getRules().generateSelectByPrimaryKey()) {
            return;
        }

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = introspectedTable.getRules()
                .calculateAllFieldsClass();
        method.setReturnType(returnType);

        String selectByPrimaryKeyStatementId = introspectedTable.getSelectByPrimaryKeyStatementId();
        method.setName(selectByPrimaryKeyStatementId);

        StringBuilder sb = new StringBuilder("return ");

        if (introspectedTable.getRules().generatePrimaryKeyClass()) {
            FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                    introspectedTable.getPrimaryKeyType());
            topLevelClass.addImportedType(type);
            method.addParameter(new Parameter(type, "key"));
            sb.append(mapperFiledName + "." + selectByPrimaryKeyStatementId + "(key)");
            method.addBodyLine(sb.toString());
        } else {

            List<IntrospectedColumn> introspectedColumns = introspectedTable
                    .getPrimaryKeyColumns();
            sb.append(mapperFiledName + "." + selectByPrimaryKeyStatementId + "(");
            for (IntrospectedColumn introspectedColumn : introspectedColumns) {
                FullyQualifiedJavaType type = introspectedColumn
                        .getFullyQualifiedJavaType();
                topLevelClass.addImportedType(type);
                Parameter parameter = new Parameter(type, introspectedColumn
                        .getJavaProperty());
                method.addParameter(parameter);
                sb.append(introspectedColumn
                        .getJavaProperty() + ", ");


            }

            String str = sb.toString();
            String substring = str.substring(0, str.length() - 2);

            method.addBodyLine(substring + ");");
        }



        topLevelClass.addMethod(method);
    }



    private void generatedPkUpdateMethod(TopLevelClass topLevelClass, String mapperFiledName) {

        if (!introspectedTable.getRules().generateUpdateByPrimaryKeySelective()) {
            return;
        }

        FullyQualifiedJavaType parameterType;
        if (introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            parameterType = new FullyQualifiedJavaType(introspectedTable
                    .getRecordWithBLOBsType());
        } else {
            parameterType = new FullyQualifiedJavaType(introspectedTable
                    .getBaseRecordType());
        }

        topLevelClass.addImportedType(parameterType);

        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName(introspectedTable
                .getUpdateByPrimaryKeySelectiveStatementId());
        method.addParameter(new Parameter(parameterType, "record"));

        StringBuilder sb = new StringBuilder("return ");
        sb.append(mapperFiledName + "." + introspectedTable
                .getUpdateByPrimaryKeySelectiveStatementId() + "(record);");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }

    private void generatedQueryMethod(TopLevelClass topLevelClass, String mapperFiledName) {


        GeneratedMethod generatedMethods = introspectedTable.getTableConfiguration().getGeneratedMethod();
        if (generatedMethods == null) {
            return;
        }


        FullyQualifiedJavaType example = new FullyQualifiedJavaType(
                introspectedTable.getExampleType());

        FullyQualifiedJavaType dto = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());

        List<GeneratedMethodBean> list = generatedMethods.getGeneratedMethodBeans();
        Map<String, IntrospectedColumn> javaBeansFieldMap = getJavaBeansFieldMap();
        Map<String, IntrospectedColumn> javaBeansFieldMapByJavaProperty = getJavaBeansFieldMapByJavaProperty();

        for (int i = 0; i < list.size(); i++) {
            GeneratedMethodBean generatedMethodBean = list.get(i);
            if (!generatedMethodBean.isEnableSelectByExample()) {
                System.out.println("Generator skip query Method : " + generatedMethodBean.getMethodName());
                continue;
            }

            List<String> columns = generatedMethodBean.getColumns();
            for (int j = 0; j < columns.size(); j++) {
                Method method = new Method();
                method.setVisibility(JavaVisibility.PUBLIC);
                String returnType = "java.util.List<" + dto.getShortName() + ">";
                method.setReturnType(new FullyQualifiedJavaType(returnType));
                String methodName = generatedMethodBean.getMethodName();
                StringBuilder sb = new StringBuilder(methodName);
                sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));


                method.setName("query" + sb.toString());

                if (j >= columns.size()-1) {
                    doCreateCriteria(example, method);
                    addParams(topLevelClass, method, columns, javaBeansFieldMap, false);
                    method.addBodyLine("return " + mapperFiledName +".selectByExample(e);");
                } else {
                    List<String> buildParams = buildParams(columns.subList(0, j+1), columns.size(), javaBeansFieldMap);
                    doOverloadMethod(method, javaBeansFieldMapByJavaProperty, buildParams);
                }

                topLevelClass.addMethod(method);
            }
        }
    }


    private void generatedUpdateMethod(TopLevelClass topLevelClass, String mapperFiledName) {

        GeneratedMethod generatedMethods = introspectedTable.getTableConfiguration().getGeneratedMethod();
        if (generatedMethods == null) {
            return;
        }

        Map<String, IntrospectedColumn> javaBeansFieldMap = getJavaBeansFieldMap();

        FullyQualifiedJavaType example = new FullyQualifiedJavaType(
                introspectedTable.getExampleType());

        FullyQualifiedJavaType dto = new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType());

        List<GeneratedMethodBean> list = generatedMethods.getGeneratedMethodBeans();

        for (int i = 0; i < list.size(); i++) {
            GeneratedMethodBean generatedMethodBean = list.get(i);
            if (!generatedMethodBean.isEnableUpdateByExample()) {
                System.out.println("Generator skip update Method : " + generatedMethodBean.getMethodName());
                continue;
            }

            Method method = new Method();
            method.setVisibility(JavaVisibility.PUBLIC);


            String methodName = generatedMethodBean.getMethodName();
            StringBuilder sb = new StringBuilder(methodName);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));

            method.addParameter(new Parameter(dto, "dto"));

            method.setName("update" + sb.toString() + "ByCondition");

            doCreateCriteria(example, method);
            addParams(topLevelClass, method, generatedMethodBean.getColumns(), javaBeansFieldMap, false);

            if (generatedMethodBean.isEnablePostPositionQuery()) {
                String returnType = "java.util.List<" + dto.getShortName() + ">";
                method.setReturnType(new FullyQualifiedJavaType(returnType));
                method.addBodyLine(mapperFiledName + ".updateByExampleSelective(dto, e);");
                method.addBodyLine("return " + mapperFiledName + ".selectByExample(e);");
            } else {
                method.addBodyLine(mapperFiledName + ".updateByExampleSelective(dto, e);");
            }

            topLevelClass.addMethod(method);
        }

    }

    private List<String> buildParams(List<String> params, int size,  Map<String, IntrospectedColumn> maps) {
        List<String> list = new ArrayList<String>();
        list.addAll(params);
        for (int i = 0; i < size && list.size() < size; i++) {
            list.add("null");
        }

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s == null || "null".equals(s)) {
                continue;
            }
            IntrospectedColumn introspectedColumn = maps.get(s);
            String javaProperty = introspectedColumn.getJavaProperty();

            StringBuilder sb = new StringBuilder();
            sb.append(javaProperty);
            sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));

            list.set(i, sb.toString());
        }

        return list;
    }

    private void doOverloadMethod( Method method,  Map<String, IntrospectedColumn> maps, List<String> columns) {


        for (int i = 0; i < columns.size(); i++) {
            String param = columns.get(i);
            if (param == null || "null".equals(param)) {
                continue;
            }
            IntrospectedColumn introspectedColumn = maps.get(param);


            FullyQualifiedJavaType fullyQualifiedJavaType = introspectedColumn
                    .getFullyQualifiedJavaType();
            method.addParameter(new Parameter(fullyQualifiedJavaType, param));

        }

        StringBuilder sb = new StringBuilder("return");
        sb.append(" ").append(method.getName());
        sb.append("(");

        for (int i = 0; i < columns.size(); i++) {
            String param = columns.get(i);
            if (i < columns.size()-1) {
                sb.append(param).append(", ");
            } else {
                sb.append(param).append(");");
            }
        }


        method.addBodyLine(sb.toString());
    }

    private void doCreateCriteria(FullyQualifiedJavaType example, Method method) {
        method.addBodyLine(example.getShortName() + " e = new " + example.getShortName() + "();");

        method.addBodyLine(example.getShortName() + ".Criteria criteria = e.createCriteria();");
        method.addBodyLine("");



    }

    private void addParams(TopLevelClass topLevelClass, Method method, List<String> params, Map<String, IntrospectedColumn> maps, boolean nullConstraint) {
        for (String param : params) {
            IntrospectedColumn introspectedColumn = maps.get(param);
            String javaProperty = introspectedColumn.getJavaProperty();

            topLevelClass.addImportedType(introspectedColumn.getFullyQualifiedJavaType());

            StringBuilder sb = new StringBuilder();
            sb.append(javaProperty);
            sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));


            FullyQualifiedJavaType fullyQualifiedJavaType = introspectedColumn
                    .getFullyQualifiedJavaType();
            method.addParameter(new Parameter(fullyQualifiedJavaType, sb.toString()));

            sb = new StringBuilder();
            sb.append(javaProperty);
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));

            if (nullConstraint) {
                method.addBodyLine("if (" + javaProperty + " == null) { return null; }" );
                method.addBodyLine("else { criteria.and" + sb.toString() + "EqualTo(" + javaProperty + ");");
            } else {
                method.addBodyLine("if (" + javaProperty + " != null) { criteria.and"  + sb.toString() + "EqualTo(" + javaProperty + ");}");
            }


            method.addBodyLine("");


        }
    }

    private Map<String, IntrospectedColumn> getJavaBeansFieldMap(){
        List<IntrospectedColumn> introspectedColumns = getColumnsInThisClass();
        Map<String, IntrospectedColumn> map = new HashMap<String, IntrospectedColumn>(8);
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            map.put(introspectedColumn.getActualColumnName(), introspectedColumn);
        }
        return map;
    }

    private Map<String, IntrospectedColumn> getJavaBeansFieldMapByJavaProperty(){
        List<IntrospectedColumn> introspectedColumns = getColumnsInThisClass();
        Map<String, IntrospectedColumn> map = new HashMap<String, IntrospectedColumn>(8);
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            map.put(introspectedColumn.getJavaProperty(), introspectedColumn);
        }
        return map;
    }

    private List<IntrospectedColumn> getColumnsInThisClass() {
        List<IntrospectedColumn> introspectedColumns;
        if (includePrimaryKeyColumns()) {
            if (includeBLOBColumns()) {
                introspectedColumns = introspectedTable.getAllColumns();
            } else {
                introspectedColumns = introspectedTable.getNonBLOBColumns();
            }
        } else {
            if (includeBLOBColumns()) {
                introspectedColumns = introspectedTable
                        .getNonPrimaryKeyColumns();
            } else {
                introspectedColumns = introspectedTable.getBaseColumns();
            }
        }

        return introspectedColumns;
    }

    private boolean includePrimaryKeyColumns() {
        return !introspectedTable.getRules().generatePrimaryKeyClass()
                && introspectedTable.hasPrimaryKeyColumns();
    }

    private boolean includeBLOBColumns() {
        return !introspectedTable.getRules().generateRecordWithBLOBsClass()
                && introspectedTable.hasBLOBColumns();
    }

}
