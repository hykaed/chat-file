package com.example.transferfile.service;

import com.example.transferfile.exception.AuthException;
import com.example.transferfile.exception.FileException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    void upload(long receiverId, MultipartFile file) throws IOException, AuthException;

    ResponseEntity<byte[]> getFile(Integer id) throws IOException, AuthException, FileException;
}
