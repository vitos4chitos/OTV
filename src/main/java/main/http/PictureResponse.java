package main.http;

import lombok.Data;
import main.entity.Author;
import main.entity.Picture;

@Data
public class PictureResponse {
    Picture picture;
    Author author;
    Long count;
}
