package com.cih.userregistration;

import com.cih.userregistration.controller.UserNotFoundException;
import com.cih.userregistration.entities.User;
import com.cih.userregistration.repository.UserRepository;
import com.cih.userregistration.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(singletonList(User.builder().build()));

        List<User> actual = userRepository.findAll();

        assertThat(actual).isEqualTo(singletonList(User.builder().build()));
        verify(userRepository).findAll();
    }

    @Test
    void findById_userExists() {
        User expected = User.builder().id(5L).firstName("test").build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        User actual = userService.findById(5L);

        assertThat(actual).isEqualTo(expected);
        verify(userRepository).findById(5L);
    }

    @Test
    void findById_userDoesNotExist() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findById(2L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Could not find user 2");

        verify(userRepository).findById(2L);
    }

    @Test
    void addUser() {
        User expected = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@user.com")
                .address("1234 Demo Street Park City, Utah 54321")
                .phoneNumber("0001234567")
                .build();

        userService.addUser(expected);
        verify(userRepository).save(expected);
    }

    @Test
    void deleteUserById() {
        userService.deleteUserById(2L);
        verify(userRepository).deleteById(2L);

    }

    @Test
    void updateUser_userDoesNotExist() {
        User newUser = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@user.com")
                .address("1234 Demo Street Park City, Utah 54321")
                .phoneNumber("0001234567")
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        User expected = User.builder()
                .id(3L)
                .firstName("Test")
                .lastName("User")
                .email("test@user.com")
                .address("1234 Demo Street Park City, Utah 54321")
                .phoneNumber("0001234567")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(expected);

        User actual = userService.updateUser(newUser, 3L);

        assertThat(actual).isEqualTo(expected);
        verify(userRepository).findById(3L);
        verify(userRepository).save(expected);
    }

    @Test
    void updateUser_userDoesExist() {
        User newUser = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@user.com")
                .address("1234 Demo Street Park City, Utah 54321")
                .phoneNumber("0001234567")
                .build();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().id(2L).build()));

        User expected = User.builder()
                .id(2L)
                .firstName("Test")
                .lastName("User")
                .email("test@user.com")
                .address("1234 Demo Street Park City, Utah 54321")
                .phoneNumber("0001234567")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(expected);

        User actual = userService.updateUser(newUser, 2L);

        verify(userRepository).findById(2L);
        verify(userRepository).save(expected);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void findByFilter(){
        User user = User.builder().firstName("name").lastName("lastname").email("email").phoneNumber("phone").address("address").build();
        Page<User> userPage = new PageImpl<>(singletonList(user));
        when(userRepository.findUserByFirstNameAndLastNameAndAddressAndEmailAndPhoneNumber(
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                anyString(),
                any(Pageable.class))).thenReturn(userPage);

        List<User> actual = userService.findByFilter(user, 2);

        assertThat(actual).isEqualTo(userPage.getContent());
        verify(userRepository).findUserByFirstNameAndLastNameAndAddressAndEmailAndPhoneNumber("name", "lastname", "address", "email", "phone", PageRequest.of(2, 10));
    }

    @Test
    void findByFilter_nullSearchFields(){
        User user = User.builder().lastName("lastname").build();
        Page<User> userPage = new PageImpl<>(singletonList(user));
        when(userRepository.findUserByFirstNameAndLastNameAndAddressAndEmailAndPhoneNumber(
                eq(null),
                anyString(),
                eq(null),
                eq(null),
                eq(null),
                any(Pageable.class))).thenReturn(userPage);

        List<User> actual = userService.findByFilter(user, 9);

        assertThat(actual).isEqualTo(userPage.getContent());
       verify(userRepository).findUserByFirstNameAndLastNameAndAddressAndEmailAndPhoneNumber(null, "lastname", null, null, null, PageRequest.of(9, 10));
    }
}