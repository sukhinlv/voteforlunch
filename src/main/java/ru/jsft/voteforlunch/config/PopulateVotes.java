package ru.jsft.voteforlunch.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.jsft.voteforlunch.meal.Meal;
import ru.jsft.voteforlunch.meal.MealRepository;
import ru.jsft.voteforlunch.menu.MealPrice;
import ru.jsft.voteforlunch.menu.Menu;
import ru.jsft.voteforlunch.menu.MenuRepository;
import ru.jsft.voteforlunch.restaurant.Restaurant;
import ru.jsft.voteforlunch.restaurant.RestaurantRepository;
import ru.jsft.voteforlunch.user.Role;
import ru.jsft.voteforlunch.user.User;
import ru.jsft.voteforlunch.user.UserRepository;
import ru.jsft.voteforlunch.vote.Vote;
import ru.jsft.voteforlunch.vote.VoteRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

@Configuration
public class PopulateVotes {

    @Bean
    CommandLineRunner commandLineRunner(VoteRepository voteRepository,
                                        UserRepository userRepository,
                                        MenuRepository menuRepository,
                                        RestaurantRepository restaurantRepository,
                                        MealRepository mealRepository
    ) {
        return args -> {
            Meal tea = new Meal("Tea");
            Meal bread = new Meal("Bread");
            Meal soup = new Meal("Soup");
            Meal pasta = new Meal("Pasta");
            Meal omelet = new Meal("omelet");
            mealRepository.save(tea);
            mealRepository.save(bread);
            mealRepository.save(soup);
            mealRepository.save(pasta);
            mealRepository.save(omelet);

            Restaurant cherryRestaurant = new Restaurant("Cherry");
            Restaurant aishaRestaurant = new Restaurant("Aisha");
            restaurantRepository.save(cherryRestaurant);
            restaurantRepository.save(aishaRestaurant);

            User admin = new User("admin", "admin", "admin@ya.ru", true,
                    LocalDate.of(2022, 10, 15), Collections.singleton(Role.ADMIN));
            User user = new User("user", "user", "user@gmail.com", true,
                    LocalDate.of(2022, 10, 20), Collections.singleton(Role.USER));
            userRepository.save(admin);
            userRepository.save(user);

            Menu menuForCherry1 = new Menu();
            menuForCherry1.setDateOfMenu(LocalDate.of(2022, 10, 25));
            menuForCherry1.setRestaurant(cherryRestaurant);
            menuForCherry1.getMealPrice().add(new MealPrice(tea, BigDecimal.valueOf(10)));
            menuForCherry1.getMealPrice().add(new MealPrice(bread, BigDecimal.valueOf(15)));
            Menu menuForCherry2 = new Menu();
            menuForCherry2.setDateOfMenu(LocalDate.of(2022, 10, 26));
            menuForCherry2.setRestaurant(cherryRestaurant);
            menuForCherry2.getMealPrice().add(new MealPrice(soup, BigDecimal.valueOf(25)));
            menuForCherry2.getMealPrice().add(new MealPrice(bread, BigDecimal.valueOf(15)));
            Menu menuForAisha1 = new Menu();
            menuForAisha1.setDateOfMenu(LocalDate.of(2022, 10, 25));
            menuForAisha1.setRestaurant(aishaRestaurant);
            menuForAisha1.getMealPrice().add(new MealPrice(tea, BigDecimal.valueOf(10)));
            menuForAisha1.getMealPrice().add(new MealPrice(pasta, BigDecimal.valueOf(30)));
            Menu menuForAisha2 = new Menu();
            menuForAisha2.setDateOfMenu(LocalDate.of(2022, 10, 26));
            menuForAisha2.setRestaurant(aishaRestaurant);
            menuForAisha2.getMealPrice().add(new MealPrice(tea, BigDecimal.valueOf(10)));
            menuForAisha2.getMealPrice().add(new MealPrice(omelet, BigDecimal.valueOf(20)));
            menuRepository.save(menuForCherry1);
            menuRepository.save(menuForCherry2);
            menuRepository.save(menuForAisha1);
            menuRepository.save(menuForAisha2);

            voteRepository.save(new Vote(admin, cherryRestaurant, LocalDate.of(2022, 10, 25),
                    LocalTime.of(9, 30)));
            voteRepository.save(new Vote(admin, cherryRestaurant, LocalDate.of(2022, 10, 26),
                    LocalTime.of(10, 15)));
            voteRepository.save(new Vote(user, cherryRestaurant, LocalDate.of(2022, 10, 25),
                    LocalTime.of(10, 45)));
            voteRepository.save(new Vote(user, aishaRestaurant, LocalDate.of(2022, 10, 26),
                    LocalTime.of(10, 0)));
        };
    }
}
