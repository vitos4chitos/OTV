package main.service;

import main.entity.PictureTags;
import main.entity.Tag;
import main.http.RequestPictures;
import main.http.RequestTag;
import main.repository.AuthorRepository;
import main.repository.PictureRepository;
import main.repository.PictureTagsRepository;
import main.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class TagsService {

    private final AuthorRepository authorRepository;
    private final PictureRepository pictureRepository;
    private final TagRepository tagRepository;
    private final PictureTagsRepository pictureTagsRepository;

    public TagsService(AuthorRepository authorRepository, PictureRepository pictureRepository, TagRepository tagRepository, PictureTagsRepository pictureTagsRepository){
        this.authorRepository = authorRepository;
        this.pictureRepository = pictureRepository;
        this.tagRepository = tagRepository;
        this.pictureTagsRepository = pictureTagsRepository;
    }

    public boolean saveTags(RequestPictures requestPictures,Long pictureId){
        List<RequestTag> requestTags = requestPictures.getTagList();
        for(RequestTag requestTag: requestTags){
            Long tagId = getTagUUIDByName(requestTag.getName());
            if(tagId == -1){
                makeNewTag(requestTag.getName(), pictureId);
            }
            else{
                savePicturesTagsByTagUUIDAndPictureUUID(tagId, pictureId);
            }
        }
        return true;
    }

    private void savePicturesTagsByTagUUIDAndPictureUUID(Long id1, Long id2){
        Optional<PictureTags> optionalPictureTags = pictureTagsRepository.getPictureTagsByTagIdAndPictureId(id1, id2);
        if(optionalPictureTags.isPresent()){
            PictureTags pictureTags = optionalPictureTags.get();
            pictureTags.setCount(pictureTags.getCount() + 1);
            pictureTagsRepository.save(pictureTags);
        }
        else{
            PictureTags pictureTags = new PictureTags();
            pictureTags.setTagId(id1);
            pictureTags.setPictureId(id2);
            pictureTags.setCount(1L);
            pictureTagsRepository.save(pictureTags);
        }
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

    private void makeNewTag(String name, Long pictureId){
        Tag tag = new Tag();
        tag.setName(name);
        tagRepository.save(tag);
        PictureTags pictureTags = new PictureTags();
        pictureTags.setTagId(tag.getId());
        pictureTags.setPictureId(pictureId);
        pictureTags.setCount(1L);
        pictureTagsRepository.save(pictureTags);
    }
}
