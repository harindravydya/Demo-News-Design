package com.enews.news.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.enews.news.model.Article;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Query("select distinct article from Article article left join fetch article.authors left join fetch article.keywords k where k in (:keywords)")
    List<Article> findAllArticlesByKeywords(@Param("keywords") Set<String> keywords);
    
    @Query("select distinct article from Article article left join fetch article.keywords left join fetch article.authors a where a = :authorName ")
    List<Article> findAllArticlesByAuthorName(@Param("authorName") String authorName);

    @Query("select a from Article a where a.publishDate between ?1 and ?2 ")
    List<Article> findAllArtilcesBetweenDates(Date from, Date to);
}
