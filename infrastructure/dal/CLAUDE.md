# dal 模块

[根目录](../../../CLAUDE.md) > [back](../../) > [infrastructure](../) > **dal**

## 变更记录 (Changelog)

### 2025-12-13 23:45:19
- 初始化模块文档

---

## 模块职责

数据访问层（Data Access Layer），负责：
- 定义 MyBatis **Mapper 接口**（DAO）
- 定义 **持久化对象**（Po - Persistence Object）
- 实现 **Repository 接口**（领域层定义的仓储接口）
- 提供 **Po ↔ Eo 转换**（使用 `PoAssembleUtil`）
- 管理数据库 **SQL 脚本**

## 入口与启动

### MyBatis Mapper 扫描

在 `starter` 模块配置：
```java
@MapperScan("com.llback.dal.**.dao")
```

### 核心模式

```
DAO (Mapper Interface)
  ↓ 使用
Po (Persistence Object)
  ↓ PoAssembleUtil.po2Eo()
Eo (Entity Object)
  ↓ 返回给领域层
Repository Implementation
```

## 对外接口

### Repository 实现

每个业务模块都有对应的 Repository 实现：

| Repository 实现 | 对应领域 | 主要职责 |
|----------------|---------|---------|
| `UserRepositoryImpl` | User | 用户 CRUD、权限查询 |
| `ArticleRepositoryImpl` | Article | 文章 CRUD、分页查询 |
| `MenuRepositoryImpl` | Menu | 菜单树查询、CRUD |
| `PermRepositoryImpl` | Permission | 权限 CRUD、用户权限查询 |
| `FileRepositoryImpl` | File | 文件元数据 CRUD |
| `AiModelConfigRepositoryImpl` | AI | AI 配置 CRUD |
| `ChatHistoryRepositoryImpl` | AI | 聊天记录 CRUD |
| `AvatarRepositoryImpl` | User | 用户头像管理 |

### 示例实现

```java
@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserDao userDao;

    @Override
    public UserEo findById(UserId userId) {
        UserPo po = userDao.selectByUserId(userId.toString());
        return PoAssembleUtil.po2Eo(po, UserEo.class);
    }

    @Override
    public void save(UserEo userEo) {
        UserPo po = PoAssembleUtil.eo2Po(userEo, UserPo.class);
        if (po.getPkId() == null) {
            userDao.insert(po);
        } else {
            userDao.update(po);
        }
    }
}
```

## 关键依赖与配置

### 依赖模块
- **MyBatis Spring Boot Starter**: 2.1.4
- **MySQL Connector**: 8.0.33
- **domain-core**: 领域实体定义
- **infrastructure-common**: PoAssembleUtil 工具类

### MyBatis 配置

通常在 `application-dev.yml` 中配置：
```yaml
mybatis:
  mapper-locations: classpath:mapper/**/*.xml
  type-aliases-package: com.llback.dal.**.po
  configuration:
    map-underscore-to-camel-case: true
```

### DAO 接口（Mapper）

```java
@Mapper
public interface UserDao {
    /**
     * 根据用户ID查询
     */
    UserPo selectByUserId(String userId);

    /**
     * 插入用户
     */
    void insert(UserPo po);

    /**
     * 更新用户
     */
    void update(UserPo po);

    /**
     * 分页查询
     */
    List<UserPo> selectPage(PageParam pageParam);
}
```

