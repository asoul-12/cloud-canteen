package my.asoul.takeout_server.controller;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;

import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.dish.Dish;
import my.asoul.takeout_server.exception.CommonException;
import my.asoul.takeout_server.mapper.dish.DishMapper;
import my.asoul.takeout_server.service.canteen.CanteenService;
import my.asoul.takeout_server.service.dish.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 4512
 * @date 2022/9/15 21:07
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonsController {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private CanteenService canteenService;

    @PostMapping("/upload/{type}/{id}")
    public BaseResponse<String> upload(MultipartFile file, @PathVariable("type") String type, @PathVariable("id") long id) {
        try {
            String filename = file.getOriginalFilename();
            Assert.notNull(filename, "filename is null");
            String fileType = filename.substring(filename.lastIndexOf("."));
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            File tempFile = File.createTempFile(uuid, fileType);
            filename = uuid + fileType;
            file.transferTo(tempFile);
            // cos
            String secretId = "AKIDyELH4oYFoHcv9GBvdHrGYcdSFLOPGwQh";
            String secretKey = "HPOAlAMJ1W3SmUAgzQdhdOzJUTJwOk1B";
            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            Region region = new Region("ap-guangzhou");
            ClientConfig clientConfig = new ClientConfig(region);
            clientConfig.setHttpProtocol(HttpProtocol.https);
            COSClient cosClient = new COSClient(cred, clientConfig);
            String bucketName = "canteen-image-1255913523";
            PutObjectRequest request;
            switch (type) {
                case "canteen":
                    canteenService.updateCanteenAvatar(id,filename);
                    request = new PutObjectRequest(bucketName, "canteen-avatar/" + filename, tempFile);
                    break;
                case "dish":
                    dishService.updateDishAvatar(id,filename);
                    request = new PutObjectRequest(bucketName, "dish-avatar/" + filename, tempFile);
                    break;
                default:
                    return BaseResponse.error("请求方式有误");
            }
            cosClient.putObject(request);
            return BaseResponse.success(filename);
        } catch (IOException | NullPointerException e) {
            throw new CommonException("upload fail");
        }
    }
}
