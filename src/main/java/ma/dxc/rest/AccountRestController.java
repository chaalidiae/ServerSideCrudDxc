package ma.dxc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.dxc.orchestration.RegisterFormOrchestration;
import ma.dxc.security.AuthenticationRequest;
import ma.dxc.security.AuthenticationResponse;
import ma.dxc.security.JwtUtil;
import ma.dxc.dto.AppUserDTO;
import ma.dxc.dto.RegisterFormDTO;
import ma.dxc.service.MyUserDetailsService;

@RestController
@CrossOrigin("*")
public class AccountRestController {
	
	
	RegisterFormOrchestration registerFormOrchestration;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	
	@PostMapping("/register")
	public AppUserDTO register(@RequestBody RegisterFormDTO userFormDto) {
		return registerFormOrchestration.register(userFormDto);
	}
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	/**
	 * cette fonction nous retourne le AppUser qui est connecté au moment 
	 * @param id
	 * @return
	 */
	@PreAuthorize("hasAuthority('READPROFILE')")
	@GetMapping(value="/profile")
	public Authentication getAppUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				return authentication;
	}

}
