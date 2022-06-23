package com.example.juniorjavatask.controller;

import com.example.juniorjavatask.ORM.BlogEntity;
import com.example.juniorjavatask.ORM.UserEntity;
import com.example.juniorjavatask.services.DataService;
import com.example.juniorjavatask.services.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class BaseController {

    @Autowired
    private DataService dataService;

    @GetMapping("/")
    public String intro() {
        return "index";
    }

    @GetMapping("/blog")
    public String getBlog() {
        List<BlogEntity> blogEntities = dataService.getBlog();
        ArrayList<String> list = new ArrayList<>();
        blogEntities.stream().forEach(blogEntitiesList -> list.add("id:" + blogEntitiesList.getId()
                + "---text:" + blogEntitiesList.getText()
                + "---userId:" + blogEntitiesList.getUserid().toString()));
        return list.toString();
    }

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
                    UserEntity user = dataService.findUserEntitiesByUsername(userDetails.getUsername());
                    BlogEntity blogEntity = new BlogEntity(text);
                    blogEntity.setUserid(user);
                    dataService.newEntry(blogEntity);
                    message = "entry was saved";
                } else {
                    message = "read only permissions true " + userDetails.getReadonly();

                }
                break;
            case "login":
                if (Optional.ofNullable(userDetails).isEmpty())
                    message = "no user is auth";
                else {
                    if (userDetails.getUsername().equals(username))
                        message = "user already auth";
                    else message = "-----";
                }
                break;
            case "new_user":
                if (username != null && password != null && role != null && ronly != null) {
                    UserEntity newUser = new UserEntity(username, password, role, ronly);
                    dataService.saveUser(newUser);
                    message = "user saved";
                }
                break;
        }
        return message;
    }

    @DeleteMapping("/blog")
    public String delete(@RequestParam (name = "action") String action,@RequestParam(name = "id")String blog_id) {

        String message="";

        dataService.deleteEntry(blog_id.);
        return "delete";
    }


}
