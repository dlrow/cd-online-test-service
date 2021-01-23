package com.cd.onlinetest.security.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cd.onlinetest.enums.ERole;
import com.cd.onlinetest.mongoDomain.User;
import com.cd.onlinetest.payload.request.LoginRequest;
import com.cd.onlinetest.payload.request.SignupRequest;
import com.cd.onlinetest.payload.response.JwtResponse;
import com.cd.onlinetest.payload.response.MessageResponse;
import com.cd.onlinetest.repository.UserRepository;
import com.cd.onlinetest.security.jwt.JwtUtils;

@Service
public class AuthService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	public JwtResponse authenticateUser(LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
	}

	public MessageResponse registerUser(SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new MessageResponse(HttpStatus.BAD_REQUEST, "Error: Username is already taken!");
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new MessageResponse(HttpStatus.BAD_REQUEST, "Error: Email is already in use!");
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		user.setRoles(ERole.getERoles(signUpRequest.getRoles()));
		userRepository.save(user);

		return new MessageResponse(HttpStatus.ACCEPTED, "User registered successfully!");
	}

}
