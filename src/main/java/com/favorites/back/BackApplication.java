package com.favorites.back;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.favorites.back.entities.assesment.Assessment;
import com.favorites.back.entities.assesment.AssessmentRepository;
import com.favorites.back.entities.registry.Registry;
import com.favorites.back.entities.registry.RegistryController;
import com.favorites.back.entities.registry.RegistryRepository;
import com.favorites.back.entities.user.User;
import com.favorites.back.entities.user.UserRepository;

@SpringBootApplication
public class BackApplication implements WebMvcConfigurer {
    private static final Logger log = LoggerFactory.getLogger(BackApplication.class);

    @SuppressWarnings("null")
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

    public static final String backEndUrl = "/favorites";

    @Bean
    public CommandLineRunner demoUsers(UserRepository repository) {
        return (args) -> {
            // save a few users
            repository.save(new User("George", "george@London.exp", "London", null));
            repository.save(new User("Georg", "georg@berlin.exp", "berlin", null));
            repository.save(new User("Georges", "georges@paris.exp", "paris", null));
            repository.save(new User("Xurxo", "xurxo@Lugo.exp", "Lugo", null));
            repository.save(new User("Gino", "gino@roma.exp", "roma", null));

            // fetch all users
            log.info("Users found with findAll():");
            log.info("-------------------------------");
            repository.findAll().forEach(user -> {
                log.info(user.toString());
            });
            log.info("");

            // fetch an individual user by ID
            Optional<User> user = repository.findById(1L);
            log.info("User found with findById(1L):");
            log.info("--------------------------------");
            log.info(user.toString());
            log.info("");

        };
    }

    @Bean
    public CommandLineRunner demoRegistries(RegistryRepository repository) {

        return (args) -> {

            repository.save(new Registry("The Hobbit", "book", "J. R. R. Tolkien", "1937"));
            repository.save(new Registry("Il Nome della Rosa", "book", "Umberto Eco", "1980"));
            repository.save(new Registry("A Brief History of Time: From the Big Bang to Black Holes", "book",
                    "Stephen Hawking", "1988"));
            repository.save(new Registry("Dune", "book", "Frank Herbert", "1965"));
            repository.save(new Registry("The Pillars of the Earth", "book", "Ken Follett", "1989"));
            repository.save(new Registry("The Hunger Games", "book", "Suzanne Collins", "2008"));
            repository.save(new Registry("1984", "book", "George Orwell", "1949"));
            repository.save(new Registry("Män som hatar kvinnor", "book", "Stieg Larsson", "2005"));
            repository.save(new Registry("A Tale of Two Cities", "book", "Charles Dickens", "1859"));
            repository.save(new Registry("Harry Potter and the Deathly Hallows", "book", "J. K. Rowling", "2007"));

            repository.save(new Registry("The Hobbit", "film", "Peter Jackson", "2012"));
            repository.save(new Registry("Raiders of the Lost Ark", "film", "Steven Spealberg", "1981"));
            repository.save(new Registry("Fight Club", "film", "David Fincher", "1999"));
            repository.save(new Registry("Blade Runner", "film", "Ridley Scott", "1982"));

            repository.save(
                    new Registry("The Wire", "serie", "David Simon, Ed Burns, George Pelecanos, David Mills", "2002"));
            repository.save(new Registry("Game of Thrones", "serie", "varios", "2011"));
            repository.save(new Registry("Chernobyl", "serie", "", ""));
            repository.save(new Registry("Breaking Bad", "serie", "Vince Gilligan", "2008"));

            repository.save(new Registry("The Wall", "album", "Pink Floyd", "1979"));
            repository.save(new Registry("Kid A", "album", "Radio Head", "2000"));
            repository.save(new Registry("Electric Ladyland", "album", "The Jimmi Hendrix Experience", "1968"));
            repository.save(new Registry("Kind of Blue", "album", "Miles Davis", "1956"));
            repository.save(new Registry("London Calling", "album", "The Clash", "1980"));

            repository.save(new Registry("Maus", "comic", "Art Spielgelman", "1977"));
            repository.save(new Registry("L\"Incal", "comic", "Moebius, Jodorowsky", "1981"));
            repository.save(new Registry("V for Vendetta", "comic", "Alan Moore, David Lloyd", "1982"));
            repository.save(new Registry("Akira", "comic", "Katsuhiro Otomo", "1982"));
            repository.save(new Registry("Le Garaje Hermetique", "comic", "Moebius", "1976"));
            repository.save(new Registry("Calvin and Hobbes", "comic", "Bill Waterson", "1985"));
            repository.save(
                    new Registry("A Contract with God and Other Tenement Stories", "comic", "Will Eisner", "1978"));
            repository.save(new Registry("Batman: The Dark Knight Returns", "comic", "Frank Miller", "1986"));

        };
    }

    @Bean
    public CommandLineRunner demoAssessments(UserRepository uRepository, RegistryRepository rRepository,
            AssessmentRepository aRepository) {
        return (args) -> {

            List<Registry> registries = rRepository.findAll();

            Random random = new Random();

            uRepository.findAll().forEach(user -> {
                log.info(user.toString());

                registries.forEach(
                    registrie -> {
                            if (random.nextInt(9) > 4) {
                                aRepository.save(
                                        new Assessment(user, registrie, random.nextInt(9), random.nextInt(9),
                                        "at " + LocalDate.now().toString())
                                );
                            }
                        });

            });


            // fetch an individual user by ID
            Optional<Assessment> assessment = aRepository.findById(1L);
            log.info("Assessment found with findById(1L):");
            log.info("--------------------------------");
            log.info(assessment.toString());
            log.info("");

        };
    }

}
