package com.quartzinsight.demo;

import com.quartzinsight.demo.model.Game;
import com.quartzinsight.demo.model.User;
import com.quartzinsight.demo.repository.GameRepository;
import com.quartzinsight.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication  implements CommandLineRunner {

	@Autowired
	GameRepository gameRepository;
	@Autowired
	UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Override
	public void run (String... arg0) throws Exception {
		Game game = new Game();
		game.setTitle("NBA");
		game.setUrl("www.test.com");

		Game game1 = new Game();
		game1.setUrl("fifa.com");
		game1.setTitle("fifa");

		gameRepository.save(game);
		gameRepository.save(game1);

		User user = new User();
		user.setEmail("user@gmail.com");
		user.setUsername("user");
		//user.getGames().add(game);


		User user1 = new User();
		user1.setUsername("TAMS");
		user1.setEmail("Emal");
		//user1.getGames().add(game);

		//user.getFriends().add(user1);

		//user1.getFriends().add(user);

		gameRepository.save(game);
		//userRepository.save(user1);
		//userRepository.save(user);
		//user1.getFriends().add(user);
		//myUserRepository.save(user1);


	}
}
