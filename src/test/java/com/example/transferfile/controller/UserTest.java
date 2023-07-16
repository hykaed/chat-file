//package com.example.transferfile.controller;
//
//import com.example.transferfile.TransferFileApplication;
//import com.example.transferfile.exception.FilesException;
//import com.example.transferfile.model.FileEntity;
//import com.example.transferfile.model.User;
//import com.example.transferfile.repository.FileRepository;
//import com.example.transferfile.repository.UserRepository;
//import com.example.transferfile.service.FileService;
//import com.example.transferfile.service.UserService;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.web.multipart.MultipartFile;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import java.io.IOException;
//import java.math.BigInteger;
//import java.nio.file.Path;
//import java.util.Optional;
//
//@SpringBootTest
//public class UserTest {
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private FileRepository fileRepository;
//    @Autowired
//    private FileService fileService;
//
//    @Test
//    public void testUpload() throws IOException, FilesException {
//
//}
