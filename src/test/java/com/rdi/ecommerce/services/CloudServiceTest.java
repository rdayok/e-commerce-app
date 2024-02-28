package com.rdi.ecommerce.services;



import com.rdi.ecommerce.exceptions.MediaUploadException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
public class CloudServiceTest {
    @Autowired
    private CloudService cloudService;
    public static final String IMAGE_LOCATION = "C:\\Users\\WEALTHYMAN\\Documents\\REALCODE\\e-commerce\\src\\main\\resources\\assets\\cap.jpeg";
    
    @Test
    public void testUploadImage() throws MediaUploadException {
        Path path = Paths.get(IMAGE_LOCATION);
        String uploadResponse;
        try(var inputStream = Files.newInputStream(path)) {
            MultipartFile file = new MockMultipartFile(path.getFileName().toString(), inputStream);
            uploadResponse = cloudService.upload(file);
        }catch (IOException exception){
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
        assertNotNull(uploadResponse);
        log.info("{}", uploadResponse);
    }

}
