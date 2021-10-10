package com.POJO;

public class ResponseMetadata {
    private String RequestId;
    private String HTTPStatusCode;
    private HTTPHeaders HTTPHeaders;

    public ResponseMetadata(String requestId, String HTTPStatusCode, com.POJO.HTTPHeaders HTTPHeaders) {
        RequestId = requestId;
        this.HTTPStatusCode = HTTPStatusCode;
        this.HTTPHeaders = HTTPHeaders;
    }

    public String getRequestId() {
        return RequestId;
    }

    public String getHTTPStatusCode() {
        return HTTPStatusCode;
    }

    public com.POJO.HTTPHeaders getHTTPHeaders() {
        return HTTPHeaders;
    }

    @Override
    public String toString() {
        return "ResponseMetadata{" +
                "RequestId='" + RequestId + '\'' +
                ", HTTPStatusCode='" + HTTPStatusCode + '\'' +
                ", HTTPHeaders=" + HTTPHeaders +
                '}';
    }
}
