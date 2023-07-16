package com.example.transferfile.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
@Entity
@Table(name = "file")
@Data
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    private Integer fromUser;
}
