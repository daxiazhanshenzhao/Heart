# JPMS 冲突修复摘要

## 问题描述
项目中存在重复引入的 JAR 包导致 JPMS (Java Platform Module System) 模块冲突。
常见的冲突来源是多个 SLF4J 日志实现（如 slf4j-simple、slf4j-log4j12、logback-classic 等）同时存在于 classpath 中。

## 依赖树分析结果
根据 `gradle dependencies` 的输出分析（参见 `docAI/aaa.txt`）：

✅ **没有发现显式的重复SLF4J实现依赖**
- 依赖树中只有 `org.slf4j:slf4j-api`（不同版本统一到 2.0.1）
- 没有 `slf4j-simple`、`slf4j-log4j12` 等实现包
- 没有 `logback-classic` 或其他日志框架

⚠️ **潜在问题来源**
虽然依赖树看起来干净，但 JPMS 冲突可能来自：
1. **JarJar 内嵌依赖** - Forge 的 JarJar 系统可能在某些 JAR 中内嵌了 SLF4J 实现
2. **运行时类路径** - 运行时可能加载了编译时看不到的依赖
3. **Service Provider 机制** - 通过 Java ServiceLoader 加载的实现
4. **模组依赖** - 某些模组可能打包了自己的 SLF4J 实现

## 解决方案
在 `build.gradle` 文件中添加了全局排除规则和新的扫描任务：

### 1. 增强的全局配置排除（第 151-174 行）
```groovy
configurations.all {
    // Exclude all SLF4J implementations (keep only slf4j-api)
    exclude group: 'org.slf4j', module: 'slf4j-simple'
    exclude group: 'org.slf4j', module: 'slf4j-log4j12'
    exclude group: 'org.slf4j', module: 'slf4j-jdk14'
    exclude group: 'org.slf4j', module: 'slf4j-nop'
    exclude group: 'org.slf4j', module: 'slf4j-reload4j'
    
    // Exclude all Log4j SLF4J bridges
    exclude group: 'org.apache.logging.log4j', module: 'log4j-slf4j-impl'
    exclude group: 'org.apache.logging.log4j', module: 'log4j-slf4j18-impl'
    
    // Exclude Logback
    exclude group: 'ch.qos.logback', module: 'logback-classic'
    exclude group: 'ch.qos.logback', module: 'logback-core'
    
    // Exclude bridges
    exclude group: 'org.slf4j', module: 'jul-to-slf4j'
    exclude group: 'org.slf4j', module: 'jcl-over-slf4j'
}
```

### 2. Minecraft/Forge 依赖排除
```groovy
minecraft("net.minecraftforge:forge:${minecraft_version}-${forge_version}") {
    exclude group: 'org.slf4j', module: 'slf4j-simple'
    exclude group: 'org.slf4j', module: 'slf4j-log4j12'
    exclude group: 'org.slf4j', module: 'slf4j-jdk14'
    exclude group: 'org.apache.logging.log4j', module: 'log4j-slf4j-impl'
    exclude group: 'ch.qos.logback', module: 'logback-classic'
}
```

### 3. Curios 依赖排除
```groovy
implementation(fg.deobf("top.theillusivec4.curios:curios-forge:5.14.1+${minecraft_version}")){
    transitive = false
    // 详细的排除规则...
}
```

### 4. 新增 JAR 扫描任务
添加了 `scanJarsForSlf4jImpls` 任务来检测 JAR 内部是否内嵌了 SLF4J 实现类。

## 被排除的包
以下重复的日志实现包已被排除，防止 JPMS 模块冲突：

**SLF4J 实现：**
- `org.slf4j:slf4j-simple` - SLF4J 简单实现
- `org.slf4j:slf4j-log4j12` - SLF4J 到 Log4j 1.x 的桥接
- `org.slf4j:slf4j-jdk14` - SLF4J 到 JDK 日志的桥接
- `org.slf4j:slf4j-nop` - SLF4J 空操作实现
- `org.slf4j:slf4j-reload4j` - SLF4J 到 Reload4j 的桥接

