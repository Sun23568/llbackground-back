# domain-core 模块

[根目录](../../../CLAUDE.md) > [back](../../) > [domain](../) > **domain-core**

## 变更记录 (Changelog)

### 2025-12-13 23:45:19
- 初始化模块文档

---

## 模块职责

领域核心模块，定义核心业务的：
- **领域实体**（Eo - Entity Object）
- **领域服务**（Domain Service）
- **业务规则和逻辑**
- **领域事件**（如有）

与 `domain-base` 的区别：
- `domain-core`: 核心业务实体（Article, AI, File, Menu, Permission）
- `domain-base`: 基础领域（User）

## 入口与启动

无独立入口，作为库被其他模块依赖。

## 对外接口

### 领域实体（Eo）

#### 1. Article 领域

**ArticleContentEo**（文章内容实体）:
```java
@Getter
@Builder
public class ArticleContentEo {
    private StringId pkId;        // 唯一键
    private StringId articleId;   // 文章ID
    private Flag draft;           // 是否草稿
    private String content;       // 内容（HTML）
    private LocalDateTime createTime;
}
```

#### 2. AI 领域

**AiConfigEo**（AI 配置实体）:
```java
@Getter
@Builder
public class AiConfigEo {
    private StringId pkId;              // 主键
    private String menuCode;            // 菜单代码（唯一）
    private String ollamaModelId;       // Ollama 模型ID
    private String comfyUiUrl;          // ComfyUI 地址
    private String ollamaUrl;           // Ollama 地址
    private StringId comfyFileId;       // ComfyUI 参数文件ID
    private String backgroundImage;     // 背景图片
    private Integer contextSize;        // 上下文大小
    private String initialCharacterState;  // 初始人物状态（JSON）
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 业务方法
    public void updateModel(String modelId) {
        AssertUtil.notEmpty(modelId, "模型ID不能为空");
        this.ollamaModelId = modelId;
    }
}
```

**ChatHistoryEo**（聊天记录实体）:
```java
@Getter
@Builder
public class ChatHistoryEo {
    private StringId pkId;
    private String menuCode;      // 关联菜单
    private String userMessage;   // 用户消息
    private String aiResponse;    // AI 响应
    private LocalDateTime createTime;
}
```

#### 3. File 领域

**FileEo**（文件实体）:
```java
@Getter
@Builder
public class FileEo {
    private StringId pkId;
    private String fileName;      // 文件名
    private String filePath;      // 存储路径
    private Long fileSize;        // 文件大小
    private String fileType;      // 文件类型
    private LocalDateTime uploadTime;
}
```

#### 4. Menu 领域

**MenuEo**（菜单实体）:
```java
@Getter
@Builder
public class MenuEo {
    private StringId pkId;
    private String menuCode;      // 菜单代码（唯一）
    private String menuName;      // 菜单名称
    private String parentCode;    // 父菜单代码
    private Integer sortOrder;    // 排序
    private String icon;          // 图标
    private String path;          // 路由路径

    // 业务方法：判断是否根菜单
    public boolean isRoot() {
        return parentCode == null || parentCode.isEmpty();
    }
}
```

#### 5. Permission 领域

**FuncPermEo**（功能权限实体）:
```java
@Getter
@Builder
public class FuncPermEo {
    private StringId pkId;
    private String permCode;      // 权限代码（格式: module:action）
    private String permName;      // 权限名称
    private String permType;      // 权限类型（menu/button/data）
    private String menuCode;      // 关联菜单代码
    private LocalDateTime createTime;

    // 业务方法：验证权限代码格式
    public void validatePermCode() {
        AssertUtil.matches(permCode, "^[a-z]+:[a-z]+$",
            "权限代码格式错误，应为 module:action");
    }
}
```

#### 6. Token 领域（Session）

**TokenEo**:
```java
@Getter
@Builder
public class TokenEo {
    private String token;         // 令牌
    private UserId userId;        // 用户ID
    private LocalDateTime expireTime;  // 过期时间

    // 业务方法：判断是否过期
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
```

## 关键依赖与配置

