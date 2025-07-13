package com.llback.api.dto.article.req;

import com.llback.frame.dto.Query;
import lombok.Data;

@Data
public class ArticleQuery implements Query {
    private String test;
}
