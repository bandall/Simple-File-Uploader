package hello.test.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.NoSuchFileException;

@Slf4j
@ControllerAdvice
public class ExControllerAdvice {

    //https://velog.io/@park2348190/Spring%EC%9D%98-MaxUploadSizeExceededException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String maxSizeError(Model model, Exception e) {
        log.info("파일 업로드 최대 크기 초과");
        model.addAttribute("errMsg", "ERROR : 파일 최대 크기는 300MB 입니다.");
        return "/error/errorMessage";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalformedURLException.class)
    public String wrongURLResourceError(Model model) {
        model.addAttribute("errMsg", "ERROR : 잘못된 URL 리소스 입니다.");
        return "/error/errorMessage";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({NoSuchFileException.class, FileNotFoundException.class})
    public String noSuchFileError(Model model) {
        model.addAttribute("errMsg", "ERROR : 파일이 존재하지 않습니다.");
        return "/error/errorMessage";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String defaultError(Model model, Exception e) {
        model.addAttribute("errMsg", "ERROR : 서버에 오류가 발생했습니다.");
        log.error("서버에 오류 발생", e);
        return "/error/errorMessage";
    }
}