### 依赖模块
- **domain-common**: 值对象（StringId, Flag 等）、工具类
- **domain-base**: 基础领域（UserEo）
- **Lombok**: 简化样板代码

### 值对象（Value Objects）

常用值对象（定义在 `domain-common`）：
- `StringId`: 字符串 ID（如主键）
- `Flag`: 布尔标志
- `UserId`: 用户 ID
- `UserName`: 用户名
- `EncryptedPassword`: 加密密码

### Builder 模式

所有 Eo 使用 Lombok 的 `@Builder` 模式：

```java
// 创建实体
ArticleContentEo article = ArticleContentEo.builder()
    .pkId(StringId.of("123"))
    .articleId(StringId.of("article-001"))
    .draft(Flag.of(false))
    .content("<h1>标题</h1>")
    .createTime(LocalDateTime.now())
    .build();

// 不可变性：Eo 字段通常只有 getter
```

## 数据模型

### 领域边界划分

```
┌─────────────────────────────────────────┐
│           domain-core                   │
├─────────────────────────────────────────┤
│ Article 聚合根                          │
│   └── ArticleContentEo                  │
├─────────────────────────────────────────┤
│ AI 聚合根                               │
│   ├── AiConfigEo                        │
│   └── ChatHistoryEo                     │
├─────────────────────────────────────────┤
│ File 聚合根                             │
│   └── FileEo                            │
├─────────────────────────────────────────┤
│ Menu 聚合根                             │
│   └── MenuEo                            │
├─────────────────────────────────────────┤
│ Permission 聚合根                       │
│   └── FuncPermEo                        │
├─────────────────────────────────────────┤
│ Session 聚合根                          │
│   └── TokenEo                           │
└─────────────────────────────────────────┘
```

## 测试与质量

**当前状态**: 无测试文件。

**建议**:
- 为每个 Eo 编写单元测试
- 测试业务方法逻辑（如 `isExpired()`, `validatePermCode()`）
- 测试 Builder 模式
- 验证不可变性

## 常见问题 (FAQ)

### Q: Eo 应该包含哪些逻辑？
A:
- **应该**: 与实体自身状态相关的业务规则（如验证、状态转换）
- **不应该**: 依赖外部服务的逻辑（如数据库查询、HTTP 调用）

### Q: 为什么 Eo 只有 getter 没有 setter？
A: 保持不可变性（Immutability），通过 Builder 模式创建新对象，避免状态被意外修改。

### Q: Eo 和 Po 如何转换？
A: 在 Infrastructure 层使用 `PoAssembleUtil`：
```java
// Po → Eo
ArticleContentEo eo = PoAssembleUtil.po2Eo(po, ArticleContentEo.class);

// Eo → Po
ArticleContentPo po = PoAssembleUtil.eo2Po(eo, ArticleContentPo.class);
```

### Q: 如何添加新的领域实体？
A:
1. 在 `src/main/java/com/llback/core/{module}/eo/` 创建 `XxxEo.java`
2. 使用 `@Getter` + `@Builder` 注解
3. 添加必要的业务方法
4. 在 Infrastructure 层创建对应的 Po 和 Repository

## 相关文件清单

```
domain-core/
├── pom.xml
└── src/main/java/com/llback/core/
    ├── article/
    │   └── eo/
    │       └── ArticleContentEo.java
    ├── ai/
    │   └── eo/
    │       ├── AiConfigEo.java
    │       └── ChatHistoryEo.java
    ├── file/
    │   └── eo/
    │       └── FileEo.java
    ├── menu/
    │   └── eo/
    │       └── MenuEo.java
    ├── perm/
    │   └── eo/
    │       └── FuncPermEo.java
    └── sa/
        └── eo/
            └── TokenEo.java
```

## 下一步建议

1. 查看 [domain-common 模块](../domain-common/CLAUDE.md) 了解值对象和工具类
2. 查看 [domain-base 模块](../domain-base/CLAUDE.md) 了解 UserEo
3. 查看 [infrastructure/dal 模块](../../infrastructure/dal/CLAUDE.md) 了解 Eo ↔ Po 转换
