package com.nposmak.crudrepo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nposmak.entity.Role;


public interface RoleRepository extends CrudRepository<Role, Integer>{
	Role findByid(int id);

}
