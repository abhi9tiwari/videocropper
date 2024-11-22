package com.videocropper.videocropper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videocropper.videocropper.model.VideoEntity;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

}
