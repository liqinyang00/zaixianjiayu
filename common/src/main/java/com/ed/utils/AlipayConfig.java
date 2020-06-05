package com.ed.utils;

public class AlipayConfig{

    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String APP_ID =  "2016102200738368";//例：2016082600317257

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCPEvPfNaNVFmXY+9d0Mld3KF0Wa8UwaolUlwWfX75QR5kfLzqVbwvZxPHWSoDe1jfiKgLBy9xoqPxHZqZpqnxUK+nH3vVCHbQU6vfWJds02kY9XuWUbEiADPAA9uBbjYCRElq7VTF1vpIIwrlDZI7aB8N89/oGcd97J+LyTJNnHx0ZinSYGV8qQ8vtiPS/Ri/4t/qU8LpqdxLX+pQ+8ZlNvtoxUohcYTqhSTiXi/J6Wn2sjmDC1yPNoPhCXjZpyLgQx6gpOuguvjiU9U9PfMe9j90inYhpji4jHHcKjejenG5HXgfzkyhBr6ovvueb/OI+p/JVdxgBeSUaZVhzf+SnAgMBAAECggEAfQSAmIBUEluZ7zpQj7YVe4eHn7nX+nB9Q68bOKH+uEN35ynrtPvtjQJsl2VOqmjhN/yhoBm0pLdUikeGDjrsnNgqVX3bLUSrQMP4f+VgYBSC38l2LnVX/JxRc2ZFsXo554n0QlE1AL/e90y/ecX6lUXHagF9Nfh24TR/OF31R1sp8e3VYnF79A743Uql5g92R3A63zd6X0T3OwZygIY81kLmB8jm7yB1GdEtbOWYN/amEkW0TfyBlo0hTIJN94ofGWvGsxLf9+YXXYMPHqWiqzgcaMVVoyKTKHBYwFco8wrk6qg0Rh/UFkU+hkEtFzfY27AyoGQfEpam0zluEAzo0QKBgQD/6ayzAibbEekRy8jxUNcxwDtGks5HLbb4TGj6E/RAOiAUjU8crbc+t7m/WVqZs6ic1eVSlWmdpiTt4bfdp/g9vkbRjR1Hrkc41DbBVQaWlKxjQA8OoKuUp41rH26iT9U/6umyQSM9hSM3uv89RRYIAxFoSLqexobGfiY7qTDCJQKBgQCPH28k/xq2NcGfMTX0SFlRttFBGHeKetjE2Sq0l6bpbCuffxfIzw8aSq6waCyDiF/B6tcyKAYk7ZAxkP59w3M7aIwV0SdwcSGXbN6T1frpTJpzKqNL/0MBkzfZ6OxTV5HZjSZLvpia8vAmjS0VwrbAQMltsjCpKIn9ODCNGyfj2wKBgQCHO6yPS+vMiEJprQ6atuWA3Nx2Bs4KvxWZUIeI31KLPiwFsEbGGOZktiHqBAXkgxPvDPkKEvqk3E9XhZfsAf8kYZB6299FKctpNHta3tuEZzxRlmBeknkZLRGGMi38h8Ng63pjQBinQdvCb4bBkeh6g2SHfydfD/nYmoEJtfaWIQKBgQCLStWaFSnDIkhiDbvsFqnPbHT1vMFDJlJoKs90WZAZ56wBG+ljB5o0RXEEwS5piUVg7pUn/GoWcJsAkpQMhDdXH2YPKbMPrRs7S7rH+N6tRl+fLGUnZrSpCMW3sAipCDYrnEMkPsg5La1qZ8X0QsAoFiW0PFM9nvXFW3VtL3vANwKBgHvn6M163htlf27iLzOjeYDnsif4eyEyyKoKEbrrx2zjib7Gk14o+5FYnTA/HGtvHF4AzWjZr4+KfNWbAd3pALahNgzdi0H/Y+rMOBeb8tjNiNarEhTFvKmu3qjjpLrTEy3tZ8yeSQTTgDKSQUe0o60MqPLp0mF2lyTT4N2OZ1BZ";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
    // 对应APPID下的支付宝公钥。
    public static String ALIPAY_PUBLIC_KEY= "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjxLz3zWjVRZl2PvXdDJXdyhdFmvFMGqJVJcFn1++UEeZHy86lW8L2cTx1kqA3tY34ioCwcvcaKj8R2amaap8VCvpx971Qh20FOr31iXbNNpGPV7llGxIgAzwAPbgW42AkRJau1Uxdb6SCMK5Q2SO2gfDfPf6BnHfeyfi8kyTZx8dGYp0mBlfKkPL7Yj0v0Yv+Lf6lPC6ancS1/qUPvGZTb7aMVKIXGE6oUk4l4vyelp9rI5gwtcjzaD4Ql42aci4EMeoKTroLr44lPVPT3zHvY/dIp2IaY4uIxx3Co3o3pxuR14H85MoQa+qL77nm/ziPqfyVXcYAXklGmVYc3/kpwIDAQAB";
    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    /**
     * 返回的时候此页面不会返回到用户页面，只会执行你写到控制器里的地址
     */
    public static String notify_url = "http://localhost:8080/notify_url";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    /**
     * 此页面是同步返回用户页面，也就是用户支付后看到的页面，上面的notify_url是异步返回商家操作，谢谢
     * 要是看不懂就找度娘，或者多读几遍，或者去看支付宝第三方接口API，不看API直接拿去就用，遇坑不怪别人
     */
    public static String return_url = "http://localhost:8070/return_url";
    // 签名方式
    public static String sign_type = "RSA2";
    // 字符编码格式
    public static String CHARSET = "UTF-8";
    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

}
