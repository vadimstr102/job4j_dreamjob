package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Optional;

public class PsqlMain {
    public static void main(String[] args) {
        Store store = PsqlStore.instOf();
        store.savePost(new Post(0, "Java Junior Job"));
        store.savePost(new Post(0, "Java Middle Job"));
        store.saveCandidate(new Candidate(0, "Elon Musk"));
        store.saveCandidate(new Candidate(0, "Ivan Petrov"));
        System.out.println("-----------------------------------------------");
        for (Post post : store.findAllPosts()) {
            System.out.println(post.getId() + " " + post.getName());
        }
        for (Candidate candidate : store.findAllCandidates()) {
            System.out.println(candidate.getId() + " " + candidate.getName());
        }
        System.out.println("-----------------------------------------------");
        Optional<Post> post = Optional.ofNullable(store.findPostById(1));
        post.ifPresentOrElse(
                val -> System.out.println(val.getId() + " " + val.getName()),
                () -> System.out.println("Post not found")
        );
        Optional<Candidate> candidate = Optional.ofNullable(store.findCandidateById(1));
        candidate.ifPresentOrElse(
                val -> System.out.println(val.getId() + " " + val.getName()),
                () -> System.out.println("Candidate not found")
        );
        System.out.println("-----------------------------------------------");
    }
}
