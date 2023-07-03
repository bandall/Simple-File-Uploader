package hello.test.domain.upload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
public class UploadService {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public FileMetadata storeFile(MultipartFile multipartFile, Long ownerId) {
        if(multipartFile.isEmpty() || ownerId == null) return null;

        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFileName);
        try {
            multipartFile.transferTo(new File(getFullPath(storeFileName)));
        } catch (IOException e) {
            log.warn("서버에 파일 저장 중 오류 발생", e);
            throw new RuntimeException(e);
        }
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setStoredFileName(storeFileName);
        fileMetadata.setOriginalFileName(originalFileName);
        fileMetadata.setFileSize(multipartFile.getSize());
        fileMetadata.setOwnerId(ownerId);

        return fileMetadata;
    }

    public void deleteFile(String storedFileName) {
        File targetFile = new File(getFullPath(storedFileName));
        if(!targetFile.exists()) {
            log.warn("파일이 존재하지 않습니다: {}", storedFileName);
            return;
        }

        boolean deleteResult = targetFile.delete();
        if(!deleteResult) {
            log.warn("파일이 삭제에 실패 : {}", storedFileName);
            return;
        }

        log.info("파일 삭제 성공 : {}", storedFileName);
    }

    public String createStoreFileName(String originalFileName) {
        String ext = extractExt(originalFileName);
        String uuid = UUID.randomUUID().toString();
        String curTimeMil = String.valueOf(System.currentTimeMillis());
        return curTimeMil + "-" + uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        if(pos == -1) {
            return "noExt";
        }
        return originalFilename.substring(pos+1);
    }
}
