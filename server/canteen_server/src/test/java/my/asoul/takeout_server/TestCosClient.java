package my.asoul.takeout_server;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author 4512
 * @date 2022/10/6 20:35
 */
@SpringBootTest
@Slf4j
public class TestCosClient {

    /**
     * 新建client
     */
    @Test
    public void test() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        // SECRETID和SECRETKEY请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = "AKIDyELH4oYFoHcv9GBvdHrGYcdSFLOPGwQh";
        String secretKey = "HPOAlAMJ1W3SmUAgzQdhdOzJUTJwOk1B";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        // 这里建议设置使用 https 协议
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);


//        查询用户的存储桶列表，参考示例如下：

        List<Bucket> buckets = cosClient.listBuckets();
        for (Bucket bucketElement : buckets) {
            String bucketName = bucketElement.getName();
            String bucketLocation = bucketElement.getLocation();
            log.info("bucketName:{},bucketLocation:{}", bucketName, bucketLocation);
        }



        //     如果想创建一个目录对象，可使用以下的示例代码：
        String bucketName = "takeout-dish-image-1255913523";
        String key = "avatar-img/";
        // 目录对象即是一个/结尾的空文件，上传一个长度为 0 的 byte 流
        InputStream input = new ByteArrayInputStream(new byte[0]);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(0);

        PutObjectRequest putObjectRequest =
                new PutObjectRequest(bucketName, key, input, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        //    上传不超过5GB的文件，参考示例如下：
        // 指定要上传的文件
        File localFile = new File("D:\\Resources\\img\\f7047626ffc64f519f6f9c61787fdf24.png");
        PutObjectRequest request = new PutObjectRequest(bucketName, "avatar-img/"+localFile.getName(), localFile);
        PutObjectResult objectResult = cosClient.putObject(request);
        // 指定文件将要存放的存储桶
//        String bucketName = "examplebucket-1250000000";
        // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
//        String key = "exampleobject";
//        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
//        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

    }

    @Test
    public void testCreateBucket() {
//        String bucket = "examplebucket-1250000000"; //存储桶名称，格式：BucketName-APPID
//        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
//        // 设置 bucket 的权限为 Private(私有读写)、
//        // 其他可选有 PublicRead（公有读私有写）、PublicReadWrite（公有读写）
//        createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
//        try {
//            Bucket bucketResult = cosClient.createBucket(createBucketRequest);
//        } catch (CosClientException serverException) {
//            serverException.printStackTrace();
//        }
    }


}
