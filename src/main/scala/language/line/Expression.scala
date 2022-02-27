package language.line

abstract class Exp()
case class IdExp(id: String) extends Exp
case class NumExp(num: Int) extends Exp
case class OpExp(l: Exp, o: BinaryOperator, r: Exp) extends Exp
case class EseqExp(s: Stm, e: Exp) extends Exp //This could maybe be joined with CompoundStm

abstract class Stm() extends Exp()
case class CompoundStm(cur: Stm, next: Stm) extends Stm //as Exp, evaluates to next
case class AssignStm(id: String, exp: Exp) extends Stm //as Exp, evaluates to assign value
case class PrintStm(par: ExpList) extends Stm //as Exp evaluates as the last argument

abstract class ExpList() extends Exp()
case class PairExpList(e: Exp, es: ExpList) extends ExpList //as Exp, evaluates to last
case class LastExpList(e: Exp) extends ExpList //as Exp, evaluates to exp, and it will be PairExpList result
