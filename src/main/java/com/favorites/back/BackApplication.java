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
import com.favorites.back.entities.viewer.Viewer;
import com.favorites.back.entities.viewer.ViewerRepository;

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

/*
    @Bean
    public CommandLineRunner demoData(ViewerRepository vRepository, RegistryRepository rRepository,
            AssessmentRepository aRepository) {
        return (args) -> {
            // save a few viewers
            vRepository.save(new Viewer("George", "george@london.exp", "London", null));
            vRepository.save(new Viewer("Georg", "georg@berlin.exp", "Berlin", null));
            vRepository.save(new Viewer("Georges", "georges@paris.exp", "Paris", null));
            vRepository.save(new Viewer("Xurxo", "xurxo@lugo.exp", "Lugo", null));
            vRepository.save(new Viewer("Gino", "gino@roma.exp", "Roma", null));

            vRepository.save(new Viewer("Joana", "joana@barcelona.exp", "Barcelona", null));
            vRepository.save(new Viewer("John", "john@london.exp", "London", null));
            vRepository.save(new Viewer("Johanes", "johanes@born.exp", "Born", null));
            vRepository.save(new Viewer("Johan", "johan@paris.exp", "Paris", null));
            vRepository.save(new Viewer("Juan", "juan@zaragoza.exp", "Zaragoza", null));
            vRepository.save(new Viewer("Juana", "juanan@valencia.exp", "Valencia", null));
            vRepository.save(new Viewer("Xoana", "xoana@vigo.exp", "Vigo", null));

            // fetch all viewers
            log.info("Viewers found with findAll():");
            log.info("-------------------------------");
            vRepository.findAll().forEach(viewer -> {
                log.info(viewer.toString());
            });
            log.info("");


           rRepository.save(new Registry("The Hobbit", "book", "J. R. R. Tolkien", "1937"));
           rRepository.save(new Registry("Il Nome della Rosa", "book", "Umberto Eco", "1980"));
           rRepository.save(new Registry("A Brief History of Time: From the Big Bang to Black Holes", "book",
                   "Stephen Hawking", "1988"));
           rRepository.save(new Registry("Dune", "book", "Frank Herbert", "1965"));
           rRepository.save(new Registry("The Pillars of the Earth", "book", "Ken Follett", "1989"));
           rRepository.save(new Registry("The Hunger Games", "book", "Suzanne Collins", "2008"));
           rRepository.save(new Registry("1984", "book", "George Orwell", "1949"));
           rRepository.save(new Registry("Män som hatar kvinnor", "book", "Stieg Larsson", "2005"));
           rRepository.save(new Registry("A Tale of Two Cities", "book", "Charles Dickens", "1859"));
           rRepository.save(new Registry("Harry Potter and the Deathly Hallows", "book", "J. K. Rowling", "2007"));

           rRepository.save(new Registry("The Hobbit", "film", "Peter Jackson", "2012"));
           rRepository.save(new Registry("Raiders of the Lost Ark", "film", "Steven Spealberg", "1981"));
           rRepository.save(new Registry("Fight Club", "film", "David Fincher", "1999"));
           rRepository.save(new Registry("Blade Runner", "film", "Ridley Scott", "1982"));

           rRepository.save(
                   new Registry("The Wire", "serie", "David Simon, Ed Burns, George Pelecanos, David Mills", "2002"));
           rRepository.save(new Registry("Game of Thrones", "serie", "varios", "2011"));
           rRepository.save(new Registry("Chernobyl", "serie", "", ""));
           rRepository.save(new Registry("Breaking Bad", "serie", "Vince Gilligan", "2008"));

           rRepository.save(new Registry("The Wall", "album", "Pink Floyd", "1979"));
           rRepository.save(new Registry("Kid A", "album", "Radio Head", "2000"));
           rRepository.save(new Registry("Electric Ladyland", "album", "The Jimmi Hendrix Experience", "1968"));
           rRepository.save(new Registry("Kind of Blue", "album", "Miles Davis", "1956"));
           rRepository.save(new Registry("London Calling", "album", "The Clash", "1980"));

           rRepository.save(new Registry("Maus", "comic", "Art Spielgelman", "1977"));
           rRepository.save(new Registry("L\"Incal", "comic", "Moebius, Jodorowsky", "1981"));
           rRepository.save(new Registry("V for Vendetta", "comic", "Alan Moore, David Lloyd", "1982"));
           rRepository.save(new Registry("Akira", "comic", "Katsuhiro Otomo", "1982"));
           rRepository.save(new Registry("Le Garaje Hermetique", "comic", "Moebius", "1976"));
           rRepository.save(new Registry("Calvin and Hobbes", "comic", "Bill Waterson", "1985"));
           rRepository.save(
                   new Registry("A Contract with God and Other Tenement Stories", "comic", "Will Eisner", "1978"));
           rRepository.save(new Registry("Batman: The Dark Knight Returns", "comic", "Frank Miller", "1986"));

            List<Registry> registries = rRepository.findAll();

            Random random = new Random();

            vRepository.findAll().forEach(viewer -> {
                log.info(viewer.toString());

                registries.forEach(
                    registrie -> {
                            if (random.nextInt(9) > 4) {
                                Assessment newAssessment =  new Assessment(viewer, registrie, random.nextInt(9), random.nextInt(9),
                                "at " + LocalDate.now().toString());
                                aRepository.save(newAssessment);
                                log.info("---------------NEW ASSESSM-----------------");
                                        log.info(newAssessment.toString());
                            }
                        });

            });


            // fetch an individual viewer by ID
            Optional<Assessment> assessment = aRepository.findById(1L);
            log.info("Assessment found with findById(1L):");
            log.info("--------------------------------");
            log.info(assessment.toString());
            log.info("");

        };
    }
*/
}
