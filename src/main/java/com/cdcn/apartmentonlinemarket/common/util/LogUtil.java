package com.cdcn.apartmentonlinemarket.common.util;

import java.util.HashMap;
import java.util.Map;

public class LogUtil {
    public static Map<String, Object> getParamsAsMap(final String[] paramNames, final Object[] paramValues) {
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++)
            params.put(paramNames[i], paramValues[i]);
        return params;
    }
}
