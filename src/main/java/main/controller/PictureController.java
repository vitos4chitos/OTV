package main.controller;

import main.http.RequestPictures;
import main.http.Response;
import main.service.PictureService;
import main.service.TagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PictureController {

    @Autowired
    PictureService pictureService;

    @Autowired
    TagsService tagsService;

    @PostMapping("/picture")
    public ResponseEntity<?> getPicture(@RequestBody RequestPictures requestPictures) {
        return new ResponseEntity<>(pictureService.getPictures(requestPictures), HttpStatus.OK);
    }

    @PostMapping("/tag")
    public ResponseEntity<?> postAnswer(@RequestBody RequestPictures requestPictures, @RequestParam Long pictureId){
        tagsService.saveTags(requestPictures, pictureId);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
