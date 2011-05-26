package com.trycatchsoft.gwt.requestfactory;

import java.lang.reflect.Method;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * This is a Guice-injected service layer decorator. This class is part
 * of the glue you need to enable dependency injection on your ServiceLocators
 * and Locators. 
 * 
 * @author Etienne Pelletier
 *
 */
public class InjectedServiceLayerDecorator extends ServiceLayerDecorator {

  private final Injector injector;

  @Inject
  protected InjectedServiceLayerDecorator(final Injector injector) {
    super();
    this.injector = injector;
  }

  @Override
  public <T extends Locator<?, ?>> T createLocator(Class<T> clazz) {
    return injector.getInstance(clazz);
  }

  @Override
  public Object createServiceInstance(Method contextMethod, Method domainMethod) {
    Class<? extends ServiceLocator> serviceLocatorClass;
    if ((serviceLocatorClass = getTop().resolveServiceLocator(contextMethod, domainMethod)) != null) {
      return injector.getInstance(serviceLocatorClass).getInstance(domainMethod.getDeclaringClass());
    } else {
      return null;
    }
  }
  
  
  
  
}
