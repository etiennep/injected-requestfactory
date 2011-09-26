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

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.web.bindery.requestfactory.server.ServiceLayerDecorator;
import com.google.web.bindery.requestfactory.shared.Locator;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Service;
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

  /**
   * JSR 303 validator used to validate requested entities.
   */

  private final Validator validator;
  private final Injector injector;

  @Inject
  protected InjectedServiceLayerDecorator(final Injector injector, final Validator validator) {
    super();
    this.injector = injector;
    this.validator = validator;
  }

  @Override
  public <T extends Locator<?, ?>> T createLocator(Class<T> clazz) {
    return injector.getInstance(clazz);
  }



  @Override
  public Object createServiceInstance(Class<? extends RequestContext> requestContext) {
    Class<? extends ServiceLocator> serviceLocatorClass;    
    if ((serviceLocatorClass = getTop().resolveServiceLocator(requestContext)) != null) {      
      return injector.getInstance(serviceLocatorClass).getInstance(requestContext.getAnnotation(Service.class).value());
    } else {
      return null;
    }
  }

  /**
   * Invokes JSR 303 validator on a given domain object.
   *
   * @param domainObject the domain object to be validated
   * @param <T>          the type of the entity being validated
   * @return the violations associated with the domain object
   */
  @Override
  public <T> Set<ConstraintViolation<T>> validate(T domainObject) {
    return validator.validate(domainObject);
  }


}
