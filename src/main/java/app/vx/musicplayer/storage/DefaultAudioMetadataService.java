package app.vx.musicplayer.storage;

import app.vx.musicplayer.exception.InvalidFileException;
import lombok.extern.slf4j.Slf4j;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class DefaultAudioMetadataService implements AudioMetadataService {

    @Value("${storage.path}")
    private String storagePath;

    @Override
    public long getDuration(String path) {

        Path filePath = Paths.get(storagePath).resolve(path);

        try {

            AudioFile audioFile = AudioFileIO.read(filePath.toFile());

            return audioFile.getAudioHeader().getTrackLength() * 1000L;
        } catch (IOException | CannotReadException | InvalidAudioFrameException | TagException | ReadOnlyFileException e) {
            throw new InvalidFileException("Invalid audio file");
        }
    }
}
