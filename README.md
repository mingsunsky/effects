## Scala Future and alternatives



## Future
A Future is a placeholder object for a value that may not yet exist.


## Good
* Non-blocking, concurrent 
* Is a Monad and can be mapped and flatMapped


## Bad
* Future is eager and memorized
* Not referential transparent


## Example
Result of 
```
val future1 = Future(println("future 1")
future1
future1
```
is not same as  
```
Future(println("future 1"))
Future(println("future 1"))
```


Future is bad for working with side effects



## Alternative Effect Types
* Cats Effect IO
* Monix Task
* ZIO


Monix, Cats IO, ZIO are not eager, not memorized, if run it twice it will run twice



## Monix
Monix is a high-performance Scala library for asynchronous programs


Monix Eval Task
```
val task = Task( 1 + 1 )
```


Example
```
val task1 = Task( println( "task 1" )
task1
task1
```
is same as  
```
Task( println( "task 1" ))
Task( println( "task 1" ))
```



### Cats Effect IO
A data type for encoding side effects as pure values


Creation
```
val ioa = IO( println( "cats IO 1" ))
```

Error Handling
```
val iob: IO[Unit] = IO.raiseError(new Exception("boom"))

iob.handleErrorWith { error => ??? }
```


Example
```
val io1 = IO( println( "IO 1" )
io1
io1
```
is same as  
```
IO( println( "IO 1" ))
IO( println( "IO 1" ))
```



Can we change Effect Types in code?



### Cats Effect Sync
* Sync is a typeclass
* Sync is a Monad that can suspend the execution of side effects in the F[_] context.


Sync
```
trait Sync[F[_]] extends Bracket[F, Throwable] with Defer[F] {
  def suspend[A](thunk: => F[A]): F[A]
  def delay[A](thunk: => A): F[A] = suspend(pure(thunk))
}
```

Example of use:
```
import cats.effect.{IO, Sync}
val ioa = Sync[IO].delay(println("Hello world!"))
```


So basically using Sync[IO].delay is equivalent to using IO.apply.

