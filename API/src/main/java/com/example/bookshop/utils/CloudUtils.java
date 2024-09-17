package com.example.bookshop.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.bookshop.exception.CustomRunTimeException;
import com.example.bookshop.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Slf4j
public class CloudUtils {
     Cloudinary cloudinary ;

     public  String uploadFile(MultipartFile image) {
          try {
               var uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
               log.info(uploadResult.toString());
               return uploadResult.get("secure_url").toString();
          } catch (IOException e) {
               throw new CustomRunTimeException(ErrorCode.FILE_NOT_FOUND);
          }
     }

     public String deleteFile(String publicId) {
          try {
               var result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
               if ("ok".equals(result.get("result"))) {
                    return "Image deleted successfully";
               } else {
                    return "Failed to delete image";
               }
          } catch (IOException e) {
              throw  new CustomRunTimeException(ErrorCode.DELETE_FILE_NOT_SUCCESS);
          }
     }
}
