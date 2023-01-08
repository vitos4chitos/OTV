package main.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "picture")
public class Picture {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "year_of_creation")
    private Long yearOfCreation;

    @Column(name = "style")
    private String style;

    @Column(name = "url")
    private String url;

}
