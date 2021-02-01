使用方法

1、在微博开放平台申请账号并新建应用  
	地址：https://open.weibo.com/

2、填写相关配置：在weibo-openapi-config.properties文件，按所新建应用时获取的配置值进行配置  

3、测试过的接口都在pjq.weibo.openapi.exxamplesnew包下(由于没有高级接口的权限，目前只测试完一般常用的开放平台接口，其它没测试过的类可以尝试根据旧版的example去调用对应接口)  
	旧版地址：https://github.com/sunxiaowei2014/weibo4j-oauth2-beta3.1.1

4、每个接口的通用参数有accessToken、clientId，一般通过Weibo.of方法初始化对象  

5、每个业务接口类本身的可选参数在接口类中的属性变量中，并使用链式调用方式设置参数值，设置值后再调用api方法(接口类中的apiXXX方法)  

6、可选参数是每个apiXXX接口方法都可共用，但实际是否会生效需要参考官网的API文档；;每个接口本身的必填参数定义在具体的方法中  

7、具体新版接口的使用可参考pjq.weibo.openapi.exxamplesnew包下每个类的写法  
