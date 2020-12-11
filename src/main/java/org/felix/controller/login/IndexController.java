package org.felix.controller.login;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
@Api(tags = "视图", value = "负责返回不同的视图")
public class IndexController {

    private final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Value("${system.sql_path}")
    private String sql_path;

    @Value("${system.mysql_db_restore}")
    private String mysql_db_restore;

    /**
     * redirect to login.html
     * the ".html" is defined in application.yml
     * thymeleaf's configuration
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
