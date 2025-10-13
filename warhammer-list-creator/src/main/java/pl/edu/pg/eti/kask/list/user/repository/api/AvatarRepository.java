package pl.edu.pg.eti.kask.list.user.repository.api;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

public interface AvatarRepository {


    Optional<byte[]> getAvatar(UUID uuid) throws IOException;

    void create(UUID userId, InputStream avatarData) throws IOException;

    void upsert(UUID userId, InputStream avatarData) throws IOException;

    void delete(UUID userId) throws IOException;
}
