package edu.shu.bowling.repository;

import edu.shu.bowling.model.Frame;
import edu.shu.bowling.model.FrameId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FrameRepository extends JpaRepository<Frame, FrameId> {


}
