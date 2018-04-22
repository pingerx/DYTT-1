package com.bzh.dytt.util;

import com.bzh.dytt.TestUtils;
import com.bzh.dytt.data.entity.CategoryMap;
import com.bzh.dytt.data.entity.MovieCategory;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

public class HomePageParserTest {

    private HomePageParser mHomePageParser;

    @Before
    public void setUp() {
        mHomePageParser = new HomePageParser();
    }

    @Test
    public void parseLatestMovieCategoryMap() throws IOException {
        String homeHtml = TestUtils.getResource(getClass(), "home.html");
        List<CategoryMap> result = mHomePageParser.parse(homeHtml, MovieCategory.NEW_MOVIE);
    }
}