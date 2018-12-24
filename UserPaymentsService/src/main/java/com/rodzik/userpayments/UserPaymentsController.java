package com.rodzik.userpayments;

import com.rodzik.models.domain.Estate;
import com.rodzik.models.domain.EstateRepository;
import com.rodzik.models.domain.User;
import com.rodzik.models.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController("Payments")
public class UserPaymentsController {
    private final UserRepository userRepository;
    @Autowired
    public UserPaymentsController(UserRepository userRepository) {
            this.userRepository = userRepository;
        }
    @PostMapping("{id}/addFunds/{funds}")
    public User addFunds(@PathVariable long id, @PathVariable BigDecimal funds) {
        Optional<User> u = userRepository.findById(id);
        if (u.isPresent()) {
            User user = u.get();
            BigDecimal newFunds = user.getFunds().add(funds);
            user.setFunds(newFunds);
            userRepository.save(user);
            return user;
        }
        return null;
    }
    @PostMapping("{id}/setFunds/{funds}")
    public User setFunds(@PathVariable long id, @PathVariable BigDecimal funds) {
            Optional<User> u=userRepository.findById(id);
            if (u.isPresent()) {
                User user=u.get();
                user.setFunds(funds);
                userRepository.save(user);
                return user;
            }
            return null;

        }
}
