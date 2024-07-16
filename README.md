# Demo

Repository created to reproduce @Query annotation **with sorting** not working in a native image.

### Quick start
You can use the image generated via the Github Actions pipeline via `docker run --network=host vr4j/demo`.

### Setup
1. Start local mongo database on port 27017 (eg. `docker run -p 27017:27017 mongo:latest`)

### Build
1. Checkout repository
2. `./gradlew bootBuildImage`
3. `docker run --network=host demo:0.0.1-SNAPSHOT`

### Observe
```
Application run failed
org.springframework.aop.framework.AopConfigException: Unexpected AOP exception
        at org.springframework.aop.framework.CglibAopProxy.buildProxy(CglibAopProxy.java:236)
        at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:163)
        at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        at org.springframework.data.mongodb.repository.query.QueryUtils.decorateSort(QueryUtils.java:69)
        at org.springframework.data.mongodb.repository.query.AbstractMongoQuery.applyAnnotatedDefaultSortIfPresent(AbstractMongoQuery.java:230)
        at org.springframework.data.mongodb.repository.query.AbstractMongoQuery.doExecute(AbstractMongoQuery.java:138)
        at org.springframework.data.mongodb.repository.query.AbstractMongoQuery.execute(AbstractMongoQuery.java:119)
        at org.springframework.data.repository.core.support.RepositoryMethodInvoker.doInvoke(RepositoryMethodInvoker.java:170)
        at org.springframework.data.repository.core.support.RepositoryMethodInvoker.invoke(RepositoryMethodInvoker.java:158)
        at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.doInvoke(QueryExecutorMethodInterceptor.java:164)
        at org.springframework.data.repository.core.support.QueryExecutorMethodInterceptor.invoke(QueryExecutorMethodInterceptor.java:143)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
        at org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor.invoke(DefaultMethodInvokingMethodInterceptor.java:70)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
        at org.springframework.data.mongodb.repository.support.CrudMethodMetadataPostProcessor$CrudMethodMetadataPopulatingMethodInterceptor.invoke(CrudMethodMetadataPostProcessor.java:129)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
        at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:97)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:184)
        at org.springframework.aop.framework.JdkDynamicAopProxy.invoke(JdkDynamicAopProxy.java:223)
        at jdk.proxy4/jdk.proxy4.$Proxy46.getFirstPersonByForename(Unknown Source)
        at com.example.demo.DemoApplication.run(DemoApplication.java:25)
        at org.springframework.boot.SpringApplication.lambda$callRunner$5(SpringApplication.java:790)
        at org.springframework.util.function.ThrowingConsumer$1.acceptWithException(ThrowingConsumer.java:83)
        at org.springframework.util.function.ThrowingConsumer.accept(ThrowingConsumer.java:60)
        at org.springframework.util.function.ThrowingConsumer$1.accept(ThrowingConsumer.java:88)
        at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:798)
        at org.springframework.boot.SpringApplication.callRunner(SpringApplication.java:789)
        at org.springframework.boot.SpringApplication.lambda$callRunners$3(SpringApplication.java:774)
        at java.base@21.0.3/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
        at java.base@21.0.3/java.util.stream.SortedOps$SizedRefSortingSink.end(SortedOps.java:357)
        at java.base@21.0.3/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:510)
        at java.base@21.0.3/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        at java.base@21.0.3/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
        at java.base@21.0.3/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
        at java.base@21.0.3/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        at java.base@21.0.3/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:596)
        at org.springframework.boot.SpringApplication.callRunners(SpringApplication.java:774)
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:342)
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1363)
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1352)
        at com.example.demo.DemoApplication.main(DemoApplication.java:17)
        at java.base@21.0.3/java.lang.invoke.LambdaForm$DMH/sa346b79c.invokeStaticInit(LambdaForm$DMH)
Caused by: java.lang.UnsupportedOperationException: CGLIB runtime enhancement not supported on native image. Make sure to include a pre-generated class on the classpath instead: org.springframework.data.mongodb.core.query.BasicQuery$$SpringCGLIB$$0
        at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:575)
        at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.lambda$new$1(AbstractClassGenerator.java:107)
        at org.springframework.cglib.core.internal.LoadingCache.lambda$createEntry$1(LoadingCache.java:52)
        at java.base@21.0.3/java.util.concurrent.FutureTask.run(FutureTask.java:317)
        at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:57)
        at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:130)
        at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:317)
        at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:562)
        at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:407)
        at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:62)
        at org.springframework.aop.framework.CglibAopProxy.buildProxy(CglibAopProxy.java:221)
        ... 41 more
```
