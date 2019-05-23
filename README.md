# point-framework
## 点系列

点系列寓意为由点发展，扩展到实际项目的各个方面。

当前该项目包含以下几个模块

- point：总的point项目所有的依赖；
- point-core：核心模块；
- point-utils：项目中常用的一些工具类；
- point-enhance：基本类功能的增强；
- point-stream：对流的操作的封装；
- point-dataset：对数据集的封装；
- point-dal：分布式应用锁的实现；
- point-excel：对excel操作的封装。


## 模块功能介绍

### point

maven 依赖地址：
```xml
<dependency>
    <groupId>io.github.bergturing</groupId>
    <artifactId>point</artifactId>
    <version>0.0.2-RELEASE</version>
</dependency>
```
该模块是对`point-framework`项目所有模块的一个整体依赖，引入了上面的`maven 依赖`即可引入整个`point-framework`项目提供的所有功能。

### point-core

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

### point-utils

maven 依赖地址：
```xml
<dependency>
    <groupId>io.github.bergturing</groupId>
    <artifactId>point-utils</artifactId>
    <version>0.0.2-RELEASE</version>
</dependency>
```
该模块是`point-framework`项目的`工具类模块`，提供了在项目中和在其他模块中所使用到的一些工具类的封装，包括以下工具类：

- 




