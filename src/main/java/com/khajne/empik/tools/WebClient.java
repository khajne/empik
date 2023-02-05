package com.khajne.empik.tools;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WebClient {

    private final OkHttpClient okHttpClient;

    public Optional<Response> sendGetRequest(String url) {

        Request request = new Request.Builder()
                .url(url)
                .build();

        return Try.of(() -> okHttpClient.newCall(request).execute()).toJavaOptional();
    }
}
