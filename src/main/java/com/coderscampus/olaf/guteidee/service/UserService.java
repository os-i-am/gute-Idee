package com.coderscampus.olaf.guteidee.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.coderscampus.olaf.guteidee.domain.Authority;
import com.coderscampus.olaf.guteidee.domain.Idea;
import com.coderscampus.olaf.guteidee.domain.Like;
import com.coderscampus.olaf.guteidee.domain.LikesId;
import com.coderscampus.olaf.guteidee.domain.User;
import com.coderscampus.olaf.guteidee.repository.LikeRepository;
import com.coderscampus.olaf.guteidee.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepo;
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	@Autowired
	private LikeRepository likeRepo;
	@Autowired
	private IdeaService ideaService;

	public User registerNewUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		user.getAuthorities().add(new Authority("ROLE_USER", user));
		return userRepo.save(user);
	}

	public Boolean checkIfUserExists(User user) {
		return (userRepo.findByUsername(user.getUsername()) != null);
	}

	public Set<User> getAllUsersWithRolesAndIdeas() {
		return userRepo.findAllUsersWithRolesAndIdeas();
	}

	public User getSingleUserWithRoles(Long userId) {
		return userRepo.findByIdWithRoles(userId);
	}

	public Boolean checkForLike(Idea idea, User user) {
		return idea.getLikes().stream().anyMatch(like -> like.getPrimaryKey().getUser().getId() == user.getId());
	}

	public User likeIdea(Long ideaId, User inputUser, Boolean liked) {
		if (inputUser != null) {
			User user = userRepo.findUserByIdWithIdeasAndLikesAndAuthorities(inputUser.getId());
			Idea idea = ideaService.getIdeaById(ideaId);
			if (liked == false) {
				user.getLikes().add(new Like(new LikesId(user, idea), true));
				return userRepo.save(user);
			} else {
				Like delLike = user.getLikes().stream()
						.filter(like -> like.getPrimaryKey().getIdea().getId() == ideaId
								&& like.getPrimaryKey().getUser().getId() == inputUser.getId())
						.findFirst().orElse(null);
				likeRepo.delete(delLike);
				return null;
			}
		}
		return null;
	}

	public String getNameById(Long userId) {
		return userRepo.findById(userId).orElse(new User()).getName();
	}

	public User editUserDetails(User inputUser) {
		User user = userRepo.findUserByIdWithIdeasAndLikesAndAuthorities(inputUser.getId());
		user.setId(inputUser.getId());
		if (user.getPassword().equals(inputUser.getPassword()))
			user.setPassword(inputUser.getPassword());
		else
			user.setPassword(encoder.encode(inputUser.getPassword()));
		user.setName(inputUser.getName());
		user.setIdeas(inputUser.getIdeas());
		user.setLikes(inputUser.getLikes());
		user.setAuthorities(inputUser.getAuthorities());
		return userRepo.save(user);
	}

	public void deleteUser(User inputUser) {
		System.out.println("delete user");

	}

	public User findById(Long userId) {
		return userRepo.findUserById(userId);
	}

	public Boolean checkForAdmin(User editUser) {
		return editUser.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
	}

}
