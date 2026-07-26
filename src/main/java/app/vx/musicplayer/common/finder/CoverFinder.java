package app.vx.musicplayer.common.finder;

import app.vx.musicplayer.cover.entity.Cover;
import app.vx.musicplayer.cover.repository.CoverRepository;
import app.vx.musicplayer.exception.CoverNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CoverFinder {

    private final CoverRepository coverRepository;

    public CoverFinder (CoverRepository coverRepository) {
        this.coverRepository = coverRepository;
    }

    public Cover findByIdOrElseNull(Long id) {
        if (id == null) {
            return null;
        }

        return coverRepository.findById(id)
                .orElseThrow(() -> new CoverNotFoundException("Cover not found"));
    }

    public Cover findByIdOrElseThrow (Long id) {
        return coverRepository.findById(id).orElseThrow(() -> new CoverNotFoundException("Cover not found"));
    }
}
