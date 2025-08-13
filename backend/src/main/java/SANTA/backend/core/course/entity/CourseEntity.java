package SANTA.backend.core.course.entity;

import SANTA.backend.core.cafe.entity.CafeEntity;
import SANTA.backend.core.course.domain.Course;
import SANTA.backend.core.mountain.entity.MountainEntity;
import SANTA.backend.core.restaurant.entity.RestaurantEntity;
import SANTA.backend.core.spot.entity.SpotEntity;
import SANTA.backend.core.stay.entity.StayEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "COURSE")
public class CourseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "MOUNTAIN_ID")
    private MountainEntity mountainEntity;

    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantEntity> restaurants = new ArrayList<>();

    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StayEntity> stays = new ArrayList<>();

    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CafeEntity> cafes = new ArrayList<>();

    @OneToMany(mappedBy = "courseEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpotEntity> spots = new ArrayList<>();

    private Long distance;

    @Builder
    protected CourseEntity(Long id, MountainEntity mountainEntity, Long distance) {
        this.id = id;
        this.mountainEntity = mountainEntity;
        this.distance = distance;
    }

    public static CourseEntity from(Course course, MountainEntity mountainEntity) {
        return CourseEntity.builder()
                .id(course.getId())
                .mountainEntity(mountainEntity)
                .distance(course.getDistance())
                .build();
    }

    public void makeCourse(MountainEntity mountain, Collection<CafeEntity> cafes, Collection<RestaurantEntity> restaurants, Collection<SpotEntity> spots,
                           Collection<StayEntity> stays){
        this.mountainEntity = mountain;
        this.addAllCafes(cafes);
        this.addAllRestaurants(restaurants);
        this.addAllSpots(spots);
        this.addAllStays(stays);
    }

    public void updateDistance(Long distance) {
        this.distance = distance;
    }


    // ===== 연관관계 편의 메서드 =====
    private void addCafe(CafeEntity cafe) {
        cafes.add(cafe);
        cafe.assignToCourse(this);
    }

    private void addRestaurant(RestaurantEntity restaurant) {
        restaurants.add(restaurant);
        restaurant.assignToCourse(this);
    }

    private void addSpot(SpotEntity spot) {
        spots.add(spot);
        spot.assignToCourse(this);
    }

    private void addStay(StayEntity stay) {
        stays.add(stay);
        stay.assignToCourse(this);
    }

    // ===== 편의 메서드(벌크) =====
    private void addAllCafes(Collection<CafeEntity> list) {
        list.forEach(this::addCafe);
    }

    private void addAllRestaurants(Collection<RestaurantEntity> list) {
        list.forEach(this::addRestaurant);
    }

    private void addAllSpots(Collection<SpotEntity> list) {
        list.forEach(this::addSpot);
    }

    private void addAllStays(Collection<StayEntity> list) {
        list.forEach(this::addStay);
    }
}
