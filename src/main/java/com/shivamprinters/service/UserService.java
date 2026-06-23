package com.shivamprinters.service;

import com.shivamprinters.dto.request.ProfileUpdateRequest;
import com.shivamprinters.dto.request.RegisterRequest;
import com.shivamprinters.dto.response.UserResponse;
import com.shivamprinters.entity.Role;
import com.shivamprinters.entity.User;
import com.shivamprinters.entity.enums.RoleName;
import com.shivamprinters.exception.BusinessException;
import com.shivamprinters.exception.ResourceNotFoundException;
import com.shivamprinters.mapper.EntityMapper;
import com.shivamprinters.repository.RoleRepository;
import com.shivamprinters.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityMapper entityMapper;
    private final EmailService emailService;

    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Email already registered");
        }
        Role customerRole = roleRepository.findByName(RoleName.CUSTOMER)
                .orElseThrow(() -> new ResourceNotFoundException("Customer role not found"));

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .roles(Set.of(customerRole))
                .build();

        User saved = userRepository.save(user);
        emailService.sendWelcomeEmail(saved.getEmail(), saved.getFirstName());
        return entityMapper.toUserResponse(saved);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserResponse getUserResponse(String email) {
        return entityMapper.toUserResponse(findByEmail(email));
    }

    @Transactional
    public UserResponse updateProfile(String email, ProfileUpdateRequest request) {
        User user = findByEmail(email);
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAddress() != null) user.setAddress(request.getAddress());
        if (request.getCity() != null) user.setCity(request.getCity());
        if (request.getState() != null) user.setState(request.getState());
        return entityMapper.toUserResponse(userRepository.save(user));
    }

    public Page<UserResponse> searchUsers(String query, Pageable pageable) {
        return userRepository.search(query, pageable).map(entityMapper::toUserResponse);
    }

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(entityMapper::toUserResponse);
    }
}
