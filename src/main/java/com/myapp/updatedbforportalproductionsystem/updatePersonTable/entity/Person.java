package com.myapp.updatedbforportalproductionsystem.updatePersonTable.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import lombok.*;

@Entity
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tabNumber;

    private String fullName;

    private String appointName;

    public Person() {
    }

    public Person(String tabNumber, Long id, String fullName, String appointName) {
        this.tabNumber = tabNumber;
        this.id = id;
        this.fullName = fullName;
        this.appointName = appointName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTabNumber(String tabNumber) {
        this.tabNumber = tabNumber;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAppointName(String appointName) {
        this.appointName = appointName;
    }
}
