package org.akhil.bg.service;

import org.springframework.web.multipart.MultipartFile;

public interface RemoveBackGroundService {
    byte[] removeBackground(MultipartFile file);
}
