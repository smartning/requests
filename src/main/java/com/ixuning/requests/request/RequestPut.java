package com.ixuning.requests.request;

import com.ixuning.requests.bean.exception.RequestsForXNException;
import com.ixuning.requests.core.Config;
import com.ixuning.requests.util.TransformationUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created by xuning on 2016/8/10.
 */
public class RequestPut extends RequestOperation {
    private String url;
    private List<Header> headers;
    private List<NameValuePair> nameValuePairs;
    private String postBody;

    public RequestPut(RequestClient requestClient) {
        this.url = requestClient.getUrl();
        this.headers = requestClient.getHeaders();
        this.nameValuePairs = requestClient.getNameValuePairs();
        this.postBody = requestClient.getPostBody();
    }

    @Override
    protected HttpResponse oper() {
        HttpResponse httpResponse = null;
        HttpPut httpPut = new HttpPut(url);
        if (headers.size() > 0 && !Objects.equals(headers, null)) {
            for (Header header : headers) {
                httpPut.setHeader(header.getKey(), header.getValue());
            }
        }
        if (!Objects.equals(nameValuePairs, null)) {
            httpPut.setEntity(TransformationUtil.toHttpEntity(nameValuePairs));
        }

        if (!Objects.equals(postBody, null)) {
            StringEntity stringEntity = TransformationUtil.toStringEntity(postBody);
            stringEntity.setContentType(Config.DEFAULT_CONTENT_TYPE);
            stringEntity.setContentEncoding(Config.DEFAULT_CHARSET);
            httpPut.setEntity(stringEntity);
        }
        RequestConfig requestConfig = RequestConfig
                .custom()
                .setSocketTimeout(Config.DEFAULT_SOCKET_TIMOUT_CHARSET)
                .setConnectTimeout(Config.DEFAULT_CONNECTION_TIMOUT)
                .build();
        httpPut.setConfig(requestConfig);
        try {
            httpResponse = httpClient.execute(httpPut);
            if (Objects.equals(null, httpResponse)){
                new RequestsForXNException("httpresponse is null");
            }
        }
        catch (IOException e) {
            throw new RequestsForXNException(e.getMessage());

        }
        return httpResponse;
    }
}
