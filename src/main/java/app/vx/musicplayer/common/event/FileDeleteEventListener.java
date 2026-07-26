package app.vx.musicplayer.common.event;

import app.vx.musicplayer.storage.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class FileDeleteEventListener {

    private final FileStorageService fileStorageService;

    public FileDeleteEventListener (FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle (FileDeleteEvent event) {
        try {
            fileStorageService.delete(event.path());
            log.info("File deleted: {}", event.path());
        } catch (Exception e) {
            log.error(
                    "Failed to delete file: {}",
                    event.path(),
                    e
            );
        }
    }
}
