package io.tguduru.micronaut.user.datastore;

import io.tguduru.micronaut.user.models.User;

import java.util.*;

/**
 * A Simple in memory datastore for storing @{@link User} data.
 */
public class UserDataStore {
    private final Map<String, User> users;
    private static UserDataStore userDataStore;
    private UserDataStore() {
        // create a default user
        String userId = UUID.randomUUID().toString();
        User user = new User(userId, "admin", "admin@gmail.com");
        this.users = new HashMap<>();
        users.put(userId, user);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
    public User getUser(final String userId) {
        return users.get(userId);
    }
    public void setUser(final String userId, final User user) {
        users.put(userId, user);
    }
    public void removeUser(final String userId) {
        users.remove(userId);
    }
    public boolean containsUser(final String userId) {
        return users.containsKey(userId);
    }
    public static UserDataStore getUserDataStore() {
        if(userDataStore == null) {
            userDataStore = new UserDataStore();
        }
        return userDataStore;
    }
}
