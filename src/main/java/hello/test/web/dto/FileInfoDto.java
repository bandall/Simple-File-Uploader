package hello.test.web.dto;

import hello.test.domain.upload.FileMetadata;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileInfoDto {
    private boolean hasFile;
    private FileMetadata fileMetadata;
}
