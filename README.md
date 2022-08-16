# Android SPI



# java spi机制:

[ Java常用机制 - SPI机制详解](https://pdai.tech/md/java/advanced/java-advanced-spi.html)

META-INF/services目录,接口名为文件名,文件内容为一行到多行的实现类全限定名.

这个机制在Android里依然可用

但如果一个接口多个module/jar包都有实现,那么此文件最终会覆盖?

> 其实并不会,会自动合并

# Android spis

>  怎么处理一个接口多个实现需要并存的情况?

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

W/AndroidSpis: [com.hss01248.android_spi.CallbackTestImpl2@bd7d8dd, com.hss01248.module_test.CallbackTestImpl@6e8dc52]
```

# 使用

* 1 接口/父类, 及其实现/子类都加上@Keep注解
* 2在assets/androidSpis/接口名或父类名(全路径类名)文件夹下,建一个或多个空文件,文件名为子类全路径名
* 3 使用时,通过AndroidSpisReadUtil.findClasses(this, 接口/父类.class)拿到List<String>,然后反射使用.
* ​    也可使用AndroidSpisReadUtil.findClassAndNewInstances拿到 List<T>

```java
 List<String> classes = AndroidSpisReadUtil.findClasses(this, ICallbackTest1.class);

        List<ICallbackTest1> classAndNewInstances = AndroidSpisReadUtil.findClassAndNewInstances(this, ICallbackTest1.class);
```



### gradle依赖:

```groovy
api 'com.github.hss01248.Android-SPIs:android-spis:1.0.0'
```



# 其他类java spi框架

[sp](https://github.com/luqinx/sp)



# java spi在Android里的表现

```java
    private void javaSpi() {
        ServiceLoader<ICallbackTestJava> serviceLoader = ServiceLoader.load(ICallbackTestJava.class);
        Log.w("AndroidSpis2", "java spi: "+serviceLoader.toString());

//遍历服务
        for (ICallbackTestJava impl : serviceLoader) {
            impl.run2();
        }
    }
```

log:

```tex
java spi: java.util.ServiceLoader[com.hss01248.module_test_java_spi.ICallbackTestJava]
CallbackTestJavaImpl3---> run---module_test_java_spi3
CallbackTestJavaImpl---> run--module_test_java_spi2
CallbackTestJavaImpl---> run---module_test_java_spi1
```

均可使用google的autoservice自动生成resources/META-INF/services内相关内容

```groovy
    implementation 'com.google.auto.service:auto-service:1.0'
    annotationProcessor 'com.google.auto.service:auto-service:1.0'
```

```java
@Keep
@AutoService(ICallbackTestJava.class)
public class CallbackTestJavaImpl implements ICallbackTestJava{
    @Override
    public void run2() {
        Log.w("AndroidSpis2","CallbackTestJavaImpl---> run---module_test_java_spi1");

    }
}
```

