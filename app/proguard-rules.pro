# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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


-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-ignorewarnings
#google推荐算法
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*

##抛出异常时保留代码行号
# 保持混淆时类的实名及行号(--------------- 调试时打开 --------------)
#-keepattributes SourceFile,LineNumberTable
##重命名抛出异常时的文件名称
#-renamesourcefileattribute SourceFile

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.view{
	public <init>(android.content.Context);
	public <init>(android.content.Context,android.util.AttributeSet);
	public <init>(android.content.Context,android.util.AttributeSet,int);
	public void set*(***);
	*** get* ();
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable{
	static final long serialVersionUID;
	private static final java.io.ObjectStreamField[] srrialPersistentFields;
	!static !transient <fields>;
	private void writeObject(java.io.ObjectOutputStream);
	private void readObject(java.io.ObjectInputStream);
	java.lang.Object writeReplace();
	java.lang.Object readResolve();
}
-keepclasseswithmembernames class * {
    native <methods>;
}



# webview + js
# keep 使用 webview 的类
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-keep class android.webkit.JavascriptInterface {*;}
-keep class android.webkit.WebView{*;}
-keepclassmembers class com.bee.user.ui.CommonWebActivity {
   public *;
}
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
 }
-keepclassmembers class * extends android.webkit.WebChromeClient{ *; }
-keepclassmembers class * extends android.webkit.WebViewClient{ *; }


#assume no side effects:删除android.util.Log输出的日志
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}


#所有的bean类
-keep class com.bee.user.bean.** {*;}
-keep class com.bee.user.entity.** {*;}
-keep class com.bee.user.event.** {*;}
-keep class com.bee.user.params.** {*;}

# 状态栏,导航栏 管理工具ImmersionBar
 -keep class com.gyf.immersionbar.* {*;}
 -dontwarn com.gyf.immersionbar.**

#EventBus
 -keepattributes *Annotation*
 -keepclassmembers class * {
     @org.greenrobot.eventbus.Subscribe <methods>;
 }
 -keep enum org.greenrobot.eventbus.ThreadMode { *; }
 -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
     <init>(java.lang.Throwable);
 }


# 一键登录
-keep class cn.com.chinatelecom.** {*;}
-keep class com.unicom.xiaowo.login.** {*;}
-keep class com.cmic.sso.sdk.** {*;}
-keep class com.mobile.auth.** {*;}
 -keep class com.nirvana.** {*;}
 -keep class com.alibaba.fastjson.** {*;}
-keepclasseswithmembernames class * {
    native <methods>;
 }
 -keepclassmembers class * {
    @android.support.annotation.Keep <fields>;
    @android.support.annotation.Keep <methods>;
 }
 -keep @android.support.annotation.Keep class * {*;}
 -dontwarn

 # --- uc crash start ----（如果集成了UC crash收集组件需要增加这个配置）
 -keep class com.uc.crashsdk.** { *; }
 -keep interface com.uc.crashsdk.** { *; }
 # --- uc crash end ---
 -keepattributes Signature
 -keepattributes *Annotation*



#二级联动列表控件
 -keep class com.kunminx.linkage.bean.** {*;}



#    图片选择器，
 -keep class com.luck.picture.lib.** { *; }

 #Ucrop
 -dontwarn com.yalantis.ucrop**
 -keep class com.yalantis.ucrop** { *; }
 -keep interface com.yalantis.ucrop** { *; }

 #Okio
 -dontwarn org.codehaus.mojo.animal_sniffer.*




 #okhttp3
 -dontwarn com.squareup.okhttp3.**
 -keep class com.squareup.okhttp3.** { *;}
 -dontwarn okio.**

 #gson
 -keep class sun.misc.Unsafe { *; }
 -keep class com.google.gson.stream.** { *; }


 #rxjava和rxandroid
 -dontwarn sun.misc.**
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode producerNode;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
 }

 # 使用注解
 -keepattributes *Annotation*,Signature
 #butterknife
 -keep class butterknife.** { *; }
 -dontwarn butterknife.internal.**
 -keep class **$$ViewBinder { *; }
 -keepclasseswithmembernames class * {
     @butterknife.* <fields>;
 }
 -keepclasseswithmembernames class * {
     @butterknife.* <methods>;
 }





#retrofit
#-dontwarn retrofit2.**
#-keep class retrofit2.** { *; }
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod
# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit
# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.-KotlinExtensions

#okhttp
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform






#3D 地图 V5.0.0之后：
-keep   class com.amap.api.maps.**{*;}
-keep   class com.autonavi.**{*;}
-keep   class com.amap.api.trace.**{*;}
#定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}
#搜索
-keep   class com.amap.api.services.**{*;}
#导航
-keep class com.amap.api.navi.**{*;}
-keep class com.autonavi.**{*;}







#支付宝
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

#bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}