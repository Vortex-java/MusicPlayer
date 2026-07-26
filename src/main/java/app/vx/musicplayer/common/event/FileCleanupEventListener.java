package app.vx.musicplayer.common.event;

import app.vx.musicplayer.storage.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileCleanupEventListener {

    private final FileStorageService fileStorageService;

    public FileCleanupEventListener (FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @EventListener
    public void handle (FileCleanupEvent event) {
        try {
            fileStorageService.delete(event.path());
            log.info("File cleaned: {}", event.path());
        } catch (Exception e) {
            log.error(
                    "Cannot cleanup file: {}",
                    event.path(),
                    e
            );
        }
    }

}
