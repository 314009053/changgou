nginx限流配置:
1、ip访问次数限制
http {
    include       mime.types;
    default_type  application/octet-stream;

    #定义nginx缓存模块
    # lua_stared_dict dis_cache 128m;

    #nginx限流设置  binary_remote_addr:根据请求ip限流，contentRateLimit：缓存空间名称，  10M：缓存空间大小， rate=2r/s:每秒允许又2个请求被处理
    limit_req_zone $binary_remote_addr zone=contentRateLimit:10m rate=2r/s;

    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;
	#用户请求/update_content?id=1,将该请求给/root/lua/68/update_content.klua脚本处理
	#location /update_content {
	#    content_by_lua_file /root/lua/68/update_content.lua;
	#}

	location /read_content {
	    #使用限流配置,burst=4 表示每秒可接受的最大请求数为4，先处理第一个请求，将剩下的放入队列，超出4的请求则直接拒绝，返回503，
	    #加nodelay表示并行执行，不等待前一个执行完毕，无延迟。
                    limit_req zone=contentRateLimit burst=4 nodelay;

	    content_by_lua_file /root/lua/68/read_content.lua;
	}
}

2、根据用户请求量的控制

http {
    include       mime.types;
    default_type  application/octet-stream;

    #定义nginx缓存模块
    # lua_stared_dict dis_cache 128m;

#============================================================
    #存储个人请求IP的限流容量
    limit_conn_zone $binary_remote_addr zone=perip:10m ;
    #整个location对应的请求并发容量
    limit_conn_zone $server_name zone=perserver:10m;
#============================================================
server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;
	#用户请求/update_content?id=1,将该请求给/root/lua/68/update_content.klua脚本处理
	#location /update_content {
	#    content_by_lua_file /root/lua/68/update_content.lua;
	#}

	location /read_content {
	#============================================================
	   #个人ip限流配置
	   limit_conn perip 3;
	   #当前location的总并发量配置
	   limit_conn perserver 5;
	#============================================================
	    content_by_lua_file /root/lua/68/read_content.lua;
	    #访问资源配置
	    proxy_pass http://192.168.19.100:18081;
	}
}


微服务网关的作用
1、整合各个微服务功能，形成一套系统
2、在微服务网关中实现日志的统一记录
3、实现用户的操作跟踪
4、实现限流
5、用户权限认证操作

生成公私钥：spring Security提供对jwt的支持，新建一个文件夹，在文件加下执行如下命令（生成证书，包含一对公私钥）
keytool -genkeypair -alias 别名 -keyalg RSA -keypass 密钥访问密码  -keystore 密钥库文件名.jks -storepass 密钥库访问密码
说明:	-alias:密钥别名
	    -keyalg:使用的加密算法
	    -keypass:密钥的访问密码
	    -keystore:密钥库文件名。xc.keystore保存了生成的证书
	    -storepass:密钥库的访问密码

	    姓氏：最好和公司域名保持一致

查询证书信息：
keytool -list -keystore 密钥库文件名.jks
删除别名
keytool -delete -alias changgou -keystore changgou.jks

导出公钥：
openssl是一个加解密工具包，http://slproweb.com/products/Win32OpenSSL.html  安装Win64OpenSSL-1_1_0g.exe 配置环境变量，
导出命令：
keytool -list -rfc --keystore changgou.jks | openssl x509 -inform pem -pubkey




