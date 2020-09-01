package pl.ligatenisa.tenis.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.ligatenisa.tenis.dao.UserRepo;
import pl.ligatenisa.tenis.entity.User;

import java.util.Optional;

@Service
public class UserManager {
    private UserRepo userRepo;

    public UserManager(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public Optional<User> findById(Long id){
        return userRepo.findById(id);
    }

    public Iterable<User> findAll(){
        return userRepo.findAll();
    }

    public User save(User user){
        return userRepo.save(user);
    }

    public void deleteById(Long id){
        userRepo.deleteById(id);
    }


}
