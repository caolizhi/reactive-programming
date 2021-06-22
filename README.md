### Operator

#### [flatmap](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#flatMap-java.util.function.Function-)

**no operations occur until the stream is subscribed**, `flatMap` eagerly subscribes. The operator doesn't
wait for the publisher to finish before moving on to the next stream, meaning the subscription is non-blocking.

Since the pipeline handles all the derived streams simultaneously, their items may come in at any moment. As a
result, the original order is lost. If the order of items is important, consider using the [flatMapSequential](https://projectreactor.io/docs/core/release/api/reactor/core/publisher/Flux.html#flatMapSequential-java.util.function.Function-) operator
instead.

#### difference between map and flatMap

- 1.One-to-One vs. One-to-Many <br/>
  The `map` operator applies a **one-to-one** transformation to stream elements, while `flatMap` does **one-to-many**.
  Difference between method signature:

    - _<V> Flux<V> map(Function<? super T, ? extends V> mapper)_ – the mapper converts a single value of type T to a single value of type V
    - _Flux<R> flatMap(Function<? super T, ? extends Publisher<? extends R>> mapper)_ – the mapper converts a single value of type T to a Publisher of elements of type R

- 2.Synchronous vs. Asynchronous <br/>
  Here are two extracts from API specification:

    - **_map_**: Transform the items emitted by this Flux by applying asynchronous function to each item
    - **_flatMap_**: Transform the elements emitted by this Flux asynchronously into Publishers

  It's easy to see **map is a synchronous operator** – it's simply a method that converts one value to another. This method executes in the same thread as the caller.

  The other statement – **flatMap is asynchronous** – is not that clear. In fact, the transformation of elements into Publishers can be either synchronous or asynchronous.
