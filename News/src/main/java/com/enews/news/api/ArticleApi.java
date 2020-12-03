package com.enews.news.api;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enews.news.model.Article;
import com.enews.news.service.ArticleServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/articles")
@Slf4j
@RequiredArgsConstructor
public class ArticleApi {
    private final ArticleServiceImpl articleService;
    
    
    @GetMapping("/keywords")
    public ResponseEntity<List<Article>> findArtclesByKeywords(@RequestParam Set<String> keywords) {
        if(keywords.isEmpty()){
        	log.error("Provide any keywords to find articles");
        	return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
        }
    	return new ResponseEntity<>(articleService.findAllArticlesByKeywords(keywords), HttpStatus.OK);
    }
    
    @GetMapping("/authors/{authorName}")
    public ResponseEntity<List<Article>> findArticlesByAuthor(@PathVariable("authorName") String authorName){
    	if(authorName == null) {
    		log.error("Author name is " + authorName );
    		return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    	}
    	List<Article> articles = articleService.findAllArticlesByAuthor(authorName);
    	if(articles.isEmpty()) {
    		log.error("No articles found for given author {}" , authorName );
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>(articles, HttpStatus.OK);	
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> findArticleById(@PathVariable Long id) {
    	if(id == null)
    	   return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	
        Optional<Article> article = articleService.findArticleById(id);
        if (!article.isPresent()) {
            log.error("Article with id {} is not present",id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(article.get(), HttpStatus.OK);
    }
    
    @PostMapping("/save")
    public ResponseEntity<Article> createArticle(@Valid @RequestBody Article article) throws IOException {
    	return new ResponseEntity<>(articleService.saveArticle(article),HttpStatus.OK); 
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @Valid @RequestBody Article article) {
    	Optional<Article> existingArticle = articleService.findArticleById(id);
        if (!existingArticle.isPresent()) {
            log.error("Article with Id {} is not found",id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(articleService.saveArticle(article),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
    	Optional<Article> existingArticle = articleService.findArticleById(id);
        if (!existingArticle.isPresent()) {
        	log.error("Article with Id {} is not not found",id);
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        articleService.deleteArticleById(id);
        return new ResponseEntity<>("Article Deleted with Given Id "+id,HttpStatus.OK);
    }
}
