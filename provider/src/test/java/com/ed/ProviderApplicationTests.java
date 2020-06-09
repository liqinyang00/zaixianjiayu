package com.ed;

import com.ed.model.CourseEntity;
import com.ed.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProviderApplicationTests {


    @Autowired
    private UserRepository userRepository;


    @Test
    public void add(){
        CourseEntity course = new CourseEntity();
        course.setCourseid(1);
        course.setCoursetitle("1");
        course.setAutherimg("1");
        course.setAuthername("1");
        course.setCoursecontext("1");
        course.setCoursenumber(1);
        course.setCourseprice(11.00);
        course.setCourseimg("1");
        course.setCoursetype(1);
        course.setName("aa");
        userRepository.save(course);
    }


    @Test
    void contextLoads() {
    }

}
