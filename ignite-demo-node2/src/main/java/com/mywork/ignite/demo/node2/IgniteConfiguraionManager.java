package com.mywork.ignite.demo.node2;

import java.util.Arrays;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.BinaryConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.ConnectorConfiguration;
import org.apache.ignite.configuration.DataRegionConfiguration;
import org.apache.ignite.configuration.DataStorageConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfiguraionManager {

    @Value("${enableFilePersistence}")
    private boolean enableFilePersistence;
    @Value("${igniteConnectorPort}")
    private int igniteConnectorPort;
    @Value("${igniteServerPortRange}")
    private String igniteServerPortRange;
    @Value("${ignitePersistenceFilePath}")
    private String ignitePersistenceFilePath;
	private static final String DATA_CONFIG_NAME = "MyDataRegionConfiguration";

	@Bean
    IgniteConfiguration igniteConfiguration() {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();       
		igniteConfiguration.setWorkDirectory(ignitePersistenceFilePath);
        //igniteConfiguration.setClientMode(false);
        // durable file memory persistence
        if(enableFilePersistence){

	        DataStorageConfiguration dataStorageConfiguration = new DataStorageConfiguration();
	        dataStorageConfiguration.setStoragePath(ignitePersistenceFilePath + "/store");
	        dataStorageConfiguration.setWalArchivePath(ignitePersistenceFilePath + "/walArchive");
	        dataStorageConfiguration.setWalPath(ignitePersistenceFilePath + "/walStore");
	        dataStorageConfiguration.setPageSize(4 * 1024);
	        DataRegionConfiguration dataRegionConfiguration = new DataRegionConfiguration();
	        dataRegionConfiguration.setName(DATA_CONFIG_NAME);
	        dataRegionConfiguration.setInitialSize(100 * 1000 * 1000);
	        dataRegionConfiguration.setMaxSize(200 * 1000 * 1000);
	        dataRegionConfiguration.setPersistenceEnabled(true);
	        dataStorageConfiguration.setDataRegionConfigurations(dataRegionConfiguration);
	        igniteConfiguration.setDataStorageConfiguration(dataStorageConfiguration);
	        igniteConfiguration.setConsistentId("node1");
        }
        // connector configuration
        ConnectorConfiguration connectorConfiguration=new ConnectorConfiguration();
        connectorConfiguration.setPort(igniteConnectorPort);
        // common ignite configuration
        igniteConfiguration.setMetricsLogFrequency(0);
        igniteConfiguration.setQueryThreadPoolSize(2);
        igniteConfiguration.setDataStreamerThreadPoolSize(1);
        igniteConfiguration.setManagementThreadPoolSize(2);
        igniteConfiguration.setPublicThreadPoolSize(2);
        igniteConfiguration.setSystemThreadPoolSize(2);
        igniteConfiguration.setRebalanceThreadPoolSize(1);
        igniteConfiguration.setAsyncCallbackPoolSize(2);
        igniteConfiguration.setPeerClassLoadingEnabled(false);
        igniteConfiguration.setIgniteInstanceName("node1");
        BinaryConfiguration binaryConfiguration = new BinaryConfiguration();
        binaryConfiguration.setCompactFooter(false);
        igniteConfiguration.setBinaryConfiguration(binaryConfiguration);
        // cluster tcp configuration
        TcpDiscoverySpi tcpDiscoverySpi=new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder tcpDiscoveryVmIpFinder=new TcpDiscoveryVmIpFinder();
        // need to be changed when it come to real cluster
        tcpDiscoveryVmIpFinder.setAddresses(Arrays.asList("127.0.0.1:47500..47509"));
        tcpDiscoverySpi.setIpFinder(tcpDiscoveryVmIpFinder);
        igniteConfiguration.setDiscoverySpi(new TcpDiscoverySpi());
        // cache configuration
        CacheConfiguration personCache=new CacheConfiguration();
        personCache.setCopyOnRead(false);
        // as we have one node for now
		personCache.setBackups(1);
        personCache.setAtomicityMode(CacheAtomicityMode.ATOMIC);
		personCache.setName("PersonCache");
		personCache.setDataRegionName(DATA_CONFIG_NAME);
		personCache.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_ASYNC);
        personCache.setIndexedTypes(Integer.class,Person.class);
      
        igniteConfiguration.setCacheConfiguration(personCache);
        return igniteConfiguration;
    }

    @Bean
    Ignite ignite(IgniteConfiguration igniteConfiguration) throws IgniteException {
	    final Ignite ignite = Ignition.start(igniteConfiguration);
	    // Activate the cluster. Automatic topology initialization occurs
	    // only if you manually activate the cluster for the very first time.
	    ignite.cluster().active(true);
	    /*// Get all server nodes that are already up and running.
	    Collection<ClusterNode> nodes = ignite.cluster().forServers().nodes();
		// Set the baseline topology that is represented by these nodes.
	    ignite.cluster().setBaselineTopology(nodes);*/
	    return ignite;
    }

}