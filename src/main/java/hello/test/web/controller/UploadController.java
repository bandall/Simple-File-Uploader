package hello.test.web.controller;

import hello.test.domain.member.MemberDetails;
import hello.test.domain.upload.FileMetaRepository;
import hello.test.domain.upload.FileMetadata;
import hello.test.domain.upload.UploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UploadController {

    private final FileMetaRepository fileMetaRepository;
    private final UploadService uploadService;

    @GetMapping("/upload")
    public String getUpload() {
        return "upload/uploadPage";
    }

    @ResponseBody
    @PostMapping("/upload")
    public Map<String, String> postUpload(@RequestParam("file") MultipartFile file, @SessionAttribute(name="id", required = false) Long ownerId, Model model) {
        log.info("starting file store={}", file);
        FileMetadata fileMetadata = uploadService.storeFile(file, ownerId);
        Map<String, String> responseMap = new HashMap<>();

        if(fileMetadata == null) {
            log.error("빈 파일 업로드");
            responseMap.put("status", "ERROR");
            responseMap.put("errMsg", "빈 파일이 업로드 되었습니다.");
            return responseMap;
        }

        fileMetaRepository.save(fileMetadata);
        log.info("file upload success={}", fileMetadata);
        responseMap.put("status", "SUCCESS");
        responseMap.put("fileId", fileMetadata.getStoredFileName());

        return responseMap;
    }

    @GetMapping("/upload/success/{fileId}")
    public String uploadSuccess(@PathVariable String fileId, Model model) {
        model.addAttribute("fileId", fileId);
        return "upload/uploadSuccess";
    }

    @GetMapping("/file-list")
    public String getFileList(Model model, @SessionAttribute("id") Long userId,
                              @RequestParam(value = "search", required = false) String searchParam,
                              @AuthenticationPrincipal MemberDetails memberDetails) {
        log.info("search param={}", searchParam);
        List<FileMetadata> files;
        if(StringUtils.hasText(searchParam)) {
            files = fileMetaRepository.findAllByOriginalFileNameOrStoredFileName(searchParam);
        } else {
            files = fileMetaRepository.findAll();
        }
        model.addAttribute("files", files);
        model.addAttribute("userId", userId);
        model.addAttribute("isAdmin", memberDetails.getAuthorities().toString().equals("[ROLE_ADMIN]"));
        model.addAttribute("searchParam", searchParam);
        return "upload/files";
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileId") String storedFileName, Model model) throws MalformedURLException, NoSuchFileException {
        log.info("파일 다운로드 요청 {}",storedFileName);
        Optional<FileMetadata> fileMetadataOptional = fileMetaRepository.findOneByStoredFileName(storedFileName);
        if(fileMetadataOptional.isEmpty()) {
            log.info("존재하지 않는 파일 : {}", storedFileName);
            throw new NoSuchFileException("존재하지 않는 파일입니다.");
        }

        FileMetadata fileMetadata = fileMetadataOptional.get();
        UrlResource urlResource = new UrlResource("file:" + uploadService.getFullPath(storedFileName));
        String encodeUploadFileName = UriUtils.encode(fileMetadata.getOriginalFileName(), StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";
        log.info("파일 다운로드 요청 성공 {}", storedFileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }

    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable("fileId") String storedFileName, @SessionAttribute("id") Long userId,
                             @AuthenticationPrincipal MemberDetails memberDetails, Model model) {
        log.info("파일 삭제 요청 (fileId={}, userId={})", storedFileName, userId);
        Optional<FileMetadata> fileMetadataOptional = fileMetaRepository.findOneByStoredFileName(storedFileName);
        if(fileMetadataOptional.isEmpty()) {
            log.error("존재하지 않는 파일 : {}", storedFileName);
            model.addAttribute("errMsg", "ERROR : 존재하지 않는 파일입니다.");
            return "error/errorMessage";
        }

        FileMetadata fileMetadata = fileMetadataOptional.get();
        if(fileMetadata.getOwnerId().longValue() != userId.longValue() && !memberDetails.getAuthorities().toString().equals("[ROLE_ADMIN]")) {
            log.info("userId={}, fileOwnerId={}", userId, fileMetadata.getOwnerId());
            model.addAttribute("errMsg", "ERROR : 파일 삭제 권한이 없습니다.");
            return "error/errorMessage";
        }

        uploadService.deleteFile(storedFileName);

        fileMetaRepository.deleteByStoredFileName(storedFileName);
        log.info("파일 메타데이터 삭제 성공 : {}", storedFileName);

        return "redirect:/file-list";
    }
}
