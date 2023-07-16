package com.example.transferfile.repository;

import com.example.transferfile.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, BigInteger> {
}
