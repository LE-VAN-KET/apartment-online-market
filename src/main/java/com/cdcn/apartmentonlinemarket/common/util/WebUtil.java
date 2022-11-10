package com.cdcn.apartmentonlinemarket.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static com.cdcn.apartmentonlinemarket.common.Constant.CommonConstant.HttpAttribute.ELAPSED_TIME;

@Component
public class WebUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);

    @Autowired
    private HttpServletRequest httpServletRequest;

    public String getRequestUri() {
        return Optional.ofNullable(httpServletRequest)
                .map(req -> req.getMethod() + " " + req.getRequestURI())
                .orElse("");
    }

    public Long getElapsedTime() {
        return Optional.ofNullable(httpServletRequest)
                .map(req -> String.valueOf(req.getAttribute(ELAPSED_TIME)))
                .filter(StringUtils::isNumeric)
                .map(t -> System.currentTimeMillis() - Long.parseLong(t))
                .orElse(-1L);
    }

}
