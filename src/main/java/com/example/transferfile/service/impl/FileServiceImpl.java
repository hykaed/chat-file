package com.example.transferfile.service.impl;

import com.example.transferfile.exception.AuthException;
import com.example.transferfile.exception.FileException;
import com.example.transferfile.model.FileEntity;
import com.example.transferfile.model.User;
import com.example.transferfile.repository.FileRepository;
import com.example.transferfile.repository.UserRepository;
import com.example.transferfile.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileServiceImpl implements FileService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    public FileServiceImpl(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    private static final String BASE_PATH = "./files/";

    @Override
    public void upload(long receiverId, MultipartFile file) throws IOException, AuthException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        User from = userRepository.findByUsername(currentPrincipalName).orElseThrow(() -> new AuthException("User not found"));
        if (!from.isLoggedIn()) {
            throw new IllegalStateException("Cannot send");
        }

        User user = userRepository.findById(receiverId).orElseThrow(() -> new AuthException("Not found"));

        FileEntity fileEntity = new FileEntity();
        fileEntity.setUser(user);
        fileEntity.setName(file.getOriginalFilename());
        fileEntity.setFromUser(Math.toIntExact(from.getId()));
        fileRepository.save(fileEntity);
        file.transferTo(Path.of(BASE_PATH, fileEntity.getId().toString()));
    }

    @Override
    public ResponseEntity<byte[]> getFile(Integer id) throws IOException, FileException {
        FileEntity file = fileRepository.findById(BigInteger.valueOf(id))
                .orElseThrow(FileException::fileNotFound);
        String headerValue = "attachment; filename=" + file.getName() + "";
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(Files.readAllBytes(Path.of("files", file.getId().toString()).toAbsolutePath()));
    }
}
