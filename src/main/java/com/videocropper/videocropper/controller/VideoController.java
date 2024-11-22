package com.videocropper.videocropper.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.videocropper.videocropper.service.VideoService;

@RestController
@RequestMapping("/videos")
public class VideoController {
	
	@Autowired
	VideoService videoService;

	@PostMapping("crop")
	public ResponseEntity<List<String>> cropVideos(
			@RequestParam("files") MultipartFile[] files,
			@RequestParam("width") Integer width,
			@RequestParam("height") Integer height,
			@RequestParam("x") int x,
			@RequestParam("y") int y) {
		try {
			if(files == null || files.length == 0) {
				throw new BadRequestException("files is mandatory");
			}
			if(width == null) {
				throw new BadRequestException("width is mandatory");
			}
			if(height == null) {
				throw new BadRequestException("height is mandatory");
			}
			List<String> croppedVideos = videoService.processVideos(files, width, height, x, y);
			return ResponseEntity.ok(croppedVideos);
		}catch(Exception e) {
			throw new RuntimeException("Error processing videos : " + e.getMessage());
		}
	}
}
