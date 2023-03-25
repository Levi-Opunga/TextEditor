package dev.levi.data;

import dev.levi.domain.Files;

public interface FileDao {
    void  createFile(Files file);

    Iterable<Files> findAllFiles();


    void updateFile(Files file);



}
