package com.POJO;

import com.google.gson.annotations.SerializedName;

public class HTTPHeaders {
    private String server;
    private String date;
    @SerializedName("content-type")
    private String content_type;
    @SerializedName("content-length")
    private String content_length;
    private String connection;
    @SerializedName("x-amzn-requestid")
    private String x_amzn_requestid;
    @SerializedName("x-amz-crc32")
    private String x_amz_crc32;

    public HTTPHeaders(String server, String date, String content_type, String content_length, String connection, String x_amzn_requestid, String x_amz_crc32) {
        this.server = server;
        this.date = date;
        this.content_type = content_type;
        this.content_length = content_length;
        this.connection = connection;
        this.x_amzn_requestid = x_amzn_requestid;
        this.x_amz_crc32 = x_amz_crc32;
    }

    public String getServer() {
        return server;
    }

    public String getDate() {
        return date;
    }

    public String getContent_type() {
        return content_type;
    }

    public String getContent_length() {
        return content_length;
    }

    public String getConnection() {
        return connection;
    }

    public String getX_amzn_requestid() {
        return x_amzn_requestid;
    }

    public String getX_amz_crc32() {
        return x_amz_crc32;
    }

    @Override
    public String toString() {
        return "HTTPHeaders{" +
                "server='" + server + '\'' +
                ", date='" + date + '\'' +
                ", content_type='" + content_type + '\'' +
                ", content_length='" + content_length + '\'' +
                ", connection='" + connection + '\'' +
                ", x_amzn_requestid='" + x_amzn_requestid + '\'' +
                ", x_amz_crc32='" + x_amz_crc32 + '\'' +
                '}';
    }
}
