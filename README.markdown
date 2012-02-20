# Guiced-up GWT RequestFactory

It would appear that a number of people have been looking for a good way of introducing dependency injection (DI) into the [GWT RequestFactory API server-side components](http://code.google.com/webtoolkit/doc/latest/DevGuideRequestFactory.html). This is fairly easy to do _IF_ you know the recipe. It's not obvious at first glance, but thanks again to my friend Thomas Broyer who has posted some good, yet incomplete, instructions on how to do this.
I will go not only one step further by providing you the complete picture, but I will also give you the code that you can include in your own project. For free. Because I'm a nice guy.

## Before You Begin

This is not a RequestFactory or [Guice](http://code.google.com/p/google-guice) tutorial. Please make sure you are familiar with Guice, GWT and the RequestFactory framework before you begin.

Please also note that this is working only with the GWT 2.3 release since they have deprecated the original RequestFactory API classes.

## Step 1. Get the Injected RequestFactory Code

Go ahead and grab it from github.  The only condition is that you use Maven to build the jar file. I'm done with Ant, and have not yet posted the artifacts to a Maven repository.

You can run the mvn install command to build, package and install in your local repository.

## Step 2. Configure Your Servlet Module

I've already created a Guice module which will provide you everything you need to use the enhanced framework as is without any additional configuration.  Just add the following line to your `ServletModule` class:

    install(new InjectedRequestFactoryModule());

Next, add the following line to map the `/gwtRequest` URI to the new injected request factory servlet. Remove the old one first if you had one already mapping to `RequestFactoryServlet`.

    serve("/gwtRequest").with(InjectedRequestFactoryServlet.class);

That's all the configuration you need.  You will, of course, have to define all the bindings and everything else needed by your service classes somewhere in a Guice module.

## Step 3. Write Services and/or Locators

Next, you can go ahead and write all the @Inject'ed services and locators you need to make your application work. I am in favor of not putting any service or locator code at all in my model to keep the RequestFactory API separate from the persistence API. There is no telling where I'll be reusing these model classes. The last thing I want is a GWT dependency in my data access layer!

There is nothing specific you need to do here. Services don't need to implement or extend anything. Just add the `@Inject` annotations where ever you need them.

Locators are the same. You don't need to do anything special to them. Just extend `com.google.web.bindery.requestfactory.shared.Locator`.

## Step 4. Write a Proxy

Once again, nothing special here.  Bellow is an example from my own code:

    @ProxyFor(value = Station.class, locator = StationLocator.class)
    
    public interface StationProxy extends EntityProxy {

The locator attribute of the `@ProxyFor` annotations points to a Locator I had created in step 3.

## Step 5. Write the Request Interface

This is one of the rare places where you will have to reference the injected RequestFactory API.  In the locator attribute of the `@Service` annotation, put `InjectingServiceLocator.class`. Like so:

    @Service(locator=InjectingServiceLocator.class, value=StationService.class)
    
    public interface StationRequest extends RequestContext {

You should not need to extend this `InjectingServiceLocator` class, like, ever.

## Step 6. Profit!

That's it. That's all you need to do to have a fully dependency-inject RequestFactory set of server-side classes. I've used this code in two projects of my own already and have had no problems.

## Some Gotchas

For some reason, the instances of service classes are singletons. I think they might be cached in the RequestFactory servlet. So beware of thread safety issues! You'll have to inject providers for non-singleton resources (like DAOs for example).

Make sure that your _EntityManager_, or  _PersistenceManager_, or whatever kind of data access manager you use is `@RequestScoped`.  I ran across some issues due to the fact that multiple locators may be invoked as part of a single request, which causes data inconsistencies if you are not properly scoping the EntityManager/PersistenceManager.

