package main.service;

import main.entity.Author;
import main.entity.Picture;
import main.entity.PictureTags;
import main.entity.Tag;
import main.helper.Finder;
import main.helper.MaxCountComparator;
import main.http.PictureResponse;
import main.http.RequestPictures;
import main.http.RequestTag;
import main.http.Response;
import main.repository.AuthorRepository;
import main.repository.PictureRepository;
import main.repository.PictureTagsRepository;
import main.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PictureService {

    private final AuthorRepository authorRepository;
    private final PictureRepository pictureRepository;
    private final TagRepository tagRepository;
    private final PictureTagsRepository pictureTagsRepository;

    public PictureService(AuthorRepository authorRepository, PictureRepository pictureRepository, TagRepository tagRepository, PictureTagsRepository pictureTagsRepository){
        this.authorRepository = authorRepository;
        this.pictureRepository = pictureRepository;
        this.tagRepository = tagRepository;
        this.pictureTagsRepository = pictureTagsRepository;
    }

    public Response getPictures(RequestPictures requestPictures){
        List<RequestTag> requestTags = requestPictures.getTagList();
        List<PictureResponse> pictures = new ArrayList<>();
        for(RequestTag requestTag: requestTags){
            Long id = getTagUUIDByName(requestTag.getName());
            if(id != -1){
                setResponse(pictures, id);
            }
        }
        Response response = new Response();
        Collections.sort(pictures, new MaxCountComparator());
        response.setTagList(requestTags);
        response.setPictures(pictures);
        return response;
    }

    private Long getTagUUIDByName(String name){
        Optional<Tag> optionalTag = tagRepository.getTagByName(name);
        if (optionalTag.isPresent()){
            return optionalTag.get().getId();
        }
        else{
            return (long) -1;
        }

    }
    private List<PictureTags> getPicturesTagsByTagUUID(Long id){
        return pictureTagsRepository.getPictureTagsByTagId(id);
    }

    private void setResponse(List<PictureResponse> pictures, Long TagId){
        List<PictureTags> pictureTags = getPicturesTagsByTagUUID(TagId);
        for(PictureTags pictureTag: pictureTags){
            Long pictureID = pictureTag.getPictureId();
            int pictureIndex = Finder.getPictureIndex(pictures, pictureID);
            if(pictureIndex == -1){
                addPicture(pictures, pictureID, pictureTag.getCount());
            }
            else{
                setPictureCount(pictures, pictureIndex, pictureTag.getCount());
            }
        }
    }

    private void addPicture(List<PictureResponse> pictures, Long pictureID, Long count){
        Picture picture = pictureRepository.getPictureById(pictureID);
        Author author = authorRepository.getAuthorById(picture.getAuthorId());
        PictureResponse pictureResponse = new PictureResponse();
        pictureResponse.setPicture(picture);
        pictureResponse.setAuthor(author);
        pictureResponse.setCount(count);
        pictures.add(pictureResponse);
    }

    private void setPictureCount(List<PictureResponse> pictures, int pictureIndex, Long count){
        Long countNow = pictures.get(pictureIndex).getCount() + count;
        pictures.get(pictureIndex).setCount(countNow);
    }
}
