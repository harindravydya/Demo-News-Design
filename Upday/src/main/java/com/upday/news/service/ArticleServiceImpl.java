package com.upday.news.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.upday.news.model.Article;
import com.upday.news.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;

	@Override
	public List<Article> findAllArticlesByAuthor(String authorName) {
		return articleRepository.findAllArticlesByAuthorName(authorName);
	}

	@Override
	public List<Article> findAllArticlesForAGivenPeriod() {
		return null;
	}

	@Override
	public List<Article> findAllArticlesByKeywords(Set<String> keywords) {
		return articleRepository.findAllArticlesByKeywords(keywords);
	}

	@Override
	public Optional<Article> findArticleById(Long id) {
		return articleRepository.findById(id);
	}

	@Override
	public Article saveArticle(Article article) {
		return articleRepository.save(article);
	}

	@Override
	public void deleteArticleById(Long id) {
	   articleRepository.deleteById(id);
	}
}
