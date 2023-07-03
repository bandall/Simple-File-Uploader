package hello.test.domain.upload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileMetadata {
    private String storedFileName;
    private String originalFileName;
    private Long ownerId;
    private Long fileSize;
}
