package com.quartzinsight.demo;

import com.quartzinsight.demo.converter.Converter;
import com.quartzinsight.demo.model.Game;
import com.quartzinsight.demo.model.User;
import com.quartzinsight.demo.repository.GameRepository;
import com.quartzinsight.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DemoApplication implements CommandLineRunner {


	@Autowired
	GameRepository gameRepository;
	@Autowired
	UserRepository userRepository;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Bean
	public Converter converter(){
		return new Converter();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run (String... arg0) throws Exception {
		Game game = new Game().builder()
				.title("NBA")
				.url("www.nba.com")
				.available(1)
				.build();


		Game game1 = new Game().builder()
				.title("NBA")
				.url("www.fifa.com")
				.available(1)
				.build();


		gameRepository.save(game);
		gameRepository.save(game1);


		User user = new User().builder()
				.email("user@gmail.com")
				.username("user")
				.build();

		User user1 = new User().builder()
				.username("TAMS")
				.email("tams.gmail.com")
				.build();



		gameRepository.save(game);
		userRepository.save(user1);
		userRepository.save(user);
		//user1.getFriends().add(user);
		//myUserRepository.save(user1);





	}

}