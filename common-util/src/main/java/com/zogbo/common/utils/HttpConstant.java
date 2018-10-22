package com.zogbo.common.utils;

/**
 * Created by cw on 15-9-11.
 */
public class HttpConstant {
    private static Object lock = new Object();

    public static abstract class Method {

        public static final String GET = "GET";

        public static final String HEAD = "HEAD";

        public static final String POST = "POST";

        public static final String PUT = "PUT";

        public static final String DELETE = "DELETE";

        public static final String TRACE = "TRACE";

        public static final String CONNECT = "CONNECT";

    }
}
