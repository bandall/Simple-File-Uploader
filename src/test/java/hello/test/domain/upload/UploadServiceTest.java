package hello.test.domain.upload;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class UploadServiceTest {

    private final UploadService uploadService = new UploadService();

    @Test
    void fileNameCreate() {
        String file1 = "test1.txt";
        String file2 = "test1.txt";

        String genName1 = uploadService.createStoreFileName(file1);
        String genName2 = uploadService.createStoreFileName(file2);
        log.info("{} <=> {}", genName1, genName2);
        assertThat(genName1).isNotSameAs(genName2);

        String genName3 = uploadService.createStoreFileName("NoExtFile");
        log.info("{}", genName3);
    }
}