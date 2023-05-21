package com.example.rigel_v1.controllers;

import com.example.rigel_v1.domain.Department;
import com.example.rigel_v1.domain.Question;
import com.example.rigel_v1.domain.Users;
import com.example.rigel_v1.repositories.QuestionRepository;
import jakarta.persistence.NamedStoredProcedureQueries;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/questions")
@RestController
public class QuestionController {
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @PostMapping
    public void addQuestion(@NonNull @RequestBody QuestionRequest questionRequest){
        Question question = new Question(questionRequest.getQuestion(), questionRequest.getAnswer(), questionRequest.getScore());
        this.questionRepository.save(question);
        System.out.println(question);
    }

    @GetMapping("/{id}")
    public Optional<Question> getQuestion(@PathVariable Long id) {
        return questionRepository.findById(id);
    }

    @GetMapping
    public Iterable<Question> getAllQuestions() {
        Iterable<Question> list = questionRepository.findAll();
        System.out.println(list);
        return list;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id,@RequestBody QuestionRequest questionRequest) {
        Optional<Question> optional = questionRepository.findById(id);
        if(optional == null){
            return ResponseEntity.notFound().build();
        }
        Question q = optional.get();
        q.setQuestion(questionRequest.getQuestion());
        q.setAnswer(questionRequest.getAnswer());
        q.setScore(questionRequest.getScore());
        Question updatedQuestion = questionRepository.save(q);
        return ResponseEntity.ok(updatedQuestion);
    }
}

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
class QuestionRequest{
    private String question;
    private String answer;
    private int score;
}