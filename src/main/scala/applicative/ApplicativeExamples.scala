package applicative
import cats.{Applicative, Semigroup, Semigroupal}



//Applicative is a Functor capable of lifting any A to F[A] (e.g pure Method)
//Notable example of an applicative that IS NOT AN monad, The Validated[E,A] type

object ApplicativeExamples {
  //TODO: ThoughtExperiment
  def productWithApplicatives[F[_],A,B](fa:F[A],fb:F[B])(implicit applicativeF:Applicative[F]):F[Tuple2[A,B]] = {
    applicativeF.product(fa,fb)
  }
}
