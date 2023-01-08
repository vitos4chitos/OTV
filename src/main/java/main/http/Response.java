package main.http;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    List<RequestTag> tagList;
    List<PictureResponse> pictures;
}
