package com.example.pamiw.config;

import com.example.pamiw.model.Director;
import com.example.pamiw.model.Movie;
import com.example.pamiw.repo.DirectorRepo;
import com.example.pamiw.repo.MovieRepo;
import com.example.pamiw.repo.UserRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;
import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class FakerConfig {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner(MovieRepo movieRepo, DirectorRepo directorRepo) {
        return args -> {
            Random random = new Random(987654321);
            Faker faker = new Faker(Locale.ENGLISH, random);
            for(int i=0; i<20; i++) {

                Director director = generateFakeDirector(faker);
                directorRepo.save(director);
                Movie fakeMovie = generateFakeMovie(faker, director);
                movieRepo.save(fakeMovie);
            }
        };
    }

    private Director generateFakeDirector(Faker faker) {
        Director director = new Director();
        director.setName(faker.name().firstName());
        director.setSurname(faker.name().lastName());

        return director;
    }
    private Movie generateFakeMovie( Faker faker, Director director ) {
        Movie movie = new Movie();
        movie.setTitle(faker.book().title());
        movie.setDirector(director);
        movie.setYearOfRelease(faker.number().numberBetween(1970, 2023));
        movie.setPosterUrl(faker.internet().image());

        return movie;
    }

}
