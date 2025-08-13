package com.llback.api.app.article.dto.req;

import com.llback.frame.dto.Query;
import lombok.Data;

@Data
public class ArticleQuery implements Query {
    private String test;
}
