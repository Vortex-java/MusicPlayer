package app.vx.musicplayer.storage;

import app.vx.musicplayer.storage.entity.Filetype;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface FileStorageService {

    String save(MultipartFile file, String directory, Filetype filetype, Set<String> allowedTypes);

    Resource load(String path);

    void delete(String path);
}
