package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;
    @Autowired
    private CrudUserRepository crudRepositoryUser;

    private static final Sort SORT_DATE_TIME = Sort.by(Sort.Direction.DESC, "dateTime");

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(crudRepositoryUser.getOne(userId));
        if (meal.isNew() || get(meal.getId(), userId) != null) {
            return crudRepository.save(meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserId(userId, SORT_DATE_TIME);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.findBetweenDate(startDateTime, endDateTime, userId);
    }
}
