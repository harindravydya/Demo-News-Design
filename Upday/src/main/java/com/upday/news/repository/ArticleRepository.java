package com.upday.news.repository;

import com.upday.news.model.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Query("select distinct article from Article article left join fetch article.keywords left join fetch article.authors where article.keywords in :keywords")
    List<Article> findAllArticlesByKeywords(@Param("keywords") List<String> keywords);
    
    @Query("select distinct article from Article article left join fetch article.keywords left join fetch article.authors where article.authors in :authorName")
    List<Article> findAllArticlesByAuthorName(@Param("authorName") String authorName);
}
