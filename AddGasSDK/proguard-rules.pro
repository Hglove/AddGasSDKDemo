# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\hg\tools\androidsdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#############################################
#
# 对于一些基本指令的添加
#
#############################################
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*

-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**

# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}
#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService


# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}


#############################################
#
# 项目中特殊处理部分
#
#############################################

#-----------处理反射类---------------



#-----------处理js交互---------------



#-----------处理实体类---------------
# 在开发的时候我们可以将所有的实体类放在一个包内，这样我们写一次混淆就行了。
-keep public class com.squid.sdk.addgas.api.ResultBean {*;}

#Gson start
 -keepattributes Signature
 # For using GSON @Expose annotation
 -keepattributes *Annotation*
 # Gson specific classes
 -keep class sun.misc.Unsafe { *; }
 #-keep class com.google.gson.stream.** { *; }
 # Application classes that will be serialized/deserialized over Gson
 ##Gson End
#--------------------------
-dontwarn com.google.gson.**
-keep public class com.google.gson.** {*;}

#不要混淆注解类型，添加混淆配置：
-keep class * extends java.lang.annotation.Annotation { *; }
#--------------------------

#--------------------------
-dontwarn retrofit.**
-keep public class retrofit.** {*;}
#--------------------------
-dontwarn retrofit2.**
-keep public class retrofit2.** {*;}
#--------------------------
-dontwarn rx.internal.**
-keep public class rx.internal.** {*;}
#--------------------------
-dontwarn org.codehaus.mojo.**
-keep public class org.codehaus.mojo.** {*;}
#--------------------------
-dontwarn okhttp3.**
-keep public class okhttp3.** {*;}
#--------------------------
-dontwarn android.**
-keep public class android.** {*;}
#--------------------------
-dontwarn com.squareup.okhttp.**
-keep public class com.squareup.okhttp.** {*;}
# 将.class信息中的类名重新定义为"Proguard"字符串
-renamesourcefileattribute Proguard
# 并保留源文件名为"Proguard"字符串，而非原始的类名 并保留行号 // blog from sodino.com
-keepattributes SourceFile,LineNumberTable

#sdk应用
-keep public class com.squid.sdk.addgas.Squid {
    public *;
}
-keep public class com.squid.sdk.addgas.api.CallBack {
     *;
}
-keep public class com.squid.sdk.addgas.api.DecodeConverterFactory {
     *;
}
#-keep public class com.squid.sdk.addgas.api.ResultBean {*;}
#忽略警告
-ignorewarnings
##保证是独立的jar,没有任何项目引用,如果不写就会认为我们所有的代码是无用的,从而把所有的代码压缩掉,导出一个空的jar
-dontshrink
#保护泛型
-keepattributes Signature


#################################以下是自己添加的混淆协议###################################
#下面代码中的路径配置,你要修改成与你相对应的路径

#引入依赖包rt.jar（jdk路径）(注意：如在makeJar的时候提示指定了两次，可以将其注释掉)
#-libraryjars 'C:\Program Files\Java\jdk1.8.0_151\jre\lib\rt.jar'

#引入依赖包android.jar(android SDK路径)(注意：如在makeJar的时候提示指定了两次，可以将其注释掉)
#-libraryjars 'C:\hg\tools\androidsdk\platforms\android-26\android.jar'

#如果用到Appcompat包，需要引入(注意：如在makeJar的时候提示指定了两次，可以将其注释掉)
#-libraryjars 'D:\AndroidStudioProjects\MyApplication\mylibrary\build\intermediates\exploded-aar\com.android.support\appcompat-v7\23.4.0\jars\classes.jar'
#-libraryjars 'D:\AndroidStudioProjects\MyApplication\mylibrary\build\intermediates\exploded-aar\com.android.support\support-v4\23.4.0\jars\classes.jar'