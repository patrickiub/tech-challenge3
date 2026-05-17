package com.fiap.pagamento.infra.client;

import com.fiap.pagamento.core.dto.RequisicaoProcpagDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ProcpagClient {

    private final RestTemplate restTemplate;
    private final String procpagUrl;

    public ProcpagClient(RestTemplate restTemplate, @Value("${procpag.url}") String procpagUrl) {
        this.restTemplate = restTemplate;
        this.procpagUrl = procpagUrl;
    }

    public boolean processar(RequisicaoProcpagDTO requisicao) {
        String url = procpagUrl + "/requisicao";
        log.info("Chamando procpag: url={}, valor={}, pagamento_id={}, cliente_id={}",
                url, requisicao.getValor(), requisicao.getPagamentoId(), requisicao.getClienteId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    url, new HttpEntity<>(requisicao, headers), String.class);
            log.info("procpag respondeu com status: {} e body: {}",
                    response.getStatusCode(), response.getBody());
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.error("procpag retornou erro: status={}, body={}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            return false;
        } catch (Exception e) {
            log.error("Erro ao chamar procpag: {}", e.getMessage());
            return false;
        }
    }
}
