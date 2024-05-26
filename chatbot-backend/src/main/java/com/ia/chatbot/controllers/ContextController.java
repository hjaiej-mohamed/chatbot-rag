package com.ia.chatbot.controllers;

import com.ia.chatbot.models.entities.Context;
import com.ia.chatbot.services.ContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/context")
@RequiredArgsConstructor
public class ContextController {
    private final ContextService contextService;

    @GetMapping()
    public List<Context> getAllContexts(){
        return contextService.getAllContexts();
    }

    @PostMapping()
    public Context addContext(@RequestParam("file")MultipartFile pdf,@RequestParam("description") String description) throws IOException {
        return contextService.create(pdf,description);
    }

    @DeleteMapping("/{id}")
    public void deleteContext(@PathVariable("id") Long id) throws Exception {
        contextService.delete(id);
    }
}
