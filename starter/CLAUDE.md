# starter 模块

[根目录](../../CLAUDE.md) > [back](../) > **starter**

## 变更记录 (Changelog)

### 2025-12-13 23:45:19
- 初始化模块文档

---

## 模块职责

Spring Boot 应用启动入口模块，负责：
- 启动 Spring Boot 应用
- 扫描并注册所有组件（`@ComponentScan`）
- 配置 MyBatis Mapper 扫描（`@MapperScan`）
- 启用 OpenFeign 客户端（`@EnableFeignClients`）
- 启用定时任务（`@EnableScheduling`）
- 管理应用配置文件

## 入口与启动

### 主类
```java
package com.llback.starter;

@SpringBootApplication
@ComponentScan(basePackages = {"com.llback"})
@MapperScan("com.llback.dal.**.dao")
@EnableFeignClients(basePackages = {"com.llback"})
@EnableScheduling
public class MyApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
            SpringApplication.run(MyApplication.class, args);
    }
}
```

**路径**: `/mnt/d/Java/SpringBoot-Shiro-Vue/back/starter/src/main/java/com/llback/starter/MyApplication.java`

### 启动命令
```bash
# 从项目根目录
cd /mnt/d/Java/SpringBoot-Shiro-Vue/back/starter && mvn spring-boot:run

# 或构建后运行 jar
cd /mnt/d/Java/SpringBoot-Shiro-Vue/back && mvn clean package -DskipTests
java -jar starter/target/starter-2.0.0-SNAPSHOT.jar
```

## 对外接口

此模块无对外 HTTP 接口，仅作为启动容器。

## 关键依赖与配置

### Maven 依赖
- 依赖父 POM: `back/pom.xml`
- 聚合所有子模块依赖（api, frame, domain, infrastructure）

### 配置文件

#### application.yml
**路径**: `/mnt/d/Java/SpringBoot-Shiro-Vue/back/starter/src/main/resources/application.yml`

```yaml
spring:
  profiles:
    active: dev  # 激活 dev 环境
  datasource:
    hikari:
      maxLifetime: 60000  # 连接最大存活时间（60秒）
```

#### application-dev.yml
**路径**: `/mnt/d/Java/SpringBoot-Shiro-Vue/back/starter/src/main/resources/application-dev.yml`

包含以下配置（需查看文件获取完整配置）：
- 数据源配置（MySQL）
- MyBatis 配置
- Shiro 配置
- Ollama AI 配置
- 文件上传配置
- 日志配置

## 数据模型

无领域模型，仅包含配置类。

## 测试与质量

**当前状态**: 无测试文件。

**建议**: 添加集成测试验证应用启动成功。

## 常见问题 (FAQ)

### Q: 如何修改数据库连接？
A: 编辑 `application-dev.yml` 中的 `spring.datasource` 配置。

### Q: 如何更换 profile？
A: 修改 `application.yml` 中的 `spring.profiles.active` 值。

### Q: 应用启动失败怎么办？
A: 检查以下内容：
1. MySQL 数据库是否启动
2. 数据库连接配置是否正确
3. 端口是否被占用
4. 所有依赖模块是否正确编译（执行 `mvn clean install`）

## 相关文件清单

```
starter/
├── pom.xml                      # Maven 配置
├── src/main/
│   ├── java/com/llback/starter/
│   │   └── MyApplication.java   # 启动类
│   └── resources/
│       ├── application.yml      # 主配置文件
│       ├── application-dev.yml  # 开发环境配置
│       └── logback-spring.xml   # 日志配置（如果存在）
└── target/                      # 编译输出（已忽略）
```

## 下一步建议

1. 查看 [app/api 模块](../app/api/CLAUDE.md) 了解 HTTP 接口定义
2. 查看 [infrastructure/spring 模块](../infrastructure/spring/CLAUDE.md) 了解 Spring 基础设施配置
3. 查看 [infrastructure/dal 模块](../infrastructure/dal/CLAUDE.md) 了解数据访问层
