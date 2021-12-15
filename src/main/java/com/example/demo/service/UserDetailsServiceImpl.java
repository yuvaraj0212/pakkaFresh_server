package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.UserModel;
import com.example.demo.repo.UserRepo;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepo repo;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = repo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not exist with name : " + username);
		}
		return UserDetailsImpl.build(user);
	}
}
