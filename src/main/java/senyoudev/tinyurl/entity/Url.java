package senyoudev.tinyurl.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String longUrl;

    @Column(nullable = false, unique = true)
    private String shortUrl;

    @Column(nullable = false)
    private Long hitCount;

    @Column(nullable = false)
    private Date createdAt;

}
