package hello.test.domain.upload;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FileMetaRepositoryJdbcTemplateTest {

    @Autowired
    FileMetaRepository fileMetaRepository;

    @Test
    void test() {
        // save
        FileMetadata file1 = new FileMetadata();
        file1.setStoredFileName("testFile");
        file1.setOriginalFileName("OriginalName");
        file1.setOwnerId(Long.valueOf(5));
        file1.setFileSize(Long.valueOf(1234566));

        fileMetaRepository.save(file1);

        // search one
        Optional<FileMetadata> fileSearch = fileMetaRepository.findOneByStoredFileName(file1.getStoredFileName());
        assertThat(fileSearch).isPresent();
        assertThat(fileSearch.get().getOriginalFileName()).isEqualTo(file1.getOriginalFileName());

        // search all
        FileMetadata file2 = new FileMetadata();
        file2.setStoredFileName("testFile22");
        file2.setOriginalFileName("OriginalName22");
        file2.setOwnerId(Long.valueOf(7));
        file2.setFileSize(Long.valueOf(5555));
        fileMetaRepository.save(file2);

        List<FileMetadata> fileMetadataList = fileMetaRepository.findAllByOriginalFileName("Origi");
        assertThat(fileMetadataList.size()).isEqualTo(2);

        // delete
        fileMetaRepository.deleteByStoredFileName(file1.getStoredFileName());
        fileMetaRepository.deleteByStoredFileName(file2.getStoredFileName());
    }
}