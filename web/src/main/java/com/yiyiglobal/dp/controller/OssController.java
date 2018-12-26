package com.yiyiglobal.dp.controller;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.yiyiglobal.dp.common.ResultEnum;
import com.yiyiglobal.dp.exception.BusinessException;
import com.yiyiglobal.dp.service.IOssService;
import com.yiyiglobal.dp.util.ValidateUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/v1/oss")
public class OssController extends BaseController{

    @Autowired
    private IOssService ossService;

    @ApiOperation(value="获取上传七牛的凭证，支持图片、音频、视频", notes="")
    @RequestMapping(value="/uptoken", method=RequestMethod.GET)
    public Map<String, Object> uptoken(@RequestParam(required = true) String key) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();

        //缺少必选参数
        if(ValidateUtil.isEmpty(key) ) {
            throw new BusinessException(ResultEnum.MISS_REQUIRED_PARAM);
        }

        //参数值非法，允许视频、音频和图片
        if(!ValidateUtil.isAudio(key) && !ValidateUtil.isVideo(key) && !ValidateUtil.isImage(key)){
            throw new BusinessException(ResultEnum.PARAM_INVALID);
        }

        //获取上传授权
        String upToken = ossService.getUpTokenByKey(key);

        map.put("upToken",upToken);

        return success(map);
    }

    @ApiOperation(value="七牛上传成功回调(暂未实现)", notes="")
    @RequestMapping(value="/callback", method=RequestMethod.POST)
    public void callback(HttpServletRequest request) throws Exception{

        //获取七牛回调数据
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder  sb = new StringBuilder();
        String line = "";
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        logger.info("返回 七牛回调 结果："+sb);

    }

    @ApiOperation(value="测试上传文件", notes="")
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public Map<String, Object> upload(@RequestParam String key,
                                      @RequestParam String upToken,
                                      @RequestParam String filePath) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        //自动识别要上传的空间(bucket)的存储区域是华东、华北、华南
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);

        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);

        try {
            //调用put方法上传
            Response res = uploadManager.put(filePath, key, upToken);
            //打印返回的信息
            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }

        return success(map);
    }


}
