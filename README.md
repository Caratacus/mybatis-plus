<p align="center">
  <a href="https://github.com/baomidou/mybatis-plus">
   <img alt="Mybatis-Plus-Logo" src="https://raw.githubusercontent.com/baomidou/logo/master/mybatis-plus-logo-new-mini.png">
  </a>
</p>

<p align="center">
  Born To Simplify Development
</p>

## 为什么要改写这个项目?

MyBatis-Plus在最近的迭代版本中，本人已经没有怎么去迭代关注（由于工作较忙的原因），在最近的版本过程中，发现还是有一些不太完善的地方（核心功能还是比较nice的）。所以我在提出这些优化问题之后，很遗憾目前的MyBatis-Plus团队没有采纳我的观点，既然用的不爽所以我决定维护一个自己和自己公司的一个使用版本

## Links

文档之类的参照之前的MyBatis-Plus官网

## 改写的地方（基于MP3.4.0）

- `com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper`

```java
//原
sqlSet.add(String.format("%s=%s", columnToString(column), formatSql("{0}", val)));
//现
sqlSet.add(String.format("%s=%s", columnToString(column), val.toString()));
```

- `com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper`
```java
//原
sqlSet.add(String.format("%s=%s", columnToString(column), formatSql("{0}", val)));
//现
sqlSet.add(String.format("%s=%s", columnToString(column), val.toString()));
```
- `com.baomidou.mybatisplus.core.metadata.IPage` 分页相关数据类型从`long`修改为`int`
- `com.baomidou.mybatisplus.core.metadata.IPage` 去除`convert`方法
- `com.baomidou.mybatisplus.core.mapper.BaseMapper`精简
- `com.baomidou.mybatisplus.extension.conditions`包下所有类修改
- com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
```java
//原
sqlSet.add(String.format("%s=%s", columnToString(column), formatSql("{0}", val)));
//现
sqlSet.add(String.format("%s=%s", columnToString(column), val.toString()));
```
- `com.baomidou.mybatisplus.extension.plugins.pagination.Page` 分页相关数据类型从`long`修改为`int`
- `com.baomidou.mybatisplus.extension.plugins.pagination.Page` 添加`convert`方法
- `com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor` 分页相关数据类型从`long`修改为`int`
- `com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor` 分页相关数据类型从`long`修改为`int`
- 添加方法`com.baomidou.mybatisplus.core.toolkit.StringUtils#resolveFieldName`