**Log4j SLF4J 桥接：**
- `org.apache.logging.log4j:log4j-slf4j-impl` - Log4j 2 的 SLF4J 实现
- `org.apache.logging.log4j:log4j-slf4j18-impl` - Log4j 2 的 SLF4J 1.8 实现
- ⚠️ `log4j-slf4j2-impl` 未排除 - Forge 需要使用此包

**Logback：**
- `ch.qos.logback:logback-classic` - Logback 日志实现
- `ch.qos.logback:logback-core` - Logback 核心

**SLF4J 桥接：**
- `org.slf4j:jul-to-slf4j` - JUL 到 SLF4J
- `org.slf4j:jcl-over-slf4j` - JCL 到 SLF4J

## Forge 的日志系统
Minecraft Forge 使用 **Log4j 2** 作为其日志框架，并通过 `log4j-slf4j2-impl` 提供 SLF4J 支持。
排除其他 SLF4J 实现可以确保只有 Forge 的日志系统被使用，避免模块冲突。

## 验证修复

### 1. 刷新 Gradle 项目
在 IntelliJ IDEA 中：
1. 打开 Gradle 工具窗口
2. 点击刷新按钮 🔄（或右键项目 -> Reload Gradle Project）

### 2. 运行验证任务
```bash
# 在 IntelliJ IDEA 的 Gradle 工具窗口中运行：
# 或者如果有 gradlew，在终端运行：

findDuplicates           # 查找重复的显式依赖
findSlf4jSimple          # 查找 slf4j-simple 的路径
showDependencyTree       # 显示完整依赖树
scanJarsForSlf4jImpls    # 🔍 扫描 JAR 内部的内嵌 SLF4J 实现（重要！）
```

**重点关注 `scanJarsForSlf4jImpls` 任务** - 这个任务会检查每个 JAR 文件内部是否包含 SLF4J 实现类，这些可能是导致 JPMS 冲突的隐藏来源。

### 3. 清理并重新构建
```bash
./gradlew clean build
```

### 4. 测试运行
运行游戏/服务器，检查是否还有 JPMS 冲突错误。

## 如果问题仍然存在

如果运行 `scanJarsForSlf4jImpls` 后发现某个 JAR 包含内嵌的 SLF4J 实现：

### 方案 A：排除整个 JAR
```groovy
configurations.all {
    exclude group: 'com.example', module: 'problematic-jar'
}
```

### 方案 B：使用 Jar-in-Jar 重新打包
某些情况下，可能需要重新打包 JAR 并移除冲突的类。

### 方案 C：联系模组作者
如果是第三方模组导致的问题，可以向模组作者报告此问题。

## 常见 JPMS 错误消息

如果看到以下错误，说明存在模块冲突：
- `java.lang.LayerInstantiationException: Package X in both module A and module B`
- `module X reads package Y from both M and N`
- `Split package: package P found in A and B`

## 注意事项
- ✅ 全局排除规则会影响所有依赖，包括传递依赖
- ✅ Forge 模组开发应该始终使用 Forge 提供的日志系统
- ⚠️ 如果某些第三方库必须使用特定的日志实现，需要单独处理
- ⚠️ 不要排除 `org.slf4j:slf4j-api` - 这是日志门面，很多库都需要它
- ⚠️ 不要排除 `log4j-slf4j2-impl` - Forge 依赖此包

## 文件修改记录
- ✅ `build.gradle` - 添加全局排除规则和扫描任务
- ✅ `docAI/aaa.txt` - 保存的依赖树分析结果
- ✅ `JPMS_FIX_SUMMARY.md` - 本文档

## 相关资源
- [Forge 日志系统文档](https://docs.minecraftforge.net/en/latest/gettingstarted/structuring/#logging)
- [SLF4J 官方文档](http://www.slf4j.org/)
- [Java Platform Module System](https://www.oracle.com/corporate/features/understanding-java-9-modules.html)

