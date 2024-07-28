package io.tguduru.micronaut.user.services;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.tguduru.micronaut.user.datastore.UserDataStore;
import io.tguduru.micronaut.user.models.User;

import java.util.List;
import java.util.UUID;

/**
 * A simple user controller to demonstrates the usages of HTTP methods using micronaut framework
 */
@Controller(value = "/users")
public class UserResource {
    @Get
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        UserDataStore userDataStore = UserDataStore.getUserDataStore();
        return userDataStore.getUsers();
    }

    @Post
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User addUser(@Body User user) {
        String userId = UUID.randomUUID().toString();
        User newUser = new User(userId, user.getName(), user.getEmail());
        UserDataStore userDataStore = UserDataStore.getUserDataStore();
        userDataStore.setUser(userId, newUser);
        return newUser;
    }

    @Get("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findUserById(@PathVariable String id) {
        UserDataStore userDataStore = UserDataStore.getUserDataStore();
        if (userDataStore.containsUser(id))
            return userDataStore.getUser(id);
        else
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    @Delete("/{id}")
    public void deleteUserById(@PathVariable String id) {
        UserDataStore userDataStore = UserDataStore.getUserDataStore();
        if(userDataStore.containsUser(id))
            userDataStore.removeUser(id);
        else
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    @Put("/{id}")
    public User updateUser(@Body User user, @PathVariable String id) {
        UserDataStore userDataStore = UserDataStore.getUserDataStore();
        User userToUpdate = userDataStore.getUser(id);
        if(userToUpdate != null) {
            userToUpdate = new User(id, user.getName(), user.getEmail());
            userDataStore.setUser(id, userToUpdate);
        }
        else
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "User not found");
        return userDataStore.getUser(id);
    }
    @Patch("/{id}")
    public User patchUser(@Body User user, @PathVariable String id) {
        UserDataStore userDataStore = UserDataStore.getUserDataStore();
        User userToUpdate = userDataStore.getUser(id);
        if(userToUpdate != null) {

            if(user.getEmail() != null)
                userToUpdate.setEmail(user.getEmail());
            if(user.getName() != null)
                userToUpdate.setName(user.getName());

            userDataStore.setUser(id, userToUpdate);
            return userDataStore.getUser(id);
        }
        else
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
}
