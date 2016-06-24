# AndroidAcp
 * Acp 为 Android check permission 缩写，此库简化Android 6.0 系统复杂的权限操作而编写。

# 特点
 * 支持批量权限申请，不需要重写 onRequestPermissionsResult 方法，Activity 与 Fragment 中用法一致，一句话搞定。
 * 处理权限拒绝，或勾选不再询问，导致不能正常使用功能的提示框，支持跳转设置权限界面开启权限，所有提示框文字可自定义。  

# 效果图
<img src="preview/gif.gif" width="240px"/>

#使用Gradle构建时添加一下依赖即可:
```javascript
compile 'com.mylhyl:acp:1.0.0'
```

# 使用说明
```java
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
//                .setDeniedMessage()
//                .setDeniedCloseBtn()
//                .setDeniedSettingBtn()
//                .setRationalMessage()
//                .setRationalBtn()
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        writeSD();
                        getIMEI();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        makeText(permissions.toString() + "权限拒绝");
                    }
                });
```
### 友情提示
在`android studio`环境 请求`Manifest.permission.CALL_PHONE`权限时，Intent不能使用带参的构造函数(Intent.ACTION_CALL)，否则会编译通不过的。

### [下载APK体验](/preview/sample-debug.apk)

## QQ交流群:435173211
