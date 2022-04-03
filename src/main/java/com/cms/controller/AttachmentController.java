//package com.cms.controller;
//
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class AttachmentController {
//
//    @GetMapping("/download/{fileId}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception {
//        Attachment attachment = null;
//        attachment = attachmentService.getAttachment(fileId);
//        return  ResponseEntity.ok()
//            .contentType(MediaType.parseMediaType(attachment.getFileType()))
//            .header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + attachment.getFileName()
//                    + "\"")
//            .body(new ByteArrayResource(attachment.getData()));
//    }
//
//}
