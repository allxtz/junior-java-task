package com.example.juniorjavatask.controller;

import com.example.juniorjavatask.ORM.BlogEntity;
import com.example.juniorjavatask.ORM.UserEntity;
import com.example.juniorjavatask.services.DataService;
import com.example.juniorjavatask.services.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alex
 * @version 1.0
 * @implNote -> injection of @DataService then RequestMapping (Get,Post,Delete) for EndPoints;
 * Validated -> for request param validation
 * @since 2022-06-21
 */
@Validated
@RestController
@RequestMapping("/")
public class BaseController {

    @Autowired
    private DataService dataService;

    /**
     * @return index/home content for page
     * @implNote no restrictions for this page -> check SecurityConfig
     * @see com.example.juniorjavatask.security.SecurityConfig
     */
    @GetMapping("/")
    public String intro() {
        return "index";
    }

    /**
     * @return list of BlogEntities (toString)
     * @implNote for <b>domain/blog</b>
     */

    @GetMapping("/blog")
    public String getBlog() {
        List<BlogEntity> blogEntities = dataService.getBlog();
        ArrayList<String> list = new ArrayList<>();
        blogEntities.forEach(blogEntity -> list.add("id:" + blogEntity.getId()
                + "---text:" + blogEntity.getText()
                + "---userId:" + blogEntity.getUserid().toString()));
        return list.toString();
    }

    /**
     * @param text       - blog text
     * @param actionType - new, login, new_entry;
     * @param username   - username
     * @param password   - password
     * @param role       - role
     * @param ronly      - readonly
     * @return
     * @implNote switch-case for an EndPoint with 3 types of actions: new,login and new_entry;
     * because login/authentication was implemented using Spring Security, the login action type EndPoint it
     * only verifies if the current user principal has same username/password as the Params from the EndPoint
     */
    @PostMapping("/blog")
    public String newEntry(@RequestParam(name = "text", required = false) String text,
                           @RequestParam(name = "action") String actionType,
                           @RequestParam(name = "user", required = false) String username,
                           @RequestParam(name = "password", required = false) String password,
                           @RequestParam(name = "permission", required = false) String role,
                           @RequestParam(name = "readonly", required = false) String ronly) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

        String message = "";

        switch (actionType) {
            case "new":
                if (userDetails.getReadonly().equals("false") && text != null) {
                    createBlogEntry(text, userDetails);
                    message = "entry was saved";
                } else {
                    message = "read only permissions: " + userDetails.getReadonly();

                }
                break;
            case "login":
                if (Optional.ofNullable(userDetails).isEmpty())
                    message = "no user is auth";
                else
                    message = Optional.of(userDetails.getUsername())
                            .filter(value -> value.equals(username))
                            .map(usernameFound -> "user is already auth" + username)
                            .orElse("credentials mismatch");
                break;
            case "new_user":
                if (username != null && password != null && role != null && ronly != null) {
                    UserEntity newUser = new UserEntity(username, password, role, ronly);
                    dataService.saveUser(newUser);
                    message = "user saved";
                }
                break;
            default:
                message = "bad req";
        }
        return message;
    }

    /**
     *
     * @param action
     * @param blog_id
     * @return confirmation message
     */
    @DeleteMapping("/blog")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String delete(@RequestParam(name = "action") String action, @RequestParam(name = "id") String blog_id) {

        String message = "";
        try {
            message = "deleted --- " + blog_id;
            dataService.deleteEntry(Integer.parseInt(blog_id));
        } catch (Exception e) {
            message = "error: " + e.getMessage();
        }
        return message;
    }

    /**
     * @param text
     * @param userDetails
     * @implNote ->create user entity for blog entity FK (on userID) either get the info from db using the username or
     * get all the info   from userDetails (current logged user)
     * ->create blog entity
     * ->save entity to repo
     */
    private void createBlogEntry(String text, MyUserDetails userDetails) {
        UserEntity user = dataService.findUserEntitiesByUsername(userDetails.getUsername());
        BlogEntity blogEntity = new BlogEntity(text);
        blogEntity.setUserid(user);
        dataService.newEntry(blogEntity);
    }

}
