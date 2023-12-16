package com.elearning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elearning.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
	
}
