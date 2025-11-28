package ru.practicum.streak.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.activity.model.ActivityCategory;
import ru.practicum.streak.model.HabitStreak;
import ru.practicum.streak.repository.StreakRepository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor(onConstructor_ = @Autowired)
public class StreakServiceImpl implements StreakService {
    private final StreakRepository streakRepository;

    @Override
    public void updateHabitStreak(Long habitId, LocalDate activityDate) {
        log.info("Начинаем обновлении данных серии по активности id = {}", habitId);
        HabitStreak streak = streakRepository.findById(habitId)
                .orElseGet(() -> new HabitStreak(habitId));

        if (streak.getLastActivityDate() == null) {
            streak.setCurrentStreak(1);
            log.info("Новая серия, присвоено значение 1");
        } else if (streak.getLastActivityDate().plusDays(1).equals(activityDate)) {
            streak.setCurrentStreak(streak.getCurrentStreak() + 1);
            log.info("Продолжаем серию, присвоено значение: {}", streak.getCurrentStreak());
        } else {
            streak.setCurrentStreak(1);
            log.info("Прерванная серия, присвоено значение 1");
        }

        if (streak.getCurrentStreak() > streak.getLongestStreak()) {
            streak.setLongestStreak(streak.getCurrentStreak());
            log.info("Обновлено значение самой длинной серии на {}", streak.getLongestStreak());
        }

        streak.setLastActivityDate(activityDate);
        log.info("Обновлено значение даты последней активности на {}", activityDate);
        streak.setUpdatedAt(OffsetDateTime.now());
        log.info("Обновлена дата последнего обновления серии на {}", streak.getUpdatedAt());

        streakRepository.save(streak);
        log.info("Серия для активности id = {} обновлена: {}", habitId, streak);
    }

    @Override
    public List<HabitStreak> findBrokenStreak() {
        log.info("Начинаем получение прерванных серий, учитываются серии, прерванные 7 дней назад");
        LocalDate cutoffDate = LocalDate.now().minusDays(7);
        return streakRepository.findByLastActivityDateBefore(cutoffDate);
    }

    @Override
    public String getRecommendation() {
        log.info("Начинаем получение рекомендации по прерванной активности с самой длинной серией");
        List<HabitStreak> find = findBrokenStreak();
        if (find == null || find.isEmpty()) {
            log.info("Рекомендаций по прерванным активностям не найдено");
            return "Вы не забываете про все свои активности. Так держать!";
        }
            List<HabitStreak> streaks = find.stream()
                    .sorted(Comparator.comparing(HabitStreak::getLongestStreak).reversed())
                    .toList();
            String habitRec = streaks.getFirst().getHabit().getTitle();
            log.info("Прерванная активность с самой длинной серией: {}", habitRec);
            return "Заметили, что у вас давно не было записей по активности "
                    + habitRec
                    + "\n возможно пора возобновить серию";
    }

    @Override
    public String getRecommendationByActivityCategory() {
        log.info("Начинаем получение рекомендации для ActivityCategory");
        List<HabitStreak> find = findBrokenStreak();
        if (find == null || find.isEmpty()) {
            log.info("Рекомендаций по прерванным активностям по ActivityCategory не найдено");
            return "Вы не забываете про все свои активности. Так держать!";
        }
        List<HabitStreak> streaks = find.stream()
                .sorted(Comparator.comparing(HabitStreak::getLongestStreak).reversed())
                .toList();
        ActivityCategory activityType = streaks.getFirst().getHabit().getActivityType().getActivityCategory();
        log.info("Рекомендация по самой длинной прерванной серии для ActivityCategory получена: {}", activityType);
        if (activityType == null) {
            log.info("Определена активность для рекомендации без категории");
            return "Давно не было записей для активности "
                    + streaks.getFirst().getHabit().getTitle()
                    + "\n возможно пора возобновить серию";
        }
        return checkCategory(activityType);
    }

    private String checkCategory(ActivityCategory category) {
        return switch (category) {
            case PHYSICAL -> "Похоже вы давно не занимались физической активностью, самое время заняться спортом!";
            case HOBBY -> "Заметили, что вы давно не уделяли время хобби, самое время";
            case REST -> "Вы давно не отдыхали, пора уделить время себе";
            case WORK -> "Кажется пора поработать";
            case SOCIAL -> "Пора пообщаться с друзьми или семьей!";
            case SELF_CARE -> "Самое время позаботиться о себе";
            case HOUSEHOLD -> "Отдых - это хорошо, но пора заняться бытовыми делами";
        };
    }
}
