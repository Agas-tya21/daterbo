package com.example.daterbo.service;

import com.example.daterbo.model.Pic;
import com.example.daterbo.repository.PicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PicService {

    @Autowired
    private PicRepository picRepository;

    public List<Pic> getAllPics() {
        return picRepository.findAll();
    }

    public Optional<Pic> getPicById(String id) {
        return picRepository.findById(id);
    }

    public Pic createPic(Pic pic) {
        if (pic.getIdpic() == null || pic.getIdpic().isEmpty()) {
            pic.setIdpic(UUID.randomUUID().toString());
        }
        return picRepository.save(pic);
    }

    public Optional<Pic> updatePic(String id, Pic picDetails) {
        return picRepository.findById(id)
                .map(pic -> {
                    pic.setNamapic(picDetails.getNamapic());
                    pic.setNohp(picDetails.getNohp());
                    pic.setNamaleasing(picDetails.getNamaleasing());
                    pic.setAsalleasing(picDetails.getAsalleasing());
                    return picRepository.save(pic);
                });
    }

    public void deletePic(String id) {
        picRepository.deleteById(id);
    }
}