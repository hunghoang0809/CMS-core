package com.example.BaseCMS.module.slide;

import com.example.BaseCMS.exc.GenericErrorException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SlideService {
    private final SlideRepository slideRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Slide create(SlideRq slideRq) {
        Slide slide = Slide.builder()
                .imageUrl(slideRq.getImageUrl())
                .build();
        return slideRepository.save(slide);
    }

    public List<Slide> getAll() {
        return slideRepository.findAll();
    }

    public List<SlideDto> getAllDto() {
        List<Slide> slides = slideRepository.findAll();
        return slides.stream()
                .map(slide -> modelMapper.map(slide, SlideDto.class))
                .toList();
    }


    @Transactional
    public Slide update(Long id, SlideRq slideRq) {
        Slide slide = slideRepository.findById(id).orElseThrow(() -> new GenericErrorException(
                "Không tìm thấy slide với id " + id,
                HttpStatus.NOT_FOUND
        ));
        slide.setImageUrl(slideRq.getImageUrl());
        return slideRepository.save(slide);
    }

    @Transactional
    public void delete(Long id) {
        Slide slide = slideRepository.findById(id).orElseThrow(() -> new GenericErrorException(
                "Không tìm thấy slide với id " + id,
                HttpStatus.NOT_FOUND
        ));
        slideRepository.delete(slide);
    }
}
