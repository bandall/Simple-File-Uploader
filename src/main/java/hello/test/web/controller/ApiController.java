package hello.test.web.controller;


import hello.test.domain.upload.FileMetaRepository;
import hello.test.domain.upload.FileMetadata;
import hello.test.web.dto.FileInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@Slf4j
@RestController("/api")
@RequiredArgsConstructor
public class ApiController {

    private final FileMetaRepository fileMetaRepository;

    @GetMapping("/api/file-info/{fileId}")
    public FileInfoDto getFileInfo(@PathVariable String fileId) {
        log.info("api file info={}", fileId);
        Optional<FileMetadata> fileMetadataOptional = fileMetaRepository.findOneByStoredFileName(fileId);

        FileInfoDto fileInfoDto = new FileInfoDto();
        if(fileMetadataOptional.isEmpty()) {
            fileInfoDto.setHasFile(false);
            fileInfoDto.setFileMetadata(null);
        } else {
            fileInfoDto.setHasFile(true);
            fileInfoDto.setFileMetadata(fileMetadataOptional.get());
        }

        return fileInfoDto;
    }
}
