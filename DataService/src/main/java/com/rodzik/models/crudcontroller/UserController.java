package com.rodzik.models.crudcontroller;

import java.util.List;
import java.util.Optional;

import com.rodzik.models.domain.User;
import com.rodzik.models.domain.UserRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @GetMapping
    public List<User> getUsers()
    {
        Iterable<User> estateIterable = userRepository.findAll();
        List<User> estates = Lists.newArrayList(estateIterable);
        return estates;
    }
    @GetMapping("/getbyid/{id}")
    public User findEstateById(@PathVariable long id)
    {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            User w=user.get();
            return w;
        }
        return null;
    }
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public User addUser(@RequestBody User user)
    {
        User savedUser = userRepository.save(user);
        return savedUser;
    }
    @DeleteMapping("/delete/{id}")
    public Boolean deleteUserById(@PathVariable long id)
    {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }





}