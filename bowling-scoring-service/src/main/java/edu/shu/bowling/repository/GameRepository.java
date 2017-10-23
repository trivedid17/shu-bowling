package edu.shu.bowling.repository;

import edu.shu.bowling.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {


}
