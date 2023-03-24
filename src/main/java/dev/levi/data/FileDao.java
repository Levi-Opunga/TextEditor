package dev.levi.data;

import dev.levi.domain.Files;

import java.util.List;

public interface FileDao {
    void  createFile(Files file);

    Iterable<Files> findAllFiles();




}
