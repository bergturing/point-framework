# point-framework
## 点系列

点系列寓意为由点发展，扩展到实际项目的各个方面。

当前该项目包含以下几个模块

- point：point-framework项目的依赖汇总；
- point-core：核心模块；
- point-utils：项目中常用的一些工具类；
- point-enhance：基本类功能的增强；
- point-stream：对流的操作的封装；
- point-dataset：对数据集的封装；
- point-dal：分布式应用锁的实现；
- point-excel：对excel操作的封装。


## 模块功能介绍

### [point](https://github.com/bergturing/point-framework/tree/master/point)

maven 依赖地址：
```xml
<dependency>
    <groupId>io.github.bergturing</groupId>
    <artifactId>point</artifactId>
    <version>0.0.2-RELEASE</version>
</dependency>
```
该模块是对`point-framework`项目所有模块的一个整体依赖，引入了上面的`maven 依赖`即可引入整个`point-framework`项目提供的所有功能。

### [point-core](https://github.com/bergturing/point-framework/tree/master/point-core) (可使用)

maven 依赖地址：
```xml
<dependency>
    <groupId>io.github.bergturing</groupId>
    <artifactId>point-core</artifactId>
    <version>0.0.2-RELEASE</version>
</dependency>
```
该模块是`point-framework`项目的`核心模块`，为其他模块提供了基本的功能，包括以下功能：

- 对象原型功能的实现：应用`原型模式`的原理，封装了给一个基本对象提供`浅拷贝`的基础实现，旨在避免应用直接创建对象，而是通过对原型的拷贝来创建对象；
- 方法返回结果封装：将方法的返回结果封装成一个包装对象，以方便解决在实际项目开发中，方法返回单个类型值得限制。

### [point-utils](https://github.com/bergturing/point-framework/tree/master/point-utils) (可使用)

maven 依赖地址：
```xml
<dependency>
    <groupId>io.github.bergturing</groupId>
    <artifactId>point-utils</artifactId>
    <version>0.0.2-RELEASE</version>
</dependency>
```
该模块是`point-framework`项目的`工具类模块`，提供了在项目中和在其他模块中所使用到的一些工具类的封装，包括以下工具类：

- ArrayUtils：数组工具类；
- BatchOperateUtils：分批处理工具类；
- CollectionUtils：集合工具类；
- EntityUtils：实体工具类；
- LoggerUtils：日志工具类；
- MapUtils：Map工具类；
- StringUtils：字符串工具类。

### [point-enhance](https://github.com/bergturing/point-framework/tree/master/point-enhance) (可使用)

maven 依赖地址：
```xml
<dependency>
    <groupId>io.github.bergturing</groupId>
    <artifactId>point-enhance</artifactId>
    <version>0.0.2-RELEASE</version>
</dependency>
```
该模块是`point-framework`项目的`类增强模块`，提供了对类基础功能的增强，包括以下增强：

- SetterR：通过增加`@SetterR`注解，为类字段增加`设值并返回当前对象`的方法。

### [point-stream](https://github.com/bergturing/point-framework/tree/master/point-stream) (可使用)

maven 依赖地址：
```xml
<dependency>
    <groupId>io.github.bergturing</groupId>
    <artifactId>point-stream</artifactId>
    <version>0.0.2-RELEASE</version>
</dependency>
```
该模块是`point-framework`项目的`集合流功能增强模块`，对集合流的使用进行增强，包括以下功能：

- 策略流：根据既定的策略决定当前的流是否使用并行流，以提升系统的性能，目前已增加基于数量进行判断的策略。

### [point-dataset](https://github.com/bergturing/point-framework/tree/master/point-dataset) (未发布)

maven 依赖地址：
```xml
<dependency>
    <groupId>io.github.bergturing</groupId>
    <artifactId>point-dataset</artifactId>
    <version>0.0.2-RELEASE</version>
</dependency>
```
该模块是`point-framework`项目的`数据集模块`，对数据集的一个包装，主要用于需数据展示功能（如Excel导出）数据层的抽象。

### [point-dal](https://github.com/bergturing/point-framework/tree/master/point-dal) (可使用)

maven 依赖地址：
```xml
<dependency>
    <groupId>io.github.bergturing</groupId>
    <artifactId>point-dal</artifactId>
    <version>0.0.2-RELEASE</version>
</dependency>
```
该模块是`point-framework`项目的`分布式应用锁模块`，提供了分布式应用锁的功能，该模块适用于需要使用分布式锁的场景，直接引入该模块的依赖即可使用分布式应用锁的功能。

目前只提供基于`Redis`的分布式应用锁，提供`编程式`和`注解式`分布式应用锁使用方式。

### [point-excel](https://github.com/bergturing/point-framework/tree/master/point-excel) (待完善)

maven 依赖地址：
```xml
<dependency>
    <groupId>io.github.bergturing</groupId>
    <artifactId>point-excel</artifactId>
    <version>0.0.2-RELEASE</version>
</dependency>
```
该模块是`point-framework`项目的`Excel操作模块`，提供对`Excel`操作的封装。




