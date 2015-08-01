package org.crawler.model.repository;

import java.util.List;

import org.crawler.model.entity.Page;
import org.springframework.data.repository.CrudRepository;

public interface PageRepository extends CrudRepository<Page, Long> {

    List<Page> findByWebsite(String website);
}
