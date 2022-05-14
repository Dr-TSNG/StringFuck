# StringFuck

## Yet Another String Obfuscator for Android

![MavenCentral](https://img.shields.io/maven-central/v/icu.nullptr.stringfuck/icu.nullptr.stringfuck.gradle.plugin)

一个字符串混淆器，旨在防止被jadx、MT等工具一键解密

目前还很简陋，只能简单骗过反编译器（毕竟纯 Java）

可能存在不少 bug，只是个玩具，不适用于生产环境

### Features

+ 只支持 AGP 7+
+ 支持 API 24-32
+ 防一键解密（目前很简陋，人工还是容易找到解密类）
+ 低侵入性，只需在启动时调用一行初始化语句
+ 不需要任何 proguard 规则

### 使用方法

```kotlin
plugins {
    id("icu.nullptr.stringfuck") version "0.2.1"
}

dependencies {
    implementation("icu.nullptr.stringfuck:library:0.2.1")
}

stringFuck {
    // 解密密钥，ByteArray 类型
    key = xxx
    // 或使用 setKey(String)
    
    // 编译时是否输出调试信息
    isPrintDebugInfo = false
    
    // 是否在 debug buildType 中使用
    isWorkOnDebug = false
    
    // 是否使用白名单模式
    // 若不启用，StringFuck 只会混淆以列表中开头的类
    // 若启用，StringFuck 会混淆以列表中开头以外的所有类
    isWhiteList = false
    
    // 混淆列表
    obfuscationList = setOf("icu.nullptr")
    
    // 自定义加密方法，类型 ((String) -> ByteArray)?，设置为空则使用默认 Xor 方案
    encryptMethod = null
    
    // 自定义解密类完整类名，设置为空则使用默认 Xor 方案
    decryptMethodClassPath = null
}
```
##### 初始化

```java
StringFuck.init();
```
