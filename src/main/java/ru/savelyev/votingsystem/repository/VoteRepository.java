package ru.savelyev.votingsystem.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.savelyev.votingsystem.model.Vote;

@Transactional
public interface VoteRepository extends BaseRepository<Vote> {
}
