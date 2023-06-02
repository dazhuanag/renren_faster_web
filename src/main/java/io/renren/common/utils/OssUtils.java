package io.renren.common.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import io.renren.common.exception.RRException;

import java.io.File;
import java.io.InputStream;

public class OssUtils {
    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
    private static final String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private static final String accessKeyId = "LTAI4FuBFBBjPHQsWscFz6Ui";
    private static final String accessKeySecret = "xUpqxcESb3nSYkQ1pjBr4gLno7ZCOI";
    // 填写Bucket名称，例如examplebucket。
    private static final String bucketName = "educ-rjw";
    private static final String fileUrl = "https://educ-rjw.oss-cn-hangzhou.aliyuncs.com/";


    public static void main(String[] args) {
        String filePath = "/Users/edy/Desktop/vip_hukumdar_new.png";
        File file = new File(filePath);
        String filename = System.currentTimeMillis() + "_" + file.getName();
        String post = post(FileUtil.getInputStream(file), filename);
        System.out.print("URL" + post);
    }

    public static String post(InputStream inputStream, String filename) {
        String objectName = "exam/" + filename;
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            // 创建PutObjectRequest对象。
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream, metadata);
            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件
            PutObjectResult result = ossClient.putObject(putObjectRequest);
            System.out.print("post result" + JSON.toJSONString(result));
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + JSON.toJSONString(oe));
            throw new RRException("上传失败");
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            throw new RRException("上传失败");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return fileUrl + objectName;
    }
}
