package com.yiyiglobal.dp.service.impl;

import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.yiyiglobal.dp.service.IOssService;
import com.yiyiglobal.dp.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by wangzukun on 2018/4/19.
 */
@Service
public class OssServiceImpl implements IOssService{

    @Value("${qiniu.access.key}")
    private String qiniuAccessKey;

    @Value("${qiniu.secret.key}")
    private String qiniuSecretKey;

    //七牛图片前缀
    @Value("${qiniu.image.prefix}")
    private String qiniuImagePrefix;

    @Value("${qiniu.oss.pipeline}")
    private String qiniuOssPipline;

    @Value("${qiniu.oss.bucket}")
    private String qiniuOssBucket;

    @Value("${qiniu.oss.expire.seconds}")
    private String qiniuOssExpireSeconds;

    /**
     * 根据key值获取相应上传授权
     * @param key
     * @return
     */
    public String getUpTokenByKey(String key){

        //获取key去除后缀的值
        String key_prefix = key.substring(0,key.lastIndexOf('.'));

//        String returnBody = "{\n  \"size\" : \"$(fsize)\",\n  \"w\" : \"$(imageInfo.width)\",\n  \"hash\" : \"$(etag)\",\n  \"persistentId\" : \"$(persistentId)\",\n  \"name\" : \"$(fname)\",\n  \"h\" : \"$(imageInfo.height)\"\n}";
        String returnBody = "{\n  \"duration\" :\"$(avinfo.format.duration)\",\n\"w\" : \"$(imageInfo.width)\",\n\"hash\" : \"$(etag)\",\n\"persistentId\" : \"$(persistentId)\",\n\"key\" : \"$(key)\",\n\"h\" : \"$(imageInfo.height)\"\n}";
        String persistentNotifyUrl = "https://yuenr.pagekite.me/oss/callback";

        //设置转码操作参数
        String pfops = null;

        //视频数据处理逻辑
        if (ValidateUtil.isVideo(key)){
            //水印的源路径，图片水印目前仅支持远程路径，需要经过urlsafe_base64_encode
            String waterMark = "wmImage/aHR0cDovLzd4dGQ4ZS5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS9zaHVpeWluLnBuZw==/wmGravityText/NorthEast";

            //超清-720p
            String fops_v_h = "avthumb/mp4/"+waterMark+"/s/1280x720/autoscale/1/vb/512k";
            String key_v_h = key_prefix+"@h.mp4";
            String urlbase64_v_h = UrlSafeBase64.encodeToString(qiniuOssBucket+":"+key_v_h);
            String pfops_v_h = fops_v_h + "|saveas/" + urlbase64_v_h;

            //高清-480p
            String fops_v_m = "avthumb/mp4/"+waterMark+"/s/854x480/autoscale/1/vb/512k";
            String key_v_m = key_prefix+"@m.mp4";
            String urlbase64_v_m = UrlSafeBase64.encodeToString(qiniuOssBucket+":"+key_v_m);
            String pfops_v_m = fops_v_m + "|saveas/" + urlbase64_v_m;

            //流畅-360p，竖屏转码时有bug，依然为横屏
            String fops_v_l = "avthumb/mp4/"+waterMark+"/s/640x360/autoscale/1/vb/512k";
            String key_v_l = key_prefix+"@l.mp4";
            String urlbase64_v_l = UrlSafeBase64.encodeToString(qiniuOssBucket+":"+key_v_l);
            String pfops_v_l = fops_v_l + "|saveas/" + urlbase64_v_l;

            //视频截图
            String vframe= ";vframe/jpg/offset/1/w/320/h/180|saveas/";
            String newThumbKey = UrlSafeBase64.encodeToString(qiniuOssBucket+ ":" +key_prefix+".jpg");
            String pfops_vframe = vframe + newThumbKey;
            pfops = pfops_v_h+";"+pfops_v_m+";"+pfops_v_l+pfops_vframe;

        }
        //音频处理逻辑
        else if(ValidateUtil.isAudio(key)){
            String fops_a = "avthumb/mp3";
            String key_a = key_prefix+".mp3";
            String urlbase64_a = UrlSafeBase64.encodeToString(qiniuOssBucket+":"+key_a);
            pfops = fops_a + "|saveas/" + urlbase64_a;
        }
        //图片处理逻辑
        else {}

        //获取授权
        Auth auth = Auth.create(qiniuAccessKey,qiniuSecretKey);

        StringMap putPolicy = new StringMap();
        putPolicy.putNotEmpty("persistentOps", pfops);
        putPolicy.putNotEmpty("persistentPipeline", qiniuOssPipline);
        putPolicy.putNotEmpty("returnBody",returnBody);
        putPolicy.putNotEmpty("persistentNotifyUrl",persistentNotifyUrl);

        //获取上传授权
        String upToken = auth.uploadToken(qiniuOssBucket, key, Long.valueOf(qiniuOssExpireSeconds), putPolicy);

        return upToken;
    }
}
