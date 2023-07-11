package jokes;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;


    @Transactional
    public void vote(Long id, String value) {
        Vote vote = this.voteRepository.findByJokeId(id);
        
        if ("up".equals(value)) {
            vote.setUpVotes(vote.getUpVotes() + 1);
        } else if ("down".equals(value)) {
            vote.setDownVotes(vote.getDownVotes() + 1);
        }

    }

    @Transactional
    public Vote getVote(Long id) {
        Vote vote = voteRepository.findByJokeId(id);
        if (vote == null) {
            vote = new Vote(id, 0, 0);
            voteRepository.save(vote);
        }
        return vote;
    }

    
}