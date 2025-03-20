package com.greatnex.semicolon_task.application.ports.input;

import com.greatnex.semicolon_task.domain.models.Course;
import com.greatnex.semicolon_task.domain.models.PlatformUser;
import org.springframework.data.domain.Page;

import java.io.IOException;

public interface CourseUseCase {
    Course registerCourse(PlatformUser identity, Course course) throws IOException;
    Course viewCourseDetails(String courseId) throws IOException;
    void updateCourse(String courseId, Course course) throws IOException;
    Page<Course> viewAllCourse(int page, int size) ;

}
