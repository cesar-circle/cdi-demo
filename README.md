# Lightning Talk: Beyond basic dependency injection

## Problem

Standing up a queue worker in our services is a repetitive, multi-step process:
1. Instantiate objects required by the worker
2. Instantiate the worker
3. Create a queue
4. Create a message poller on the queue and link it to the worker 

_Regular_ dependency injection definitively helps with #1 & #2 but we still have to 
create the queue, and the poller. 

This can be achieved leveraging CDI's portable extensions, and the lifecycle events 
it includes.

## Goal

Our goal is to be able to automate the instantiation of queue, pollers and the linking
of pollers to workers.

### Proposed Solution

```java
import com.circle.cesar.cdi.extension.QueueName;
import jakarta.enterprise.context.ApplicationScoped;

@QueueName("my-queue")
@ApplicationScoped
public class MyWorker implements Worker {
    public void process(Message msg) {
        // message processing
    }
}

@ApplicationScoped
public class MyService {
    @QueueName("my-queue")
    private MessagePoller poller;
    
    public void start() {
        poller.start();
    }
}
```


## Container Lifecycle Events

 - Before Bean Discovery
 - After Type Discovery
   - ProcessAnnotatedType
   - ProcessInjectionPoint
   - ProcessInjectionTarget
   - ProcessBeanAttributes
   - ProcessBean
   - ProcessProducer
   - ProcessObserverMethod
 - After Bean Discover
 - After Deployment Validation

 - Before Shutdown

## Resources

 - CDI Spec: [Portable Extensions -> Container Lifecycle Events](https://jakarta.ee/specifications/cdi/4.0/jakarta-cdi-spec-4.0#init_events)
