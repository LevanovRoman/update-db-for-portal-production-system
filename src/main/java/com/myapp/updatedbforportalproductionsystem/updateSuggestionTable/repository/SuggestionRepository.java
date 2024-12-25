package com.myapp.updatedbforportalproductionsystem.updateSuggestionTable.repository;

import com.myapp.updatedbforportalproductionsystem.updateSuggestionTable.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
