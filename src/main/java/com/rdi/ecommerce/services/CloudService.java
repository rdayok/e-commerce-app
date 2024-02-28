package com.rdi.ecommerce.services;

import com.rdi.ecommerce.exceptions.MediaUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    String upload(MultipartFile file) throws MediaUploadException;
}
