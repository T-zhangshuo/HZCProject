项目模块介绍
主工程为Pom项目，有新的 maven引入，在HZCProject/pom.xml按照规则添加。

>HZC-ACTable
--根据实体类自动创建表和更新表

>HZC-DAO
--mybalits的Dao层，示例在里面的Test类

>HZC-Entity
--存放非数据库实体类的 实体类文件。返回统一由BaseData封装。

>HZC-Model
--model层，具体表创建相关的看里面的test类

>HZC-Service
--Service层，具体使用请看ITestService类。

>HZC-Utils
--自定义相关的层，方便以后可以剥离给其他项目使用。

>HZC-Web
--web 层