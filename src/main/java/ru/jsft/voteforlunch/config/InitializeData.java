package ru.jsft.voteforlunch.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.model.*;
import ru.jsft.voteforlunch.repository.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Configuration
public class InitializeData {

    @Bean
    @Transactional
    CommandLineRunner fillAllTables(VoteRepository voteRepository,
                                    UserRepository userRepository,
                                    MenuRepository menuRepository,
                                    RestaurantRepository restaurantRepository,
                                    MealRepository mealRepository
    ) {
        return args -> {
            Meal tea = new Meal("Tea");
            Meal burger = new Meal("Burger");
            Meal soup = new Meal("Soup");
            Meal pasta = new Meal("Pasta");
            Meal sandwich = new Meal("Sandwich");
            mealRepository.saveAll(List.of(tea, burger, soup, pasta, sandwich));

            Restaurant cherryRestaurant = new Restaurant("Cherry");
            Restaurant aishaRestaurant = new Restaurant("Aisha");
            restaurantRepository.saveAll(List.of(cherryRestaurant, aishaRestaurant));

            // pre-defined users for testing purposes
            User admin = new User("admin", "admin", "admin@ya.ru", true,
                    LocalDate.of(2022, 10, 15), Collections.singleton(Role.ADMIN));
            User user = new User("user", "user", "user@gmail.com", true,
                    LocalDate.of(2022, 10, 20), Collections.singleton(Role.USER));
            // collection of users to create large amount of votes for run-time testing using curl
            List<User> userList = new ArrayList<>(2000);
            userList.add(admin);
            userList.add(user);
            for (int i = 0; i < 1998; i++) {
                userList.add(new User("user" + i, "user" + i, "user" + i + "@gmail.com", true,
                        LocalDate.of(2022, 10, 15), Collections.singleton(Role.USER)));
            }
            userRepository.saveAll(userList);

            // create two menus
            LocalDate nowMinusTwoDays = LocalDate.now().minusDays(2);
            LocalDate nowMinusOneDay = LocalDate.now().minusDays(1);

            Menu menuForCherry1 = new Menu();
            menuForCherry1.setDateOfMenu(nowMinusTwoDays);
            menuForCherry1.setRestaurant(cherryRestaurant);
            menuForCherry1.setMealPrice(new TreeSet<>(Set.of(
                    new MealPrice(tea, 10, menuForCherry1),
                    new MealPrice(burger, 15, menuForCherry1)
            )));
            Menu menuForCherry2 = new Menu();
            menuForCherry2.setDateOfMenu(nowMinusOneDay);
            menuForCherry2.setRestaurant(cherryRestaurant);
            menuForCherry2.setMealPrice(new TreeSet<>(Set.of(
                    new MealPrice(soup, 25, menuForCherry2),
                    new MealPrice(burger, 15, menuForCherry2)
            )));
            Menu menuForAisha1 = new Menu();
            menuForAisha1.setDateOfMenu(nowMinusTwoDays);
            menuForAisha1.setRestaurant(aishaRestaurant);
            menuForAisha1.setMealPrice(new TreeSet<>(Set.of(
                    new MealPrice(tea, 15, menuForAisha1),
                    new MealPrice(pasta, 25, menuForAisha1)
            )));
            Menu menuForAisha2 = new Menu();
            menuForAisha2.setDateOfMenu(nowMinusOneDay);
            menuForAisha2.setRestaurant(aishaRestaurant);
            menuForAisha2.setMealPrice(new TreeSet<>(Set.of(
                    new MealPrice(sandwich, 25, menuForAisha2),
                    new MealPrice(tea, 15, menuForAisha2)
            )));
            menuRepository.saveAll(List.of(menuForCherry1, menuForCherry2, menuForAisha1, menuForAisha2));

            List<Vote> votes = new ArrayList<>();
            // this votes will be used for testing
            votes.add(new Vote(admin, aishaRestaurant, nowMinusTwoDays, LocalTime.of(9, 30)));
            votes.add(new Vote(user, aishaRestaurant, nowMinusTwoDays, LocalTime.of(10, 30)));
            votes.add(new Vote(admin, cherryRestaurant, nowMinusOneDay, LocalTime.of(9, 30)));
            votes.add(new Vote(user, aishaRestaurant, nowMinusOneDay, LocalTime.of(10, 30)));
            // and this votes can be used for testing in run-time using curl
            Random rnd = new Random();
            for (User usr : userList.stream().skip(2L).toList()) {
                votes.add(new Vote(
                        usr,
                        rnd.nextBoolean() ? aishaRestaurant : cherryRestaurant,
                        LocalDate.now(),
                        LocalTime.of(rnd.nextInt(6, 11), rnd.nextInt(0, 60))));
            }
            voteRepository.saveAll(votes);

//            SecurityUtil.authenticatedUser = admin;
        };
    }
}
