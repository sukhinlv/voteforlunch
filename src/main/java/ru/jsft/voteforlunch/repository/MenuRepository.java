package ru.jsft.voteforlunch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.jsft.voteforlunch.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m " +
            "   join fetch m.restaurant left join fetch m.mealPrice" +
            "   where m.id = :id")
    Menu findMenuByIdWithMealPrices(@Param("id") long id);
}
