MyBatis Generator (MBG)
=======================

[![Build Status](https://travis-ci.org/mybatis/generator.svg?branch=master)](https://travis-ci.org/mybatis/generator)

[![Dependency Status](https://www.versioneye.com/user/projects/561964c6a193340f2800033c/badge.svg?style=flat)](https://www.versioneye.com/user/projects/561964c6a193340f2800033c)
[![Maven central](https://maven-badges.herokuapp.com/maven-central/org.mybatis.generator/mybatis-generator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.mybatis.generator/mybatis-generator)
[![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

![mybatis-generator](http://mybatis.github.io/images/mybatis-logo.png)

Code generator for MyBatis and iBATIS.

它将为所有版本的MyBatis生成代码，以及版本2.2.0之后的iBATIS版本。它将内省一个数据库表(或多个表)，并生成可用于访问表的构件。这减少了设置对象和配置文件与数据库表交互的最初麻烦。MBG试图对简单CRUD(创建、检索、更新、删除)的大量数据库操作产生重大影响。

基于官方源码更改，仅供测试和学习使用。

## 功能简介
- javaModelGenerator标签拆分为：javaModelGenerator、javaExampleGenerator，使model和example生成位置更加灵活。
- 增加javaPoServiceGenerator标签，通过此标签可以根据配置生成对应的组合查询、更新（单表）。


## 效果展示
包结构：

```
.
├── dao
│   └── UserMapper.java
├── dto
│   └── User.java
├── example
│   └── UserExample.java
├── mapping
│   └── UserMapper.xml
└── po
    └── UserPoService.java
```


在逆向工程的同时帮你生成好poService，下面为后续演示实例的最的poService生成效果。
```
@Service
public class UserPoService {
    @Resource
    private UserMapper userMapper;

    /**
     * 只要配置了javaPoServiceGenerator标签就好默认生成
     */
    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 只要配置了javaPoServiceGenerator标签就好默认生成
     */
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * enableSelectByExample = true
     */
    public List<User> queryUserMobile(String mobile) {
        return queryUserMobile(mobile, null);
    }

    /**
     * enableSelectByExample = true
     */
    public List<User> queryUserMobile(String mobile, Short registerType) {
        UserExample e = new UserExample();
        UserExample.Criteria criteria = e.createCriteria();
        
        if (mobile != null) { criteria.andMobileEqualTo(mobile);}
        
        if (registerType != null) { criteria.andRegisterTypeEqualTo(registerType);}
        
        return userMapper.selectByExample(e);
    }

    /**
     * enableSelectByExample = true
     */
    public List<User> queryUserId(String userId) {
        UserExample e = new UserExample();
        UserExample.Criteria criteria = e.createCriteria();
        
        if (userId != null) { criteria.andUserIdEqualTo(userId);}
        
        return userMapper.selectByExample(e);
    }

    /**
     * enableUpdateByExample="true"和enablePostPositionQuery="true" 都为true时，生成的更新方法会同时查询最新的数据
     *
     * @return
     */
    public List<User> updateUserMobileByCondition(User dto, String mobile, Short registerType) {
        UserExample e = new UserExample();
        UserExample.Criteria criteria = e.createCriteria();
        
        if (mobile != null) { criteria.andMobileEqualTo(mobile);}
        
        if (registerType != null) { criteria.andRegisterTypeEqualTo(registerType);}
        
        userMapper.updateByExampleSelective(dto, e);
        return userMapper.selectByExample(e);
    }

    /**
     * enableUpdateByExample="true" 只会进行更新，没有返回结果
     */
    public void updateIdByCondition(User dto, Long id) {
        UserExample e = new UserExample();
        UserExample.Criteria criteria = e.createCriteria();
        
        if (id != null) { criteria.andIdEqualTo(id);}
        
        userMapper.updateByExampleSelective(dto, e);
    }
}
```



## 使用方法


jar都在当前目录下：
- 方式一jar：mybatis-generator-core-1.3.7-SNAPSHOT.jar
- 方式二plugin：mybatis-generator-maven-plugin-1.3.7-SNAPSHOT.jar


### 方式一：jar包方式单独运行







### 建立配置文件
## 新建一份xml，这里建了一个Hello.xml，并加入下列内容：

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
    <classPathEntry location="E:\IDEA\generator\core\mybatis-generator-core\src\test\resources\mysql-connector-java-5.1.36.jar" />
    <context id="XHTables" targetRuntime="MyBatis3">

        <plugin type="org.mybatis.generator.plugins.SerializablePlugin" />
        <plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin" />
        <plugin type="org.mybatis.generator.plugins.ToStringPlugin" />
        
        <commentGenerator>
            <property name="suppressDate" value="true" />
            <!-- 是否[去除]自动生成的注释  true：是 ：   false:否 -->
            <property name="suppressAllComments" value="false" />
            <!-- 是否显示数据库字段名备注 -->
            <property name="addRemarkComments" value="true" />
        </commentGenerator>
        <!--数据库连接-->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://cx:3306/userdb?useUnicode=true&amp;characterEncoding=UTF-8"
                        userId="test"
                        password="test123">
        </jdbcConnection>
        <!--model 和 example位置-->
        <javaModelGenerator targetPackage="dto" targetProject="E:\IDEA\generator\core\mybatis-generator-core\src\test\java"/>

        <!-- 新加入标签，将生成的 Example文件放到指定包下，官方是和 javaModelGenerator 在一个包下，不支持单独配置-->
        <javaExampleGenerator targetPackage="example" targetProject="E:\IDEA\generator\core\mybatis-generator-core\src\test\java"/>
        <!--po 生成的位置-->
        <javaPoServiceGenerator targetPackage="po" targetProject="E:\IDEA\generator\core\mybatis-generator-core\src\test\java"/>

        <sqlMapGenerator targetPackage="mapping"  targetProject="E:\IDEA\generator\core\mybatis-generator-core\src\test\java"/>

        <javaClientGenerator type="XMLMAPPER" targetPackage="dao" targetProject="E:\IDEA\generator\core\mybatis-generator-core\src\test\java"/>

        <!--要生成的表名-->
        <table tableName="user" domainObjectName="User"
               enableCountByExample="true"
               enableUpdateByExample="true"
               enableDeleteByExample="true"
               enableSelectByExample="true"
               selectByExampleQueryId="false">
            <generatedKey column="ID" sqlStatement="MYSQL" identity="true" />
            <generatedMethods>
                <generatedMethod methodName="userMobile"
                                 columns="mobile,register_type"
                                 enableSelectByExample="true"
                                 enableUpdateByExample="true"
                                 enablePostPositionQuery="true"
                />
                <generatedMethod methodName="userId"
                                 columns="user_id"
                                 enableSelectByExample="true"
                />
                <generatedMethod methodName="id"
                                 columns="id"
                                 enableUpdateByExample="true"
                />
            </generatedMethods>

        </table>
    </context>
</generatorConfiguration>
```


在jar文件目录下打开终端直接执行
```
java -jar mybatis-generator-core-1.3.7-SNAPSHOT.jar -configfile E:\IDEA\generator\Hello.xml -overwrite -verbose
```
### 方式二：maven插件方式运行
maven核心配置如下

- 配置连接参数、生成文件目录
```
<properties>
		<mybatis.jdbc.driver>com.mysql.jdbc.Driver</mybatis.jdbc.driver>
		<mybatis.jdbc.url>jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&amp;characterEncoding=UTF-8</mybatis.jdbc.url>
		<mybatis.jdbc.username>dev</mybatis.jdbc.username>
		<mybatis.jdbc.password>dev</mybatis.jdbc.password>
</properties>
```

- 加入修改后的插件

注意，这个插件没有时，你可能需要先下载到本地，再导入到你的仓库中才能被使用。
```
    <groupId>org.mybatis.generator.cx</groupId>
	<artifactId>mybatis-generator-maven-plugin</artifactId>
	<version>1.3.7-SNAPSHOT</version>
```
配置信息：
```
<plugin>
	<groupId>org.mybatis.generator.cx</groupId>
	<artifactId>mybatis-generator-maven-plugin</artifactId>
	<version>1.3.7-SNAPSHOT</version>
	<configuration>
		<jdbcDriver>${mybatis.jdbc.driver}</jdbcDriver>
		<jdbcURL>${mybatis.jdbc.url}</jdbcURL>
		<jdbcUserId>${mybatis.jdbc.username}</jdbcUserId>
		<jdbcPassword>${mybatis.jdbc.password}</jdbcPassword>
		<verbose>true</verbose>
		<overwrite>true</overwrite>
	</configuration>
	<dependencies>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.46</version>
		</dependency>
	</dependencies>
</plugin>

```
在项目src/java/resource下建立generatorConfig.xml配置文件，这里和方式区别的地方在于jdbcConnection改为读取pom里的配置（固定写死在xml配置也是没问题的）。

```
<jdbcConnection driverClass="${mybatis.jdbc.driver}"
			connectionURL="${mybatis.jdbc.url}" userId="${mybatis.jdbc.username}"
			password="${mybatis.jdbc.password}" />
```

### 配置拆解
- javaPoServiceGenerator：配置了此项时，将会启用poService生成。
```
<!--po 生成的位置-->
        <javaPoServiceGenerator targetPackage="po" targetProject="E:\IDEA\generator\core\mybatis-generator-core\src\test\java"/>
```
默认情况下生成的每一个poService都有2个方法，主键更新和主键查询：

```
    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }
```

- generatedMethods：包含了一组generatedMethod标签。


- generatedMethod：要生产的方法列表，它有5个属性：

    

属性 | 描述 | 实例
---|--- |---
methodName | 方法名，程序会基于此参数进行生成查询和更新方法 | userMobile -> queryUserMobile、updateUserMobileByCondition
columns | 要查询的字段名，多个用‘,’分割，注意使用的是数据库的原始字段名|单个："user_id"，多个："mobile,register_type"
enableSelectByExample| 生成查询方法 | true
enableUpdateByExample| 生成更新方法 | true
enablePostPositionQuery| 不能单独使用，和更新方法配合使用，启用时将在更新完毕后查询最新的数据库数据，见实例poService的updateUserMobileByCondition 和 updateIdByCondition方法 | true

懒出高效，未完待续。

