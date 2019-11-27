#  qm-security - 安全框架


## 1. Preface

### *1.1 Help Document*

|    Version    |                Document Help                 |
| :-----------: | :------------------------------------------: |
| 1.0.3-RELEASE | [Reference Doc.](/qm-security/103/Home.html) |
|     1.0.2     | [Reference Doc.](/qm-security/102/Home.html) |


### *1.2 Update Version Log*

**[Show Version](/qm-security/UpdateLog.html)**

### *1.3 Open Source*

**[https://github.com/starmcc/qm-security](https://github.com/starmcc/qm-security)**

### *1.4 Introduce*

> `qm-security`是利用拦截器进行一系列安全校验的权限框架。

利用`AES`进行`token`多重加密生成，使用`MD5`对`token`进行签名，建立自身拦截器并对请求进行登录认证和授权认证。如果不通过，则自动踢出请求并返回对应的`JSON`信息。

### *1.5 Internal coordination*

框架内部提供接口，由调用者实现授权过程，不接管登录认证操作，只辅助生成密度较高的`token`字符串，专注于校验机制，所见即所得，这是一个轻量级的`URL`权限框架。

```
1、登录成功后生成token返回前端。
2、当该用户请求API接口时，header中带上token，安全框架将自动获取并通过层层校验。
3、当该用户请求时token的失效时间已过去一半，程序会重新签发token。
4、当该用户这次请求的token值已经过期，authorizationUserInfo返回null，则直接拦截。
5、当用户请求接口时遇到第3种情况时，会给Response的Header中设置token字段，该token映射的v为新的有效token。
6、注：当前端检测到Response的Header中有token字段时需替换旧的token的值。
```


## 2. Maven Warehouse

- [中央仓库查询](https://search.maven.org/)
- [阿里仓库查询](https://maven.aliyun.com/mvn/search)
- [Maven镜像仓库查询](https://mvnrepository.com/artifact/com.starmcc)

```xml
<dependency>
  <groupId>com.starmcc</groupId>
  <artifactId>qm-security</artifactId>
  <version>1.0.3-RELEASE</version>
</dependency>
```

## 3. Relation

在使用前，请确保项目中存在以下依赖

* servlet-api
* spring-web
* spring-webmvc
* slf4j

一般情况下，使用`springboot`进行`web`开发时我们会在`Maven`增加如下依赖。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

上述配置已经包含了所需依赖，无需进行其他配置直接引入`qm-security`依赖库即可。