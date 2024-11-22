package com.videocropper.videocropper.service;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class VideoService {

	@Value("${file.output-dir}")
    private String outputDir;

    public List<String> processVideos(MultipartFile[] files, int width, int height, int x, int y) throws Exception {
        List<String> outputFiles = new ArrayList<>();

        FFmpeg ffmpeg = new FFmpeg("C://ffmpeg/ffmpeg");
        FFprobe ffprobe = new FFprobe("C://ffmpeg/ffprobe");

        for (MultipartFile file : files) {
            String originalFilename = file.getOriginalFilename();
            String outputFile = outputDir + UUID.randomUUID() + "_cropped_" + originalFilename;

            File inputFile = new File(outputDir + originalFilename);
            file.transferTo(inputFile);

            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(inputFile.getAbsolutePath())
                    .overrideOutputFiles(true)
                    .addOutput(outputFile)
                    .setVideoFilter(String.format("crop=%d:%d:%d:%d", width, height, x, y))
                    .done();

            ffmpeg.run(builder);
            outputFiles.add(outputFile);
        }

        return outputFiles;
    }
}