package com.yiyiglobal.dp.common;

public class Cons {

    public static final String EMPTY_STRING = "";

    public static final long VERIFYCODE_EXPIRE_SECONDS = 60 * 30; // 验证码过期时间设为60*30秒

    public static final int VERIFYCODE_FAIL_COUNT = 5; // 短信验证码在输入错误的情况下最多使用5次

    public static final int SESSION_EXPIRE_SECONDS = 60 * 60 * 24 * 100; // 用户session过期时间设为100天

    public final static String[] QINIU_VIDEO_TYPE = {"AVI","ASF","WMV","AVS","FLV","MKV","MOV","3GP","MP4","MPG","MPEG","DAT","OGM","VOB","RM","RMVB","TS","TP","IFO","NSV"};
    public final static String[] QINIU_AUDIO_TYPE = {"MP3","AAC","WAV","WMA","CDA","FLAC","M4A","MID","MKA","MP2","MPA","MPC","APE","OFR","OGG","RA","WV","TTA","AC3","DTS","AMR"};
    public final static String[] QINIU_IMAGE_TYPE = {"JPG","PNG","JPEG","BMP","GIF"};

    //账号状态
    public static class AccountStatus {
        public final static int UNACTIVATED = 0;    //未激活
        public final static int ACTIVATED = 1;      //激活
    }

    public static class Sex {
        public final static int UNKNOWN = 0;    //未知
        public final static int BOY = 1;        //男
        public final static int GIRL = 2;       //女
    }

    //删除状态
    public static class DeleteStatus {
        public final static Byte DELETING = 0;  //未删除
        public final static Byte DELETED = 1;  //已删除
    }

    public static class LoginType {
        public final static int WEIBO=1;        //微博登录
        public final static int QQ = 2;         //QQ登录
        public final static int WECHAT_APP=3;   //微信App登录
        public final static int WECHAT_MP = 4;  //微信公众号登录

    }

    public static class Config {
        public final static String WX_MENU = "WX_MENU";     //微信菜单
        public final static String HOT_TAGS = "HOT_TAGS";     //热门标签
    }

    /** incomeDetail type*/
    public static class IncomeDetailType {
        public final static Integer token = 1;  // 代币
        public final static Integer contribution = 2;  // 贡献值
    }

    /** incomeDetail type*/
    public static class AdvertisementType {
        public final static Integer home = 1;  // 主页广告
        public final static Integer rank = 2;  // 排行广告
    }

    /** task id*/
    public static class TaskId {
        public final static Integer Comment_Review = 1;  // 发布点评
        public final static Integer Harvest_Support = 2;  // 收获点赞
        public final static Integer Invite_People = 3;  //  邀请好友
        public final static Integer Sign_In = 4;  // 签到
    }

    /** material related_type*/
    public static class MaterialRelatedType {
        public final static Integer Res = 1;  // 1-商家图片
        public final static Integer Review = 2;  // 2-点评图片
    }

    /** material type*/
    public static class MaterialType {
        public final static Integer pic = 1;  // 1-图片；2-视频；3-音频
        public final static Integer video = 2;  //1-图片；2-视频；3-音频
        public final static Integer audio = 3;  //1-图片；2-视频；3-音频
    }

    public static  String  H5RegisterPage="/account/login?inviteCode=";

    //通知状态
    public static class NotificationStatus{
        public final static Integer UNREAD=0;	//未读
        public final static Integer ISREAD=1;	//已读
    }

}
