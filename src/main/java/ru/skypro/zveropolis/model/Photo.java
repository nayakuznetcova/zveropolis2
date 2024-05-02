package ru.skypro.zveropolis.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fileSize;
    private String mediaType;
    private byte[] data;
    @ManyToOne
    @JoinColumn(name = "report_id")
    private Report report;
    private String path;

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
