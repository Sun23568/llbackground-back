# frame 模块

[根目录](../../../CLAUDE.md) > [back](../../) > [app](../) > **frame**

## 变更记录 (Changelog)

### 2025-12-13 23:45:19
- 初始化模块文档

---

## 模块职责

应用层框架（Application Layer），提供：
- **Handler 模式**: 业务逻辑的统一编排接口 `Handler<R, Q>`
- **CQRS 支持**: 区分 `Query` 和 `Command` 接口
- **权限控制**: `@HandlerAcl` 注解实现方法级权限校验
- **HandlerBus**: Handler 的注册和调度中心
- **RestApi 接口**: 为 API 层提供统一的执行入口
- **RestResult**: 统一的 HTTP 响应封装

## 入口与启动

### 核心接口

#### Handler<R, Q>
所有业务逻辑的执行单元：

```java
public interface Handler<R, Q> {
    /**
     * 执行业务逻辑
     * @param req 请求对象（Query 或 Command）
     * @return 响应结果
     */
    R execute(Q req);
}
```

#### HandlerBus
Handler 的注册和调度中心：

```java
public interface HandlerBus {
    /**
     * 执行 Handler
     * @param req 请求对象
     * @return 执行结果
     */
    <R> R execute(Object req);
}
```

### 使用示例

```java
// 1. 定义 Handler
@Component
@HandlerAcl("user:add")  // 权限控制
public class AddUserHandler implements Handler<Void, AddUserCmd> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Void execute(AddUserCmd cmd) {
        // 校验
        AssertUtil.notEmpty(cmd.getUserName(), "用户名不能为空");

        // 业务逻辑
        UserEo user = UserEo.builder()
            .userId(UserId.of(cmd.getUserId()))
            .userName(UserName.of(cmd.getUserName()))
            .build();

        userRepository.save(user);
        return Void.TYPE.cast(null);
    }
}

// 2. 在 API 层调用
@RestController
public class UserApi implements RestApi {
    @PostMapping("/user/add")
    public RestResult addUser(@RequestBody AddUserCmd cmd) {
        return this.execute(cmd);  // 自动路由到 AddUserHandler
    }
}
```

## 对外接口

### RestApi（供 API 层实现）

```java
public interface RestApi {
    /**
     * 执行查询
     */
    default <R> RestResult<R> execute(Query req) {
        return RestResult.ok(AppFrame.handlerBus().execute(req));
    }

    /**
     * 执行命令
     */
    default <R> RestResult<R> execute(Command cmd) {
        return RestResult.ok(AppFrame.handlerBus().execute(cmd));
    }
}
```

### RestResult<T>（统一响应格式）

```java
public class RestResult<T> {
    private String code;    // 状态码（"200" 表示成功）
    private String msg;     // 消息
    private T data;         // 数据

    public static <T> RestResult<T> ok(T data) {
        return new RestResult<>("200", "success", data);
    }

    public static <T> RestResult<T> error(String msg) {
        return new RestResult<>("500", msg, null);
    }
}
```

## 关键依赖与配置

### 依赖模块
- `domain-common`: 提供 `Query` 和 `Command` 接口
- Spring Context: 用于 Bean 管理和扫描

### 权限控制机制

#### @HandlerAcl 注解
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerAcl {
    /**
     * 权限代码（格式: module:action）
     */
    String value() default "";

    /**
     * 是否公开访问（不需要登录）
     */
    boolean pub() default false;
}
```

#### @PubAcl 注解
标记公开接口（无需登录）：

```java
@Component
@PubAcl
public class LoginHandler implements Handler<LoginResp, LoginCmd> {
    // 登录接口无需权限校验
}
```

### HandlerBus 实现（MapHandlerBus）

- 在 Spring 容器启动时扫描所有 `Handler` Bean
- 根据请求对象类型（Query/Command）自动路由到对应 Handler
- 执行前进行权限校验（基于 `@HandlerAcl`）

## 数据模型

### DTO 接口

#### Query（查询）
```java
public interface Query {
    // 标记接口，表示只读操作
}
```

#### Command（命令）
```java
public interface Command {
    // 标记接口，表示写操作
}
```

### HandlerInfo
存储 Handler 的元数据：

```java
@Data
public class HandlerInfo {
    private String handlerName;     // Handler 名称
    private String permission;      // 权限代码
    private boolean isPublic;       // 是否公开
    private Class<?> requestType;   // 请求类型
}
```

## 测试与质量

**当前状态**: 无测试文件。

**建议**:
- 测试 HandlerBus 的自动路由机制
- 测试权限控制逻辑（`@HandlerAcl`）
- 测试 RestResult 的序列化

## 常见问题 (FAQ)

### Q: HandlerBus 如何知道路由到哪个 Handler？
A: 通过请求对象的类型匹配。每个 Handler 的泛型参数 `<R, Q>` 中的 `Q` 对应一个唯一的请求类型。

### Q: @HandlerAcl 和 @PubAcl 的区别？
A:
- `@HandlerAcl("perm:code")`: 需要登录且拥有指定权限
- `@PubAcl`: 无需登录，公开访问（如登录接口）

### Q: 如何调试 Handler 未被调用？
A: 检查以下内容：
1. Handler 是否标记 `@Component`
2. Handler 是否在扫描路径下（`com.llback`）
3. 请求对象类型是否与 Handler 泛型参数匹配
4. 查看日志确认 Handler 是否注册到 HandlerBus

### Q: 为什么要使用 Void.TYPE.cast(null)？
A: 当 Handler 无返回值时，必须返回 `Void` 类型，不能直接返回 `null`。

## 相关文件清单

```
frame/
├── pom.xml
└── src/main/java/com/llback/frame/
    ├── Handler.java              # Handler 接口
    ├── HandlerBus.java           # Handler 调度接口
    ├── MapHandlerBus.java        # HandlerBus 实现
    ├── HandlerInfo.java          # Handler 元数据
    ├── HandlerAcl.java           # 权限注解
    ├── PubAcl.java               # 公开访问注解
    ├── HandlerPermUtil.java      # 权限工具类
    ├── AppFrame.java             # 应用框架入口
    ├── BeanContext.java          # Spring Bean 上下文
    ├── LLBack.java               # 框架配置
    ├── SaTokenConfig.java        # SaToken 配置（如果使用）
    ├── dto/
    │   ├── Query.java            # 查询接口
    │   └── Command.java          # 命令接口
    └── rest/
        ├── RestApi.java          # REST API 接口
        └── RestResult.java       # 统一响应格式
```

## 下一步建议

1. 查看 [app/api 模块](../api/CLAUDE.md) 了解如何在 API 层使用 Handler
2. 查看 [infrastructure/spring 模块](../../infrastructure/spring/CLAUDE.md) 了解权限拦截器实现
3. 查看根目录 [CLAUDE.md](../../../CLAUDE.md) 的"编码规范 > 权限控制"章节
