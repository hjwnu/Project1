package com.mainproject.be28.domain.shopping.review.service.Layer2;

import com.mainproject.be28.domain.shopping.review.entity.UserVote;
import com.mainproject.be28.domain.shopping.review.repository.UserVoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service @Slf4j
public class UserVoteService {
    private final UserVoteRepository repository;

    public UserVoteService(UserVoteRepository repository) {
        this.repository = repository;
    }

    public void save(Long memberId, Long reviewId, UserVote.VoteType voteType) {
        UserVote userVote = new UserVote(memberId, reviewId, voteType.equals(UserVote.VoteType.LIKE));
        repository.save(userVote);
    }

    public boolean verifyDuplicateVote(Long memberId, Long reviewId, UserVote.VoteType voteType) {
        List<UserVote> votes = repository.findByMemberIdAndReviewId(memberId, reviewId);
        return votes.stream()
                .anyMatch(vote -> isVoteTypeDuplicated(voteType, vote));
    }

    public void delete(Long memberId, Long reviewId, UserVote.VoteType voteType) {
        List<UserVote> userVotes = repository.findByMemberIdAndReviewId(memberId, reviewId);
        userVotes.stream()
                .filter(vote -> voteMatchesType(vote, voteType))
                .findFirst()
                .ifPresent(repository::delete);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanupExpiredData() {
        try {
            LocalDateTime expirationDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT); // 자정 시간 지정
            repository.deleteExpiredData(expirationDateTime);
        } catch (Exception e) {
        log.error("스케줄러 실행 에러 발생", e);
        }
    }
    private boolean isVoteTypeDuplicated(UserVote.VoteType voteType, UserVote vote) {
        switch (voteType) {
            case LIKE: return vote.getUserLike() != null && vote.getUserLike();
            case UNLIKE: return vote.getUnlike() != null && vote.getUnlike();
            default: return false;
        }
    }
    private boolean voteMatchesType(UserVote vote, UserVote.VoteType voteType) {
        return (voteType == UserVote.VoteType.LIKE && Boolean.TRUE.equals(vote.getUserLike()))
                || (voteType == UserVote.VoteType.UNLIKE && Boolean.TRUE.equals(vote.getUnlike()));
    }


}
