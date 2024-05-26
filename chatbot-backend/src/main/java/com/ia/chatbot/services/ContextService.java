package com.ia.chatbot.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.ia.chatbot.models.entities.Context;
import com.ia.chatbot.repositories.ContextRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContextService {

    public String uri="http://agent-pgv:8000/chat/add-document";
    public String uriDelete="http://agent-pgv:8000/chat/document";
    private final ContextRepository contextRepository;

    public Context create(MultipartFile pdf,String description) throws IOException {
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("file", new ByteArrayResource(pdf.getBytes()) {
            @Override
            public String getFilename() {
                return pdf.getOriginalFilename();
            }
        });
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map);
        ResponseEntity<JsonNode> response = restTemplate.postForEntity(uri,requestEntity,JsonNode.class);

        Context context=Context.builder()
                .pdf(pdf.getBytes())
                .pdfName(pdf.getOriginalFilename())
                .description(description)
                .build();
        return  contextRepository.save(context);
    }
    public void delete(Long id) throws Exception {
        Context context=contextRepository.findById(id).orElseThrow(()-> new Exception("Context not found with id"+id));
        RestTemplate restTemplate = new RestTemplate();
        try{
            restTemplate.delete(this.uriDelete+"?collection_name="+context.getPdfName());
        }catch (Exception e ) {
            log.error("Error occurred when deleting the collection"+context.getPdfName());
        }
        contextRepository.deleteById(id);
    }

    public List<Context> getAllContexts() {
        return contextRepository.findAll();
    }
}
