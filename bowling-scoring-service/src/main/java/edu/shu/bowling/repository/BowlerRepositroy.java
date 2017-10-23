package edu.shu.bowling.repository;

import edu.shu.bowling.model.Bowler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BowlerRepositroy extends JpaRepository<Bowler, String> {


}
