package com.study.movida.controller;

import com.study.movida.dto.UploadResultDto;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@Slf4j
public class UploadController {


    @Value("${com.study.movida.upload.path}")
    private String uploadPath;

    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResultDto>> uploadFile(MultipartFile[] uploadFiles) {

        List<UploadResultDto> resultDtoList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {

            if(!Objects.requireNonNull(uploadFile.getContentType()).startsWith("image")) {
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String originalFilename = uploadFile.getOriginalFilename();
            assert originalFilename != null;
            String fileName = originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
            log.info("fileName : {}", fileName);


            String folderPath = makeFolder();
            String uuid = UUID.randomUUID().toString();

            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath);

                //썸네일 생성
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName;
                File thumbnailFile = new File(thumbnailSaveName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile, 100, 100);
                resultDtoList.add(new UploadResultDto(fileName, uuid, folderPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(resultDtoList, HttpStatus.OK);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size) {
        ResponseEntity<byte[]> result;

        try {
            String srcFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
            log.info("file Name : {}" , srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);

            if (size != null && size.equals("1")) {
                file = new File(file.getParent(), file.getName().substring(2));
            }

            log.info("## file : {}" , fileName);

            HttpHeaders headers = new HttpHeaders();

            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }

    @PostMapping("removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName) {
        String srcFileName = null;

        srcFileName = URLDecoder.decode(fileName, StandardCharsets.UTF_8);
        File file = new File(uploadPath + File.separator + srcFileName);
        boolean result = file.delete();

        File thumbnail = new File(file.getParent(), "s_" + file.getName());

        result = thumbnail.delete();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String folderPath = str.replace("/", File.separator);

        // make folder--
        File uploadPathFolder = new File(uploadPath, folderPath);

        if(!uploadPathFolder.exists()) {
            boolean responseMakes = uploadPathFolder.mkdirs();
            if (responseMakes) {
                log.info("make complete! {}", uploadPathFolder.toPath());
            } else {
                log.warn("not make file");
            }
        }
        return folderPath;
    }


//    @PostMapping("/uploadAjax")
//    public ResponseEntity<List<UploadResultDto>> uploadFile(MultipartFile[] uploadFiles){
//
//        List<UploadResultDto> resultDTOList = new ArrayList<>();
//
//        for (MultipartFile uploadFile: uploadFiles) {
//
//            if(uploadFile.getContentType().startsWith("image") == false) {
//                log.warn("this file is not image type");
//                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//            }
//
//            //실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
//            String originalName = uploadFile.getOriginalFilename();
//            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
//
//            log.info("fileName: " + fileName);
//            //날짜 폴더 생성
////            String folderPath = makeFolder();
//
//            //UUID
//            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 "_"를 이용해서 구분
//            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid +"_" + fileName;
//            Path savePath = Paths.get(saveName);

//            try {
//                //원본 파일 저장
//                uploadFile.transferTo(savePath);
//
//                //섬네일 생성
//                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator
//                        +"s_" + uuid +"_" + fileName;
//                //섬네일 파일 이름은 중간에 s_로 시작하도록
//                File thumbnailFile = new File(thumbnailSaveName);
//                //섬네일 생성
////                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile,100,100 );
////                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        }//end for
//        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
//    }


//    private String makeFolder() {
//
//        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
//
//        String folderPath =  str.replace("//", File.separator);
//
//        // make folder --------
//        File uploadPathFolder = new File(uploadPath, folderPath);
//
//        if (uploadPathFolder.exists() == false) {
//            uploadPathFolder.mkdirs();
//        }
//        return folderPath;
//    }

//    @GetMapping("/display")
//    public ResponseEntity<byte[]> getFile(String fileName) {
//
//        ResponseEntity<byte[]> result = null;
//
//        try {
//            String srcFileName =  URLDecoder.decode(fileName,"UTF-8");
//
//            log.info("fileName: " + srcFileName);
//
//            File file = new File(uploadPath +File.separator+ srcFileName);
//
//            log.info("file: " + file);
//
//            HttpHeaders header = new HttpHeaders();
//
//            //MIME타입 처리
//            header.add("Content-Type", Files.probeContentType(file.toPath()));
//            //파일 데이터 처리
//            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return result;
//    }

//    @PostMapping("/removeFile")
//    public ResponseEntity<Boolean> removeFile(String fileName){
//
//        String srcFileName = null;
//        try {
//            srcFileName = URLDecoder.decode(fileName,"UTF-8");
//            File file = new File(uploadPath +File.separator+ srcFileName);
//            boolean result = file.delete();
//
//            File thumbnail = new File(file.getParent(), "s_" + file.getName());
//
//            result = thumbnail.delete();
//
//            return new ResponseEntity<>(result, HttpStatus.OK);
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }

}
