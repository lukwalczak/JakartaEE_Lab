package pl.edu.pg.eti.kask.list.authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "List Creator")
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/ListCreatorDS",
        callerQuery = "SELECT password FROM users WHERE login = ?",
        groupsQuery = "SELECT ur.role FROM user_roles ur JOIN users u ON ur.user_id = u.id WHERE u.login = ?",
        hashAlgorithm = Pbkdf2PasswordHash.class,
        priority = 10,
        hashAlgorithmParameters = {
                "Pbkdf2PasswordHash.Iterations=50000",
                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA256",
                "Pbkdf2PasswordHash.SaltSizeBytes=16",
                "Pbkdf2PasswordHash.KeySizeBytes=32"
        }
)
public class AuthenticationConfig {
}
