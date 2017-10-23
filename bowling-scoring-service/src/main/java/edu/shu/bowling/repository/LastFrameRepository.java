package edu.shu.bowling.repository;

import edu.shu.bowling.model.FrameId;
import edu.shu.bowling.model.LastFrame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LastFrameRepository extends JpaRepository<LastFrame, FrameId> {


}
