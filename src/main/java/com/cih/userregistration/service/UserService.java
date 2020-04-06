package com.cih.userregistration.service;

import com.cih.userregistration.controller.UserNotFoundException;
import com.cih.userregistration.entities.User;
import com.cih.userregistration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(User newUser, Long id) {
        return userRepository.findById(id)
                .map(u -> {
                    u.setFirstName(newUser.getFirstName());
                    u.setLastName(newUser.getLastName());
                    u.setAddress(newUser.getAddress());
                    u.setEmail(newUser.getEmail());
                    u.setPhoneNumber(newUser.getPhoneNumber());
                    return userRepository.save(u);
                }).orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });
    }

    public List<User> findByLastName(String lastName) {
            return userRepository.findByLastName(lastName);
    }

    public List<User> findByFilter(User filteredUser, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);
        return userRepository
                .findUserByFirstNameAndLastNameAndAddressAndEmailAndPhoneNumber(filteredUser.getFirstName(),
                        filteredUser.getLastName(),
                        filteredUser.getAddress(),
                        filteredUser.getEmail(),
                        filteredUser.getPhoneNumber(),
                        page).getContent();
    }
}
