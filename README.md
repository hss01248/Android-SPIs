Android SPI



# java spi机制:

[ Java常用机制 - SPI机制详解](https://pdai.tech/md/java/advanced/java-advanced-spi.html)

META-INF/services目录,接口名为文件名,文件内容为一行到多行的实现类全限定名.

如果一个接口多个module/jar包都有实现,那么此文件最终会覆盖

# Android spis

>  怎么处理一个接口多个实现同时处理的情况?

借用Android 打包时assets文件夹的合并情况,略作更改:

assets/androidSpis/接口名/实现类名

接口名为文件夹

实现类名为空文件.

使用AndroidSpisReadUtil.findClasses(this, 接口/父类.class),返回List<String>,然后自行去反射.

### 防混淆

接口和实现类都加上@Keep注解.(androidx.annotation.Keep)

# 示例:

![image-20220816163115666](https://cdn.jsdelivr.net/gh/shuiniuhss/myimages@main/imagemac2/1660638681361-image-20220816163115666.jpg)

看到:

```tex
D/AndroidSpis: find classes in assets/androidSpis/com.hss01248.module_test.ICallbackTest1,list: [com.hss01248.android_spi.CallbackTestImpl2, com.hss01248.module_test.CallbackTestImpl]
```

# 使用

* 1 接口/父类, 及其实现/子类都加上@Keep注解
* 2在assets/androidSpis/接口名或父类名(全路径类名)文件夹下,建一个或多个空文件,文件名为子类全路径名
* 3 使用时,通过AndroidSpisReadUtil.findClasses(this, 接口/父类.class)拿到List<String>,然后反射使用.

### gradle依赖:

