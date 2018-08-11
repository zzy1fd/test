package cn.itcast.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.domain.Article;

public interface ArticleService {
	
	public void save(Article article);

	public void delete(Article article);

	public Article findOne(Integer i);

	public Page<Article> findAll(Pageable pageable);

	public Page<Article> findByTitle(String title, Pageable pageable);

}