对应的 XML Mapper：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.llback.dal.user.dao.UserDao">
    <select id="selectByUserId" resultType="UserPo">
        SELECT * FROM sys_user WHERE user_id = #{userId}
    </select>

    <insert id="insert">
        INSERT INTO sys_user (pk_id, user_id, user_name, password)
        VALUES (#{pkId}, #{userId}, #{userName}, #{password})
    </insert>
</mapper>
```

## 数据模型

### 主要业务表

#### 1. 用户相关
- **sys_user**: 用户表
- **sys_avatar**: 用户头像表

#### 2. 权限相关
- **sys_menu**: 菜单表（树形结构）
- **sys_perm**: 权限表
- **sys_user_perm**: 用户权限关联表

#### 3. 文章相关
- **article**: 文章元数据表
- **article_content**: 文章内容表

#### 4. AI 相关
- **ai_config**: AI 配置表（关联菜单代码）
  - `menu_code`: 菜单代码（唯一键）
  - `ollama_model_id`: Ollama 模型 ID
  - `comfy_ui_url`: ComfyUI 地址
  - `context_size`: 上下文大小
  - `initial_character_state`: 初始人物状态（JSON）
- **chat_history**: 聊天记录表

#### 5. 文件相关
- **file**: 文件元数据表

### Po（持久化对象）示例

```java
@Data
public class UserPo {
    private String pkId;           // 主键
    private String userId;         // 用户ID
    private String userName;       // 用户名
    private String password;       // 密码（加密）
    private String avatar;         // 头像ID
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
```

### SQL 脚本

**位置**: `/mnt/d/Java/SpringBoot-Shiro-Vue/back/infrastructure/dal/src/main/resources/sql/`

主要脚本：
- `ai_config.sql`: AI 配置表结构
- `ai_config_add_initial_state.sql`: 添加初始状态字段

## 测试与质量

**当前状态**: 无测试文件。

**建议**:
- 为每个 Repository 编写集成测试（使用 `@MybatisTest` + H2）
- 测试分页查询
- 测试事务回滚
- 验证 Po ↔ Eo 转换逻辑

## 常见问题 (FAQ)

### Q: Po 和 Eo 的区别？
A:
- **Po**: 持久化对象，字段映射数据库表，无业务逻辑
- **Eo**: 领域实体，包含业务逻辑和规则，使用值对象（如 `UserId`, `UserName`）

### Q: 为什么不直接在 DAO 返回 Eo？
A: 保持分层清晰，DAO 层只关心数据持久化，领域逻辑由 Eo 封装。

### Q: 如何添加新表？
A:
1. 在 `sql/` 目录创建建表脚本
2. 在对应模块的 `po/` 包创建 Po 类
3. 创建对应的 DAO 接口和 XML Mapper
4. 实现 Repository 接口

### Q: 分页查询如何实现？
A: 使用 PageHelper 插件：
```java
PageHelper.startPage(pageNum, pageRow);
List<UserPo> list = userDao.selectAll();
PageInfo<UserPo> pageInfo = new PageInfo<>(list);
```

## 相关文件清单

```
dal/
├── pom.xml
└── src/main/
    ├── java/com/llback/dal/
    │   ├── user/
    │   │   ├── dao/
    │   │   │   ├── UserDao.java
    │   │   │   └── AvatarDao.java
    │   │   ├── po/
    │   │   │   ├── UserPo.java
    │   │   │   └── AvatarPo.java
    │   │   └── repository/
    │   │       ├── UserRepositoryImpl.java
    │   │       └── AvatarRepositoryImpl.java
    │   ├── article/
    │   │   ├── dao/ArticleDao.java
    │   │   ├── po/ArticlePo.java
    │   │   └── repository/ArticleRepositoryImpl.java
    │   ├── perm/
    │   ├── menu/
    │   ├── file/
    │   ├── ai/
    │   └── sa/
    └── resources/
        ├── mapper/                # MyBatis XML Mapper
        │   ├── user/
        │   │   └── UserMapper.xml
        │   ├── article/
        │   └── ...
        └── sql/                   # 数据库脚本
            ├── ai_config.sql
            └── ai_config_add_initial_state.sql
```

## 下一步建议

1. 查看 [domain/domain-core 模块](../../domain/domain-core/CLAUDE.md) 了解领域实体（Eo）
2. 查看 [infrastructure-common 模块](../infrastructure-common/CLAUDE.md) 了解 PoAssembleUtil
3. 查看 [starter 模块](../../starter/CLAUDE.md) 了解数据库配置
