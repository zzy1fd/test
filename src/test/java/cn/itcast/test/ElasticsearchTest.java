package cn.itcast.test;

import java.util.List;

import org.elasticsearch.client.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.domain.Article;
import cn.itcast.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ElasticsearchTest {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private Client client;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Test
	public void createIndex() {
		elasticsearchTemplate.createIndex(Article.class);
		elasticsearchTemplate.putMapping(Article.class);
	}
	
	//保存
	@Test
	public void save() {
		Article article = new Article();
		article.setId(001);
		article.setTitle("国王与乞丐");
		article.setContent("抱紧你的我比国王富有，失去你的我比乞丐落魄!");
		
		articleService.save(article);
	}
	
	//删除
	@Test
	public void dalete() {
		Article article = new Article();
		article.setId(001);
		
		articleService.delete(article);
	}
	
	//查询
	@Test
	public void findOne() {
		System.out.println(articleService.findOne(001));
	}
	
	//批量插入
	@Test
	public void saveBatch() {
		Article article = new Article();
		
		for (int i = 0; i < 100; i++) {
			article.setId(i);
			article.setTitle(i+"国王与乞丐");
			article.setContent(i+"抱紧你的我比国王富有，失去你的我比乞丐落魄!");
			
			articleService.save(article);
		}
	}
	
	
	//分页查询,也可以查询全部
	@Test
	public void pageQuery() {
		Pageable pageable = new PageRequest(0, 10);
		Page<Article> pagedata = articleService.findAll(pageable);
		for (Article article : pagedata.getContent()) {
			System.out.println(article);
		}
	}
	
	//条件查询
	@Test
	public void conditionQuery() {
		/*List<Article> list = articleService.findByTitle("国王");
		for (Article article : list) {
			System.out.println(article);
		}*/
		
		//分页条件查询
		Pageable pageable = new PageRequest(0, 10);
		Page<Article> pagedata = articleService.findByTitle("乞丐",pageable);
		System.out.println("总记录数"+pagedata.getTotalElements());
		for (Article article : pagedata.getContent()) {
			System.out.println(article);
		}
	}

}
