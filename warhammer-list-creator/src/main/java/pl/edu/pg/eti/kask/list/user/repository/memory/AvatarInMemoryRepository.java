package pl.edu.pg.eti.kask.list.user.repository.memory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import pl.edu.pg.eti.kask.list.user.repository.api.AvatarRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AvatarInMemoryRepository implements AvatarRepository {

    private final Path dir;

    @Inject
    public AvatarInMemoryRepository(@ConfigProperty(name = "avatar.dir") String dirString) {
        this.dir = Paths.get(dirString);
    }

    private Path getAvatarPath(UUID id) {
        return dir.resolve(id.toString() + ".png");
    }

    @Override
    public Optional<byte[]> getAvatar(UUID uuid) throws IOException {
        Path avatarPath = getAvatarPath(uuid);
        if (Files.exists(avatarPath)) {
            return Optional.of(Files.readAllBytes(avatarPath));
        }
        return Optional.empty();
    }

    @Override
    public void create(UUID userId, InputStream avatarData) throws IOException {
        Files.createDirectories(dir);
        Path avatarPath = getAvatarPath(userId);
        if (Files.exists(avatarPath)) {
            throw new IOException("Avatar already exists for user: " + userId);
        }
        Files.copy(avatarData, avatarPath);
    }

    @Override
    public void upsert(UUID userId, InputStream avatarData) throws IOException {
        Files.createDirectories(dir);
        Path avatarPath = getAvatarPath(userId);
        if(Files.exists(avatarPath)){
            Files.delete(avatarPath);
        }
        Files.copy(avatarData, avatarPath);
    }

    @Override
    public void delete(UUID userId) throws IOException {
        Path avatarPath = getAvatarPath(userId);
        if(!Files.exists(avatarPath)){
            throw new IOException("Avatar does not exist for user: " + userId);
        }
        Files.delete(avatarPath);
    }
}
