package com.kassandra.repository;

import static junit.framework.TestCase.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.kassandra.repository.config.RepositoryConfiguration;
import com.kassandra.repository.config.RepositoryConfigurationProvider;

/**
 * Created by aAlex on 10/9/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class RepoConfigurationTest {

    @Test
    public void testConfig() {
        RepositoryConfigurationProvider repositoryConfigurationProvider = new
                RepositoryConfigurationProvider();
        RepositoryConfiguration config = repositoryConfigurationProvider.getConfig();
        assertEquals("localhost", config.getHost());
        assertEquals(30000, config.getPort());
    }

}
