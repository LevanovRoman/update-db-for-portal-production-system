package com.myapp.updatedbforportalproductionsystem.updatePersonTable.repository;

import com.myapp.updatedbforportalproductionsystem.updatePersonTable.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {


}
