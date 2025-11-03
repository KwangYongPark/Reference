package dev.binz.reference.user.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import dev.binz.reference.user.service.StreamService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class StreamRestController {
	
	@Autowired
	protected StreamService  streamService ;
	
    @GetMapping("/down")
    public ResponseEntity<StreamingResponseBody> validateUser() {
    	log.debug("=====validateUser");
    	
    	StreamingResponseBody responseBody = outputStream -> {
            try {
                // 엑셀 생성 서비스 메서드를 호출하여 outputStream에 직접 쓴다.
            	streamService.createLargeExcelStream(outputStream);
            } catch (IOException e) {
                // 스트리밍 중 오류 발생 시 처리
                e.printStackTrace();
            } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        };
    	
        log.debug("thread holding test printer");
        
        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "users.xlsx");

//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(responseBody);
        return new ResponseEntity<StreamingResponseBody>(responseBody, headers, HttpStatus.OK);
    }
}
