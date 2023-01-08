package main.repository;

import main.entity.PictureTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PictureTagsRepository extends JpaRepository<PictureTags, Long> {

    List<PictureTags> getPictureTagsByTagId(Long tagId);
    Optional<PictureTags> getPictureTagsByTagIdAndPictureId(Long tagId, Long pictureId);
}
