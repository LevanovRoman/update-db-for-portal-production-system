package com.myapp.updatedbforportalproductionsystem.updateSuggestionTable.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "suggestion")
public class Suggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(nullable = true)
    private String numberAndDateRegistration;

    private String department;

    private String author;

    private String registered;

    private String agreed;

    private String implemented;

    private String dateImplementation;

    public Suggestion() {
    }

    public Suggestion(Long id, String title, String numberAndDateRegistration, String department,
                      String author, String registered, String agreed, String implemented,
                      String dateImplementation) {
        this.id = id;
        this.title = title;
        this.numberAndDateRegistration = numberAndDateRegistration;
        this.department = department;
        this.author = author;
        this.registered = registered;
        this.agreed = agreed;
        this.implemented = implemented;
        this.dateImplementation = dateImplementation;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumberAndDateRegistration(String numberAndDateRegistration) {
        this.numberAndDateRegistration = numberAndDateRegistration;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public void setAgreed(String agreed) {
        this.agreed = agreed;
    }

    public void setImplemented(String implemented) {
        this.implemented = implemented;
    }

    public void setDateImplementation(String dateImplementation) {
        this.dateImplementation = dateImplementation;
    }
}
