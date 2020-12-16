package org.felix.controller.login;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.felix.aop.annotation.CheckLicense;
import org.felix.aop.constants.Constant;
import org.felix.captcha.ArithmeticCaptcha;
import org.felix.code.BaseResponseCode;
import org.felix.exception.ServiceException;
import org.felix.model.vo.req.LoginReqVO;
import org.felix.model.vo.resp.LoginRespVO;
import org.felix.service.RedisService;
import org.felix.service.UserService;
import org.felix.utils.DataResult;
import org.felix.utils.RedisCheckUtil;
import org.felix.utils.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Felix
 */
@RestController
@Api(tags = {"组织模块-用户管理"})
@RequestMapping("/sys")
public class UserConroller {

    private Logger log = LoggerFactory.getLogger(UserConroller.class);

    @Resource
    private Producer producer;

    @Resource
    private ArithmeticCaptcha captcha;

    @Resource
    private UserService userService;

    @Resource
    private RedisService redisService;

    @Resource
    private RedisCheckUtil redisCheckUtil;

    @Value("${system.loginCodeTimeOut}")
    private Integer loginCodeTimeOut;

    @Value("${face.system_img}")
    private String system_img;

    @Value("${system.authorization_expired_date}")
    private String authorization_expired_date;

    @ApiOperation(value = "图片验证码")
    @GetMapping("/getPictureVerification")
    @ApiImplicitParams({@ApiImplicitParam(name = "time", value = "时间戳", required = true, paramType = "query", dataType = "String")})
    public void captcha(HttpServletResponse response, @RequestParam(value = "time") String time) throws IOException {

        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        String text = producer.createText();
        String captchaCodeKey = Constant.PICTURE_VERIFICATION + time;
        redisService.set(captchaCodeKey, text, loginCodeTimeOut, TimeUnit.MINUTES);
        BufferedImage image = producer.createImage(text);
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);

    }

    @PostMapping("/user/login")
    @ApiOperation(value = "用户账号密码登录接口")
    @CheckLicense
    public DataResult<LoginRespVO> login(@RequestBody @Valid LoginReqVO vo, HttpServletRequest request) {

        if (Tool.dateExpired(authorization_expired_date)) {
            throw new ServiceException(BaseResponseCode.AUTHORIZATION_EXPIRED_ERROR);
        }
        String redisCode = (String) redisService.get(Constant.PICTURE_VERIFICATION + vo.getTime());
        if (redisCode == null || !redisCode.equalsIgnoreCase(vo.getCode())) {
            throw new ServiceException(BaseResponseCode.PICTURE_CODE_ERROR);
        }
        DataResult<LoginRespVO> result = DataResult.success();
        System.err.println(result.getData());
        result.setData(userService.login(vo, request));

        return result;
    }





}
