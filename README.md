#  qm-security - 安全框架

## 描述

> `qm-security`是利用拦截器进行一系列安全校验的权限框架。

利用`AES`进行`token`多重加密生成，使用`md5`对`token`进行签名，建立自身拦截器并对请求进行登录认证和授权认证。如果不通过，则自动踢出请求并返回对应的`JSON`信息。

```
1、登录成功后生成token返回。
2、当该用户在次请求时，通过安全框架层层校验。
3、当该用户这次请求的token值已经过期，程序会重新签发token。
4、当该用户这次请求的token值已经过期，authorizationUserInfo返回null，则直接拦截。
5、当用户请求接口时遇到第3种情况时，会给Response的Header中设置token字段，该token映射的v为新的有效token。
6、注：当前端检测到Response的Header中有token字段时需替换旧的token内容。
```

## 内部协调

框架内部提供接口，由调用者实现授权过程，不接管登录认证操作，只辅助生成密度较高的`token`字符串，专注于校验机制，所见即所得，这是一个轻量级的`URL`权限框架。

## maven

您可以在Maven上获取到它。

> https://search.maven.org/

```xml
<dependency>
  <groupId>com.starmcc</groupId>
  <artifactId>qm-security</artifactId>
  <version>x.x.x</version>
</dependency>
```

## 帮助文档

**[点击进入帮助文档](https://github.com/starmcc/qm-security/wiki)**

## 依赖关系

在使用前，请确保项目中存在以下依赖

* servlet-api
* spring-web
* spring-webmvc
* slf4j

一般情况下，使用springboot进行web开发时我们会在maven增加如下依赖。

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

上述配置已经包含了所需依赖，无需进行其他配置直接引入`qm-security`依赖库即可。



