package com.staysync.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mess_menus", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"dayOfWeek", "mealType"})
})
public class MessMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dayOfWeek; // MONDAY, TUESDAY, etc.

    @Column(nullable = false)
    private String mealType; // BREAKFAST, LUNCH, SNACKS, DINNER

    @Column(nullable = false)
    private String timing;

    @Column(nullable = false, length = 1000)
    private String menuItems;

    private String dietaryInfo;
    private Integer caloriesEst;
    private LocalDateTime createdAt = LocalDateTime.now();

    public MessMenu() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }

    public String getTiming() { return timing; }
    public void setTiming(String timing) { this.timing = timing; }

    public String getMenuItems() { return menuItems; }
    public void setMenuItems(String menuItems) { this.menuItems = menuItems; }

    public String getDietaryInfo() { return dietaryInfo; }
    public void setDietaryInfo(String dietaryInfo) { this.dietaryInfo = dietaryInfo; }

    public Integer getCaloriesEst() { return caloriesEst; }
    public void setCaloriesEst(Integer caloriesEst) { this.caloriesEst = caloriesEst; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
