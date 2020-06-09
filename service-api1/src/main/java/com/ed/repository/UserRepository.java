package com.ed.repository;

import com.ed.model.CourseEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ElasticsearchRepository<CourseEntity,Integer> {

}
