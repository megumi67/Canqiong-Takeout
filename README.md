# Canqiong-Takeout

## 项目结构

```
sky-take-out
├── pom.xml                          # 父工程 Maven 配置
├── README.md                         # 项目说明文档
│
├── sky-common                       # 通用模块
│   ├── pom.xml
│   └── src/main/java/com/sky/
│       ├── constant/                 # 常量定义
│       ├── context/                  # 上下文
│       ├── enumeration/              # 枚举类
│       ├── exception/                # 异常类
│       ├── json/                     # JSON 处理
│       ├── properties/              # 配置属性类
│       ├── result/                   # 统一返回结果
│       └── utils/                    # 工具类
│
├── sky-pojo                          # 实体类模块
│   ├── pom.xml
│   └── src/main/java/com/sky/
│       ├── dto/                      # 数据传输对象
│       ├── entity/                   # 实体类
│       └── vo/                       # 视图对象
│
└── sky-server                        # 服务端模块
    ├── pom.xml
    └── src/main/java/com/sky/
        ├── SkyApplication.java       # 启动类
        ├── config/                   # 配置类
        ├── controller/
        │   └── admin/                # 管理端控制器
        ├── handler/                  # 处理器
        ├── interceptor/              # 拦截器
        ├── mapper/                   # MyBatis Mapper 接口
        └── service/                 # 业务服务
            └── impl/                 # 业务服务实现
```

## 技术栈

- **后端框架**: Spring Boot
- **持久层**: MyBatis + MySQL
- **工具库**: Lombok, Jackson
- **其他**: JWT, 阿里云 OSS