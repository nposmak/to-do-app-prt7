package com.nposmak.crudrepo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nposmak.entity.Task;


public interface TaskRepository extends CrudRepository<Task, Long>{
	
}
