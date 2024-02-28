package com.rdi.ecommerce.services.ecommerceimplementations;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.rdi.ecommerce.exceptions.MediaUploadException;
import com.rdi.ecommerce.services.CloudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ECommerceCloudService implements CloudService {

    private final Cloudinary cloudinary;
    @Override
    public String upload(MultipartFile multipartFile) throws MediaUploadException {
        try {
            Map<?, ?> uploadResponse = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap(
                    "resource_type", "auto"
            ));
            log.info("upload response -> {}", uploadResponse);
            return uploadResponse.get("secure_url").toString();
        } catch (IOException exception) {
            throw new MediaUploadException(exception.getMessage());
        }
    }
}
