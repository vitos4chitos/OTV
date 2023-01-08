package main.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "picturetags")
public class PictureTags {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "picture_id")
    private Long pictureId;

    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "count")
    private Long count;

}
