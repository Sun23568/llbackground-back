# api 模块

[根目录](../../../CLAUDE.md) > [back](../../) > [app](../) > **api**

## 变更记录 (Changelog)

### 2025-12-13 23:45:19
- 初始化模块文档

---

## 模块职责

API 层（对外接口层），负责：
- 定义所有 HTTP REST 接口（`@RestController`）
- 定义 DTO（数据传输对象）和 Req/Cmd（请求/命令对象）
- 实现 CQRS 模式（Query 和 Command 分离）
- 协调 Handler 执行业务逻辑
- 返回统一格式的 `RestResult<T>` 响应

## 入口与启动

### 核心接口模式

所有 API 类实现 `RestApi` 接口，通过 `execute()` 方法调用 Handler：

```java
@RestController
@RequestMapping("/article")
public class ArticleApi implements RestApi {
    @GetMapping("/list")
    public RestResult listArticle(int pageNum, int pageRow) {
        return this.execute(QueryArticleReq.of(pageNum, pageRow));
    }

    @PostMapping("/update")
    public RestResult updateArticle(@RequestBody UpdateArticleCmd cmd) {
        return this.execute(cmd);
    }
}
```

## 对外接口

### API 控制器列表

| 控制器 | 路径 | 职责 |
|--------|------|------|
| `AccessApi` | `/access` | 权限管理（菜单、权限、用户授权） |
| `AiApi` | `/ai` | AI 对话和图像生成 |
| `AiConfigApi` | `/ai/config` | AI 配置管理 |
| `ArticleApi` | `/article` | 文章管理（CRUD） |
| `FileApi` | `/file` | 文件上传和下载 |
| `SaApi` | `/sa` | 会话和认证（登录、登出、公钥获取） |
| `UserApi` | `/user` | 用户管理（CRUD） |

### 主要端点示例

#### ArticleApi
- `GET /article/list?pageNum={}&pageRow={}` - 分页查询文章
- `POST /article/query/content` - 查询文章详情
- `POST /article/update` - 新增或更新文章
- `POST /article/delete` - 删除文章

#### UserApi
- `GET /user/list?pageNum={}&pageRow={}` - 分页查询用户
- `POST /user/add` - 添加用户
- `POST /user/update` - 更新用户
- `POST /user/update/cur` - 更新当前用户
- `POST /user/remove` - 删除用户

#### SaApi
- `POST /sa/login` - 用户登录
- `POST /sa/logout` - 用户登出
- `GET /sa/session` - 获取会话信息
- `GET /sa/publicKey` - 获取加密公钥

## 关键依赖与配置

### 依赖模块
- `frame`: 提供 `RestApi` 接口和 `RestResult` 类
- `domain-core`: 领域实体定义
- `domain-common`: 共享工具类

### DTO 设计规范

#### Query（查询）
```java
@Getter
@Builder
public class QueryArticleReq implements Query {
    private int pageNum;
    private int pageRow;

    public static QueryArticleReq of(int pageNum, int pageRow) {
        return QueryArticleReq.builder()
            .pageNum(pageNum)
            .pageRow(pageRow)
            .build();
    }
}
```

#### Command（命令）
```java
@Data
public class UpdateArticleCmd implements Command {
    /** 文章ID（null表示新增） */
    private String articleId;

    /** 文章标题 */
    private String title;

    /** 文章内容 */
    private String content;
}
```

### 对象转换工具

**DtoEoAssemblerUtil**（EO → DTO）：
```java
// 单个对象转换
UserDto dto = DtoEoAssemblerUtil.eo2Dto(userEo, UserDto.class);

// 分页结果转换
PageResult<UserDto> page = DtoEoAssemblerUtil.eoPageToDtoResult(eoPage, UserDto.class);
```

## 数据模型

### 主要业务模块

#### 1. Access（权限管理）
- **DTO**: `MenuDto`, `PermDto`, `UserAccessDto`
- **Req/Cmd**: `AddMenuCmd`, `AddPermCmd`, `UpdateUserAccessCmd`
- **Handler**: `AddMenuHandler`, `AddPermHandler`, `UpdateUserAccessHandler`

#### 2. Article（文章管理）
- **DTO**: `ArticleDto`
- **Req/Cmd**: `QueryArticleReq`, `UpdateArticleCmd`, `RemoveArticleCmd`
- **Handler**: `QueryArticleHandler`, `UpdateArticleHandler`, `RemoveArticleHandler`

#### 3. User（用户管理）
- **DTO**: `UserDto`
- **Req/Cmd**: `AddUserCmd`, `UpdateUserCmd`, `RemoveUsersCmd`
- **Handler**: `AddUserHandler`, `UpdateUserHandler`, `RemoveUsersHandler`

#### 4. AI（AI 集成）
- **DTO**: `AiConfigDto`
- **Req/Cmd**: `ModelChat`, `GenerateImageReq`, `AddAiConfigCmd`
- **Handler**: `ModelChatHandler`, `GenerateImageHandler`

#### 5. File（文件管理）
- **Req/Cmd**: `UploadFileCmd`, `UploadImageCmd`, `GetFileContentReq`
- **Handler**: `UploadFileHandler`, `GetFileContentHandler`

## 测试与质量

**当前状态**: 无测试文件。

**建议**:
- 为每个 API 端点编写集成测试（使用 `@WebMvcTest`）
- 测试 DTO 的序列化/反序列化
- 验证权限控制逻辑

## 常见问题 (FAQ)

### Q: 为什么所有 API 都必须返回 RestResult<T>？
A: 前端 axios 拦截器依赖统一的响应格式 `{code, msg, data}`，在 `code === '200'` 时自动提取 `data` 字段。

### Q: Query 和 Command 有什么区别？
A:
- **Query**: 只读操作，不改变系统状态，实现 `Query` 接口
- **Command**: 写操作（增删改），改变系统状态，实现 `Command` 接口

### Q: 如何添加新的 API 端点？
A:
1. 在对应模块下创建 Req/Cmd 类
2. 创建 Handler 实现业务逻辑
3. 在 Api 类中添加控制器方法，调用 `this.execute(req)`

### Q: 文件上传怎么处理？
A: 使用 `UploadFileCmd` 或 `UploadImageCmd`，通过 `@RequestParam MultipartFile file` 接收，前端使用 `FormData` 上传。

## 相关文件清单

```
api/
├── pom.xml
└── src/main/java/com/llback/api/
    ├── api/                      # REST 控制器
    │   ├── AccessApi.java
    │   ├── AiApi.java
    │   ├── ArticleApi.java
    │   ├── FileApi.java
    │   ├── SaApi.java
    │   └── UserApi.java
    ├── app/                      # 按业务模块组织
    │   ├── access/
    │   │   ├── dto/             # 数据传输对象
    │   │   ├── handler/         # 业务处理器
    │   │   └── fetch/           # 数据获取（可选）
    │   ├── ai/
    │   ├── article/
    │   ├── file/
    │   ├── menu/
    │   ├── sa/
    │   └── user/
    └── util/
        └── DtoEoAssemblerUtil.java  # DTO/EO 转换工具
```

## 下一步建议

1. 查看 [app/frame 模块](../frame/CLAUDE.md) 了解 Handler 框架和权限控制
2. 查看 [domain/domain-core 模块](../../domain/domain-core/CLAUDE.md) 了解领域实体
3. 查看根目录 [CLAUDE.md](../../../CLAUDE.md) 的"编码规范"章节
