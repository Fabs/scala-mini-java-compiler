package language.line

abstract class Exp()
case class IdExp(id: String) extends Exp
case class NumExp(num: Int) extends Exp
case class OpExp(l: Exp, o: BinaryOperator, r: Exp) extends Exp
case class EseqExp(s: Stm, e: Exp) extends Exp //This could maybe be joined with CompoundStm
case class ExpList(e: Exp, es: ExpList) extends Exp
case class LastExpList(e: Exp)

abstract class Stm() extends Exp()
case class CompoundStm(cur: Stm, next: Stm) extends Stm //as Exp, evaluates to next
case class AssignStm(id: String, exp: Exp) extends Stm //as Exp, evaluates to assign value
case class PrintStm(par: ExpList) extends Stm //as Exp evaluates as the last argument
