package com.cih.userregistration;

import com.cih.userregistration.controller.UserController;
import com.cih.userregistration.entities.User;
import com.cih.userregistration.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void findAll() {
        when(userService.findAll()).thenReturn(singletonList(User.builder().build()));

        List<User> actual = userController.findAll();

        assertThat(actual).isEqualTo(singletonList(User.builder().build()));
        verify(userService).findAll();
    }

    @Test
    void createUser() {
        userController.createUser(User.builder().firstName("Test").build());
        verify(userService).addUser(User.builder().firstName("Test").build());
    }

    @Test
    void findById() {
        when(userService.findById(anyLong())).thenReturn(User.builder().id(3L).build());

        User actual = userController.findById(3L);

        assertThat(actual).isEqualTo(User.builder().id(3L).build());
        verify(userService).findById(3L);
    }

    @Test
    void updateUserById() {
        User newUser = User.builder().firstName("User").build();
        User expected = User.builder().id(6L).firstName("User").build();
        when(userService.updateUser(any(User.class), anyLong()))
                .thenReturn(expected);

        User actual = userController.updateUserById(newUser, 6L);

        assertThat(actual).isEqualTo(expected);
        verify(userService).updateUser(newUser, 6L);
    }

    @Test
    void deleteUserById() {
        userController.deleteUserById(3L);
        verify(userService).deleteUserById(3L);
    }

    @Test
    void findByPageNumber(){
        List<User> pageResult = Collections.nCopies(10, User.builder()
                .firstName("first")
                .lastName("last")
                .address("address")
                .phoneNumber("phone")
                .email("email")
                .build());
        when(userService.findByFilter(any(User.class), anyInt())).thenReturn(pageResult);

        List<User> actual = userController.findByFilter("first",
                "last",
                "address",
                "phone",
                "email",
                4);

        assertThat(actual).isEqualTo(pageResult);
        verify(userService).findByFilter(User.builder()
                .firstName("first")
                .lastName("last")
                .address("address")
                .phoneNumber("phone")
                .email("email")
                .build(),4);
    }

    @Test
    void findByPageNumber_nullSearchFields(){
        List<User> pageResult = Collections.nCopies(10, User.builder().firstName("first").build());
        when(userService.findByFilter(any(User.class), anyInt())).thenReturn(pageResult);

        List<User> actual = userController.findByFilter("first",
                null,
                null,
                null,
                null,
                4);

        assertThat(actual).isEqualTo(pageResult);
        verify(userService).findByFilter(User.builder()
                .firstName("first")
                .build(),4);
    }
}