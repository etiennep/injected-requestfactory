package com.trycatchsoft.gwt.requestfactory;

import com.google.inject.Injector;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

/**
 * InjectingConstraintValidationFactory is a custom JSR 303 ConstraintValidatorFactory that is using Guice's {@link Injector}
 * to create {@link ConstraintValidator}'s and to inject dependencies that are associated by each of the validators.
 *
 * @author Miroslav Genov (mgenov@gmail.com)
 * @see @see com.trycatchsoft.gwt.requestfactory.InjectingRequestFactoryModule#getValidatorFactory(com.google.inject.Injector)
 */
class InjectingConstraintValidationFactory implements ConstraintValidatorFactory {
  /**
   * The entry point injector that is used to inject different kind of dependencies.
   */
  private final Injector injector;
  /**
   * Creates a new InjectingConstraintValidationFactory by using the injector.
   *
   * @param injector the injector that will be used for the injection.
   */
  public InjectingConstraintValidationFactory(Injector injector) {
    this.injector = injector;
  }
  public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> tClass) {

    return injector.getInstance(tClass);
  }
}