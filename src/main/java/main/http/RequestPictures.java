package main.http;

import lombok.Data;

import java.util.List;

@Data
public class RequestPictures {
    List<RequestTag> tagList;
}
