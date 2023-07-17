package com.example.transferfile.controller;
import com.example.transferfile.exception.AuthException;
import com.example.transferfile.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/file")
@AllArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public void upload(@RequestParam long receiverId, @RequestParam("file") MultipartFile file) throws IOException, AuthException {
        fileService.upload(receiverId, file);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> getFile(@RequestParam Integer id) throws IOException, AuthException {
        return fileService.getFile(id);
    }
}
