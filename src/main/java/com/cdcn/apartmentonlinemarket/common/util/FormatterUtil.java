package com.cdcn.apartmentonlinemarket.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FormatterUtil {
    public static Date convertToUtilDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
