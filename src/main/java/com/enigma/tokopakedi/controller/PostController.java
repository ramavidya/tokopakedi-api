package com.enigma.tokopakedi.controller;

import lombok.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class PostController {
    private final RestTemplate restTemplate;


//    @GetMapping
//    public ResponseEntity<?> getAllPost(){
//        String url = "https://jsonplaceholder.typicode.com/posts";
//        ResponseEntity<List<ArticleResponse>> forEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<ArticleResponse>>() {
//
//                }
//        );
//        return forEntity;
//   }


    @PostMapping
    public ResponseEntity<?> createArticle(@RequestBody Article article){
        HttpEntity<Article> request = new HttpEntity<>(article);
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles";
        return restTemplate.exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<>() {});
    }
    @PutMapping
    public ResponseEntity<?> updateArticle(@RequestBody Article article){
        HttpEntity<Article> request = new HttpEntity<>(article);
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles";
        return restTemplate.exchange(url, HttpMethod.PUT, request, new ParameterizedTypeReference<>() {});
    }
    @DeleteMapping(path = "{id}")
    public ResponseEntity<?> deleteArticle(@PathVariable String id){
        String url = "https://17c4-2001-448a-2020-d35a-bcf8-3e81-f5c3-d41c.ngrok-free.app/articles/" + id;

        return restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Article{
        private String id;
        private String title;
        private String body;
        private String author;
    }
}
