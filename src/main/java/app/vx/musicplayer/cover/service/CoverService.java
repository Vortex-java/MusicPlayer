package app.vx.musicplayer.cover.service;

import app.vx.musicplayer.cover.dto.CreateCoverRequest;
import app.vx.musicplayer.cover.entity.Cover;
import app.vx.musicplayer.cover.repository.CoverRepository;
import app.vx.musicplayer.exception.CoverNotFoundException;
import app.vx.musicplayer.storage.FileStorageService;
import app.vx.musicplayer.storage.entity.Filetype;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class CoverService {

    private final CoverRepository coverRepository;
    private final FileStorageService fileStorageService;

    private static final String COVER_DIRECTORY = "covers";
    private static final Filetype FILE_TYPE = Filetype.IMAGE;

    private final Set<String> allowedTypes = Set.of(
            "image/png",
            "image/jpeg",
            "image/webp"
    );

    public CoverService (CoverRepository coverRepository, FileStorageService fileStorageService) {
        this.coverRepository = coverRepository;
        this.fileStorageService = fileStorageService;
    }

    public void create (MultipartFile file, CreateCoverRequest request) {
        String path = fileStorageService.save(file, COVER_DIRECTORY, FILE_TYPE, allowedTypes);

        Cover cover = new Cover(request.name(), path);
        coverRepository.save(cover);
    }

    public Resource getFile (Long id) {
        Cover cover = coverRepository.findById(id).orElseThrow(() -> new CoverNotFoundException("Cover not found"));

        return fileStorageService.load(cover.getPath());
    }

    @Transactional
    public void delete (Long id) {
        Cover cover = coverRepository.findById(id).orElseThrow(() -> new CoverNotFoundException("Cover not found"));

        fileStorageService.delete(cover.getPath());
        coverRepository.delete(cover);
    }
}
