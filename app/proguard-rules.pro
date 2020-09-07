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


-keep class com.bee.user.bean.** {*;}
-keep class com.bee.user.entity.** {*;}
-keep class com.bee.user.event.** {*;}


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