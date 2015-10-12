package com.kassandra.repository;

import com.google.inject.Inject;
import com.kassandra.repository.config.RepositoryConfiguration;
import com.kassandra.repository.config.RepositoryConfigurationProvider;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by aAlex on 10/9/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class RepoConfigurationTest {

    @Test
    public void testConfig() {
        RepositoryConfigurationProvider repositoryConfigurationProvider = new RepositoryConfigurationProvider();
        RepositoryConfiguration config = repositoryConfigurationProvider.getConfig();
        assertEquals("localhost", config.getHost());
        assertEquals(30000, config.getPort());
    }

}
