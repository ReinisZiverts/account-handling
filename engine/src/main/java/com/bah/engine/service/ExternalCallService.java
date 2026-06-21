package com.bah.engine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ExternalCallService {

    private final RestClient restClient;

    public void logDebit() {
        restClient
                .get()
                .uri("https://tools-httpstatus.pickup-services.com/200")
                .retrieve()
                .toBodilessEntity();
    }

}
