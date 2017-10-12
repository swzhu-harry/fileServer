# 文件服务器

----------- 测试--------------------------------------

#单个文件上传测试连接 ：
http://ip:port/mutifile


#批量文件上传测试连接 ：
http://ip:port/file

------------- 开发 -----------------------------------

restful 接口：
    /fileserver/upload
    
---------------application.yml配置说明-------------------------------------

1、里面分别配置了开发环境dev、测试环境信息

2、文件上传配置

file: 

    upload:
  
        --保存上传临时文件目录
    
        filedir: /xxxx/xxx 
    
        --配置文件上传白名单，不配置则 全部可上传（后续可根据实际情况 新增或改为黑名单配置）
    
        whiteSuffix: jpg,jsp    
    
        --枚举值：七牛-qiniu，阿里云-aliyun，腾讯云-qyun,又拍云-upyun 默认qiniu
    
        server: qiniu 

3、七牛服务配置

qiniu:

    --七牛下载分配的域名（跟用户相关的）
  
    httpBase: http://xxxxxxxxx/   
  
    --下面两项是授权的key信息
  
    secretKey: xxxxx
  
    --创建的存储空间
  
    bucket: test 
  
    --区域：枚举 z0-华东 z1-华北 z2-华南 na0-北美
  
    zoom: z0   
  
  
4、文件上传大小限制配置

http:

    multipart:
    
      --文件最大设置
      
      max-file-size: 8Mb   
      
      --请求最大设置
      
      max-request-size: 9Mb 

