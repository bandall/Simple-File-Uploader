package hello.test.domain.upload;

import java.util.List;
import java.util.Optional;

public interface FileMetaRepository {
    FileMetadata save(FileMetadata fileMetadata);

    void deleteByStoredFileName(String storedFileName);

    Optional<FileMetadata> findOneByStoredFileName(String storedFileName);

    List<FileMetadata> findAllByOriginalFileName(String queryName);

    List<FileMetadata> findAllByOriginalFileNameOrStoredFileName(String queryName);

    List<FileMetadata> findAll();

    int countFile();

    long countTotalFileSize();
}
