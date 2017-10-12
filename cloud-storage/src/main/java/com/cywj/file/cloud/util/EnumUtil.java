package com.cywj.file.cloud.util;

import org.apache.commons.lang.StringUtils;

import com.cywj.file.cloud.service.impl.AliyunCloudStorageService;
import com.cywj.file.cloud.service.impl.QiniuCloudStorageService;
import com.cywj.file.cloud.service.impl.QyunCloudStorageService;
import com.cywj.file.cloud.service.impl.UpyunCloudStorageService;
import com.qiniu.common.Zone;

import lombok.Getter;

/**
 * 枚举工具
 * @author zhushiwu
 *
 */
public class EnumUtil {
	public enum ZoomEnum{
		zoom01(Zone.zone0(),"z0","华东"),
		zoom02(Zone.zone1(),"z1","华北"),
		zoom03(Zone.zone2(),"z2","华南"),
		zoom04(Zone.zoneNa0(),"na0","北美");
		
		@Getter
		private Zone zone;
		@Getter
		private String zoomCode;
		@Getter
		private String decs;
		private ZoomEnum(Zone z,String zoomCode,String decs){
			this.zone = z;
			this.zoomCode = zoomCode;
			this.decs = decs;
		}
		public static ZoomEnum getZoneByZoomCode(String zoomCode)
		{
			if(!StringUtils.isBlank(zoomCode)){
				ZoomEnum[] itms = values();
				for (ZoomEnum itm : itms) {
					if(zoomCode.toString().trim().equalsIgnoreCase(itm.zoomCode)){
						return itm;
					}
				}
			}
			return zoom01;
		}
	}
	
	public enum QinuyErrorEnum{
		SUCC(200,"成功"),
		ERROR_400(400,"请求报文格式错误"),
		ERROR_401(401,"管理凭证无效"),
		ERROR_404(404,"资源不存在"),
		ERROR_631(631,"bucket存储空间不存在"),
		ERROR_599(599,"七牛服务端操作失败，直接联系七牛侧人员"),
		
		//自定义错误码
		ERROR_4001(4001,"白名单不包括此类型文件"),
		
		UNKNOW(999,"未知服务端错误"),;
		
		@Getter
		private int statusCode;
		@Getter
		private String decs;
		private QinuyErrorEnum(int statusCode,String decs){
			this.statusCode = statusCode;
			this.decs = decs;
		}
		public static QinuyErrorEnum getQinuyErrorEnum(int statusCode)
		{
			QinuyErrorEnum[] itms = values();
			for (QinuyErrorEnum itm : itms) {
				if(statusCode==itm.statusCode){
					return itm;
				}
			}
			return UNKNOW;
		}
	}
	
	public enum CloudServiceEnum{
		QINIU(QiniuCloudStorageService.class,"qiniu"),
		ALI_YUN(AliyunCloudStorageService.class,"aliyun"),
		Q_YUN(QyunCloudStorageService.class,"qyun"),
		UP_YUN(UpyunCloudStorageService.class,"upyun"),
		;
		
		@Getter
		private Class<?> serviceClass;
		@Getter
		private String serviceCode;
		
		private CloudServiceEnum(Class<?> serviceClass,String serviceCode){
			this.serviceClass = serviceClass;
			this.serviceCode = serviceCode;
		}
		public static CloudServiceEnum getCloudServiceEnum(String serviceCode)
		{
			if(!StringUtils.isBlank(serviceCode)){
				CloudServiceEnum[] itms = values();
				for (CloudServiceEnum itm : itms) {
					if(serviceCode.toString().trim().equalsIgnoreCase(itm.serviceCode)){
						return itm;
					}
				}
			}
			return QINIU;
		}
	}
}
