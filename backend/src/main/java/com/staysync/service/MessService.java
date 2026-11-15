package com.staysync.service;

import com.staysync.model.MessMenu;
import com.staysync.repository.MessMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessService {

    @Autowired
    private MessMenuRepository messMenuRepository;

    public List<MessMenu> getAllMenus() {
        return messMenuRepository.findAll();
    }

    public List<MessMenu> getMenuByDay(String dayOfWeek) {
        return messMenuRepository.findByDayOfWeek(dayOfWeek.toUpperCase());
    }

    public MessMenu updateMeal(MessMenu menuData) {
        return messMenuRepository.findByDayOfWeekAndMealType(menuData.getDayOfWeek(), menuData.getMealType())
                .map(existing -> {
                    existing.setTiming(menuData.getTiming());
                    existing.setMenuItems(menuData.getMenuItems());
                    existing.setDietaryInfo(menuData.getDietaryInfo());
                    existing.setCaloriesEst(menuData.getCaloriesEst());
                    return messMenuRepository.save(existing);
                })
                .orElseGet(() -> messMenuRepository.save(menuData));
    }
}
