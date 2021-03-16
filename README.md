﻿使用方法
=

在微博开放平台申请账号并新建应用
-
	开放平台地址：https://open.weibo.com

配置方式：
-
 + 配置文件：在weibo-openapi-config.properties文件，按所新建应用时获取的配置值进行配置；该配置文件编码必须保持为UTF-8  

 + 从外部来源获取配置值(例如数据库)：通过Weibo.of方法初始化对象时传入WeiboConfiguration参数(配置文件还是要保留，只是不用配置应用信息)  

 + 如果不能保证只使用SDK中的WeiboApiOauth2.apiBuildAuthorizeURL的方法获取带state参数的授权链接(即state可能为空)，则必须要有配置文件且配置一个默认应用的信息

其它
-
 + 测试过的接口都在pjq.weibo.openapi.exxamplesnew包下(由于没有高级接口的权限，目前只测试完一般常用的开放平台接口，其它没测试过的类可以尝试根据旧版的example去调用对应接口)  
	旧版地址：https://github.com/sunxiaowei2014/weibo4j-oauth2-beta3.1.1

 + 一般通过Weibo.of方法初始化业务接口对象  

 + 每个接口的通用参数有accessToken；clientId等参数从对应参数名的方法中获取

 + 每个业务接口类本身的可选参数在接口类中的属性变量中，并使用链式调用方式设置参数值，设置值后再调用api方法(接口类中的apiXXX方法)  

 + 可选参数是每个apiXXX接口方法都可共用，但实际是否会生效需要参考官网的API文档；每个接口本身的必填参数定义在具体的方法中  

 + 具体新版接口的使用可参考pjq.weibo.openapi.exxamplesnew包下每个类的写法  

 + 服务器需要连通以下域名(除了api.weibo.com和XXX.sinaimg.cn，其它都是根据实际使用到的业务判断是否需要连通)
	+ api.weibo.com  
	+ c.api.weibo.com  
	+ rm.api.weibo.com  
	+ m.api.weibo.com  
	+ XXX.sinaimg.cn(XXX不固定)