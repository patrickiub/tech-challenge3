package com.fiap.pagamento.infra.client;

import com.fiap.pagamento.core.dto.RequisicaoProcpagDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProcpagClient {

    private final RestTemplate restTemplate;
    private final String procpagUrl;

    public ProcpagClient(RestTemplate restTemplate, @Value("${procpag.url}") String procpagUrl) {
        this.restTemplate = restTemplate;
        this.procpagUrl = procpagUrl;
    }

    public boolean processar(RequisicaoProcpagDTO requisicao) {
        try {
            ResponseEntity<Void> response = restTemplate.postForEntity(
                    procpagUrl + "/requisicao", requisicao, Void.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}
