package com.yiyiglobal.dp.util.third;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.processing.OperationStatus;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.yiyiglobal.dp.common.Cons;
import com.yiyiglobal.dp.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by wangzukun on 2018/4/19.
 */
@Service
public class QiniuService {

    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${qiniu.access.key}")
    private String qiniuAccessKey;

    @Value("${qiniu.secret.key}")
    private String qiniuSecretKey;

    @Value("${qiniu.image.prefix}")
    private String qiniuImagePrefix;

    @Value("${qiniu.oss.pipeline}")
    private String qiniuOssPipline;

    @Value("${qiniu.oss.bucket}")
    private String qiniuOssBucket;

    @Value("${qiniu.oss.expire.seconds}")
    private String qiniuOssExpireSeconds;

    public boolean fetch(String remoteSrcUrl,String key){

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());

        Auth auth = Auth.create(qiniuAccessKey, qiniuSecretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);

        //抓取网络资源到空间
        try {
            FetchRet fetchRet = bucketManager.fetch(remoteSrcUrl, qiniuOssBucket, key);
            return true;
        } catch (QiniuException e) {
            logger.error("Exception{}",e);
            return false;
        }

    }


    public static String uploadToken(String key){
        String bucket = "yuenr-oss";
        String ak = "68-wB4MzU0HYKh0LlNdhQsptearKELGLVMZ66jZO";
        String sk = "H5vDtdpJyQYPPpHSAiouB5CceQ0_oS14caLGL8Yb";

        Auth auth = Auth.create(ak, sk);

        String upToken = auth.uploadToken(bucket,key);

        System.out.println(upToken);

        return upToken;
    }

    /**
     * 上传指定目录的文件至七牛
     * @param filePath
     * @param key
     * @return
     */
    public boolean upload(String filePath, String key){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(qiniuAccessKey, qiniuSecretKey);

        //token可以有多种形式
        String token = auth.uploadToken(qiniuOssBucket);

        UploadManager uploadManager= new UploadManager(cfg);
        try {
            uploadManager.put(filePath, key, token);
            return true;
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }


    }

    /**
     * Base64传到指定目录的文件中
     * @param data
     * @return
     */
    public boolean upload(byte[] data){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        Auth auth = Auth.create(qiniuAccessKey, qiniuSecretKey);

        UploadManager uploadManager= new UploadManager(cfg);
//        uploadManager.put(data,"123",auth.)

        return false;
    }

    /**
     * 视频转m3u8,链接地址：http://static.qiniu.yuenr.com/video_2017525_1495698170751@h.mp4
     * @return
     */
    public void getStatus(String persistentId){

        String ak = "68-wB4MzU0HYKh0LlNdhQsptearKELGLVMZ66jZO";
        String sk = "H5vDtdpJyQYPPpHSAiouB5CceQ0_oS14caLGL8Yb";
        //数据处理队列名称，必须
        String persistentPipeline = "yuenr-pipeline";

        //oss视频直接转码
        Auth auth = Auth.create(ak, sk);


        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //构建持久化数据处理对象
        OperationManager operationManager = new OperationManager(auth, cfg);

        try {
            OperationStatus operationStatus = operationManager.prefop(persistentId);

            //可以根据该 persistentId 查询任务处理进度
            System.out.println(operationStatus.code +" - " + operationStatus.desc);
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
        }

    }

    /**
     * 视频转m3u8,链接地址：http://static.qiniu.yuenr.com/video_2017525_1495698170751@h.mp4
     * @return
     */
    public void transferToM3u8(String key){

//        String waterMark="wmImage/aHR0cDovLzd4dGQ4ZS5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS8lRTYlQjAlQjQlRTUlOEQlQjAlMjBjb3B5QDJ4LnBuZw==/wmGravityText/NorthEast/dx/8/dy/8";
        String bucket = "yuenr-oss";
//        String bucket = "yuenr_live";
//        String bucket = "yuenr-hb-oss";

        String ak = "68-wB4MzU0HYKh0LlNdhQsptearKELGLVMZ66jZO";
        String sk = "H5vDtdpJyQYPPpHSAiouB5CceQ0_oS14caLGL8Yb";
        //数据处理队列名称，必须
        String persistentPipeline = "yuenr-pipeline";

        //获取key去除后缀的值
        String key_prefix = key.substring(0,key.lastIndexOf('.'));

        //oss视频直接转码
        Auth auth = Auth.create(ak, sk);

//        String fops_v = "avthumb/m3u8"+"/s/1280x720/autoscale/1/vb/512k";
        String fops_v = "avthumb/m3u8"+"/s/1280x720/autoscale/1/vb/5m";
        String key_v = key_prefix+"@h5.m3u8";

        String urlbase64_v = UrlSafeBase64.encodeToString(bucket+":"+key_v);
        String persistentOpfs = fops_v + "|saveas/" + urlbase64_v;

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //构建持久化数据处理对象
        OperationManager operationManager = new OperationManager(auth, cfg);
        try {
            String persistentId = operationManager.pfop(bucket,key,persistentOpfs,persistentPipeline,true);

            //可以根据该 persistentId 查询任务处理进度
            System.out.println(persistentId);
            OperationStatus operationStatus = operationManager.prefop(persistentId);
            //解析 operationStatus 的结果
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
        }

    }

    /**
     * 获取缩略图
     * @param key
     */
    public void vframe(String key){
        String bucket = "yuenr-oss";
        String ak = "68-wB4MzU0HYKh0LlNdhQsptearKELGLVMZ66jZO";
        String sk = "H5vDtdpJyQYPPpHSAiouB5CceQ0_oS14caLGL8Yb";
        //数据处理队列名称，必须
        String persistentPipeline = "yuenr-pipeline";

        //获取key去除后缀的值
        String key_prefix = key.substring(0,key.lastIndexOf('.'));

        //oss视频直接转码
        Auth auth = Auth.create(ak, sk);

        String fops_v = "vframe/jpg/offset/10";
        String key_v = key_prefix+"@m.jpg";
        String urlbase64_v = UrlSafeBase64.encodeToString(bucket+":"+key_v);
        String persistentOpfs = fops_v + "|saveas/" + urlbase64_v;

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //构建持久化数据处理对象
        OperationManager operationManager = new OperationManager(auth, cfg);
        try {
            String persistentId = operationManager.pfop(bucket,key,persistentOpfs,persistentPipeline,false);

            //可以根据该 persistentId 查询任务处理进度
            System.out.println(persistentId);
            OperationStatus operationStatus = operationManager.prefop(persistentId);
            //解析 operationStatus 的结果
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
        }

    }
    /**
     * 视频转码加水印，处理学部数据，链接地址：http://static.qiniu.yuenr.com/video_2017525_1495698170751.mp4
     * @return
     */
    public void transferVideo(String key){

        String waterMark="wmImage/aHR0cDovLzd4dGQ4ZS5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS8lRTYlQjAlQjQlRTUlOEQlQjAlMjBjb3B5QDJ4LnBuZw==/wmGravityText/NorthEast/dx/8/dy/8";
        String bucket = "yuenr-oss";
        String ak = "68-wB4MzU0HYKh0LlNdhQsptearKELGLVMZ66jZO";
        String sk = "H5vDtdpJyQYPPpHSAiouB5CceQ0_oS14caLGL8Yb";
        //数据处理队列名称，必须
        String persistentPipeline = "yuenr-pipeline";

        //获取key去除后缀的值
        String key_prefix = key.substring(0,key.lastIndexOf('.'));

        //oss视频直接转码
        Auth auth = Auth.create(ak, sk);

        String fops_v = "avthumb/mp4/"+waterMark+"/s/1280x720/autoscale/1/vb/512k";
        String key_v = key_prefix+"@h.mp4";
        String urlbase64_v = UrlSafeBase64.encodeToString(bucket+":"+key_v);
        String persistentOpfs = fops_v + "|saveas/" + urlbase64_v;

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //构建持久化数据处理对象
        OperationManager operationManager = new OperationManager(auth, cfg);
        try {
            String persistentId = operationManager.pfop(bucket,key,persistentOpfs,persistentPipeline,false);

            //可以根据该 persistentId 查询任务处理进度
            System.out.println(persistentId);
            OperationStatus operationStatus = operationManager.prefop(persistentId);
            //解析 operationStatus 的结果
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
        }

    }


    /**
     * 视频裁剪 + 水印
     * @param key
     */
    public void cropAndWaterMark(String key, int starTime, int time){
        String fop_crop = "/ss/"+starTime+"/t/"+time;
        if(starTime == -1) fop_crop = "";

//        String waterMark="wmImage/aHR0cDovLzd4dGQ4ZS5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS8lRTYlQjAlQjQlRTUlOEQlQjAlMjBjb3B5QDJ4LnBuZw==/wmGravityText/NorthEast/dx/8/dy/8";
//        String waterMark="wmImage/aHR0cDovL3N0YXRpYy5xaW5pdS55dWVuci5jb20vYTEucG5n/wmGravity/NorthEast/wmOffsetX/-21/wmOffsetY/17";
        String waterMark="";
//        String waterMark="wmImage/aHR0cDovL3N0YXRpYy5xaW5pdS55dWVuci5jb20vYTEucG5n";
        String bucket = "yuenr-oss";
        String ak = "68-wB4MzU0HYKh0LlNdhQsptearKELGLVMZ66jZO";
        String sk = "H5vDtdpJyQYPPpHSAiouB5CceQ0_oS14caLGL8Yb";
        //数据处理队列名称，必须
        String persistentPipeline = "yuenr-pipeline";

        //获取key去除后缀的值
        String key_prefix = key.substring(0,key.lastIndexOf('.'));

        //oss视频直接转码
        Auth auth = Auth.create(ak, sk);
        String fops_v = "";
        if(ValidateUtil.isEmpty(waterMark)) {
            fops_v = "avthumb/m3u8"+fop_crop;
        }
        else fops_v = "avthumb/m3u8/"+waterMark+fop_crop;
        String key_v = key_prefix+"@d.m3u8";
        String urlbase64_v = UrlSafeBase64.encodeToString(bucket+":"+key_v);
        String persistentOpfs = fops_v + "|saveas/" + urlbase64_v;

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //构建持久化数据处理对象
        OperationManager operationManager = new OperationManager(auth, cfg);
        try {
            String persistentId = operationManager.pfop(bucket,key,persistentOpfs,persistentPipeline,true);

            //可以根据该 persistentId 查询任务处理进度
            System.out.println(persistentId);
            OperationStatus operationStatus = operationManager.prefop(persistentId);
            //解析 operationStatus 的结果
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
        }

    }

    /**
     * 视频转码加水印，处理学部直播，链接地址：http://static.lvb.yuenr.com/recordings/z1.yuenr_live.1625721497325692/1497351965to1497352328.mp4
     * @return
     */
    public void transferLive(String key){

        String waterMark="wmImage/aHR0cDovLzd4dGQ4ZS5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS8lRTYlQjAlQjQlRTUlOEQlQjAlMjBjb3B5QDJ4LnBuZw==/wmGravityText/NorthEast/dx/8/dy/8";
        String bucket = "yuenr_live";
        String ak = "68-wB4MzU0HYKh0LlNdhQsptearKELGLVMZ66jZO";
        String sk = "H5vDtdpJyQYPPpHSAiouB5CceQ0_oS14caLGL8Yb";
        //数据处理队列名称，必须
        String persistentPipeline = "yuenr-pipeline";

        //获取key去除后缀的值
        String key_prefix = key.substring(0,key.lastIndexOf('.'));

        //oss视频直接转码
        Auth auth = Auth.create(ak, sk);

        String fops_v = "avthumb/mp4/"+waterMark;
        String key_v = key_prefix+".mp4";
        String urlbase64_v = UrlSafeBase64.encodeToString(bucket+":"+key_v);
        String persistentOpfs = fops_v + "|saveas/" + urlbase64_v;

        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //构建持久化数据处理对象
        OperationManager operationManager = new OperationManager(auth, cfg);
        try {
            String persistentId = operationManager.pfop(bucket,key,persistentOpfs,persistentPipeline,true);

            //可以根据该 persistentId 查询任务处理进度
            System.out.println(persistentId);
            OperationStatus operationStatus = operationManager.prefop(persistentId);
            //解析 operationStatus 的结果
        } catch (QiniuException e) {
            System.err.println(e.response.toString());
        }
    }

