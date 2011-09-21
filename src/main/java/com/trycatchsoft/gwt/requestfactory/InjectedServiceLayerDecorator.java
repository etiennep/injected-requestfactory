/*
 * Copyright 2011 Etienne Pelletier
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.trycatchsoft.gwt.requestfactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.google.web.bindery.requestfactory.shared.RequestContext;
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
  public Object createServiceInstance(Class<? extends RequestContext> requestContext) {
    Class<? extends ServiceLocator> serviceLocatorClass;    
    if ((serviceLocatorClass = getTop().resolveServiceLocator(requestContext)) != null) {
      return injector.getInstance(serviceLocatorClass).getInstance(requestContext.getDeclaringClass());
    } else {
      return null;
    }
  }
  
  
}
