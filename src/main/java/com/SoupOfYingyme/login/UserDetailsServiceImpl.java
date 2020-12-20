package com.SoupOfYingyme.login;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{
	private AccountRepository repository;
	
	@Autowired
	public void UserDetailServiceImpl(AccountRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(StringUtils.isEmpty(username)) {
			throw new UsernameNotFoundException("username is empty");
		}
		
		Account account = repository.findByUsername(username);
		
		if(account == null) {
			throw new UsernameNotFoundException("Not found username :" + username);
		}
		
		Collection<GrantedAuthority>authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(account.getRole()));
		
		User user = new User(account.getUsername(),account.getPassword(),authorities);
		return user;
	}
}
