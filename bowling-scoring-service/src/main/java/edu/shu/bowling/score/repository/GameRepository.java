package edu.shu.bowling.score.repository;

import edu.shu.bowling.score.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, String> {

}