//    public static void move(String bucket,String key){
//
//        //构造一个带指定Zone对象的配置类
//        Configuration cfg = new Configuration(Zone.zone0());
//        Auth auth = Auth.create(Cons.QINIU_ACCESS_KEY, Cons.QINIU_SECRET_KEY);
//        //新建一个OperationManager对象
//        OperationManager operater = new OperationManager(auth, cfg);
//
//        //水印的源路径，图片水印目前仅支持远程路径，需要经过urlsafe_base64_encode
////        String waterMark = "wmImage/aHR0cDovLzd4dGQ4ZS5jb20xLnowLmdsYi5jbG91ZGRuLmNvbS9zaHVpeWluLnBuZw==/wmGravityText/NorthEast";
//
//        //设置转码操作参数
////        String fops = "avthumb/mp4/"+waterMark+"/s/1280x720/autoscale/1/vb/1.25m";
//        String fops = "avthumb/mp4/s/1280x720/autoscale/1/vb/512k";
//
//        //获取key去除后缀的值
//        String key_prefix = key.substring(0,key.lastIndexOf('.'));
//        String newKey = key_prefix+"@h.mp4";
//
//        String urlbase64 = UrlSafeBase64.encodeToString(bucket+":"+newKey);
//        String pfops = fops + "|saveas/" + urlbase64;
//
//        //设置转码的队列
//        String pipeline = "yuenr-pipeline";
//        //设置pipeline参数
//        StringMap params = new StringMap().putWhen("force", 1, true).putNotEmpty("pipeline", pipeline);
//
//        try {
//            String persistid = operater.pfop(bucket, key, pfops, params);
//            //打印返回的persistid
//            System.out.println(persistid);
//        } catch (QiniuException e) {
//            //捕获异常信息
//            Response r = e.response;
//            // 请求失败时简单状态信息
//            System.out.println(r.toString());
//            try {
//                // 响应的文本信息
//                System.out.println(r.bodyString());
//            } catch (QiniuException e1) {
//                //ignore
//            }
//        }
//    }

}
