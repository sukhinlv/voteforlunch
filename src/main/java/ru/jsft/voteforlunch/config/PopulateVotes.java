package ru.jsft.voteforlunch.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import ru.jsft.voteforlunch.model.*;
import ru.jsft.voteforlunch.repository.*;
import ru.jsft.voteforlunch.web.security.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Configuration
public class PopulateVotes {

    @Bean
    @Transactional
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
            mealRepository.saveAll(List.of(tea, bread, soup, pasta, omelet));

            Restaurant cherryRestaurant = new Restaurant("Cherry");
            Restaurant aishaRestaurant = new Restaurant("Aisha");
            restaurantRepository.saveAll(List.of(cherryRestaurant, aishaRestaurant));

            User admin = new User("admin", "admin", "admin@ya.ru", true,
                    LocalDate.of(2022, 10, 15), Collections.singleton(Role.ADMIN));
            User user = new User("user", "user", "user@gmail.com", true,
                    LocalDate.of(2022, 10, 20), Collections.singleton(Role.USER));
            userRepository.saveAll(List.of(admin, user));

            LocalDate nowMinusTwoDays = LocalDate.now().minusDays(2);
            LocalDate nowMinusOneDay = LocalDate.now().minusDays(1);

            Menu menuForCherry1 = new Menu();
            menuForCherry1.setDateOfMenu(nowMinusTwoDays);
            menuForCherry1.setRestaurant(cherryRestaurant);
            menuForCherry1.setMealPrice(Set.of(
                    new MealPrice(tea, 10, menuForCherry1),
                    new MealPrice(bread, 15, menuForCherry1)
            ));
            Menu menuForCherry2 = new Menu();
            menuForCherry2.setDateOfMenu(nowMinusOneDay);
            menuForCherry2.setRestaurant(cherryRestaurant);
            menuForCherry2.setMealPrice(Set.of(
                    new MealPrice(soup, 25, menuForCherry2),
                    new MealPrice(bread, 15, menuForCherry2)
            ));
            Menu menuForAisha1 = new Menu();
            menuForAisha1.setDateOfMenu(nowMinusTwoDays);
            menuForAisha1.setRestaurant(aishaRestaurant);
            menuForAisha1.setMealPrice(Set.of(
                    new MealPrice(tea, 15, menuForAisha1),
                    new MealPrice(pasta, 25, menuForAisha1)
            ));
            Menu menuForAisha2 = new Menu();
            menuForAisha2.setDateOfMenu(nowMinusOneDay);
            menuForAisha2.setRestaurant(aishaRestaurant);
            menuForAisha2.setMealPrice(Set.of(
                    new MealPrice(omelet, 25, menuForAisha2),
                    new MealPrice(tea, 15, menuForAisha2)
            ));
            menuRepository.saveAll(List.of(menuForCherry1, menuForCherry2, menuForAisha1, menuForAisha2));

            voteRepository.saveAll(List.of(
                    new Vote(admin, cherryRestaurant, nowMinusTwoDays, LocalTime.of(9, 30)),
                    new Vote(admin, cherryRestaurant, nowMinusOneDay, LocalTime.of(10, 15)),
                    new Vote(user, cherryRestaurant, nowMinusTwoDays, LocalTime.of(10, 45)),
                    new Vote(user, aishaRestaurant, nowMinusOneDay, LocalTime.of(10, 0))
            ));

            SecurityUtil.authenticatedUser = admin;
        };
    }
}
