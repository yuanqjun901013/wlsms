package com.web.wlsms.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//@Document(collection = "demo")
public class DemoEntity implements Serializable {
    @Id
    private Long id;

    private String title;

    private String description;

    private String by;

    private String url;
}
