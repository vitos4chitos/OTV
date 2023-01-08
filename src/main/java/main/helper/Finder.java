package main.helper;


import main.entity.Picture;
import main.http.PictureResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Finder {

    public static int getPictureIndex(List<PictureResponse> pictures, Long pictureId){
        Optional<PictureResponse> optionalPictureResponse = pictures
                .stream()
                .filter(pictureResponse -> pictureResponse.getPicture().getId().equals(pictureId))
                .findFirst();
        return optionalPictureResponse.map(pictures::indexOf).orElse(-1);
    }
}
