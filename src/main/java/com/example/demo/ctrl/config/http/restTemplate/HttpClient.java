package com.example.demo.ctrl.config.http.restTemplate;

public interface HttpClient {
    <T> T exec(PortalHttpRequest request, Class<T> clazz);
}
