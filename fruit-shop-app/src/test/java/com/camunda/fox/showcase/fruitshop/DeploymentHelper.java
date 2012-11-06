package com.camunda.fox.showcase.fruitshop;

import java.util.Collection;

import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;


public class DeploymentHelper {
    
    private static JavaArchive CACHED_CLIENT_ASSET;

    public static JavaArchive getFoxPlatformClient() {
      if(CACHED_CLIENT_ASSET != null) {
        return CACHED_CLIENT_ASSET;
      } else {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).goOffline().loadMetadataFromPom("pom.xml");
        Collection<JavaArchive> resolvedArchives = resolver.artifact("com.camunda.fox.platform:fox-platform-client").resolveAs(JavaArchive.class);
        
        if(resolvedArchives.size() != 1) {
          throw new RuntimeException("could not resolve com.camunda.fox.platform:fox-platform-client");
        } else {    
          CACHED_CLIENT_ASSET = resolvedArchives.iterator().next();
          return CACHED_CLIENT_ASSET;
        }
      }
      
    }
    
}
