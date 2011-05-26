package com.trycatchsoft.gwt.requestfactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.web.bindery.requestfactory.shared.ServiceLocator;

/**
 * This is Guice-injected ServiceLocator.  Use this class to provide dependency-injected
 * instances of your RequestFactory services. When defining a new Request interface,
 * set this in the locator attribute of the @Service annotation.
 * 
 * @author Etienne Pelletier
 *
 */
public class InjectingServiceLocator implements ServiceLocator {

  private final Injector injector;
  
  @Inject
  protected InjectingServiceLocator(final Injector injector) {
    super();
    this.injector = injector;
  }

  @Override
  public Object getInstance(Class<?> clazz) {
    return injector.getInstance(clazz);
  }

}
