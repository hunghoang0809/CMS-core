package com.example.BaseCMS.module.slide;

import com.example.BaseCMS.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/slide")
@RequiredArgsConstructor
public class SlideController {
    private final SlideService slideService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Success", slideService.getAllDto()));
    }

}
