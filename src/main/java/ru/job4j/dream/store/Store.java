package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;

public interface Store {
    void savePost(Post post);

    void saveCandidate(Candidate candidate);

    void saveUser(User user);

    Post findPostById(int id);

    Candidate findCandidateById(int id);

    User findUserByEmail(String email);

    Collection<Post> findAllPosts();

    Collection<Candidate> findAllCandidates();

    void removePost(int id);

    void removeCandidate(int id);
}
