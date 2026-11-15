package com.staysync.repository;

import com.staysync.model.MessMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessMenuRepository extends JpaRepository<MessMenu, Long> {
    List<MessMenu> findByDayOfWeek(String dayOfWeek);
    Optional<MessMenu> findByDayOfWeekAndMealType(String dayOfWeek, String mealType);
}
