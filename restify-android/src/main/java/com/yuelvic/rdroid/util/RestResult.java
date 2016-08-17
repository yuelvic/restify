package com.yuelvic.rdroid.util;

import java.util.HashMap;

/**
 * Created by yuelvic on 8/17/16.
 */
public class RestResult extends HashMap<String, Object> {

    protected RestResult(HashMap<String, Object> body) {
        putAll(body);
    }

}
