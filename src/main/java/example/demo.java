package example;

import com.ixuning.requests.request.RequestClient;

import java.io.IOException;

/**
 * Created by me on 2016/12/18.
 */
public class demo {
    public static void main(String[] args) throws IOException {
        RequestClient requestClient=new RequestClient();
        String httpResponse=requestClient.openRequest("http://localhost:9200", "GET").getStringResponse();
        System.out.println(httpResponse);

    }
}
