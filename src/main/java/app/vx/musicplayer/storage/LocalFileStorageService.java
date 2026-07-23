package app.vx.musicplayer.storage;

import app.vx.musicplayer.exception.InvalidFileException;
import app.vx.musicplayer.storage.entity.Filetype;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

@Service
public class LocalFileStorageService implements FileStorageService {

    @Value("${storage.path}")
    private String storagePath;

    @Override
    public String save(MultipartFile file, String directory, Filetype filetype, Set<String> allowedTypes) {

        if (file.isEmpty()) {
            throw new InvalidFileException("File is empty");
        }

        try {
            switch (filetype) {
                case IMAGE -> validateImage(file, allowedTypes);
                case AUDIO -> validateAudio(file, allowedTypes);
            }

            Path directoryPath = Paths.get(storagePath, directory);

            Files.createDirectories(directoryPath);

            String originalFileName = file.getOriginalFilename();

            if (originalFileName == null
                    || !originalFileName.contains(".")) {
                throw new InvalidFileException("Invalid filename");
            }

            String extension = originalFileName.substring(
                    originalFileName.lastIndexOf(".")
            ).toLowerCase();

            String fileName = UUID.randomUUID() + extension;

            Path path = directoryPath.resolve(fileName);

            file.transferTo(path);

            return directory + "/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Resource load(String path) {
        Path file = Paths.get(storagePath).resolve(path);

        Resource resource = new FileSystemResource(file);

        if (!resource.exists()) {
            throw new InvalidFileException("File not found");
        }

        return resource;
    }

    @Override
    public void delete(String path) {
        Path file = Paths.get(storagePath).resolve(path);

        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new InvalidFileException("File delete error");
        }
    }

    private void validateImage (MultipartFile file, Set<String> allowedTypes) throws IOException {
        String contentType = file.getContentType();

        if (contentType == null || !allowedTypes.contains(contentType)) {
            throw new InvalidFileException("File is not image");
        }

        BufferedImage image = ImageIO.read(file.getInputStream());

        if (image == null) {
            throw new InvalidFileException("Invalid image");
        }

        if (image.getWidth() != image.getHeight()) {
            throw new InvalidFileException("Image must be square");
        }
    }

    private void validateAudio (MultipartFile file, Set<String> allowedTypes) {

    }
}
