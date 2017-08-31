package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;

@RestController
@RequestMapping(ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {
    static final String REST_URL = "/rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(AuthorizedUser.getID());
    }

    @DeleteMapping
    public void delete() {
        super.delete(AuthorizedUser.getID());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        super.update(user, AuthorizedUser.getID());
    }

//    https://stackoverflow.com/questions/3616359/who-sets-response-content-type-in-spring-mvc-responsebody
    @GetMapping(value = "/text", produces = "application/json; charset=utf-8")
    public String testUTF() {
        return "Русский текст";
    }
}