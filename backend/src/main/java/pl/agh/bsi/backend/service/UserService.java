package pl.agh.bsi.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.agh.bsi.backend.repository.UserRepository;

import javax.security.auth.login.CredentialException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ConcurrentHashMap<String, String> sessionIdUsername = new ConcurrentHashMap<>();

    private final UserRepository userRepository;

    public void registerUser(String username, String password){
        userRepository.addNewUser(username, password);
    }

    public boolean isUserLogged(String sessionId){
        return sessionIdUsername.containsKey(sessionId);
    }

    public String logUser(String username, String password) throws CredentialException {
        String userPassword = userRepository.getUsersPassword(username);

        if (!password.equals(userPassword)){
            throw new CredentialException();
        }

        String sessionId = UUID.randomUUID().toString();
        sessionIdUsername.put(sessionId, username);
        System.out.println(sessionId);
        return sessionId;
    }

    public String getUsernameForSessionId(String sessionId) {
        String username = sessionIdUsername.get(sessionId);
        // validate null?
        return username;
    }

    public void logOutUser(String sessionId){
        sessionIdUsername.remove(sessionId);
    }

    public String getLoggedUser(String sessionId) {
        if (isUserLogged(sessionId) && sessionIdUsername.containsKey(sessionId) ) {
            return sessionIdUsername.get(sessionId);
        }
        return "";
    }
}
